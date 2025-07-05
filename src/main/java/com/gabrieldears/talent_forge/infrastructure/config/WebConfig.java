package com.gabrieldears.talent_forge.infrastructure.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TraceIdInterceptor traceIdInterceptor;

    public WebConfig(TraceIdInterceptor traceIdInterceptor) {
        this.traceIdInterceptor = traceIdInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor);
    }

    @Bean
    public ApplicationRunner runner(Tracer tracer) {
        return args -> {
            Span span = tracer.nextSpan().name("manual-span").start();
            try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
                System.out.println("Running span: " + span.context().traceId());
            } finally {
                span.end();
            }
        };
    }


}
