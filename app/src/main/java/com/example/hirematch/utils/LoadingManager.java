package com.example.hirematch.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.example.hirematch.R;

public class LoadingManager {

    private static long startTime;
    private static Dialog dialog;

    public static void show(Context context) {

        if (dialog != null && dialog.isShowing())
            return;

        startTime = System.currentTimeMillis();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
    }

    public static void hide() {

        if (dialog == null || !dialog.isShowing())
            return;

        long elapsed = System.currentTimeMillis() - startTime;

        long MIN_TIME = 1500;   // 1.5 seconds

        if (elapsed >= MIN_TIME) {

            dialog.dismiss();

        } else {

            new android.os.Handler().postDelayed(() -> {

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

            }, MIN_TIME - elapsed);
        }
    }

}