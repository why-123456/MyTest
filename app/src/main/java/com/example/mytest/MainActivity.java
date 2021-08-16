package com.example.mytest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;


import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.obs.services.ObsClient;

import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText etPhone;
    private EditText etEmail;
    private EditText etContent;
    private TextView tvHint;
    private GridView gvPic;//网格显示缩略图
    private Button btnAddPic;
    private Button btnCommit;
    private View contentInclude;
    private GridAdapter adapter;
    private final int IMAGE_OPEN=1;//打开图片标记
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private List<File> files=new ArrayList<>();
    private List<String> filesNameList=new ArrayList<>();
    private String fileName=null;
    private static final int REQUEST_CODE=123;
    private ArrayList<String> imagePaths=new ArrayList<>();
    private boolean isGreen=true;
    private List<String> urlList=new ArrayList<>();
    private String urlString="";
   private OkHttpClient okHttpClient=new OkHttpClient();
   private Request request=null;
    private MyFeedBackClass feedBackClass=new MyFeedBackClass();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Fresco
        Fresco.initialize(getApplicationContext());
        /*
         * 防止键盘挡住输入框
         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        //获取控件对象
        initView();
        myPermission();
    }

    private void initView() {
        etContent=findViewById(R.id.et_content);
        tvHint=contentInclude.findViewById(R.id.tv_hint);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        gvPic = findViewById(R.id.add_pic);
        btnAddPic = findViewById(R.id.btn_add_pic);
        btnCommit = findViewById(R.id.btn_commit);
        btnAddPic.setOnClickListener(new btnAddPicOnClickListener());
        btnCommit.setOnClickListener(new btnCommitOnClickListener());
    }

    public   class btnAddPicOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this, ImagesSelectorActivity.class);
            intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER,3);
            intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE,100000);
            intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA,false);
            intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST,imagePaths);
            startActivityForResult(intent,REQUEST_CODE);
        }
    }

    public  class btnCommitOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Gson gson=new Gson();
            for (int i=0;i<urlList.size();i++){
                urlString+=urlList.get(i).toString()+",";
            }
            feedBackClass.setPhoneNum(etPhone.getText().toString()+"");
            feedBackClass.setEmail(etEmail.getText().toString()+"");
            feedBackClass.setRemark(etContent.getText().toString()+"");
            feedBackClass.setPicObsUrl(urlString+"");
            feedBackClass.setEquipmentVersion("1");
            String json = gson.toJson(feedBackClass);
            RequestBody body=RequestBody.create(JSON, json);
            request=new Request.Builder()
                    .url("http://119.3.23.16:8088/public/questionBackByNoLogin")
                    .post(body)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.i("yue", "相关数据: "+feedBackClass.getPhoneNum().toString()+"-----------"+feedBackClass.getEmail().toString());
                    Log.i("yue", "内容为: "+feedBackClass.getRemark().toString());
                    Log.i("yue", "urlList: "+urlList.toString());
                    Log.i("yue", "urlString: "+urlString);
                    Log.i("yue", "结果: "+response.body().string()+json.toString());
                }
            });
        }
    }

    //高版本手动获取权限
    public void myPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    IMAGE_OPEN
            );
        }
    }

    //申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IMAGE_OPEN:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    isGreen = false;
                } else {
                    isGreen = true;
                }
                break;
        }
    }

    //获取图片路径 响应startActivityForResult
    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_OK){
               ArrayList<String> list=data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
               loadAdapter(list);
                Log.i("jjjj", "list: "+ list.toString());
               gvPic.setVisibility(View.VISIBLE);
               gvPic.setNumColumns(list.size());
               for (String s:list){
                   File f=new File(s);
                   files.add(f);
               }
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       for (String s:list){
                           fileName=getFileName(s);
                           filesNameList.add(fileName);
                           uploadAndGetPicUrl(filesNameList,files);
                       }
                   }
               }).start();
            }
        }
    }

    @SuppressLint("LongLogTag")
    private void uploadAndGetPicUrl(List<String> fileNames,List<File> fs) {
        String endPoint = "https://obs.cn-east-3.myhuaweicloud.com";
        String ak = "IP3GPV0JTU2VPFT8MCQ7";
        String sk = "3qDRxizVN8ukaMfPPpqtvprzr34TEx7BpwhM1jhC";
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        final String keySuffixWithSlash = "test/feedback_pic/";
        obsClient.putObject("ahxd-private", keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
        // 初始化线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 执行并发上传
        for (File f:fs){
                executorService.execute(() -> {
                    if (f.isDirectory()){
                        // 如果是空文件夹，则在桶内创建对应的空文件夹对象
                        for (String name:fileNames){
                            if (f.getPath().contains(name)){
                                String remoteObjectKey = keySuffixWithSlash + fileNames + "/";
                                obsClient.putObject("ahxd-private", remoteObjectKey, new ByteArrayInputStream(new byte[0]));
                            }
                        }
                    }else{
                        urlList.clear();
                        for (String name:fileNames){
                            if (f.getPath().contains(name)){
                                String remoteObjectKey = keySuffixWithSlash + name;
                                obsClient.putObject("ahxd-private", remoteObjectKey, new File(f.getPath()));
                                urlList.add(remoteObjectKey);
                            }
                        }
                    }
                });
        }
        // 等待上传完成
        executorService.shutdown();
        while (!executorService.isTerminated())
        {
            try
            {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        // 关闭obsClient
        try {
            obsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//获取文件名称
    public  String getFileName(String pathandname){
        int  start=pathandname.lastIndexOf( "/" );
        int  end=pathandname.lastIndexOf( "." );
        if  (start!=- 1  && end!=- 1 ) {
            return  pathandname.substring(start+ 1 );
        } else  {
            return  null ;
        }
    }


    private void loadAdapter(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("000000")) {
            paths.remove("000000");
        }
//        paths.add("000000");
        imagePaths.addAll(paths);
        adapter = new GridAdapter(imagePaths, MainActivity.this);
//        adapter.notifyDataSetChanged();
        gvPic.setAdapter(adapter);
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        private Context context;

        public GridAdapter(ArrayList<String> listUrls, Context context) {
            this.listUrls = listUrls;
            this.context = context;
            inflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.add_pic_item, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.img_pic);
                holder.image.setBackground(getResources().getDrawable(R.drawable.add_picture_bg));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String path = listUrls.get(position);
            if (path.equals("000000")) {
//                    holder.image.setImageResource(R.mipmap.ic_launcher);
//                listUrls.clear();
                listUrls.remove("000000");
            } else {
                Glide.with(MainActivity.this)
                        .load(path)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);

                if (listUrls.size() == 3) {
//                listUrls.remove(listUrls.size()-1);
//                Toast.makeText(context, "只能选择3张哦!", Toast.LENGTH_SHORT).show();
                    btnAddPic.setVisibility(View.GONE);
                }
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }
    }
}
