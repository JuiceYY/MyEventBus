package cn.istary.myeventbus.eventbus;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:53
 * DESCRIPTION:
 */

import java.lang.reflect.Method;

public class SubscriberMethod {

    private Method method;
    private Class<?> paramType;
    private ThreadMode threadMode;

    public SubscriberMethod(Method method, Class<?> paramType, ThreadMode threadMode) {
        this.method = method;
        this.paramType = paramType;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }
}
