package com.tsungweiho.ilovezappos.utilities;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tsungweiho.ilovezappos.R;

/**
 * Created by mike on 2/3/17.
 */

public class AnimUtilities {
    private static Context context;

    public AnimUtilities(Context context) {
        this.context = context;
    }

    public static void setflSearchAnimToVisible(final FrameLayout frameLayout) {
        frameLayout.setVisibility(View.VISIBLE);
        Animation am = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        frameLayout.setAnimation(am);
        am.startNow();
    }

    public static void setllSearchResultAnimToVisible(final LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
        Animation am = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        linearLayout.setAnimation(am);
        am.startNow();
    }
}
