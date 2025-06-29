package com.gabrieldears.talent_forge.infrastructure.messaging.consumer;

import com.gabrieldears.talent_forge.adapter.web.dto.NotificationUpdateDto;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MatchNotificationConsumer {

    private final CustomCandidateRepository candidateRepository;

    public MatchNotificationConsumer(CustomCandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @KafkaListener(topics = "match.notification.update", groupId = "candidate-matcher-group")
    public void handleNotificationUpdate(NotificationUpdateDto message) {
        String candidateId = message.getCandidateId();
//        log.info("✅ Received notification update for candidate: {}", candidateId);
        candidateRepository.findById(candidateId)
                .ifPresent(candidate -> {
                    candidate.setDateNotification(LocalDate.now().plusDays(14));
                    candidateRepository.update(candidate);
                });
    }

}

