package com.eightbitlab.bottomnavigationbar;

import android.view.View;

import androidx.annotation.NonNull;

class TabAnimator {

    private static final int ANIMATION_DURATION = 200;

    static void animateTranslationY(@NonNull final View view, int to) {
        view.animate()
                .translationY(to)
                .setDuration(ANIMATION_DURATION);
    }
}
