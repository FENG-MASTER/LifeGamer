package com.lifegamer.fengmaster.lifegamer.util;

import android.os.Bundle;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.lifegamer.fengmaster.lifegamer.App;

/**
 * 语音说话工具包
 * Created by FengMaster on 19/02/11.
 */
public class TTSUtil implements InitListener, SynthesizerListener {
    private static TTSUtil ourInstance=null;

    public static TTSUtil getInstance() {
        if (ourInstance==null){
            synchronized (TTSUtil.class){
                if (ourInstance==null){
                    ourInstance=new TTSUtil();
                }
            }
        }
        return ourInstance;
    }

    /**
     * 发声器
     */
    private SpeechSynthesizer speechSynthesizer;

    private TTSUtil() {
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(App.getContext(), this);
        speechSynthesizer.setParameter(SpeechConstant.PARAMS, null);
        //云端模式
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //青年男声
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,"xiaoyu");
        // 设置发音语速
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        // 设置音调
        speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
        // 设置合成音量
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        speechSynthesizer.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

    }

    @Override
    public void onInit(int i) {

    }

    public void speak(String msg){

        if (!PreferenceUtil.enableTTS()){
            return;
        }

        if (speechSynthesizer.isSpeaking()){
            speechSynthesizer.stopSpeaking();
        }

        speechSynthesizer.startSpeaking(msg,this);



    }

    @Override
    public void onSpeakBegin() {

    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {

    }

    @Override
    public void onSpeakPaused() {

    }

    @Override
    public void onSpeakResumed() {

    }

    @Override
    public void onSpeakProgress(int i, int i1, int i2) {

    }

    @Override
    public void onCompleted(SpeechError speechError) {

    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }
}
