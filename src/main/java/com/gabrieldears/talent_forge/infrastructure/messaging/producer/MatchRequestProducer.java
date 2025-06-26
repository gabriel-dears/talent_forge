package com.gabrieldears.talent_forge.infrastructure.messaging.producer;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchRequestProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MatchRequestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCandidateForMatching(Candidate candidate) {
        kafkaTemplate.send("match.candidate.request", candidate.getId(), candidate);
    }
}

