package com.didiaoyuan.blog.nerdlauncher;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class NerdLauncherActivity extends SimpleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return  NerdLauncherFragment.newInstance();
    }
}
