package com.spring.boot.vlt.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vlt.settings")
public class VltSettings {
    private String pathsUploadedFiles;
    private String framesXml;

    public String getPathsUploadedFiles() {
        return pathsUploadedFiles;
    }

    public void setPathsUploadedFiles(String pathsUploadedFiles) {
        this.pathsUploadedFiles = pathsUploadedFiles;
    }

    public String getFramesXml() {
        return framesXml;
    }

    public void setFramesXml(String framesXml) {
        this.framesXml = framesXml;
    }
}
