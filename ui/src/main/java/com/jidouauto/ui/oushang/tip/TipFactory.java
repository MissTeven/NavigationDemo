package com.jidouauto.ui.oushang.tip;

import android.content.Context;

public class TipFactory implements ITipFactory {
    public static ITipFactory getInstance() {
        return TipFactoryHolder.sTipFactory;
    }

    private static class TipFactoryHolder {
        private static final TipFactory sTipFactory = new TipFactory();
    }

    @Override
    public BaseTip getTip(Context context, final String viewTag) {
        return new BaseTip(context);
    }

    @Override
    public BaseTip getLoadingTip(Context context) {
        return new LoadingTip(context);
    }

    @Override
    public BaseTip getSuccessTip(Context context) {
        return new SuccessTip(context);
    }

    @Override
    public BaseTip getFailureTip(Context context) {
        return new FailureTip(context);
    }


}
