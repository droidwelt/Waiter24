package ru.droidwelt.waiter24.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;

public class SoundUtils {

    private static SoundPool sp;
    private static int[] sm;

    public static void playSoundAddPosition() {
        sp.play(sm[0], 1, 1, 1, 0, 1f);
    }

    public static void playSoundDelPosition() {
        sp.play(sm[1], 1, 1, 1, 0, 1f);
    }

    public static void playSoundCallWaiter() {
        sp.play(sm[2], 1, 1, 1, 0, 1f);
    }

    public static void playSoundRequestBill() {
        sp.play(sm[4], 1, 1, 1, 0, 1f);
    }

    public static void playSoundSendOrder() {
        sp.play(sm[3], 1, 1, 1, 0, 1f);
    }

    public static void initSound() {

        int maxStreams = 1;
        sp = new SoundPool.Builder()
                .setMaxStreams(maxStreams)
                .build();

        Context c= Appl.getContext();
        sm = new int[5];
        sm[0] = sp.load(c, R.raw.add_position, 1);
        sm[1] = sp.load(c, R.raw.del_position, 1);
        sm[2] = sp.load(c, R.raw.call_waiter_1, 1);
        sm[3] = sp.load(c, R.raw.send_order, 1);
        sm[4] = sp.load(c, R.raw.request_bill, 1);

        AudioManager amg = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
    }




    public static void vibrate() {
        Vibrator v = (Vibrator) Appl.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE));
           // long[] pattern = {0, 400, 200, 400};
           // v.vibrate(VibrationEffect.createWaveform(pattern,5));
        } else {
            //deprecated in API 26
            v.vibrate(1500);
        }
    }

}
