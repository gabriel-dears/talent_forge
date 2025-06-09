package com.gabrieldears.talent_forge.domain.port;

import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

public interface ResumeParserPort {
    String parse(File file) throws IOException, TikaException;
}
