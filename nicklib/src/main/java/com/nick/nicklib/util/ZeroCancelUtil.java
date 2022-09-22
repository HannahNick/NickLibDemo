package com.nick.nicklib.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Nick on 2018/1/23.
 * 去除小数末尾的0
 */

public class ZeroCancelUtil {

    private DecimalFormat mDecimalFormat ;

    private ZeroCancelUtil(){
        mDecimalFormat=new DecimalFormat("###,###,###,###.###########");
    }

    public static ZeroCancelUtil getInstance(){
        return Holder.sUtil;
    }

    public String formatBigDecimal(BigDecimal bigDecimal){return formatDouble(bigDecimal.doubleValue());}

    public String formatDouble(double number){
        return mDecimalFormat.format(number);
    }



    private static class Holder{

        private static final ZeroCancelUtil sUtil = new ZeroCancelUtil();
    }
}
