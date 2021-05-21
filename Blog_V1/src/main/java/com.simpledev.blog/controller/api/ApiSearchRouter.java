package com.simpledev.blog.controller.api;

import com.simpledev.blog.ServiceManager;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiSearchRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String keyword = request.queryParams("keyword");
        return ServiceManager.getInstance().getArticleService().getIndex(keyword);
    }
}
