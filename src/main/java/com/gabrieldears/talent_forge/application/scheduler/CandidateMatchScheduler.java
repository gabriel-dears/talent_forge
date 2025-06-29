package com.gabrieldears.talent_forge.application.scheduler;

import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.repository.CustomCandidateRepository;
import com.gabrieldears.talent_forge.infrastructure.messaging.producer.MatchRequestProducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CandidateMatchScheduler {

    private static final int BATCH_SIZE = 100;

    private final CustomCandidateRepository candidateRepository;
    private final MatchRequestProducer matchRequestProducer;

    public CandidateMatchScheduler(CustomCandidateRepository candidateRepository, MatchRequestProducer matchRequestProducer) {
        this.candidateRepository = candidateRepository;
        this.matchRequestProducer = matchRequestProducer;
    }

    @Scheduled(cron = "0 0 8 ? * MON,THU", zone = "America/Sao_Paulo") // 8am Mon & Thu
    public void scheduleMatchJob() {
//        log.info("Starting match scheduler at {}", LocalDate.now());

        Pageable pageable = PageRequest.of(0, BATCH_SIZE);
        Page<Candidate> page;

        do {
            page = candidateRepository.findByDateNotificationLessThanEqual(LocalDate.now().plusDays(7), pageable);
//            log.info("Sending {} candidates for match", page.getNumberOfElements());
            sendBatchAsync(page);

            System.out.printf("From candidate %d to candidate %d %n", page.getNumber(), page.getTotalPages() - 1);

            pageable = pageable.next();
        } while (page.hasNext());
    }

    @Async
    public void sendBatchAsync(Page<Candidate> candidates) {
        candidates.forEach(matchRequestProducer::sendCandidateForMatching);
    }

}
