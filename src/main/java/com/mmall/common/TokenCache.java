package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * Created by u6035457 on 9/17/2017.
 * 本地的缓存
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static String TOKEN_PREFIX = "token_";
    //默认LRU算法, 最小使用算法
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).//初始化容量
            maximumSize(10000).//最大容量
            expireAfterAccess(12,TimeUnit.HOURS).//自动过期
            build(new CacheLoader<String, String>() {
        //默认的实现，如果没有取到值，就调用这个结果
        // get 没有取到值, 就会调用这个方法取值, 一般从数据库,文件去取值. 变成"null"处置, 防止报错
        @Override
        public String load(String key) throws Exception {
            return "null";
        }
    });

    public static  void setToCache(String key,String value){
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            logger.error("TokenCache.setFromCache(): 不能设置空值到localCahe");
            return;
        }
        localCache.put(key,value);
    }
    public static String getFromCache(String key){

        String value = null;
        if (StringUtils.isBlank(key)) {
            logger.error("TokenCache.setFromCache(): key值不能为null");
        }else{
            try {
                value = localCache.get(key);
                if ("null".equals(value)) {
                    value = null;
                }
            } catch (ExecutionException e) {
                logger.error("TokenCache.getFromCache() ", e);
            }
        }
        return value;
    }

    public static void main(String[] args) {
        TokenCache.setToCache("a","abc");
        TokenCache.setToCache("b",null);
        TokenCache.setToCache(null,"abc");
        System.out.println(TokenCache.getFromCache("c"));
    }












}
