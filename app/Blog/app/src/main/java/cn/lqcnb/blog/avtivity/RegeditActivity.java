package cn.lqcnb.blog.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.entity.ResultJson;
import cn.lqcnb.blog.utils.CacheUtils;
import cn.lqcnb.blog.utils.Constants;
import cn.lqcnb.blog.utils.NetUtils;
import okhttp3.Call;

public class RegeditActivity extends AppCompatActivity {
    private static final String TAG = "RegeditActivity";
    private EditText et_mobile, et_password,et_code;
    private Button btn_register,btn_getCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regedit);
        initView();
        initListener();
    }

    private void initListener() {
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=et_mobile.getText().toString();
                if(mobile.length()!=11){
                    Toast.makeText(RegeditActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else{
                    OkHttpUtils.get().url(Constants.getAliyunSMSCode).addParams("mobile",mobile).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.e(TAG, "onError: 获取验证码请求失败"+e.getMessage());
                        }

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegeditActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=et_mobile.getText().toString();
                String password = et_password.getText().toString();
                String code = et_code.getText().toString();
                if(TextUtils.isEmpty(mobile)||TextUtils.isEmpty(password)||TextUtils.isEmpty(code)){
                    Toast.makeText(RegeditActivity.this, "请按要求注册", Toast.LENGTH_SHORT).show();
                }else{
                    OkHttpUtils.get().url(Constants.addMember).addParams("code",code).addParams("mobile",mobile)
                            .addParams("password",password).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.e(TAG, "onError: 登录请求失败"+e.getMessage());
                        }

                        @Override
                        public void onResponse(String response) {
                            ResultJson resultJson = JSON.parseObject(response, ResultJson.class);
                            if (resultJson.getCode()==0){
                                Toast.makeText(RegeditActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegeditActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegeditActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        et_mobile =  findViewById(R.id.et_mobile);
        et_password =  findViewById(R.id.et_password);
        et_code = findViewById(R.id.et_code);
        btn_register = findViewById(R.id.btn_register);
        btn_getCode = findViewById(R.id.btn_getCode);
    }
}
