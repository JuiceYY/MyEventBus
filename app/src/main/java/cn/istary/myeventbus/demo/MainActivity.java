package cn.istary.myeventbus.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import cn.istary.myeventbus.R;
import cn.istary.myeventbus.eventbus.EventBus;
import cn.istary.myeventbus.eventbus.MySubscribe;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new FragmentOne();
        fragment2 = new FragmentOne();
        fragment3 = new FragmentTwo();
        fragment4 = new FragmentTwo();
        textView = findViewById(R.id.activity_text);

        //动态添加fragmetn
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame1, fragment1);
        transaction.add(R.id.frame2, fragment2);
        transaction.add(R.id.frame3, fragment3);
        transaction.add(R.id.frame4, fragment4);

        transaction.commit();

        EventBus.getInstance().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }

    @MySubscribe
    public void handleEventOne(EventOne eventOne){
        textView.setText(eventOne.toString());
        Log.d("MyEventBus: invoke", eventOne.toString() + " --> " + Thread.currentThread().getName() + " in activity.");
    }

    @MySubscribe
    public void handleEventTwo(EventTwo eventTwo){
        textView.setText(eventTwo.toString());
        Log.d("MyEventBus: invoke", eventTwo.toString() + " --> " + Thread.currentThread().getName() + " in activity.");
    }
}
