package com.company.crm.datacleanup;

import java.util.regex.Pattern;

public class PhoneNormalizerUtil {

    private static final Pattern NON_DIGIT =
            Pattern.compile("[^0-9]");

    private PhoneNormalizerUtil() {}

    public static String normalizeToE164(String rawPhone, String countryCode) {

        if (rawPhone == null || rawPhone.trim().isEmpty()) {
            return null;
        }

        if (countryCode == null || countryCode.trim().isEmpty()) {
            return null;
        }

        String digits = NON_DIGIT
                .matcher(rawPhone)
                .replaceAll("");

        if (digits.isEmpty()) {
            return null;
        }

        switch (countryCode) {
            case "IN":
                return normalizeIndia(digits);
            case "US":
                return normalizeUS(digits);
            case "UK":
                return normalizeUK(digits);
            default:
                return null;
        }
    }

    private static String normalizeIndia(String digits) {
        if (digits.length() == 10) {
            return "+91" + digits;
        }
        if (digits.startsWith("91") && digits.length() == 12) {
            return "+" + digits;
        }
        return null;
    }

    private static String normalizeUS(String digits) {
        if (digits.length() == 10) {
            return "+1" + digits;
        }
        if (digits.startsWith("1") && digits.length() == 11) {
            return "+" + digits;
        }
        return null;
    }

    private static String normalizeUK(String digits) {
        if (digits.startsWith("44") && digits.length() == 12) {
            return "+" + digits;
        }
        if (digits.length() == 10) {
            return "+44" + digits;
        }
        return null;
    }
}
