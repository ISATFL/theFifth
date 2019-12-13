package com.example.thefifth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
public class MainActivity extends AppCompatActivity{
    static final int CHANGE_VIEW=1;
    private MyHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button change = findViewById(R.id.button);
        mHandler = new MyHandler(MainActivity.this);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView();
            }
        });
    }
    public void changeView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(CHANGE_VIEW);
            }
        }).start();
    }
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        private MyHandler(MainActivity mainActivity) {
            this.weakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            final MainActivity mActivity = weakReference.get();
            if (msg.what == CHANGE_VIEW) {
                TextView textView = mActivity.findViewById(R.id.text);
                textView.setText("这是handler修改的");
            }
        }
    }

}
