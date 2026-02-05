# 🚀 Zoho CRM Bulk Lead Data Standardization & Intelligence Processor

## Overview

This repository contains a Java-based bulk record processor designed to fix post-migration data quality issues and introduce lead intelligence in Zoho CRM at scale.

The solution is intended for Zoho CRM **Bulk Read → Process → Bulk Write** flows and is safe to execute on large historical datasets (10K to millions of records).

---

## Problem Statement

After migrating from a legacy CRM, Zoho CRM data commonly suffers from:

- Inconsistent mobile number formats
- Missing or incorrect country values
- No historical lead scoring
- No lead prioritization
- Low trust in CRM data from sales teams

CRM workflows are record-level and unsuitable for historical cleanup.  
This solution provides a dataset-level processing approach.

---

## Solution Summary

The processor performs the following actions on each CRM record:

1. Standardizes country values to ISO codes
2. Normalizes mobile numbers to E.164 format
3. Calculates lead score based on business signals
4. Assigns lead segment and sales priority
5. Flags data quality status
6. Writes changes back to the same CRM record

All updates happen **in-place on ZCRMRecord objects**.

---

## Architecture Flow

Zoho CRM (Bulk Read)
        |
        v
ZCRMRecordsProcessorImpl
        |
        v
Stateless Java Utilities
        |
        v
Updated ZCRMRecords
        |
        v
Zoho CRM (Bulk Write)

---

## Project Structure

src/
 ├── com.processor.record
 │   └── ZCRMRecordsProcessorImpl.java
 │
 ├── com.company.crm.datacleanup
 │   ├── PhoneNormalizerUtil.java
 │   ├── CountryStandardizerUtil.java
 │   ├── LeadScoringUtil.java
 │   ├── DataQualityUtil.java
 │   └── LeadProcessingResult.java

---

## Input Record Fields

The processor expects the following fields in each record:

- Mobile
- Country
- Job_Title
- Company_Size
- Last_Activity_Days
- Web_Engagement_Score

These fields may contain null, empty, or inconsistent values.

---

## Processing Logic

### Country Standardization

India, IND, +91        → IN  
USA, US, United States → US  
UK, U.K, +44           → UK  

### Mobile Normalization

9876543210        → +919876543210  
(415) 555-2671    → +14155552671  
0091-9876543210   → +919876543210  

Invalid or ambiguous numbers are not modified.

---

## Lead Scoring Rules

- Decision maker title (CEO, CTO, Director, VP): +25
- Company size ≥ 200: +20
- Recent activity ≤ 7 days: +15
- High engagement score ≥ 70: +10

---

## Lead Segmentation

Score ≥ 70  → Hot (High Priority)  
Score 40–69 → Warm (Medium Priority)  
Score < 40  → Cold (Low Priority)

---

## Data Quality Status

Clean        → Valid mobile and country  
Needs Review → One valid field  
Critical     → Both invalid or missing  

---

## Output Fields Written Back

- Mobile (E.164 formatted)
- Country_Code
- Lead_Score
- Lead_Segment
- Sales_Priority
- Data_Quality_Status

---

## How to Use

1. Import defective leads into Zoho CRM
2. Perform Bulk Read
3. Pass records to ZCRMRecordsProcessorImpl
4. Execute processing logic
5. Perform Bulk Write with updated records

---

## Design Principles

- Stateless utilities
- Deterministic processing
- Re-runnable logic
- Bulk-safe updates
- No CRM UI or workflow impact

---

## Out of Scope

- Lead deduplication
- Email validation
- Guessing missing countries
- Fixing totally invalid phone numbers

---

## License

MIT / Internal Use (update as required)
