package com.mmall.controller.backend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by u6035457 on 9/25/2017.
 */

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManageControllerTest extends AbstractJUnit4SpringContextTests {

    UserManageController controller = null;
    @Before
    public void setUp() throws Exception {
        controller = new UserManageController();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

    }
}