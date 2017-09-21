mmal learning
一. Mybatis 三剑客,
    Mybatis generator: 根据DB的数据库表自动生成DAO 和 Mapping文件
    Mybatis plugin: 使用后能从DAO的interface到Mapping的直接跳转,并且能在添加的时候提示错误
    Mybatis page-help: 分页的插件, 截获sql,自己处理自动分页的逻辑
二. 打开problem视图: 搜索compiler,勾选make project automatically.
     Spring 在autoWire注入的时候,因为用的是mybatis idea会报错,
     修改方法 search inspections --> spring module-->spring core-->Autowiring for bean class error 改为warning
三.2个有用的插件:
    Http的调试神器,模拟请求: Restlet Client
    Json格式化等集成的神奇: FE助手
四. 课程: spring mvc 数据绑定
    Mybatis 多个参数需要用@param 注解








