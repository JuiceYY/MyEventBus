package cn.istary.myeventbus.eventbus;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:14
 * DESCRIPTION:
 */

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {
    private static volatile EventBus instance;
    private Map<Object, List<SubscriberMethod>> cacheMap;
    private Handler handler;
    private ExecutorService executorService;

    private EventBus() {
        cacheMap = new ConcurrentHashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public static EventBus getInstance() {
        if (null == instance) {
            synchronized (EventBus.class) {
                if (null == instance) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    /**
     * @param obj
     * @throws ParamsNumTooMuchException
     */
    public void register(Object obj) throws ParamsNumTooMuchException{
        List<SubscriberMethod> methodList = cacheMap.get(obj);
        if (null == methodList) {
            //该对象还没有注册
            subscribe(obj);
        }
    }

    private void subscribe(Object obj) throws ParamsNumTooMuchException {
        List<SubscriberMethod> methodList = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();//获取该类所有public方法, 包括继承的方法
        for (Method method : methods) {
            /*
              1. 使用了特定注解
              2. 只有一个参数
              3. 获取注解的线程模式
             */
            MySubscribe myAnnotation = method.getAnnotation(MySubscribe.class);
            if (myAnnotation != null) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 1) {
                    throw new ParamsNumTooMuchException();
                }
                ThreadMode threadMode = myAnnotation.threadMode();
                SubscriberMethod subscriberMethod = new SubscriberMethod(method, paramTypes[0], threadMode);
                methodList.add(subscriberMethod);
            }
        }
        cacheMap.put(obj, methodList);
    }

    public void unregister(Object obj) {
        Set<Object> subsciberSet = cacheMap.keySet();
        if (subsciberSet.contains(obj)) {
            cacheMap.remove(obj);
        }
    }

    public void post(Object param) {
        //遍历cachemap, 找到参数类型相同的方法通过反射执行
        Set<Object> subscriberSet = cacheMap.keySet();
        for (Object subscriber : subscriberSet) {
            List<SubscriberMethod> methodList = cacheMap.get(subscriber);
            for (SubscriberMethod method : methodList) {
                if (method.getParamType().isAssignableFrom(param.getClass())) {
                    //确定由此类对象表示的类或接口是否与由指定的Class类表示的类或接口相同或是超类或类接口
                    invoke(subscriber, method, param, method.getThreadMode());
                }
            }
        }
    }

    private void invoke(final Object subscriber, final SubscriberMethod subscriberMethod, final Object param, ThreadMode threadMode) {
        final Method method = subscriberMethod.getMethod();
        switch (threadMode) {
            case MAIN:
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    //主-主
                    try {
                        method.invoke(subscriber, param);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    //子-主
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                method.invoke(subscriber, param);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;

            case BACKGROUND:
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    //主-子
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                method.invoke(subscriber, param);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    //子-子
                    try {
                        method.invoke(subscriber, param);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}
