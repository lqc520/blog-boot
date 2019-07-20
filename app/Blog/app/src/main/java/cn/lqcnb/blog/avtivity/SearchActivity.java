package cn.lqcnb.blog.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private ListView lv_seaData;
    private EditText et_search_name;
    private Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        Intent intent=getIntent();
        String key = intent.getStringExtra("key");
        getDataFromNet(key);
        initListener();
    }

    private void initView() {
        btn_search=findViewById(R.id.btn_search);
        et_search_name=findViewById(R.id.et_search_name);
        lv_seaData=findViewById(R.id.lv_seaData);
    }
    private void initListener() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = et_search_name.getText().toString();
                getDataFromNet(key);
            }
        });
    }

    private void getDataFromNet(String key) {

        OkHttpUtils.get().url(Constants.getArticlesByTitle).addParams("title",key).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d(TAG, "文章数据网络请求失败，失败原因：" + e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                List<Article> articles = JSONArray.parseObject(response, new TypeReference<List<Article>>() {
                });
                Log.d(TAG, "onResponse->articles: " + articles.toString());
                lv_seaData.setAdapter(new ArticleAdapt(SearchActivity.this, articles));

                lv_seaData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        intent.putExtra("data",(Serializable) articles.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
