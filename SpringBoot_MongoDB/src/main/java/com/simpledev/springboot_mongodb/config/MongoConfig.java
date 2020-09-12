package com.simpledev.springboot_mongodb.config;

import com.simpledev.springboot_mongodb.converter.CommObjReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjWriteConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class MongoConfig extends AbstractMongoClientConfiguration
{
    @Override
    protected String getDatabaseName() {
        return "db_1";
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
        adapter.registerConverter(new CommObjReadConverter());
        adapter.registerConverter(new CommObjWriteConverter());
        super.configureConverters(adapter);
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new CommObjReadConverter());
        converterList.add(new CommObjWriteConverter());
        return new  MongoCustomConversions(converterList);
    }
}
