package cn.istary.myeventbus.eventbus;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:26
 * DESCRIPTION:
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MySubscribe {

    ThreadMode threadMode() default ThreadMode.MAIN;

}
