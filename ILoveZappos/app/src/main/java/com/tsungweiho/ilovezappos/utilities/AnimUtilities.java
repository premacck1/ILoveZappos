package com.tsungweiho.ilovezappos.utilities;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsungweiho.ilovezappos.R;

/**
 * Created by mike on 2/3/17.
 */

public class AnimUtilities {
    private static Context context;

    // Animation duration
    private int ANIM_DURATION = 1000;
    private int FAST_ANIM_DURATION = 500;

    public AnimUtilities(Context context) {
        this.context = context;
    }

    public void setflSearchAnimToVisible(final FrameLayout frameLayout) {
        frameLayout.setVisibility(View.VISIBLE);
        Animation am = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
        am.setDuration(ANIM_DURATION);
        frameLayout.setAnimation(am);
        am.startNow();
    }

    public void showFABAnim(final FloatingActionButton floatingActionButton) {
        floatingActionButton.clearAnimation();
        floatingActionButton.setVisibility(View.VISIBLE);
        Animation am = android.view.animation.AnimationUtils.loadAnimation(
                floatingActionButton.getContext(), R.anim.design_fab_in);
        am.setDuration(ANIM_DURATION);
        floatingActionButton.startAnimation(am);
    }

    public void hideFABAnim(final FloatingActionButton floatingActionButton) {
        floatingActionButton.clearAnimation();
        floatingActionButton.setVisibility(View.INVISIBLE);
        Animation am = android.view.animation.AnimationUtils.loadAnimation(
                floatingActionButton.getContext(), R.anim.design_fab_out);
        am.setDuration(FAST_ANIM_DURATION);
        floatingActionButton.startAnimation(am);
    }

    public void showCartCountAnim(final TextView tvCartItemCount) {
        tvCartItemCount.clearAnimation();
        tvCartItemCount.setVisibility(View.VISIBLE);
        Animation am = android.view.animation.AnimationUtils.loadAnimation(
                tvCartItemCount.getContext(), R.anim.design_fab_in);
        am.setDuration(ANIM_DURATION);
        tvCartItemCount.startAnimation(am);
    }
}
