package com.tsungweiho.ilovezappos.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
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

    public void showFABAnim(final FloatingActionButton fab) {
        fab.clearAnimation();
        fab.setVisibility(View.VISIBLE);
        Animation am = android.view.animation.AnimationUtils.loadAnimation(
                fab.getContext(), R.anim.design_fab_in);
        am.setDuration(ANIM_DURATION);
        fab.startAnimation(am);
    }

    public void hideFABAnim(final FloatingActionButton fab) {
        fab.clearAnimation();
        fab.setVisibility(View.INVISIBLE);
        Animation am = android.view.animation.AnimationUtils.loadAnimation(
                fab.getContext(), R.anim.design_fab_out);
        am.setDuration(FAST_ANIM_DURATION);
        fab.startAnimation(am);
    }

    public void showAddToCartFABAnim(final FloatingActionButton fab) {
        fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.light_green)));
        fab.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_check));
        showFABAnim(fab);
    }

    public void switchFABIconAnim(final FloatingActionButton fab) {
        // fade out animation
        fab.clearAnimation();
        fab.setVisibility(View.VISIBLE);
        Animation amOut = android.view.animation.AnimationUtils.loadAnimation(
                fab.getContext(), R.anim.design_fab_out);
        amOut.setDuration(FAST_ANIM_DURATION);
        amOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // fade in animation
                fab.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));
                fab.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_cart));
                showFABAnim(fab);
            }
        });
        fab.startAnimation(amOut);
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
