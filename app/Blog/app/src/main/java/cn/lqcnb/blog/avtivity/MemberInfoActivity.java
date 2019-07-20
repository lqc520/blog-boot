package cn.lqcnb.blog.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.List;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.adapter.ArticleAdapt;
import cn.lqcnb.blog.entity.Article;
import cn.lqcnb.blog.utils.Constants;
import okhttp3.Call;

public class MemberInfoActivity extends AppCompatActivity {
    private static final String TAG ="MemberInfoActivity" ;
    private TextView tv_edit;
    private ListView lv_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        initView();
        initListener();
        getDataFromNet();
    }

    private void initListener() {
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MemberInfoActivity.this,EditMemberActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        tv_edit=findViewById(R.id.tv_edit);
        lv_data=findViewById(R.id.lv_data);
    }
    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.getArticles).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d(TAG, "文章数据网络请求失败，失败原因：" + e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                List<Article> articles = JSONArray.parseObject(response, new TypeReference<List<Article>>() {
                });
                Log.d(TAG, "onResponse->articles: " + articles.toString());
                lv_data.setAdapter(new ArticleAdapt(MemberInfoActivity.this, articles));
                lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MemberInfoActivity.this, DetailActivity.class);
                        intent.putExtra("data", (Serializable) articles.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
