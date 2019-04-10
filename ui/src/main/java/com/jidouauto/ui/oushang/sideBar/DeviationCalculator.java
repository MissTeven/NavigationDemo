package com.jidouauto.ui.oushang.sideBar;

import android.animation.ValueAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DeviationCalculator implements IDeviationCalculator {
    private static final String TAG = "DeviationCalculator";

    public interface OnDeviationCalculatorListener {
        void onCalculated(int position, int top, int bottom, int passedPosition, boolean s2b);

        void startRolling(int startPosition);

        void endRolling(int endPosition);
    }

    private int mItemHeight = 176;
    private int mItemInterval = 200;
    private ValueAnimator mValueAnimator;
    private List<OnDeviationCalculatorListener> mOnDeviationCalculatorListeners;

    public DeviationCalculator() {
        mOnDeviationCalculatorListeners = new ArrayList<>();
    }

    @Override
    public void calculate(final int fromPosition, final int toPosition) {
        Log.d(TAG, "calculate: fromPosition=> " + fromPosition + " toPosition=>" + toPosition + "\n");
        if (fromPosition == toPosition) {
            return;
        }
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        mValueAnimator = ValueAnimator.ofInt(fromPosition * mItemHeight, toPosition * mItemHeight);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(200);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int residue = value % mItemHeight;
                int position = value / mItemHeight;
                Log.d(TAG, "onAnimationUpdate: value=> " + value + " residue=>" + residue + " position=>" + position + "\n");
                if (toPosition > fromPosition) {
                    if (residue == 0) {
                        if (value == toPosition * mItemHeight) {
                            onCalculated(position, 0, 0, position, true);
                            endRolling(toPosition);
                        } else {
                            onCalculated(position, 0, mItemHeight, position, true);
                        }
                    } else {
                        onCalculated(position, residue, 0, position - 1, true);
                        onCalculated(position + 1, 0, mItemHeight - residue, position - 2, true);
                    }
                } else {
                    if (residue == 0) {
                        if (value == toPosition * mItemHeight) {
                            endRolling(toPosition);
                            onCalculated(position, 0, 0, position, false);
                        } else {
                            onCalculated(position, 0, mItemHeight, position, false);
                        }
                    } else {
                        onCalculated(position, residue, 0, position + 1, false);
                        onCalculated(position + 1, 0, mItemHeight - residue, position + 2, false);
                    }
                }
            }
        });
        mValueAnimator.start();
        startRolling(fromPosition);
    }

    private void startRolling(int fromPosition) {
        for (OnDeviationCalculatorListener bean : mOnDeviationCalculatorListeners) {
            bean.startRolling(fromPosition);
        }
    }

    private void endRolling(int toPosition) {
        for (OnDeviationCalculatorListener bean : mOnDeviationCalculatorListeners) {
            bean.endRolling(toPosition);
        }
    }

    private void onCalculated(int position, int top, int bottom, int passedPosition, boolean s2b) {
        for (OnDeviationCalculatorListener bean : mOnDeviationCalculatorListeners) {
            bean.onCalculated(position, top, bottom, passedPosition, s2b);
        }
    }

    public IDeviationCalculator addOnDeviationCalculatorListener(OnDeviationCalculatorListener onDeviationCalculatorListener) {
        boolean isContain = false;
        for (OnDeviationCalculatorListener bean : mOnDeviationCalculatorListeners) {
            if (bean == onDeviationCalculatorListener) {
                isContain = true;
            }
        }
        if (!isContain) {
            mOnDeviationCalculatorListeners.add(onDeviationCalculatorListener);
        }
        return this;
    }

    public IDeviationCalculator setItemHeight(int itemHeight) {
        mItemHeight = itemHeight;
        return this;
    }

    public IDeviationCalculator setItemInterval(int itemInterval) {
        mItemInterval = itemInterval;
        return this;
    }
}
