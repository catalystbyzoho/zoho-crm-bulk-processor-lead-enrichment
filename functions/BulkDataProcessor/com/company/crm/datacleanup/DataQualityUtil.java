package com.company.crm.datacleanup;

public class DataQualityUtil {

    private DataQualityUtil() {}

    public static String determineStatus(
            Boolean phoneValid,
            Boolean countryValid) {

        if (Boolean.TRUE.equals(phoneValid)
                && Boolean.TRUE.equals(countryValid)) {
            return "Clean";
        }

        if (Boolean.FALSE.equals(phoneValid)
                && Boolean.FALSE.equals(countryValid)) {
            return "Critical";
        }

        return "Needs Review";
    }
}
