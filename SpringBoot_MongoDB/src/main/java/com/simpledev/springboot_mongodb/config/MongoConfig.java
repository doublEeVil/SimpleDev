package com.simpledev.springboot_mongodb.config;

import com.simpledev.springboot_mongodb.converter.CommObjReadConverter;
import com.simpledev.springboot_mongodb.converter.CommObjWriteConverter;
import com.simpledev.springboot_mongodb.converter.LimitUserReadConverter;
import com.simpledev.springboot_mongodb.converter.LimitUserWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new CommObjReadConverter());
        converterList.add(new CommObjWriteConverter());
        converterList.add(new LimitUserReadConverter());
        converterList.add(new LimitUserWriteConverter());
        return new  MongoCustomConversions(converterList);
    }
}
