package com.company.crm.datacleanup;

public class LeadScoringUtil {

    public static int calculateLeadScore(
            String jobTitle,
            Integer companySize,
            Integer lastActivityDays,
            Integer webEngagementScore) {

        int score = 0;

        // Decision Maker
        if (jobTitle != null) {
            String title = jobTitle.toLowerCase();
            if (title.contains("ceo") || title.contains("cto")
                    || title.contains("director") || title.contains("vp")) {
                score += 25;
            }
        }

        // Company Size
        if (companySize != null && companySize >= 200) {
            score += 20;
        }

        // Recency
        if (lastActivityDays != null && lastActivityDays <= 7) {
            score += 15;
        }

        // Engagement
        if (webEngagementScore != null && webEngagementScore >= 70) {
            score += 10;
        }

        return score;
    }

    public static String deriveSegment(int score) {
        if (score >= 70) return "Hot";
        if (score >= 40) return "Warm";
        return "Cold";
    }

    public static String derivePriority(String segment) {
        switch (segment) {
            case "Hot":
                return "High";
            case "Warm":
                return "Medium";
            default:
                return "Low";
        }
    }
}
