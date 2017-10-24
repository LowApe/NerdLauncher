package com.didiaoyuan.blog.nerdlauncher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mr.Qu on 2017/10/24.
 */

public  class  NerdLauncherFragment extends Fragment{
//  初始化变量
    private RecyclerView mRecyclerView;
//  创建 NerdLauncherFragment 方法
    public static NerdLauncherFragment newInstance(){
        return new NerdLauncherFragment();
    }
//  创建 Fragment 视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_fragment,container,false);
        mRecyclerView = v.findViewById(R.id.nerd_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
