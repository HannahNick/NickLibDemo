package com.nick.nicklibdemo.constants;

/**
 * Created by Nick on 2017/11/8.
 */

public class BaseUrl {

//    public static final String URL = "http://heyguys.haohuoji.com.cn";//测试环境
//    public static final String TMS_BASEURL = "http://tmsdev.ap88.com";//TMS测试环境
//    public static final String GROUP_ID = "357719618650625";//测试环境GroupId
//    public static final String SA_SERVER_URL = "http://shence.ap-ec.cn:8106/sa?project=ZNW_HHJtest";//神策测试环境

    public static final String URL = "https://shop.haohuoji.com.cn";//正式环境
    public static final String TMS_BASEURL = "https://shop.haohuoji.com.cn";//TMS正式环境
    public static final String GROUP_ID = "367866402263553";//正式环境GroupId
    public static final String SA_SERVER_URL = "http://shence.ap-ec.cn:8106/sa?project=ZNW_HHJ";//神策正式环境

//    public static final String URL = "http://heyguys.ap88.com";
//    public static final String URL = "http://192.168.7.28:8890";
//    public static final String URL = "http://heyguys.haohuoji.com.cn/dev/";//开发环境
    public static final String IMGURL = "https://heyguys-image.oss-cn-shenzhen.aliyuncs.com/";
    public static final String ALIYUN_URL = "http://oss-cn-shenzhen.aliyuncs.com";
    public static final String TMS_ROUTE = "/tms-manager-service/page/getPageById.apec2";
    public static final String TMS_GOODS_LIST = "/TMS-GOODSPOOL-SERVICE/tmsGoodsPool/getGoodsById.apec2";

    /**
     * 用户协议
     */
    public static final String REGIST_AGREEMENT = "/hhjhtml/index.html#/agreement";

    /**
     * 隐私协议
     */
    public static final String SECRET_AGREEMENT = "/hhjhtml/index.html#/api/agreementIOS";

    /**
     * 新闻路由
     */
    public static final String NEWSURL = "/hhjhtml/index.html";

    /**
     * 更多新闻
     */
    public static final String MORENEWSURL = "/hhjhtml/index.html#/noticeList/";

    /**
     * 二级页面路由
     */
    public static final String VUEURL = "/hhjhtml/index.html#/";

    /**
     * 商品详情路由
     */
    public static final String GOODSDETAIL = "/hhjhtml/index.html#/goodsDetail/";

    /**
     * 好伙计首页
     */
    public static final String INDEX = "/hhjhtml/index.html";

    /**
     * 等级路由
     */
    public static final String USER_LEVEL = "/hhjhtml/index.html#/level/levelRule";

    /**
     * 首页商品详情路由,增加城市参数
     */
    public static final String GOODSDETAIL_ROUTER = URL+"/hhjhtml/index.html/goodsDetail/:goodsDetail";

    /**
     * 列表图片等比缩放参数
     */
    public static final String LIST_IMG_PARAMS = "?x-oss-process=image/resize,m_mfit,h_%1$s,w_%2$s";

    /**
     * 列表图片的缩放类型
     */
    public static final int ONE_COLUMN_IMG = 0x01;
    public static final int TWO_COLUMN_IMG = 0x02;
    public static final int HOME_ONE_COLUMN_IMG = 0x03;
    public static final int ONE_ROW_IMG = 0x04;

}
