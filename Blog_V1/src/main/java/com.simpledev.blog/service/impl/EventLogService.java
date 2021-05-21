package com.simpledev.blog.service.impl;

import com.simpledev.blog.entity.EventLog;
import com.simpledev.blog.service.IEventLogService;
import com.simpledev.module_cache.mysql.service.GenericMySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventLogService implements IEventLogService {
    @Autowired
    private GenericMySqlService genericMySqlService;

    @Override
    public void add(EventLog log) {
        log.setCreateDate(System.currentTimeMillis());
        log.setUpdateDate(System.currentTimeMillis());
        genericMySqlService.save(log);
    }
}
