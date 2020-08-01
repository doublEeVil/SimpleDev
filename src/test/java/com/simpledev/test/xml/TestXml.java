package com.simpledev.test.xml;

import com.simpledev.test.xml.bean.ObjA;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

public class TestXml {

    @Test
    public void testGenXmlFromBean() {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.aliasSystemAttribute(null, "class");
        System.out.println(xStream.toXML(ObjA.class));
    }
}
