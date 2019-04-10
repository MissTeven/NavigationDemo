package com.jidouauto.ui.oushang.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;
import com.jidouauto.ui.oushang.widget.JButton;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDialog implements IDialog {
    private Dialog mDialog;
    private Context context;
    private String title;
    private boolean isShowBottomButtons = true;
    private boolean isCanceledOnTouchOutside = true;
    private float mDimAmount = 0.5f;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private View contentView;
    private List<DialogButtonBuilder> buttonBuilders = new ArrayList<>();
    private boolean isRightShifted = true;
    private boolean isVerticalSpaced = true;

    BaseDialog(Context context) {
        this.context = context;
    }

    protected abstract View createContentView();

    @Override
    public void show() {
        if (mDialog == null) {
            mDialog = createDialog();
        }
        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void hide() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    public boolean isFinished() {
        return context == null || ((Activity) context).isFinishing();
    }

    private Dialog createDialog() {
        if (isFinished()) return null;
        ViewGroup contentView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.dialog_base, null);
        ScrollView scrollView = contentView.findViewById(R.id.scrollView);
        FrameLayout.LayoutParams scrollViewLp = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
        if (isRightShifted) {
            scrollViewLp.setMargins(getContext().getResources().getDimensionPixelOffset(R.dimen.sidebar_width) / 2, 0, 0, 0);
        } else {
            scrollViewLp.setMargins(0, 0, 0, 0);
        }
        View fl_main = contentView.findViewById(R.id.fl_main);
        View ll_main_container = contentView.findViewById(R.id.ll_main_container);
        ll_main_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        fl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        View iv_cancel = contentView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        FrameLayout fl_content_container = contentView.findViewById(R.id.fl_content_container);
        if (isVerticalSpaced){
            fl_content_container.setPadding(0, getContext().getResources().getDimensionPixelOffset(R.dimen.padding_vertical_dialog_content), 0, getContext().getResources().getDimensionPixelOffset(R.dimen.padding_vertical_dialog_content));
        }else {
            fl_content_container.setPadding(0, 0, 0, 0);
        }
        setContent(fl_content_container);
        setConfig(contentView);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setDimAmount(mDimAmount);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.setOnDismissListener(mOnDismissListener);
        return dialog;
    }

    private void setConfig(View contentView) {
        TextView tv_title = contentView.findViewById(R.id.tv_title);
        tv_title.setText((title == null || title.length() == 0) ? getContext().getText(R.string.tip) : title);
        LinearLayout ll_btns_container = contentView.findViewById(R.id.ll_btns_container);
        setBottomButtons(ll_btns_container);
        ll_btns_container.setVisibility(isShowBottomButtons ? View.VISIBLE : View.GONE);
    }

    private void setContent(FrameLayout contentContainer) {
        if (contentView == null) {
            this.contentView = createContentView();
        }
        if (contentView != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contentView.setLayoutParams(lp);
            contentContainer.addView(contentView);
        }
    }

    private void setBottomButtons(LinearLayout buttonsContainer) {
        if (buttonBuilders.size() == 0) {
            buttonsContainer.addView(initButton(DialogButtonBuilder.builder().setPosition(0)));
        } else {
            buttonsContainer.removeAllViews();
            for (int i = 0; i < buttonBuilders.size(); i++) {
                final DialogButtonBuilder dialogButtonBuilder = buttonBuilders.get(i).setPosition(i);
                Button btn = initButton(dialogButtonBuilder);
                if (buttonBuilders.size() > 1 && i > 0) {
                    View dividerLine = new View(context);
                    LinearLayout.LayoutParams dlLp = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
                    dividerLine.setBackgroundColor(context.getApplicationContext().getResources().getColor(R.color.color_divider_dialog));
                    dividerLine.setLayoutParams(dlLp);
                    buttonsContainer.addView(dividerLine);
                }
                buttonsContainer.addView(btn);
            }
        }
    }

    private Button initButton(final DialogButtonBuilder dialogButtonBuilder) {
        Button btn = new JButton(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        btn.setGravity(Gravity.CENTER);
        btn.setLayoutParams(lp);

        btn.setText(dialogButtonBuilder.getTextRes());
        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getApplicationContext().getResources().getDimensionPixelSize(dialogButtonBuilder.getTextSize()));
        btn.setTextColor(context.getResources().getColor(dialogButtonBuilder.getTextColorRes()));
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (dialogButtonBuilder.getOnClickListener() != null) {
                    dialogButtonBuilder.getOnClickListener().onClick(v);
                }
            }
        });
        return btn;
    }

    public Context getContext() {
        return context;
    }

    public BaseDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public BaseDialog setTitle(int titleRes) {
        return setTitle(getContext().getString(titleRes));
    }

    public BaseDialog addButton(DialogButtonBuilder buttonBuilder) {
        buttonBuilders.add(buttonBuilder);
        return this;
    }

    public BaseDialog showBottomButtons(boolean showBottomButtons) {
        isShowBottomButtons = showBottomButtons;
        return this;
    }

    public BaseDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        isCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public BaseDialog setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    /**
     * @param dimAmount
     * @return 設置Dialog背景透明度
     */
    public BaseDialog setDimAmount(float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * @param rightShifted
     * @return 设置是否右偏移退出左边栏空间
     */
    public BaseDialog setRightShifted(boolean rightShifted) {
        isRightShifted = rightShifted;
        return this;
    }

    /**
     * @param verticalSpaced
     * @return 设置弹窗内容是否有上下边距
     */
    public BaseDialog setVerticalSpaced(boolean verticalSpaced) {
        isVerticalSpaced = verticalSpaced;
        return this;
    }
}
