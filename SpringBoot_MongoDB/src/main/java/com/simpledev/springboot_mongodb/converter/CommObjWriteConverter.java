package com.simpledev.springboot_mongodb.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.simpledev.springboot_mongodb.entity.CommObj;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
public class CommObjWriteConverter implements Converter<CommObj, DBObject> {
    @Override
    public DBObject convert(CommObj commObj) {
        DBObject write = new BasicDBObject();
        write.put("pv", commObj.getP1() + "," + commObj.getP2());
        return write;
    }
}
