package com.xjh1994.bmob.ui;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import cn.bmob.v3.BmobUser;


/**
 * Created by XJH on 16/3/8.
 * 引导界面
 */
public class GuideActivity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {

        String[] titles = new String[]{"title1", "title2", "title3", "title4"};
        String[] descriptions = new String[]{"description1", "description2", "description3", "description4"};
        int[] images = new int[]{com.xjh1994.bmob.R.mipmap.ic_launcher, com.xjh1994.bmob.R.mipmap.ic_launcher, com.xjh1994.bmob.R.mipmap.ic_launcher, com.xjh1994.bmob.R.mipmap.ic_launcher};
        int[] background_colors = new int[]{com.xjh1994.bmob.R.color.colorPrimary, com.xjh1994.bmob.R.color.colorPrimary, com.xjh1994.bmob.R.color.colorPrimary, com.xjh1994.bmob.R.color.colorPrimary};

        int length = titles.length;
        for (int i = 0; i < length; i++) {
            addSlide(AppIntroFragment.newInstance(titles[i], descriptions[i], images[i], background_colors[i]));
        }

//        addSlide(FragmentFactory.newInstance(R.layout.your_slide_here));
    }

    @Override
    public void onDonePressed() {
        if (BmobUser.getCurrentUser(getApplicationContext()) == null)
            startAnimActivity(LoginActivity.class);
        else
            startAnimActivity(MainActivity.class);
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }

    public void startAnimActivity(Class<?> cla) {
        finish();
        this.startActivity(new Intent(this, cla));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
