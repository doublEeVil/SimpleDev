package com.simpledev.springboot_cache_ehcache.dao;

import com.simpledev.springboot_cache_ehcache.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable
    User findByName(String name);

    User findByNameAndAge(String name, int age);

//    @Transactional
//    @CachePut
//    @Modifying
//    @Query("update User set age=?2 where name=?1")
//    int updateAgeByName(String name, int age);
}
