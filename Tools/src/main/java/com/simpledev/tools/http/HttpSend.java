package com.simpledev.tools.http;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Date;

public class HttpSend {

    public static void main(String ... args) throws Exception {
        HttpClient client = HttpClients.createMinimal();
        System.out.println("111" + new Date());
        String base = "http://10.193.78.22:61001/baseifsys/thirdparty/restful/send?_servicecode=202108161613235209&_token=24bf6a7f0e3e17b5ea804197a809e4c8&_orgId=001066003&_refuladdress=DwdSwjwAdultVaccineRecord_8823730r&id_card=";
        HttpGet req = new HttpGet(base + "360782199305121512");
        HttpResponse resp = client.execute(req);
        String ret = EntityUtils.toString(resp.getEntity());
        System.out.println(ret);
        System.out.println("222" + new Date());
        req = new HttpGet(base + "360782199305121522");
        resp = client.execute(req);
        ret = EntityUtils.toString(resp.getEntity());
        System.out.println(ret);
        System.out.println("333" + new Date());
    }
}
