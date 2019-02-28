package cn.istary.myeventbus.demo;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:15
 * DESCRIPTION:
 */

public class EventTwo {

    private String msg;
    private String tag;

    public EventTwo(String msg) {
        this.msg = msg;
        this.tag = "Event2";
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
        return "EventTwo{" +
                "msg='" + msg + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
