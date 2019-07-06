package com.lin.soundshelper;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linxiujie on 2018/6/24.
 * TODO：音频播放工具类
 */

public class SoundPoolUtil {

    private static volatile SoundPoolUtil mInstance;

    private Context mContext;

    //新的音频播放器
    private SoundPool mSoundPool = null;
    //音频数量
    private int maxStreams=1;
    //音频资源
    public static Map<Integer, Integer> soundMap;

    public SoundPoolUtil(){}

    public static SoundPoolUtil getInstance() {
        if (mInstance == null) {
            synchronized (SoundPoolUtil.class) {
                if (mInstance == null) {
                    mInstance = new SoundPoolUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void init(Context context, int res){
        this.mContext = context;
        initSoundPool();
        loadResource(res);

    }

    public void initSoundPool(){

        if (Build.VERSION.SDK_INT >= 21){
            SoundPool.Builder builder = new SoundPool.Builder();

            //可同时播放的音频流
            builder.setMaxStreams(1);
            //音频属性的Builder
            AudioAttributes.Builder attrBuild = new AudioAttributes.Builder();
            //音频类型
            attrBuild.setLegacyStreamType(AudioManager.STREAM_MUSIC);

            builder.setAudioAttributes(attrBuild.build());

            mSoundPool = builder.build();
        }else{
            mSoundPool =  new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
    }

    public void loadResource(int res){
        soundMap=new HashMap<>();
        soundMap.put(0,mSoundPool.load(mContext,res,1));
    }

    //播放
    public void play(){
        mSoundPool.play(soundMap.get(0),1,1,0,0,2);
    }

    //暂停
    public void pause(){
        if(mSoundPool != null){
            mSoundPool.pause(soundMap.get(0));
        }
    }

    //停止
    public void stop(){
        if(mSoundPool != null){
            mSoundPool.stop(soundMap.get(0));
        }
    }

    //释放资源
    public void release(){
        if(mSoundPool!=null){
            mSoundPool.release();
        }
    }



}
