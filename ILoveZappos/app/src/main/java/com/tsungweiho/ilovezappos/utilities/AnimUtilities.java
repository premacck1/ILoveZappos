package com.tsungweiho.ilovezappos.utilities;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.tsungweiho.ilovezappos.R;

/**
 * Created by mike on 2/3/17.
 */

public class AnimUtilities {
    private static Context context;

    public AnimUtilities(Context context) {
        this.context = context;
    }

    public static void setEdAnimToVisible(final EditText editText) {
        editText.setVisibility(View.VISIBLE);
        Animation am = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        editText.setAnimation(am);
        am.startNow();
    }
}
