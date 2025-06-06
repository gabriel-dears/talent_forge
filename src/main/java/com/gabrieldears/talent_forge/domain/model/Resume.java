package com.gabrieldears.talent_forge.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Resume {
    private String text;
    private String filePath;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
