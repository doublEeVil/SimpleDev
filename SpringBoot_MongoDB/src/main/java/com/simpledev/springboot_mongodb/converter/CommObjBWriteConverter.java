package com.simpledev.springboot_mongodb.converter;

import com.mongodb.BasicDBObject;
import com.simpledev.springboot_mongodb.entity.CommObjA;
import com.simpledev.springboot_mongodb.entity.CommObjB;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class CommObjBWriteConverter implements Converter<CommObjB, String> {
    @Override
    public String convert(CommObjB commObjB) {
        // return write;
        return commObjB.getInfo() + "|" + commObjB.getWeight();
    }
}
