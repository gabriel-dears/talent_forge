package com.gabrieldears.talent_forge.infrastructure.parser;

import com.gabrieldears.talent_forge.domain.port.ResumeParserPort;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class TikaResumeParser implements ResumeParserPort {
    private final Tika tika = new Tika();

    @Override
    public String parse(File file) throws IOException, TikaException {
        return tika.parseToString(file);
    }
}

