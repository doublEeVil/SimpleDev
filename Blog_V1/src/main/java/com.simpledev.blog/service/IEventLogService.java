package com.simpledev.blog.service;

import com.simpledev.blog.entity.EventLog;

public interface IEventLogService {

    /**
     * 添加记录
     * @param log
     */
    void add(EventLog log);
}
