package com.gabrieldears.talent_forge.adapter.client;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiMatcherClient {

    private final RestTemplate restTemplate;
    private final String aiMatcherBaseUrl;
    private final ObservationRegistry observationRegistry;

    public AiMatcherClient(RestTemplate restTemplate,
                           @Value("${ai.matcher.url}") String aiMatcherBaseUrl,
                           ObservationRegistry observationRegistry) {
        this.restTemplate = restTemplate;
        this.aiMatcherBaseUrl = aiMatcherBaseUrl;
        this.observationRegistry = observationRegistry;
    }

    public com.gabrieldears.talent_forge.model.MatchResult[] getMatchesForCandidate(String candidateId) {
        return Observation
                .createNotStarted("ai-matcher.get-matches-for-candidate", observationRegistry)
                .lowCardinalityKeyValue("candidate.id", candidateId)
                .observe(() -> {
                    String url = aiMatcherBaseUrl + "/match/candidate/" + candidateId;
                    return restTemplate.getForObject(url, com.gabrieldears.talent_forge.model.MatchResult[].class);
                });
    }

    public com.gabrieldears.talent_forge.model.CandidateResponse[] getMatchesForJob(String jobId) {
        return Observation
                .createNotStarted("ai-matcher.get-matches-for-job", observationRegistry)
                .lowCardinalityKeyValue("job.id", jobId)
                .observe(() -> {
                    String url = aiMatcherBaseUrl + "/match/job/" + jobId;
                    return restTemplate.getForObject(url, com.gabrieldears.talent_forge.model.CandidateResponse[].class);
                });
    }
}
