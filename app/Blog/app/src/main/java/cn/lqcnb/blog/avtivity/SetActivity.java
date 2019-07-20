package cn.lqcnb.blog.avtivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.utils.CacheUtils;

public class SetActivity extends AppCompatActivity {
    private TextView tv_logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
        initListener();
    }

    private void initListener() {
        tv_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = CacheUtils.getString(SetActivity.this, "user");
                if(user==null){
                    Toast.makeText(SetActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    CacheUtils.saveString(SetActivity.this,"user",null);
                    Toast.makeText(SetActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        tv_logOut=findViewById(R.id.tv_logOut);

    }

}
