package cn.lqcnb.blog.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.entity.ResultJson;
import cn.lqcnb.blog.utils.CacheUtils;
import cn.lqcnb.blog.utils.Constants;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText et_mobile, et_password;
    private Button btn_login;
    private TextView toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();

    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.get().url(Constants.memberLogin).addParams("mobile",et_mobile.getText().toString()).addParams("password",et_password.getText().toString()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e(TAG, "onError: 网络请求失败"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        ResultJson resultJson = JSON.parseObject(response, ResultJson.class);
                        if (resultJson.getCode()==0){
                            Log.d(TAG, "onResponse: user-->"+resultJson.getData().toString());
                            CacheUtils.saveString(LoginActivity.this,"user",resultJson.getData().toString());
                            Toast.makeText(LoginActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        LoginActivity.this.finish();
                    }
                });

            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegeditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        btn_login =  findViewById(R.id.btn_login);
        toRegister=findViewById(R.id.toRegister);
    }
}
