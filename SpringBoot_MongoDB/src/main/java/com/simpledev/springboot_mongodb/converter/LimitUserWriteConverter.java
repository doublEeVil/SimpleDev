package com.simpledev.springboot_mongodb.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.simpledev.springboot_mongodb.entity.LimitUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
public class LimitUserWriteConverter implements Converter<LimitUser, DBObject> {
    @Override
    public DBObject convert(LimitUser limitUser) {
        DBObject object = new BasicDBObject();
        object.put("_id", limitUser.getId());
        object.put("age", limitUser.getAge());
        object.put("addr", limitUser.getAddress());
        object.put("name", limitUser.getName());
        return object;
    }
}
