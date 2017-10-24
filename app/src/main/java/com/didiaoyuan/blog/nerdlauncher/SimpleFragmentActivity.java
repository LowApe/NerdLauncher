package com.didiaoyuan.blog.nerdlauncher;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr.Qu on 2017/10/24.
 */

public abstract class SimpleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        加载 fragment 布局
        setContentView(R.layout.activity_fragment);
//        获取 FragmentManager
        FragmentManager fm = getSupportFragmentManager();
//        通过 fm 获取队列中的 fragment
        Fragment fragment = fm.findFragmentById(R.id.activity_container);
//        如果没有 fragment 给fm 添加 fragment
        if (fragment == null) {
            fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.activity_container,fragment)
                    .commit();
        }
    }
}
