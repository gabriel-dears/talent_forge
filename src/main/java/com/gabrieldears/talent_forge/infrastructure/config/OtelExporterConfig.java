package com.gabrieldears.talent_forge.infrastructure.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtelExporterConfig {

    @Bean
    public OtlpGrpcSpanExporter otlpSpanExporter() {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint("http://tempo:4317")
                .build();
    }
}
