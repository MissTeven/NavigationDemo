package com.jidouauto.ui.oushang.tip;

import android.content.Context;

public interface ITipFactory {
    BaseTip getTip(Context context,String viewTag);
    BaseTip getLoadingTip(Context context);
    BaseTip getSuccessTip(Context context);
    BaseTip getFailureTip(Context context);
}
