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
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Mr.Qu on 2017/10/24.
 */

public class NerdLauncherFragment extends Fragment {
    private RecyclerView mRecyclerView;

    //  创建 NerdLauncherFragment 方法
    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }

    //  创建 Fragment 视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
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
        Log.e("TAG", "\n activities have ：" + activities.size() + "\n");
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo resolveInfo, ResolveInfo t1) {
                PackageManager pm = getActivity().getPackageManager();

                return String.CASE_INSENSITIVE_ORDER.compare(
                        resolveInfo.loadLabel(pm).toString(),
                        t1.loadLabel(pm).toString()
                );
            }
        });
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    /*ViewHolder 内部类显示标签名*/
    private class ActivityHolder extends RecyclerView.ViewHolder {
        //        创建引用信息加载获取用户标签
        private ResolveInfo mResolveInfo;
        //        初始化显示控件
        private TextView mNameTextView;


        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }
    }

    /* Adapter 内部了实现数据*/
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        //        初始化数据变量
        private final List<ResolveInfo> mActivities;

        //         构造方法获取 activities
        private ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        //         创建列表布局视图样式
        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }

        //          获取每个activity 信息 并与ViewHolder 进行数据传递并绑定
        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }

        //          获取整个 list 数据
        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
}
