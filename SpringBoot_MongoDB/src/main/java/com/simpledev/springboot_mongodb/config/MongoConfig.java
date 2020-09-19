package com.simpledev.springboot_mongodb.config;

import com.simpledev.springboot_mongodb.converter.CommObjAReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjAWriteConverter;
import com.simpledev.springboot_mongodb.converter.CommObjBReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjBWriteConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new CommObjAReadConverter());
        converterList.add(new CommObjAWriteConverter());

        converterList.add(new CommObjBReadConverter());
        converterList.add(new CommObjBWriteConverter());
        return new  MongoCustomConversions(converterList);
    }
}
