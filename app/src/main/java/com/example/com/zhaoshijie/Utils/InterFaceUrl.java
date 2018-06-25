package com.example.com.zhaoshijie.Utils;

/**
 * Created by 老赵的拯救者 on 2018/6/9.
 *
 *
 *
 * 接口类
 *
 */

public class InterFaceUrl {

    //轮播图接口
    public static String broadPath = "https://www.zhaoapi.cn/ad/getAd";

    //默认模块(秒杀，全球购等)
    public static String modulePath = "https://www.zhaoapi.cn/product/getCatagory";

    //子分类
    public static String subPath ="https://www.zhaoapi.cn/product/getProductCatagory";

    //子分类下的商品列表
    public static  String product_list_path = "https://www.zhaoapi.cn/product/getProducts";

    //主页商品详情
    public static String homegoods_path = "https://www.zhaoapi.cn/product/getProductDetail";

    //热门搜索
    public static String hot_edit_Path = "https://www.zhaoapi.cn/product/searchProducts";

    //注册接口
    public static String reg_user_path = "https://www.zhaoapi.cn/user/reg";

    //登录接口
    public static String login_user_path = "https://www.zhaoapi.cn/user/login";

    //添加到购物车
    public static final String add_shopping  = "https://www.zhaoapi.cn/product/addCart";

    //查询到购物车
    public static final String query_shopping  = "https://www.zhaoapi.cn/product/getCarts";

    //更新购物车
    public static final String update_shopping  = "https://www.zhaoapi.cn/product/updateCarts";

    //删除购物车
    public static final String delete_shopping  = "https://www.zhaoapi.cn/product/deleteCart";

}
