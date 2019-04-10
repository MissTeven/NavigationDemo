package com.jidouauto.ui.oushang.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jidouauto.ui.oushang.R;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class JIndicator extends FrameLayout {
    private int tabTitleRes;
    private int selectPosition;
    private int spaceWidth;

    public JIndicator(@Nullable Context context) {
        this(context, null);
    }

    public JIndicator(@Nullable Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JIndicator(@Nullable Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_indicator, this, false);


        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(contentView, lp);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.JIndicator, 0, 0);
        final List<String> titles = new ArrayList<>();
        TypedArray titlesRes = null;
        try {
            tabTitleRes = a.getResourceId(R.styleable.JIndicator_tabTitleRes, 0);
            selectPosition = a.getInteger(R.styleable.JIndicator_selectedPosition, 0);
            spaceWidth = a.getDimensionPixelOffset(R.styleable.JIndicator_spaceWidth, 30);
            titlesRes = getResources().obtainTypedArray(tabTitleRes);
            for (int i = 0; i < titlesRes.length(); i++) {
                String title = titlesRes.getString(i);
                titles.add(title);
            }
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage() + "/" + ex.getCause());
        } finally {
            a.recycle();
            if (titlesRes != null) {
                titlesRes.recycle();
            }
        }
        final MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator3);
        final CommonNavigator commonNavigator = new CommonNavigator(getContext());
        final FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#33FFFFFF"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(27.2f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.WHITE);
                linePagerIndicator.setLineHeight(4);
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            public int getIntrinsicWidth() {
                return spaceWidth;
            }
        });
    }
}
