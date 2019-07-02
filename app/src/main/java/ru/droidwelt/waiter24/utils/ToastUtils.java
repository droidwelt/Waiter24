package ru.droidwelt.waiter24.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;

public class ToastUtils {

    public static void DisplayToastError(String result) {
        Toast toast3 = CustomToast.makeText(Appl.getContext(), result, Toast.LENGTH_LONG, R.mipmap.ic_cancel);
        toast3.show();
    }

    @SuppressWarnings("unused")
    public static void DisplayToastOk(String result) {
        Toast toast3 = CustomToast.makeText(Appl.getContext(), result, Toast.LENGTH_LONG, R.mipmap.ic_ok);
        toast3.show();
    }

    @SuppressWarnings("unused")
    public static void DisplayToastError(int resID) {
        Toast toast3 = CustomToast.makeText(Appl.getContext(), Appl.getContext().getString(resID), Toast.LENGTH_LONG, R.mipmap.ic_cancel);
        toast3.show();
    }

    public static void DisplayToastOk(int resID) {
        Toast toast3 = CustomToast.makeText(Appl.getContext(), Appl.getContext().getString(resID), Toast.LENGTH_LONG, R.mipmap.ic_ok);
        toast3.show();
    }



     static class CustomToast extends Toast {

        private static TextView toastText;
        private static ImageView toastImage;

        private CustomToast(Context context) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;

            View rootView = inflater.inflate(R.layout.toast_info, null);
            toastImage = rootView.findViewById(R.id.iv_mesedit_lbl);
            toastImage.setImageResource(R.mipmap.ic_launcher);
            toastText = rootView.findViewById(R.id.textView1);
            this.setView(rootView);
            this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            this.setDuration(Toast.LENGTH_LONG);
        }


        static CustomToast makeText(Context context, CharSequence text, int duration, int resId) {
            CustomToast result = new CustomToast(context);
            result.setDuration(duration);
            toastText.setText(text);
            toastImage.setImageResource(resId);
            return result;
        }

        public static CustomToast makeText(Context context, CharSequence text, int duration, Drawable dr) {
            CustomToast result = new CustomToast(context);
            result.setDuration(duration);
            toastText.setText(text);
            toastImage.setImageDrawable(dr);
            return result;
        }

        public static CustomToast makeText(Context context, int resID, int duration, Drawable dr) {
            CustomToast result = new CustomToast(context);
            result.setDuration(duration);
            toastText.setText(Appl.getContext().getString(resID));
            toastImage.setImageDrawable(dr);
            return result;
        }

        public static CustomToast makeText(Context context, int resID, int duration, int resIDDraw) {
            CustomToast result = new CustomToast(context);
            result.setDuration(duration);
            toastText.setText(Appl.getContext().getString(resID));
            toastImage.setImageDrawable(Appl.getContext().getDrawable(resID));
            return result;
        }


    }





}
