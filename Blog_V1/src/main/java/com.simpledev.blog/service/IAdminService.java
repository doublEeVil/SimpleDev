package com.simpledev.blog.service;

import com.alibaba.fastjson.JSONObject;

public interface IAdminService {

    /**
     * 登录
     * @param user
     * @param pwd
     * @return
     */
    boolean login(String user, String pwd);
}
