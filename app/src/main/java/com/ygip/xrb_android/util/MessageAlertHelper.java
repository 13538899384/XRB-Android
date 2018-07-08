package com.ygip.xrb_android.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;

import java.io.IOException;

/**
 * 控制声音跟震动
 * Created by XQM on 2017/7/14.
 */

public class MessageAlertHelper {
    private static MessageAlertHelper helper;
    private static MediaPlayer mediaPlayer;
    private static Vibrator vibrator;
    private static int millisecond = 500;
    public MessageAlertHelper(Context context) {
        mediaPlayer = new MediaPlayer();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        try {
            mediaPlayer.setDataSource(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static synchronized MessageAlertHelper getInstance(Context context){
        if (helper == null){
            synchronized (MessageAlertHelper.class){
                if (helper == null){
                    helper = new MessageAlertHelper(context);
                }
            }
        }
        return helper;
    }

    /**
     * 震动和声音设置
     * @param voiceState
     * @param vibrateState
     */
    public void messageAlert(boolean voiceState, boolean vibrateState){
        if (voiceState){
            mediaPlayer.start();
        }
        if (vibrateState){
            vibrator.vibrate(millisecond);
        }
    }
}
