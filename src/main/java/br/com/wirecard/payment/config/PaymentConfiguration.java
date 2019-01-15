package br.com.wirecard.payment.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Date;

@Configuration
public class PaymentConfiguration {
/*
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.deserializerByType(Date.class, new DateWithoutTimezoneDeserializer());
        builder.serializerByType(Date.class, new DateWithTimezoneSerializer());
        return builder;
    }
*/
}
