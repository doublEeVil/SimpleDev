package com.simpledev.springboot_mongodb.converter;

import com.simpledev.springboot_mongodb.entity.CommObjA;
import com.simpledev.springboot_mongodb.entity.CommObjB;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class CommObjBReadConverter implements Converter<String, CommObjB> {
    @Override
    public CommObjB convert(String data) {
        CommObjB ret = new CommObjB();
        String[] datas = data.split("\\|");
        ret.setInfo(datas[0]);
        ret.setWeight(Integer.valueOf(datas[1]));
        return ret;
    }
}
