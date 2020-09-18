package com.simpledev.springboot_mongodb.converter;

import com.mongodb.BasicDBObject;
import com.simpledev.springboot_mongodb.entity.CommObjA;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class CommObjAWriteConverter implements Converter<CommObjA, String> {
    @Override
    public String convert(CommObjA commObjA) {
        return commObjA.getP1() + "," + commObjA.getP2();
    }
}
