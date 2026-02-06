package com.company.crm.datacleanup;

public class LeadScoringUtil {

    private LeadScoringUtil() {}

    public static Integer calculateLeadScore(
            String jobTitle,
            Integer companySize,
            Integer lastActivityDays,
            Integer webEngagementScore) {

        int score = 0;

        if (jobTitle != null) {
            String title = jobTitle.toLowerCase();
            if (title.contains("ceo")
                    || title.contains("cto")
                    || title.contains("director")
                    || title.contains("vp")) {
                score += 25;
            }
        }

        if (companySize != null && companySize >= 200) {
            score += 20;
        }

        if (lastActivityDays != null && lastActivityDays <= 7) {
            score += 15;
        }

        if (webEngagementScore != null && webEngagementScore >= 70) {
            score += 10;
        }

        return score;
    }

    public static String deriveSegment(Integer score) {
        if (score == null) {
            return null;
        }
        if (score >= 70) return "Hot";
        if (score >= 40) return "Warm";
        return "Cold";
    }

    public static String derivePriority(String segment) {
        if (segment == null) {
            return null;
        }
        switch (segment) {
            case "Hot":
                return "High";
            case "Warm":
                return "Medium";
            case "Cold":
                return "Low";
            default:
                return null;
        }
    }
}
