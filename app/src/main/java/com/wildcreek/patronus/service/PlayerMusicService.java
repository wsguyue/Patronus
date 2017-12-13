package com.wildcreek.patronus.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jiangdg.keepappalive.R;
import com.wildcreek.patronus.utils.LogHelper;

/**循环播放一段无声音频，以提升进程优先级
 *
 * Created by jianddongguo on 2017/7/11.
 * http://blog.csdn.net/andrexpert
 */

public class PlayerMusicService extends Service {
    private final static String TAG = "PlayerMusicService";
    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.error(TAG+"---->onCreate,启动服务");
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.silent);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startPlayMusic();
            }
        }).start();
        return START_STICKY;
    }

    private void startPlayMusic(){
        if(mMediaPlayer != null){

            LogHelper.error("启动后台播放音乐");
            mMediaPlayer.start();
        }
    }

    private void stopPlayMusic(){
        if(mMediaPlayer != null){

            LogHelper.error(TAG + "关闭后台播放音乐");
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayMusic();

        LogHelper.error(TAG+"---->onCreate,停止服务");
        // 重启自己
        Intent intent = new Intent(getApplicationContext(),PlayerMusicService.class);
        startService(intent);
    }
}
