package com.gabrieldears.talent_forge.adapter.web.dto;

public class MatchResultDto {
    private String jobId;
    private String jobTitle;
    private float matchScore;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public float getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(float matchScore) {
        this.matchScore = matchScore;
    }
}
