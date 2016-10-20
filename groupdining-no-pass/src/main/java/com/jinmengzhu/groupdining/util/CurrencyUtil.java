package com.jinmengzhu.groupdining.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CurrencyUtil {
	public static float formatFloat(String price) {
		if(price == null || price.equals("")){
			return 0;
		}
		float ff=Float.parseFloat(price);
		DecimalFormat decimalFormat =new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		decimalFormat.format(ff);//format 返回的是字符串
		return ff;
	}

	public static double formatDouble(String discount) {
		
		BigDecimal df = new BigDecimal(Double.parseDouble(discount));
        double rate2 = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return rate2;
	}
}
