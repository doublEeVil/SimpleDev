package com.simpledev.module_cache.mysql.service.impl;

import com.simpledev.module_cache.mysql.BaseEntity;
import com.simpledev.module_cache.mysql.BaseIdAutoIncEntity;
import com.simpledev.module_cache.mysql.dao.GenericDao;
import com.simpledev.module_cache.mysql.service.GenericMySqlService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component("com.simpledev.module_cache.mysql.service.impl.GenericMysqlServiceImpl")
@Transactional
public class GenericMysqlServiceImpl implements GenericMySqlService {
    @Autowired
    private GenericDao genericDao;

    @Override
    public void save(BaseIdAutoIncEntity t) {
        t.setCreateDate(System.currentTimeMillis());
        genericDao.save(t);
    }

    @Override
    public void update(BaseIdAutoIncEntity t) {
        t.setUpdateDate(System.currentTimeMillis());
        genericDao.update(t);
    }

    @Override
    public void delete(BaseIdAutoIncEntity t) {
        genericDao.delete(t);
    }

    public void save(BaseEntity t) {
        genericDao.save(t);
        //GenericDao.flush();
    }

    public <T> T get(Class<T> clazz, int id) {
        return genericDao.get(clazz, id);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        return genericDao.getAll(clazz);
    }

    @Override
    public <T> List<T> getAllByHql(String hql, Object... params) {
        return genericDao.getAllByHql(hql, params);
    }

    public <T> T getByHql(String hql, Object ... params) {
        return genericDao.getByHql(hql, params);
    }

    public void update(BaseEntity t) {
        genericDao.update(t);
    }

    public void delete(BaseEntity t) {
        genericDao.delete(t);
    }

    @Override
    public void flush() {
        genericDao.flush();
    }

    @Override
    public <T> List<T> getBySql(Class<T> clazz, String sql) {
        return genericDao.getBySql(clazz, sql);
    }

    @Override
    public <T> int countBySql(String sql) {
        return genericDao.countBySql(sql);
    }

    @Override
    public List<Object[]> getBySql(String sql, Object ... params) {
        Session session = genericDao.getSession();
        Query query = session.createSQLQuery(sql);
        int i = 1;
        for (Object obj : params) {
            query.setParameter(i++, obj);
        }
        return query.list();
    }
}
