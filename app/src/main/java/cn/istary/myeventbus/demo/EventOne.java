package cn.istary.myeventbus.demo;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:15
 * DESCRIPTION:
 * 注解搞定getter setter, lombok;
 */

public class EventOne {

    private String msg;
    private String tag;

    public EventOne(String msg) {
        this.msg = msg;
        this.tag = "Event1";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "EventOne{" +
                "msg='" + msg + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
