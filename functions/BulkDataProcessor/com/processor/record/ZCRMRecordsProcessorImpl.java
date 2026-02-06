//$Id$
package com.processor.record;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.ZCRMRecordsProcessor;

// Utils
import com.company.crm.datacleanup.PhoneNormalizerUtil;
import com.company.crm.datacleanup.CountryStandardizerUtil;
import com.company.crm.datacleanup.LeadScoringUtil;
import com.company.crm.datacleanup.DataQualityUtil;

public class ZCRMRecordsProcessorImpl implements ZCRMRecordsProcessor {

	private static final Logger LOGGER = Logger.getLogger(ZCRMRecordsProcessorImpl.class.getName());

	@Override
	public List<ZCRMRecord> processRecords(List<ZCRMRecord> zcrmRecords) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		LOGGER.log(Level.INFO, "Processing {0} records", zcrmRecords.size());

		for (ZCRMRecord record : zcrmRecords) {

			Map<String, Object> data = record.data;

			/*
			 * ------------------------
			 * 1️⃣ Read Input Fields
			 * ------------------------
			 */
			String rawMobile = getString(data.get("Mobile"));
			String rawCountry = getString(data.get("Country"));
			String jobTitle = getString(data.get("Designation"));
			Integer companySize = getInteger(data.get("No_of_Employees"));
			Integer lastActivityDays = getInteger(data.get("Last_Activity_Days"));
			Integer webEngagementScore = getInteger(data.get("Web_Engagement_Score"));
			// Last_Name,Company,Mobile,Country,Designation,No_of_Employees,Web_Engagement_Score,Lead_Score_Value

			/*
			 * ------------------------
			 * 2️⃣ Country Standardization
			 * ------------------------
			 */
			String countryCode = CountryStandardizerUtil.standardize(rawCountry);

			/*
			 * ------------------------
			 * 3️⃣ Mobile Normalization
			 * ------------------------
			 */
			String normalizedMobile = PhoneNormalizerUtil.normalizeToE164(rawMobile, countryCode);
			boolean phoneValid = normalizedMobile != null;

			if (phoneValid) {
				data.put("Mobile", normalizedMobile);
			}
			/*
			 * ------------------------
			 * 4️⃣ Lead Scoring
			 * ------------------------
			 */
			int leadScore = LeadScoringUtil.calculateLeadScore(
					jobTitle,
					companySize,
					lastActivityDays,
					webEngagementScore);

			String leadSegment = LeadScoringUtil.deriveSegment(leadScore);
			String salesPriority = LeadScoringUtil.derivePriority(leadSegment);

			/*
			 * ------------------------
			 * 5️⃣ Data Quality Status
			 * ------------------------
			 */
			String dataQualityStatus = DataQualityUtil.determineStatus(
					phoneValid,
					countryCode != null);

			/*
			 * ------------------------
			 * 6️⃣ Write Back to Record
			 * ------------------------
			 */
			if (normalizedMobile != null) {
				data.put("Mobile", normalizedMobile);
			}

			if (countryCode != null) {
				data.put("Country_Code", countryCode);
			}

			data.put("Lead_Score_Value", leadScore);
			data.put("Lead_Segment", leadSegment);
			data.put("Sales_Priority", salesPriority);
			data.put("Data_Quality_Status", dataQualityStatus);
		}

		LOGGER.log(Level.FINE,
				"Processed records sample: {0}",
				objectMapper.writeValueAsString(zcrmRecords.get(0)));

		return zcrmRecords;
	}

	/*
	 * ------------------------
	 * Utility Converters
	 * ------------------------
	 */

	private String getString(Object value) {
		return value == null ? null : String.valueOf(value).trim();
	}

	private Integer getInteger(Object value) {
		try {
			if (value == null)
				return null;
			return Integer.valueOf(value.toString());
		} catch (Exception e) {
			return null;
		}
	}
}
