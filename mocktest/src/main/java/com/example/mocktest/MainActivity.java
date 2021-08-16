package com.example.mocktest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "yue";
//    private Button btn;
//    private ProgressBar pb1;
//    private Button btnPb1;
//    private ProgressBar pb2;
//    private Button btnPb2;
    private Toolbar toolbar1;
    private Toolbar toolbar2;
//private NotificationManager manager;
//private Notification notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationChannel channel=new NotificationChannel("zyy","测试通知",NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//        }
//        Intent intent=new Intent(this,MainActivity2.class);
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
//        notification=new NotificationCompat.Builder(this,"yue")
//                .setContentTitle("官方通知")
//                .setContentText("世界那么大,我想去看看。。。。。")
//                .setSmallIcon(R.drawable.main)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.jiya))
//                .setColor(Color.parseColor("#ffff0000"))
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("zyyue", "tooLbar1被点击了!");
            }
        });
        toolbar2.setNavigationIcon(R.drawable.btn_two_back);
        toolbar2.setTitle("标题2");
        toolbar2.setSubtitle("子标题2");
        toolbar2.setSubtitleTextColor(Color.parseColor("#ffff0000"));
        toolbar2.setTitleTextColor(Color.parseColor("#ff00ff00"));
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("zyyue", "tooLbar2被点击了!");
            }
        });

//        //点击事件
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: ");
//            }
//        });
//        //长按事件
//        btn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.i(TAG, "onLongClick: ");
//                return false;
//            }
//        });
//        //触摸事件
//        btn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i(TAG, "onTouch: ");
//                return false;
//            }
//        });
    }

    private void initView() {
//        btn = findViewById(R.id.btn);
//        pb1 = findViewById(R.id.pb1);
//        btnPb1 = findViewById(R.id.btn_pb1);
//        pb2 = findViewById(R.id.pb2);
//        btnPb2 = findViewById(R.id.btn_pb2);
         toolbar1 = findViewById(R.id.toolbar1);
         toolbar2=findViewById(R.id.toolbar2);
    }

//    public void goneAndVisClick(View view) {
//        if (pb1.getVisibility() == View.VISIBLE) {
//            pb1.setVisibility(View.GONE);
//        } else {
//            pb1.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void loadClick(View view) {
//        int progress=pb2.getProgress();
//        progress+=10;
//        pb2.setProgress(progress);
//    }

//    public void sendClick(View view) {
////        manager.notify(1,notification);
//    }
//
//    public void chanelCLick(View view) {
////        manager.cancel(1);
//    }
}