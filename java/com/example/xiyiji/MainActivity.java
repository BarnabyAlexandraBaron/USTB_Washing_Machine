package com.example.xiyiji;

import static android.os.SystemClock.sleep;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button button_1;
    Button button_2;
    public StringBuilder req_response = new StringBuilder("");
    public static String info_xiyiji = "";
    public static String info_moto = "";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button_1 = (Button) findViewById(R.id.Button_1);
        recyclerView = findViewById(R.id.recycler);
        ArrayList<InfoModel> info_list = new ArrayList<>();


        getSync_2();
//        sleep(500);
//        info_moto = info_moto.replace("<","");
//        info_moto = info_moto.replace("/","");
//        info_moto = info_moto.replace(">","");
//        info_moto = info_moto.replace("p","");
//        info_list.add(new InfoModel(info_moto));
//        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,info_list);
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //延时0.5s执行
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO
                info_moto = info_moto.replace("<","");
                info_moto = info_moto.replace("/","");
                info_moto = info_moto.replace(">","");
                info_moto = info_moto.replace("p","");
                info_list.add(new InfoModel(info_moto));
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,info_list);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        }, 600);


        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSync();// & set info_xiyiji
                Toast toast = Toast.makeText(MainActivity.this, "Loading....... 你别急！！", Toast.LENGTH_SHORT);
                toast.show();
                sleep(3000);

                Gson gson = new Gson();
                Map<String, String> map = gson.fromJson(info_xiyiji, Map.class);
                Set<String> keys = map.keySet();
                info_list.clear();
                for(String key : keys){
                    Log.i(TAG, "getSync:" + key);
                    Log.i(TAG, "Ans:" + map.get(key));
                    info_list.add(new InfoModel(key+map.get(key)));
                }
                info_list.add(new InfoModel("undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("别滑了,undefined"));
                info_list.add(new InfoModel("何处觅只因"));
                info_list.add(new InfoModel("114514 114514 114514"));
                info_list.add(new InfoModel("说明:随机语只在第一次打开app才会有,你别急,行不行,吉吉国王"));
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,info_list);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }
        });

    }

    //get同步请求
    public void getSync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建客户端
                OkHttpClient httpClient=new OkHttpClient();
                //发送请求
                Request request=new Request.Builder()
                        .url("????????")	//这里写入获取空闲洗衣机信息的API，北科用到的是U净，抓包自己写API即可，API返回格式为 {"":"","":""} 
                        .build();	//若不会写API，抓包然后用安卓okhttp发包，然后安卓java进行解析（yysy没有python解析json来的方便）
                //使用客户端发送请求
                try (Response response=httpClient.newCall(request).execute()) {
                    assert response.body() != null;
                    String st=response.body().string();
//                    handler.sendEmptyMessage(0x111);
                    info_xiyiji = st;
                    Log.i(TAG, "getSync:" + st);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getSync_2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建客户端
                OkHttpClient httpClient=new OkHttpClient();
                //发送请求
                Request request=new Request.Builder()
                        .url("https://v.api.aa1.cn/api/yiyan/index.php")
                        .build();
                //使用客户端发送请求
                try (Response response=httpClient.newCall(request).execute()) {
                    assert response.body() != null;
                    String st=response.body().string();
//                    handler.sendEmptyMessage(0x111);
                    info_moto = st;
                    Log.i(TAG, "getSync:" + st);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}