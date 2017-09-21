package com.mmall.util;

import com.google.common.cache.Cache;
import com.mmall.vo.UserLoginRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by u6035457 on 9/14/2017.
 */
public class UserLoginRecordUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testUpdateUserLoginStatus() throws Exception {

        UserLoginRecord record = new UserLoginRecord();
        record.setErrorNum(2);
        record.setLastLoginTime(LocalDateTime.of(2017,12,01,10,26,33));
        record.setUsername("Zhang san");
        Cache<String,UserLoginRecord> localCache = UserLoginRecordUtil.localCache;

        localCache.put(record.getUsername(),record);

        UserLoginRecord record2 = localCache.get(record.getUsername(), () -> {
            //Callable只有在缓存值不存在时，才会调用-- TODO 以后可能会做数据保存到数据库,缓存会同步数据库的内容
            return null;
        });
        System.out.println(record2);
    }
}