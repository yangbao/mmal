package com.mmall.util;

import java.util.List;

/**
 * Created by Administrator on 2018\8\1 0001.
 */
public class BeanUtilTest {

    public static void main(String[] args) {

        BeanA ba = new BeanA();
        BeanB bb = new BeanB();
        ba.setName1("ba-name1");
        ba.setName2("ba-name2");
//        bb.setName1("bb-name1");
//        org.springframework.beans.BeanUtils.copyProperties(ba, bb, "name1");
        org.springframework.beans.BeanUtils.copyProperties(ba, bb);
        System.out.println(bb.getName1());
        System.out.println(bb.getName2());
    }
     static class BeanA {
        private String name1;
        private String name2;
        private List<String> nameList;

        public String getName1() {
            return name1;
        }
        public void setName1(String name1) {
            this.name1 = name1;
        }
        public String getName2() {
            return name2;
        }
        public void setName2(String name2) {
            this.name2 = name2;
        }
        public List<String> getNameList() {
            return nameList;
        }
        public void setNameList(List<String> nameList) {
            this.nameList = nameList;
        }
    }

    static class BeanB {
        private String name1;
        private String name2;
        private List<String> nameList;
        public String getName1() {
            return name1;
        }
        public void setName1(String name1) {
            this.name1 = name1;
        }
        public String getName2() {
            return name2;
        }
        public void setName2(String name2) {
            this.name2 = name2;
        }
        public List<String> getNameList() {
            return nameList;
        }
        public void setNameList(List<String> nameList) {
            this.nameList = nameList;
        }
    }
}
