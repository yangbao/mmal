package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by u6035457 on 9/26/2017.
 */
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest extends AbstractJUnit4SpringContextTests {

//    @Autowired
    private UserMapper userMapper = null;
//    @Autowired
    private UserServiceImpl userService;
    @Before
    public void setUp() throws Exception {

        userMapper = EasyMock.createMock(UserMapper.class);
        userService = new UserServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }

    @Test
    public void testRegister() throws Exception {

    }

    @Test
    public void testValidate() throws Exception {
        EasyMock.expect(userMapper.checkUser("abc@163.com")).andReturn(0);
        EasyMock.replay(userMapper);
        userService.setUserMapper(userMapper);
        ServerResponse<String> response = userService.validate("abc@163.com", Const.VALIDATE_USERNAME);
        System.out.println(response);
        assertEquals(true,response.isSuccess());
        assertEquals("校验成功",response.getMessage());
        EasyMock.verify(userMapper);
        System.out.println("=========================================================");
        EasyMock.reset(userMapper);
        EasyMock.expect(userMapper.checkUser("abc@163.com")).andReturn(1);
        EasyMock.replay(userMapper);
        userService.setUserMapper(userMapper);
        response = userService.validate("abc@163.com", Const.VALIDATE_USERNAME);
        System.out.println(response);
        assertEquals(false,response.isSuccess());
        assertEquals("用户名已存在",response.getMessage());
        EasyMock.verify(userMapper);
    }

    @Test
    public void testSelectQuestion() throws Exception {

    }

    @Test
    public void testCheckForgetAnswer() throws Exception {

    }

    @Test
    public void testForgetResetPassword() throws Exception {

    }

    @Test
    public void testResetPassword() throws Exception {

    }

    @Test
    public void testModifyUserInformation() throws Exception {

    }

    @Test
    public void testGetInformation() throws Exception {

    }
}