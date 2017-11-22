package easyMock;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Created by u6035457 on 9/21/2017.
 */
//@RunWith(value = EasyMockRunner.class)
public class TestEasyMock extends TestCase{

    private UserDao userDao;
    private UserService2 userService = new UserService2();
    @Test
    public void testUser() {

        User user = new User();
        user.type = "vip2";
        //创建mock对象
        userDao = EasyMock.createMock(UserDao.class);
        //记录mock对象期望的行为
        /*总结说，在record阶段，我们需要给出的是我们对mock对象的一系列期望：
        若干个mock对象被调用，依从我们给定的参数，顺序，次数等，并返回预设好的结果(返回值或者异常).*/
        EasyMock.expect(userDao.insertUser(user)).andReturn(false).times(2);
        /**
         * 在replay阶段，我们关注的主要测试对象将被创建，之前在record阶段创建的相关依赖被关联到主要测试对象，
         * 然后执行被测试的方法，以模拟真实运行环境下主要测试对象的行为。
         */
        EasyMock.replay(userDao);

        userService.setUserDao(userDao);
        assertEquals(false, userService.registerUser(user));
        //验证交互行为，典型如依赖是否被调用，调用的参数，顺序和次数等
        EasyMock.verify(userDao);
    }
}
