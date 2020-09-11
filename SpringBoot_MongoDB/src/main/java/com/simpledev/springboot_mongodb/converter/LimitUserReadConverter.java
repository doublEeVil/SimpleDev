package com.simpledev.springboot_mongodb.converter;

import com.mongodb.DBObject;
import com.simpledev.springboot_mongodb.entity.LimitUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@ReadingConverter
public class LimitUserReadConverter implements Converter<DBObject, LimitUser> {
    @Override
    public LimitUser convert(DBObject dbObject) {
        LimitUser limitUser = new LimitUser();
        limitUser.setId((int)dbObject.get("_id"));
        limitUser.setName((String) dbObject.get("name"));
        limitUser.setAddress((String)dbObject.get("addr"));
        limitUser.setAge((int) dbObject.get("age"));
        return limitUser;
    }
}
