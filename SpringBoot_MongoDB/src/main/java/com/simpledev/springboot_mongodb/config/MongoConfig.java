package com.simpledev.springboot_mongodb.config;

import com.simpledev.springboot_mongodb.converter.CommObjAReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjAWriteConverter;
import com.simpledev.springboot_mongodb.converter.CommObjBReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjBWriteConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

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
        adapter.registerConverter(new CommObjAReadConverter());
        adapter.registerConverter(new CommObjAWriteConverter());
        super.configureConverters(adapter);
    }

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
