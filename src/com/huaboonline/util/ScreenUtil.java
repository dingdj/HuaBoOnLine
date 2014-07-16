/**
 * @author dingdj
 * Date:2014-7-15下午2:15:00
 *
 */
package com.huaboonline.util;

import android.content.Context;

/**
 * @author dingdj
 * Date:2014-7-15下午2:15:00
 *
 */
public class ScreenUtil {
	
	private static float currentDensity = 0;

	/**
	 * dp转px
	 * @param context
	 * @param dipValue
	 * @return int
	 */
	public static int dip2px(Context context, float dipValue) {
		if (currentDensity > 0)
			return (int) (dipValue * currentDensity + 0.5f);

		currentDensity = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * currentDensity + 0.5f);
	}

}
