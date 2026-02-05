package com.company.crm.datacleanup;

public class LeadProcessingResult {

    private String normalizedMobile;
    private String countryCode;
    private Integer leadScore;
    private String segment;
    private String priority;
    private String dataQualityStatus;

    public String getNormalizedMobile() {
        return normalizedMobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Integer getLeadScore() {
        return leadScore;
    }

    public String getSegment() {
        return segment;
    }

    public String getPriority() {
        return priority;
    }

    public String getDataQualityStatus() {
        return dataQualityStatus;
    }

    public void setNormalizedMobile(String normalizedMobile) {
        this.normalizedMobile = normalizedMobile;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setLeadScore(Integer leadScore) {
        this.leadScore = leadScore;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDataQualityStatus(String dataQualityStatus) {
        this.dataQualityStatus = dataQualityStatus;
    }
}