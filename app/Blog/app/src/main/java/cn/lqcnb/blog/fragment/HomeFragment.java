package cn.lqcnb.blog.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.List;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.adapter.ArticleAdapt;
import cn.lqcnb.blog.avtivity.DetailActivity;
import cn.lqcnb.blog.avtivity.SearchActivity;
import cn.lqcnb.blog.entity.Article;
import cn.lqcnb.blog.utils.Constants;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private static final String TAG ="HomeFragment" ;
    private Context context;
    private View view;
    private ListView lv_data;
    private EditText et_search_name;
    private Button btn_search;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = View.inflate(context, R.layout.fragment_home, null);
        initView();
        initListener();
        getDataFromNet();
        return view;
    }

    private void initListener() {
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = et_search_name.getText().toString();
                Intent intent =new Intent(context, SearchActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
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
                Log.d(TAG, "onResponse->articles: "+articles.toString());
                lv_data.setAdapter(new ArticleAdapt(context,articles));
                lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("data",(Serializable) articles.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void initView() {
        lv_data=view.findViewById(R.id.lv_data);
        btn_search=view.findViewById(R.id.btn_search);
        et_search_name=view.findViewById(R.id.et_search_name);

    }

}
