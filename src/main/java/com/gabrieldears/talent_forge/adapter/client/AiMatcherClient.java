package com.gabrieldears.talent_forge.adapter.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiMatcherClient {

    private final RestTemplate restTemplate;
    private final String aiMatcherBaseUrl;

    public AiMatcherClient(RestTemplate restTemplate,
                           @Value("${ai.matcher.url}") String aiMatcherBaseUrl) {
        this.restTemplate = restTemplate;
        this.aiMatcherBaseUrl = aiMatcherBaseUrl;
    }

    public com.gabrieldears.talent_forge.model.MatchResult[] getMatchesForCandidate(String candidateId) {
        String url = aiMatcherBaseUrl + "/match/candidate/" + candidateId;
        return restTemplate.getForObject(url, com.gabrieldears.talent_forge.model.MatchResult[].class);
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse[] getMatchesForJob(String jobId) {
        String url = aiMatcherBaseUrl + "/match/job/" + jobId;
        return restTemplate.getForObject(url, com.gabrieldears.talent_forge.model.CandidateResponse[].class);
    }
}
