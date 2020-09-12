package com.simpledev.springboot_mongodb.converter;

import com.mongodb.DBObject;
import com.simpledev.springboot_mongodb.entity.CommObj;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class CommObjReadConverter implements Converter<DBObject, CommObj> {
    @Override
    public CommObj convert(DBObject dbObject) {
        CommObj ret = new CommObj();
        String data = (String) dbObject.get("pv");
        String[] datas = data.split(",");
        ret.setP1(datas[0]);
        ret.setP2(datas[1]);
        return ret;
    }
}
