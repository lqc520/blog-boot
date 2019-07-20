package cn.lqcnb.blog.avtivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zzhoujay.richtext.RichText;


import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.entity.Article;
import okhttp3.Call;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG ="DetailActivity";
    private TextView tv_title,tv_content,tv_author,tv_time,tv_column,tv_tag;
    private ImageView iv_avatar;
    private WebView wv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        Article data = (Article) intent.getSerializableExtra("data");
        tv_title.setText(data.getTitle());
        RichText.fromMarkdown(data.getContent()).into(tv_content);
        //tv_content.setText(data.getContent());
//        try {
//            String content=new Markdown4jProcessor().process(data.getContent());
//            Log.d(TAG, "getData: "+content);
//            wv_content.loadDataWithBaseURL(null,content,"text/html","utf-8",null);;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        tv_author.setText(data.getAuthor());
        String subDate = data.getCreate_time().substring(5, 10);
        tv_time.setText(subDate);
        tv_column.setText(data.getSpecial());
        tv_tag.setText(data.getTag());
        OkHttpUtils.get().url(data.getAvatar()).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "用户头像请求失败，失败原因： "+e.getMessage());
            }

            @Override
            public void onResponse(Bitmap response) {
                iv_avatar.setImageBitmap(response);
            }
        });
    }

    private void initView() {
        tv_title=findViewById(R.id.tv_title);
        tv_content=findViewById(R.id.tv_content);
        tv_author=findViewById(R.id.tv_author);
        tv_time=findViewById(R.id.tv_time);
        tv_column=findViewById(R.id.tv_column);
        tv_tag=findViewById(R.id.tv_tag);
        iv_avatar=findViewById(R.id.iv_avatar);
    }
}
