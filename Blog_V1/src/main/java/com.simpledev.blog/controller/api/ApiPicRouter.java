package com.simpledev.blog.controller.api;

import com.simpledev.blog.common.GlobalData;
import com.alibaba.fastjson.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ApiPicRouter implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        List<String> recentPicList = GlobalData.getInstance().getRecentPicList();
        JSONObject json = new JSONObject();
        json.put("picList", recentPicList);
        return json;
    }
}
