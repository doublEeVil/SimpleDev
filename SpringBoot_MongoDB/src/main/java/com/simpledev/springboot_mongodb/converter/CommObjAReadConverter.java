package com.simpledev.springboot_mongodb.converter;

import com.simpledev.springboot_mongodb.entity.CommObjA;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class CommObjAReadConverter implements Converter<String, CommObjA> {
    @Override
    public CommObjA convert(String data) {
        CommObjA ret = new CommObjA();
        String[] datas = data.split(",");
        ret.setP1(datas[0]);
        ret.setP2(datas[1]);
        return ret;
    }
}
