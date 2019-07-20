package cn.lqcnb.blog.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.lqcnb.blog.R;
import cn.lqcnb.blog.avtivity.LoginActivity;
import cn.lqcnb.blog.avtivity.MemberInfoActivity;
import cn.lqcnb.blog.avtivity.MyArticlesActivity;
import cn.lqcnb.blog.avtivity.SetActivity;
import cn.lqcnb.blog.avtivity.UpdatePasswordActivity;
import cn.lqcnb.blog.entity.Member;
import cn.lqcnb.blog.utils.CacheUtils;
import cn.lqcnb.blog.utils.NetUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private static final String TAG ="UserFragment" ;
    private Context context;
    private View view;
    private WebView wv_content;
    private TextView tv_name,tv_nickname,tv_updatePwd,tv_logOut,tv_set,tv_myArticle;
    private ImageView iv_avatar,iv_set;
    private LinearLayout toLogin;
    private Member member;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context=getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = View.inflate(context, R.layout.fragment_user, null);
        initView();
        initListener();
         getMemberData();
        return view;
    }

    private void initView() {
        toLogin=view.findViewById(R.id.toLogin);
        tv_name=view.findViewById(R.id.tv_name);
        tv_nickname=view.findViewById(R.id.tv_nickname);
        iv_avatar=view.findViewById(R.id.iv_avatar);
        tv_logOut=view.findViewById(R.id.tv_logOut);
        tv_updatePwd=view.findViewById(R.id.tv_updatePwd);
        iv_set=view.findViewById(R.id.iv_set);
        tv_set=view.findViewById(R.id.tv_set);
        tv_myArticle=view.findViewById(R.id.tv_myArticle);
    }

    private void initListener() {
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 点击了toLogin" );
                if(member==null){
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "个人中心", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MemberInfoActivity.class);
                    startActivity(intent);
                }

            }
        });
        tv_updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent =new Intent(context, UpdatePasswordActivity.class);
                    intent.putExtra("id",member.getId());
                    startActivity(intent);
                }
            }
        });
        tv_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    CacheUtils.saveString(context,"user",null);
                    member=null;
                    Toast.makeText(context, "退出登录成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
        iv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetActivity.class);
                startActivity(intent);
            }
        });
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetActivity.class);
                startActivity(intent);
            }
        });
        tv_myArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent =new Intent(context, MyArticlesActivity.class);
                    intent.putExtra("id",member.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void getMemberData() {
        String user = CacheUtils.getString(context, "user");
        if(!TextUtils.isEmpty(user)){
            member = JSON.parseObject(user, Member.class);
            tv_name.setText(member.getName());
            if(member.getNickname()!=null){
                tv_nickname.setText(member.getNickname());
            }else{
                tv_nickname.setText("此人很懒，什么都没写");
            }

            if(member.getAvatar()!=null){
                iv_avatar.setImageBitmap(NetUtils.getBitmap(member.getAvatar()));
            }
        }
    }

}
