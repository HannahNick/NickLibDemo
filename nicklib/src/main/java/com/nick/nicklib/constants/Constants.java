package com.nick.nicklib.constants;

import android.Manifest.permission;
import android.os.Environment;

/**
 * Created by Nick on 2017/9/11.
 */

public class Constants {
    /**
     * 权限常量相关
     */
    public static final int WRITE_READ_EXTERNAL_CODE = 0x01;
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION = new String[]{permission.WRITE_EXTERNAL_STORAGE,
            permission.READ_EXTERNAL_STORAGE};

    public static final int HARDWEAR_CAMERA_CODE = 0x02;
    public static final String[] HARDWEAR_CAMERA_PERMISSION = new String[]{permission.CAMERA};

    public static final int READ_CONTACTS_CODE = 0x03;
    public static final String[] READ_CONTACTS_PERMISSION = new String[]{permission.READ_CONTACTS};

    public static final int LOCATION_CODE = 0x04;
    public static final String[] LOCATION_PERMISSION = new String[]{permission.ACCESS_COARSE_LOCATION};

    public static final int CUSTOM_PERMISSION_CODE = 0x05;

    public static final int ALL_CODE = 0x16;
    public static final String[] ALL_PERMISSION = new String[]{permission.ACCESS_COARSE_LOCATION, permission.READ_CONTACTS, permission.CAMERA,
            permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE, permission.RECORD_AUDIO};

    public static final int GET_UNKNOWN_APP_SOURCES_REQUEST_CODE = 0x10;

    /**
     * 整个应用文件下载保存路径
     */
    public static String APP_DIR = Environment.
            getExternalStorageDirectory().getAbsolutePath().
            concat("/haohuoji/");

    /**
     * 首页组件类型
     */
    public static final String IMAGE_TITLE = "PolyHotArea";
    public static final String BANNER_CARD = "PolyCarousel";
    public static final String NEWS = "PolyScrollNews";
    public static final String NEWGOODS_ONE = "PolyVerticalBox";
    public static final String BRANDDAY = "PolyHorizontalScrollList";
    public static final String LAUNCHER = "PolyNavBox";
    public static final String GROUPBUY_THREE = "PolyCube";
    public static final String HEYGUNS_RECOMMEND = "PolyVerticalBox";
    public static final String SECKILL = "PolySecKill";

    /**
     * l5天时间
     */
    public static final long SESSION_TIME = 1296000000;

    /**
     * 节假日时间
     */
    public static final String FESTIVAL_START = "2019-9-11 00:00:00";
    public static final String FESTIVAL_END = "2019-9-13 23:59:59";

    /**
     * 会员等级
     */
    public static final String NO_LEVEL = "NO_LEVEL";//无
    public static final String ORDINARY_LEVEL = "ORDINARY_LEVEL";//普通会员
    public static final String SILVER_LEVEL = "SILVER_LEVEL";//白银会员
    public static final String GOLD_LEVEL = "GOLD_LEVEL";//黄金会员
    public static final String DIAMOND_LEVEL = "DIAMOND_LEVEL";//钻石会员
    public static final String SUPREME_LEVEL = "SUPREME_LEVEL";//至尊会员

    /**
     * 用户特权
     */
    public static final String AFTER_SALES_PRIORITY = "AFTER_SALES_PRIORITY";//售后优先处理
    public static final String MEMBER_EXCLUSIVE_PRICE = "MEMBER_EXCLUSIVE_PRICE";//会员专享价
    public static final String POINTS_DOUBLED = "POINTS_DOUBLED";//积分翻倍
    public static final String EVALUATION_REWARD = "EVALUATION_REWARD";//评价奖励
    public static final String EXCLUSIVE_MEMBER_PACKAGE = "EXCLUSIVE_MEMBER_PACKAGE";//会员专享礼包
    public static final String NEW_PRODUCT_FREE_EXPERIENCE = "NEW_PRODUCT_FREE_EXPERIENCE";//新品免费体验
    public static final String TIME_LIMIT = "TIME_LIMIT";//限时达
    public static final String NO_THRESHOLD_DELIVERY = "NO_THRESHOLD_DELIVERY";//无门槛配送

    /**
     * 积分明细itemTitle对照
     */
    public static final String INCOME_SIGN_IN = "INCOME_SIGN_IN";
    public static final String INCOME_EVALUATE = "INCOME_EVALUATE";
    public static final String INCOME_OFFSET_EXPEND_CANCEL = "INCOME_OFFSET_EXPEND_CANCEL";
    public static final String INCOME_COMPLETE_ORDER = "INCOME_COMPLETE_ORDER";
    public static final String CONSUME_COMPLETE_ORDER_REFUND = "CONSUME_COMPLETE_ORDER_REFUND";
    public static final String CONSUME_OFFSET_EXPEND = "CONSUME_OFFSET_EXPEND";
    public static final String CONSUME_EXCHANGE_GOODS = "CONSUME_EXCHANGE_GOODS";

    /**
     * 支付方式
     */
    public static final String COD_TYPR = "PAY_COD";
    public static final String WEIXIN_TYPR = "PAY_WEIXIN";
    public static final String ALIPAY_TYPR = "PAY_ALIPAY";
    public static final String BALANCE_TYPR = "PAY_BALANCE";

    public static final String APPID = "wx05890d2d0ce64970";

    /**
     * 第三方商家标识
     */
    public static final String OTHER_SUPPORT = "OTHER_SUPPORT";

    /**
     * 分页显示每页的数量
     */
    public static final int PAGESIZE=20;

    public static final int REGISTER_TYPE = 1;
    public static final int MODIFI_TYPE = 2;
    public static final int LOGIN_TYPE = 3;
}
