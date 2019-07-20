package cn.lqcnb.blog.avtivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;
import cn.lqcnb.blog.R;
import cn.lqcnb.blog.fragment.HomeFragment;
import cn.lqcnb.blog.fragment.UserFragment;
import cn.lqcnb.blog.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg_bottom;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragments();
        initRadioGroupListener();
        rg_bottom.check(R.id.rb_home);
    }



    /**
     * 初始化组件
     */
    private void initView() {
        rg_bottom=findViewById(R.id.rg_bottom);
    }
    /**
     * 初始化碎片
     */
    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new UserFragment());
    }

    /**
     * 初始化按钮监听器
     */
    private void initRadioGroupListener() {
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position=0;
                switch (checkedId){
                    case R.id.rb_home:
                        position=0;
                        break;
                    case R.id.rb_card:
                        position=1;
                        break;
                    case R.id.rb_user:
                        position=2;
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_content,fragmentList.get(position));
                transaction.commit();
            }
        });
    }
}
