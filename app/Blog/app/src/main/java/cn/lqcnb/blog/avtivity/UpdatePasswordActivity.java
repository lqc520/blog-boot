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
import okhttp3.Call;

public class UpdatePasswordActivity extends AppCompatActivity {
    private static final String TAG = "UpdatePasswordActivity";
    private EditText et_pwd, et_newPwd,et_conPwd;
    private Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initView();

        initListener();
    }

    private void initListener() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conPwd = et_conPwd.getText().toString();
                String newPwd= et_newPwd.getText().toString();
                String pwd= et_pwd.getText().toString();
                if(TextUtils.isEmpty(conPwd)||TextUtils.isEmpty(newPwd)||TextUtils.isEmpty(pwd)) {
                    Toast.makeText(UpdatePasswordActivity.this, "请按要求输入", Toast.LENGTH_SHORT).show();
                }if(!conPwd.equals(newPwd)){
                    Toast.makeText(UpdatePasswordActivity.this, "密码不一致请重新输入", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("id");
                    OkHttpUtils.post().url(Constants.updateMember).addParams("id",id).addParams("password",newPwd).build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.e(TAG, "onError: 修改密码请求失败"+e.getMessage() );
                        }

                        @Override
                        public void onResponse(String response) {
                            ResultJson resultJson = JSON.parseObject(response, ResultJson.class);
                            if (resultJson.getCode()==0){
                                Log.d(TAG, "onResponse: user-->"+resultJson.getData().toString());
                                CacheUtils.saveString(UpdatePasswordActivity.this,"user",resultJson.getData().toString());
                                Toast.makeText(UpdatePasswordActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UpdatePasswordActivity.this, resultJson.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            UpdatePasswordActivity.this.finish();
                        }
                    });
                }

            }
        });
    }

    private void initView() {
        et_pwd=findViewById(R.id.et_pwd);
        et_newPwd=findViewById(R.id.et_newPwd);
        et_conPwd=findViewById(R.id.et_conPwd);
        btn_update=findViewById(R.id.btn_update);
    }
}
