package br.com.pw.gestao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

@Configuration
public class MetricsConfiguration {
    @Bean
    @Primary
    MeterBinder processorMetrics() {
        return new MeterBinder() {
            @Override
            public void bindTo(MeterRegistry registry) {
                
            }
        };
    }
}
