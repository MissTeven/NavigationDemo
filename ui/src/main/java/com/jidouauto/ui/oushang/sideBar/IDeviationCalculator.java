package com.jidouauto.ui.oushang.sideBar;

import android.animation.ValueAnimator;

public interface IDeviationCalculator {
    void calculate(int fromPosition, int topPosition);
    IDeviationCalculator setItemHeight(int itemHeight);
    IDeviationCalculator setItemInterval(int itemInterval);
    IDeviationCalculator addOnDeviationCalculatorListener(DeviationCalculator.OnDeviationCalculatorListener onDeviationCalculatorListener);
}
