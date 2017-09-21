import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by u6035457 on 9/1/2017.
 */
public class Test {
    public static void main(String[] args) {
    /*
    expireAfterWrite是在指定项在一定时间内没有创建/覆盖时，会移除该key，下次取的时候从loading中取
    expireAfterAccess是指定项在一定时间内没有读写，会移除该key，下次取的时候从loading中取
    refreshAfterWrite是在指定时间内没有被创建/覆盖，则指定时间过后，再次访问时，会去刷新该缓存，在新值没有到来之前，始终返回旧值
        跟expire的区别是，指定时间过后，expire是remove该key，下次访问是同步去获取返回新值；
        而refresh则是指定时间后，不会remove该key，下次访问会触发刷新，新值没有回来时返回旧值
     */
        // 模拟数据
        final List<Person> list = new ArrayList<Person>(5);
        list.add(new Person(1, "zhangsan"));
        list.add(new Person(2, "lisi"));
        list.add(new Person(3, "wangwu"));
        LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
                //.refreshAfterWrite(1, TimeUnit.DAYS)//给定时间内没有被读/写访问，则回收。
                .expireAfterAccess(1,TimeUnit.DAYS)//缓存过期时间
                .initialCapacity(1000)
                .maximumSize(10000)
                .build(new CacheLoader<String, Person>() {
                    //当本地缓存命没有中时，调用load方法获取结果并将结果缓存
                    @Override
                    public Person load(String key) throws Exception {
                        System.out.println(key + " load in cache");
                        return getPerson(key);
                    }
                    private Person getPerson(String key) {
                        //会有处理, 比如去数据库里面查询
                        System.out.println(key + " query");
                        for (Person p: list) {
                            if ((p.getId()+"").equals(key)){
                                return p;
                            }
                        }
                        return new Person(1001,"TOm");
                    }
                });
        try {
            cache.get("1");
            System.out.println(cache.get("1"));
            cache.get("2");
            System.out.println(cache.get("2"));
            cache.get("3");
            System.out.println(cache.get("3"));
            System.out.println("==============");
            cache.get("1");
            System.out.println(cache.get("1"));
            cache.get("2");
            System.out.println(cache.get("2"));
            cache.get("3");
            System.out.println(cache.get("3"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    static class Person{
        private  int id;
        private String name;

        public Person() {
        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
