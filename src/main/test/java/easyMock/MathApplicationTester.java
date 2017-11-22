package easyMock;


import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;

/**
 * Created by u6035457 on 9/25/2017.
 */
//@RunWith attaches a runner with the test class to initialize the test data
@RunWith(EasyMockRunner.class)
public class MathApplicationTester {

    //@TestSubject annotation is used to identify class which is going to use the
    //mock object
    @TestSubject
    MathApplication mathApplication = new MathApplication();

    //@Mock annotation is used to create the mock object to be injected
    @Mock
    CalculatorService calcService;

    @Test(expected = RuntimeException.class)
    public void testAdd(){
        //add the behavior to throw exception
//        EasyMock.expect(calcService.divide(0,0)).andReturn(30.00);
        EasyMock.expect(calcService.divide(0,0)).andThrow(new FileNotFoundException("aaaaaa"));
        //activate the mock
        EasyMock.replay(calcService);
        //test the add functionality
        Assert.assertEquals(mathApplication.divide(0,0),30.0,0);
        //verify call to calcService is made or not
        EasyMock.verify(calcService);
    }
}
