package cn.istary.myeventbus.demo;
/*
 * CREATED BY: Sinry
 * TIME: 2019/2/27 23:28
 * DESCRIPTION:
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.istary.myeventbus.R;
import cn.istary.myeventbus.eventbus.EventBus;
import cn.istary.myeventbus.eventbus.MySubscribe;
import cn.istary.myeventbus.eventbus.ThreadMode;

public class FragmentTwo extends Fragment implements View.OnClickListener{

    private Button button1;
    private Button button2;
    private EditText edit1;
    private EditText edit2;
    private TextView textView;

    private EventBus eventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getInstance();
        eventBus.register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        button1 = view.findViewById(R.id.btn1);
        edit1 = view.findViewById(R.id.edit1);
        button2 = view.findViewById(R.id.btn2);
        edit2 = view.findViewById(R.id.edit2);
        textView = view.findViewById(R.id.fragment_text);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                String msg1 = edit1.getText().toString();
                EventOne eventOne = new EventOne(msg1);
                eventBus.post(eventOne);
                break;

            case R.id.btn2:
                String msg2 = edit2.getText().toString();
                EventTwo eventTwo = new EventTwo(msg2);
                eventBus.post(eventTwo);
                break;
        }

    }

    @MySubscribe
    public void handleEventOne(EventOne eventOne){
        textView.setText(eventOne.toString());
        Log.d("MyEventBus: invoke", eventOne.toString() + " --> " + Thread.currentThread().getName() + " in fragment two.");
    }

//    @MySubscribe
//    public void testException(EventOne eventOne, EventTwo two){
//        Log.d("MyEventBus: ", "这个方法编译时异常");
//    }
}
