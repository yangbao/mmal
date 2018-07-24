package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by u6035457 on 8/28/2017.
 */
public class Const{

    public static String CURRENT_USER = "currentUser";
    public static String VALIDATE_USERNAME = "username";
    public static String VALIDATE_EMAIL = "email";

    //枚举太重了(就需要2个值), 所以使用常量接口, 参考有道NOTE
    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;   //管理员
    }
    public interface ProductListOrderBy{
        //set contains 时间复杂度O(1)， list是O(n)
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface LoginStatus{
        int LOGIN_SUCCESS = 0;
        int LOGIN_FAIL = 1;
    }
    public enum ProductStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
