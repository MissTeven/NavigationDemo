package com.jidouauto.ui.oushang.wheel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by you on 2017/9/26.
 * 作QQ: 86207610
 */

class SimpleWheelDecoration extends WheelDecoration {
    /**
     * wheel item颜色与中心选中时的颜色
     */
    private final int textColor, textColorCenter;
    /**
     * 画文本居中时文本画笔的高度
     */
    private final float textHeight;
    /**
     * wheel paint, dividerPaint
     */
    private final Paint paint;

    private final WheelAdapter adapter;

    SimpleWheelDecoration(WheelAdapter adapter, int gravity, float gravityCoefficient, int textColor, int textColorCenter, float textSize) {
        super(adapter.itemCount, adapter.itemSize, gravity, gravityCoefficient);
        this.textColor = textColor;
        this.textColorCenter = textColorCenter;
        this.adapter = adapter;

        this.paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = paint.getFontMetrics();
        textHeight = (fm.bottom + fm.top) / 2.0f;
    }

    @Override
    void drawItem(Canvas c, Rect rect, int position, int alpha, boolean isCenterItem, boolean isVertical) {
        String s = adapter.getItemString(position);
        paint.setColor(isCenterItem ? textColorCenter : textColor);
        paint.setAlpha(alpha);
        //在rect区域内画居中文字
        c.drawText(s, rect.exactCenterX(), rect.exactCenterY() - textHeight, paint);
    }

    @Override
    void drawDivider(Canvas c, Rect rect, boolean isVertical) {

    }

}
