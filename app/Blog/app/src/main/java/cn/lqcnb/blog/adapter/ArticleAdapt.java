package cn.lqcnb.blog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.entity.Article;
import okhttp3.Call;

public class ArticleAdapt extends BaseAdapter {

    private static final String TAG ="ArticleAdapt" ;
    private Context context;
    private List<Article> dataList;

    public ArticleAdapt(Context context, List<Article> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = View.inflate(context, R.layout.articleitem,null);
        }
        TextView tv_title=convertView.findViewById(R.id.tv_title);
        TextView tv_content=convertView.findViewById(R.id.tv_content);
        TextView tv_author=convertView.findViewById(R.id.tv_author);
        TextView tv_time=convertView.findViewById(R.id.tv_time);
        final ImageView iv_avatar=convertView.findViewById(R.id.iv_avatar);
        Article article=dataList.get(position);
        tv_title.setText(article.getTitle());
        tv_content.setText(article.getContent());
        tv_author.setText(article.getAuthor());
        tv_time.setText(article.getCreate_time());
        OkHttpUtils.get().url(article.getAvatar()).build().execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e(TAG, "用户头像请求失败，失败原因： "+e.getMessage());
            }

            @Override
            public void onResponse(Bitmap response) {
                iv_avatar.setImageBitmap(response);
            }
        });
        return convertView;
    }

}
