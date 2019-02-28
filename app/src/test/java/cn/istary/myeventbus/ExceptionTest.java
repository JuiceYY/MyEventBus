package cn.istary.myeventbus;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/28 12:54
 * DESCRIPTION:
 */

import org.junit.Test;

import cn.istary.myeventbus.eventbus.ParamsNumTooMuchException;

public class ExceptionTest {

    @Test
    public void paramsNumTooMuchExceptionTest(){
        throw new IndexOutOfBoundsException();
    }
}
