package com.simpledev.blog.controller.api;

import spark.Request;
import spark.Response;
import spark.Route;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApiJianliRouter implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Path path = Paths.get("static/jianli.md");
        byte[] data = Files.readAllBytes(path);
        return new String(data);
    }
}
