package cn.lqcnb.blog.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import cn.lqcnb.blog.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    private Context context;
    private View view;

    public VideoFragment() {
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
        view = View.inflate(context, R.layout.fragment_video, null);

        VideoView videoView = view.findViewById(R.id.videoView);
        //加载指定的视频文件
        String path = "http://smxy.xyz:10000/Shopping/MV/1.mp4";
        videoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(context);

        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);

        //让VideoView获取焦点
        videoView.requestFocus();
        return view;
    }

}
