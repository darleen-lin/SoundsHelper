package com.lin.soundshelper;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.lin.soundshelper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private Animation mTabAnim;
    private SoundPoolUtil mSoundPoolUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_main);
        mBinding.setOnClick(this);
        mSoundPoolUtil = SoundPoolUtil.getInstance();
        mSoundPoolUtil.init(this,R.raw.tabclick_music);
        mTabAnim = AnimationUtils.loadAnimation(this,R.anim.tab_btn_anim);
        mTabAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始
                mSoundPoolUtil.play();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                mSoundPoolUtil.stop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
               //动画重复
            }
        });
    }

    public void onTabClick(View view){
        Toast.makeText(this,"点击了图标",Toast.LENGTH_LONG).show();
        mBinding.tab.startAnimation(mTabAnim);
    }

    @Override
    protected void onDestroy() {
        mSoundPoolUtil.release();
        super.onDestroy();
    }
}
