package com.company.crm.datacleanup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryStandardizerUtil {

    private static final Map<String, String> COUNTRY_MAP = new HashMap<>();

    static {
        // India
        COUNTRY_MAP.put("india", "IN");
        COUNTRY_MAP.put("ind", "IN");
        COUNTRY_MAP.put("+91", "IN");

        // USA
        COUNTRY_MAP.put("usa", "US");
        COUNTRY_MAP.put("us", "US");
        COUNTRY_MAP.put("united states", "US");
        COUNTRY_MAP.put("+1", "US");

        // UK
        COUNTRY_MAP.put("uk", "UK");
        COUNTRY_MAP.put("u.k", "UK");
        COUNTRY_MAP.put("united kingdom", "UK");
        COUNTRY_MAP.put("+44", "UK");
    }

    public static String standardize(String rawCountry) {

        if (rawCountry == null || rawCountry.trim().isEmpty()) {
            return null;
        }

        String key = rawCountry.trim().toLowerCase(Locale.ENGLISH);
        return COUNTRY_MAP.getOrDefault(key, null);
    }
}
