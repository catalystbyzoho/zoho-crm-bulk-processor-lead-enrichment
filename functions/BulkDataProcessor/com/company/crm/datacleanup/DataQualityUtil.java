package com.company.crm.datacleanup;

public class DataQualityUtil {

    public static String determineStatus(
            boolean phoneValid,
            boolean countryValid) {

        if (phoneValid && countryValid) {
            return "Clean";
        }
        if (!phoneValid && !countryValid) {
            return "Critical";
        }
        return "Needs Review";
    }
}
