package com.nick.nicklibdemo.mvp.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Nick on 9/14/22 10:02 AM.
 */
public class GoodsVO {

    private DataBean data;
    private String errorCode;
    private String errorMsg;
    private String repeatAct;
    private boolean succeed;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getRepeatAct() {
        return repeatAct;
    }

    public void setRepeatAct(String repeatAct) {
        this.repeatAct = repeatAct;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public static class DataBean {

        private int pageCount;
        private int currentNo;
        private int totalElements;
        private List<RowsBean> rows;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getCurrentNo() {
            return currentNo;
        }

        public void setCurrentNo(int currentNo) {
            this.currentNo = currentNo;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {

            private String showClient;
            private Object citySkus;
            private String goodsId;
            private String goodsBrandId;
            private String goodsCategory;
            private Object orderMinAmount;
            private String shopName;
            private String goodsBrand;
            private String skuName;
            private Object stockSufficient;
            private String goodsCategoryId;
            private String oecdId;
            private BigDecimal price;
            private String id;
            private String shopId;
            private String goodsName;
            private String cityIds;
            private String skuId;
            private String goodsDescription;
            private Object bsscDate;
            private String goodsNo;
            private Object inventoryAmount;
            private String skuSpec;
            private int maximalBuy;
            private int minimalBuy;
            private String goodsType;
            private String shopTag;
            private int goodsCount;
            private String skuNo;
            private String shopType;
            private String skuImage;
            private String status;
            private List<?> goodsLabels;
            private List<?> actTags;


            public String getShowClient() {
                return showClient;
            }

            public void setShowClient(String showClient) {
                this.showClient = showClient;
            }

            public Object getCitySkus() {
                return citySkus;
            }

            public void setCitySkus(Object citySkus) {
                this.citySkus = citySkus;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsBrandId() {
                return goodsBrandId;
            }

            public void setGoodsBrandId(String goodsBrandId) {
                this.goodsBrandId = goodsBrandId;
            }

            public String getGoodsCategory() {
                return goodsCategory;
            }

            public void setGoodsCategory(String goodsCategory) {
                this.goodsCategory = goodsCategory;
            }

            public Object getOrderMinAmount() {
                return orderMinAmount;
            }

            public void setOrderMinAmount(Object orderMinAmount) {
                this.orderMinAmount = orderMinAmount;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getGoodsBrand() {
                return goodsBrand;
            }

            public void setGoodsBrand(String goodsBrand) {
                this.goodsBrand = goodsBrand;
            }

            public String getSkuName() {
                return skuName;
            }

            public void setSkuName(String skuName) {
                this.skuName = skuName;
            }

            public Object getStockSufficient() {
                return stockSufficient;
            }

            public void setStockSufficient(Object stockSufficient) {
                this.stockSufficient = stockSufficient;
            }

            public String getGoodsCategoryId() {
                return goodsCategoryId;
            }

            public void setGoodsCategoryId(String goodsCategoryId) {
                this.goodsCategoryId = goodsCategoryId;
            }

            public String getOecdId() {
                return oecdId;
            }

            public void setOecdId(String oecdId) {
                this.oecdId = oecdId;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getCityIds() {
                return cityIds;
            }

            public void setCityIds(String cityIds) {
                this.cityIds = cityIds;
            }

            public String getSkuId() {
                return skuId;
            }

            public void setSkuId(String skuId) {
                this.skuId = skuId;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public Object getBsscDate() {
                return bsscDate;
            }

            public void setBsscDate(Object bsscDate) {
                this.bsscDate = bsscDate;
            }

            public String getGoodsNo() {
                return goodsNo;
            }

            public void setGoodsNo(String goodsNo) {
                this.goodsNo = goodsNo;
            }

            public Object getInventoryAmount() {
                return inventoryAmount;
            }

            public void setInventoryAmount(Object inventoryAmount) {
                this.inventoryAmount = inventoryAmount;
            }

            public String getSkuSpec() {
                return skuSpec;
            }

            public void setSkuSpec(String skuSpec) {
                this.skuSpec = skuSpec;
            }

            public int getMaximalBuy() {
                return maximalBuy;
            }

            public void setMaximalBuy(int maximalBuy) {
                this.maximalBuy = maximalBuy;
            }

            public int getMinimalBuy() {
                return minimalBuy;
            }

            public void setMinimalBuy(int minimalBuy) {
                this.minimalBuy = minimalBuy;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public String getShopTag() {
                return shopTag;
            }

            public void setShopTag(String shopTag) {
                this.shopTag = shopTag;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getSkuNo() {
                return skuNo;
            }

            public void setSkuNo(String skuNo) {
                this.skuNo = skuNo;
            }

            public String getShopType() {
                return shopType;
            }

            public void setShopType(String shopType) {
                this.shopType = shopType;
            }

            public String getSkuImage() {
                return skuImage;
            }

            public void setSkuImage(String skuImage) {
                this.skuImage = skuImage;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public List<?> getGoodsLabels() {
                return goodsLabels;
            }

            public void setGoodsLabels(List<?> goodsLabels) {
                this.goodsLabels = goodsLabels;
            }

            public List<?> getActTags() {
                return actTags;
            }

            public void setActTags(List<?> actTags) {
                this.actTags = actTags;
            }
        }
    }
}
