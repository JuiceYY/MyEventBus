package cn.istary.myeventbus.eventbus;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/28 11:24
 * DESCRIPTION:
 */

public class ParamsNumTooMuchException extends RuntimeException {

    public static final String MSG = "使用@MySubscribe注解的方法只能有一个参数";
}
