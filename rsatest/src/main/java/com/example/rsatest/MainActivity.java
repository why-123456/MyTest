package com.example.rsatest;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rsatest.info.LoginInfo;
import com.example.rsatest.util.AesUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import android.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.datatype.DatatypeFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn;
//    private LoginInfo loginInfo;
    private List<LoginInfo> loginInfoList=new ArrayList<>();
    private OkHttpClient client=new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    private byte[] bytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                LoginInfo info=new LoginInfo();
//                info.setEquipmentName("");
//                info.setLoginType(2);
//                info.setMeid("");
//                info.setOsType(2);
//                info.setPassword("123456");
//                info.setPhoneNum("17333187286");
//                info.setSmsCode("");
//                info.setUserNum(123);
//                info.setVersion("");
//                loginInfoList.add(info);
//                Gson gson=new Gson();
//                String json = gson.toJson(loginInfoList);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("equipmentName","");
                    jsonObject.put("loginType","2");
                    jsonObject.put("meid","");
                    jsonObject.put("osType","");
                    jsonObject.put("password","123456");
//                    jsonObject.put("phoneNum","17333187286");
//                    jsonObject.put("smsCode","");
                    jsonObject.put("userNum","1001002");
                    jsonObject.put("version","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //生成一个动态key
                String secretKey = AesUtils.generateKey();
                String s = rsaEncode(secretKey);
                String encryStr = AesUtils.encrypt(secretKey, jsonObject.toString());

                //根据RSA公钥加密json数据
//                String rsaEncode = rsaEncode(jsonObject.toString());
                System.out.println(encryStr);

//                try {
//                    byte[] gongkey = Base64.decode(publicKey,Base64.DEFAULT);
//                    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
//                    X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(gongkey);
//                    try {
////                        publicKey1 =keyFactory.generatePublic(x509EncodedKeySpec);
//                        Cipher cipher=Cipher.getInstance("RSA");
//                        cipher.init(Cipher.ENCRYPT_MODE,keyFactory.generatePublic(x509EncodedKeySpec));
//                        byte[] bytes = cipher.doFinal(json.getBytes());
//                        Log.e("zy", "加密后的请求体："+bytes.toString());
//                    } catch (InvalidKeySpecException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }   catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
//                    e.printStackTrace();
//                    try {
//                        throw new IOException(e);
//                    } catch (IOException ioException) {
//                        ioException.printStackTrace();
//                    }
//                }
//                Log.i("zy", "加密后的请求体："+bytes.toString());
                RequestBody requestBody=RequestBody.create(JSON,encryStr);
                Request request=new Request.Builder()
                        .url("http://119.3.23.16:8088/user/login")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i("yueyue", "请求出错信息----------"+e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.i("yueyue", "请求正确信息----------"+response.body().string());
                    }
                });
            }
        });
    }

//根据RSA公钥加密json数据
    public static String rsaEncode(String str) {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlYIwxN8f77oZlXBOuhzj+qym8NckvFLJPhtihMLL0xEpwQqK8zvOv7DwUwcfW/JrKBG/oOZbPW6OAUjsWQTBp8imJlA43rTMXTPsAwTllM2aGZdWXw2sYsgaq9864b8KjTuXCSewUWZJoQ3aAgoJDLSOpP7SOd/fpcLc/AjXzCB2auYRR4G3+W+39akGwHOqvnQzz2dwzlekhVtkywfLzt0nll8W4CkGEI+pq9Gy5R5XViJsvj7tLCy3DtBWHcKT0/WmoEOojqESjsY09SWWOKG+KjJfrABWJK4r1OcVrGWUkr/VRhpDGzPXD9LiU05efayK6BHJsX5RMBDkIZDlHQIDAQAB";
        String outStr = "";
        try {
            // base64编码的公钥
            byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            // RSA加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //初始化密码器
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeToString(cipher.doFinal(str.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }
}