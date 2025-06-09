package com.gabrieldears.talent_forge.application.candidate;

import com.gabrieldears.talent_forge.adapter.web.dto.CandidateRequestDto;
import com.gabrieldears.talent_forge.domain.model.Candidate;
import com.gabrieldears.talent_forge.domain.model.Resume;
import com.gabrieldears.talent_forge.domain.port.FileStoragePort;
import com.gabrieldears.talent_forge.domain.port.ResumeParserPort;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ResumeProcessor {

    private final FileStoragePort fileStoragePort;
    private final ResumeParserPort resumeParserPort;

    public ResumeProcessor(FileStoragePort fileStoragePort, ResumeParserPort resumeParserPort) {
        this.fileStoragePort = fileStoragePort;
        this.resumeParserPort = resumeParserPort;
    }

    public void processResume(CandidateRequestDto candidateRequestDto, Candidate candidate) {
        MultipartFile resumeFile = candidateRequestDto.resume();
        String filename = String.format("%s_%s", UUID.randomUUID(), resumeFile.getOriginalFilename());
        try {
            String filepath = fileStoragePort.store(resumeFile, filename);
            String parsedText = resumeParserPort.parse(new File(filepath));
            Resume resume = new Resume();
            resume.setFilePath(filepath);
            resume.setText(parsedText);
            candidate.setResume(resume);
        } catch (IOException | TikaException e) {
            throw new RuntimeException(e);
        }
    }

}
