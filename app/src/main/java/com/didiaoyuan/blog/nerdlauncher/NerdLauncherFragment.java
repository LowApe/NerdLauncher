package com.didiaoyuan.blog.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.ICUCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //        创建引用信息加载获取用户标签
        private ResolveInfo mResolveInfo;
        //        初始化显示控件
        private TextView mNameTextView;
        private ImageView mImageView;

        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView =itemView.findViewById(R.id.list_text_view);
            mImageView = itemView.findViewById(R.id.list_image_view);
            mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            Drawable icon= mResolveInfo.loadIcon(pm);
            mImageView.setImageDrawable(icon);
            mNameTextView.setText(appName);

        }

        @Override
        public void onClick(View view) {
//            从 ResolveInfo 获取包名或类名
            ActivityInfo activityInfo=mResolveInfo.activityInfo;
//            创建 Intent 对象
            Intent i=new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            启动 Intent
            startActivity(i);

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
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
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
