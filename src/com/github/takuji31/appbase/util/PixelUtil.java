package com.github.takuji31.appbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class PixelUtil {
	
	public static int dpToPixel(int dp, Context ctx) {
		Resources r = ctx.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				r.getDisplayMetrics());
	}

}
