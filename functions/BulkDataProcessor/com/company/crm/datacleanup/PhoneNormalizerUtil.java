package com.company.crm.datacleanup;

import java.util.Optional;
import java.util.regex.Pattern;

public class PhoneNormalizerUtil {

    private static final Pattern NON_DIGIT = Pattern.compile("[^0-9]");

    public static Optional<String> normalizeToE164(String rawPhone, String countryCode) {

        if (rawPhone == null || rawPhone.trim().isEmpty()) {
            return Optional.empty();
        }

        String digits = NON_DIGIT.matcher(rawPhone).replaceAll("");

        // Already in E.164
        if (rawPhone.startsWith("+") && digits.length() >= 10) {
            return Optional.of("+" + digits);
        }

        switch (countryCode) {
            case "IN":
                return normalizeIndia(digits);
            case "US":
                return normalizeUS(digits);
            case "UK":
                return normalizeUK(digits);
            default:
                return Optional.empty();
        }
    }

    private static Optional<String> normalizeIndia(String digits) {
        if (digits.length() == 10) {
            return Optional.of("+91" + digits);
        }
        if (digits.startsWith("91") && digits.length() == 12) {
            return Optional.of("+" + digits);
        }
        return Optional.empty();
    }

    private static Optional<String> normalizeUS(String digits) {
        if (digits.length() == 10) {
            return Optional.of("+1" + digits);
        }
        if (digits.startsWith("1") && digits.length() == 11) {
            return Optional.of("+" + digits);
        }
        return Optional.empty();
    }

    private static Optional<String> normalizeUK(String digits) {
        if (digits.startsWith("44") && digits.length() == 12) {
            return Optional.of("+" + digits);
        }
        if (digits.length() == 10) {
            return Optional.of("+44" + digits);
        }
        return Optional.empty();
    }
}
