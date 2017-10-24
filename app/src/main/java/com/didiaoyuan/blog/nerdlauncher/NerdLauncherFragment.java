package com.didiaoyuan.blog.nerdlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Mr.Qu on 2017/10/24.
 */

public class NerdLauncherFragment extends Fragment {
    //  初始化变量
    private static final String TAG = "NerdLauncherFragment";
    private RecyclerView mRecyclerView;

    //  创建 NerdLauncherFragment 方法
    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }

    //  创建 Fragment 视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);
        mRecyclerView = v.findViewById(R.id.nerd_launcher_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return v;
    }

    private void setupAdapter() {
//        创建一个隐式 Intent 添加分类选择
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
//        利用 pm 查询所有 startupIntent 的 activity 返回给 list 列表
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
//        log 打印含有多少个 activities
        Log.e(TAG, "\n activities have ："+activities.size()+"\n");
    }
}
