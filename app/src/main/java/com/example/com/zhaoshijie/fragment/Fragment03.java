package com.example.com.zhaoshijie.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.com.zhaoshijie.R;
import com.example.com.zhaoshijie.Utils.InterFaceUrl;
import com.example.com.zhaoshijie.Utils.OkhttpManager;
import com.example.com.zhaoshijie.Utils.PayResult;
import com.example.com.zhaoshijie.Utils.SignUtils;
import com.example.com.zhaoshijie.adapter.QueryAdapter;
import com.example.com.zhaoshijie.model.QueryBean;
import com.example.com.zhaoshijie.view.LoginActivity;
import com.example.com.zhaoshijie.view.MainActivity;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 老赵的拯救者 on 2018/6/8.
 */

public class Fragment03 extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRv;
    private CheckBox mCheckbox2;
    // 商户PID
    public static final String PARTNER = "2088221871911835";
    // 商户收款账号
    public static final String SELLER = "zhuangshiyigou@163.com";
    // 商户私钥(你配置的东西)，pkcs8格式,注意复制到这里的公私钥信息要掐头去尾,没有回车,空格,就一行.
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALmhW0c+ZO7sB3utNmkQu5hkpqnw+nAPLCdBoPEw+E+7qcOZBdXxwZsG05yr2BYp5j0MTE0FpKRu+uNTVTAX/MzhcInAColtnWkF1R8rsIdlLdleRoMcM3Lo4XesctdsyxLeummrFLKQfASMqS64kkTUoEFGb3tlYZs7iSFgpM1pAgMBAAECgYAGm6zhK2JycuqNR4xBTzwuX57jO9XeeVvMBfURwPmF9RtFAESJ6jJHL4YG9MMbfuBYWgC5WTMUO3Mo9oV40dHI9dPwANL5aPeKEIUawoQyuCyY/js84fOY3+TbEnXym8G6+Zxm+bGKQn3ZZqlSgR3CHk5f/CceoXvPWDLwl//RtQJBANzDaQTeckTUEtxVyZ9vM26Sv+TKULvqf8OHdrGm7WOWY6/CbChrxMKHxkdrNwZPNIhozNfTaOmnJaPqeMKo8SMCQQDXQmEPHj0h4Qjn3D6n6WiNOvUsNbzVpLP/TgwHFYkRLcz+GPRkbXXdvkSUKxNo7vwZr8vwTIquYA+K3CFTpr4DAkEAu3Ox2NCJdqgc27p8WUSzB1DUYBDqPKYBlqWPw4laSRWJz9Pmwuu/Ru7DDiGbt1/J24ohZaG9k6i57VVK9P8+wQJBAIgGtFrfWvY7xGrwbM+i2aTVqvTDCI9hQzWEVmlrnHA0pyOzFU0ZNrBneeK/zcYzry90PcWeOMy0e13eeVjpN40CQQCMBjVBeTdQ9afgGnBR6glIWrCtqTBAsUIr3gvNZYWdaznr0FmG2pjLwDLUsx0SUCpcrTQxhWu16HDEyQuCm7Ar";
    // 支付宝公钥,不是你本地生成
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mTvNum;
    private InterFaceUrl url;
    private OkhttpManager manager;
    private QueryAdapter queryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment03, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean("isLog", false);
        if (b) {
            Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
            getQueryDate();
        } else {
            Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    //查询购物车
    private void getQueryDate() {
        int uid = getActivity().getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
        manager.asynStringByDATA(url.query_shopping + "?uid=" + uid, new OkhttpManager.Fun1() {
            @Override
            public void onResponse(String result) {
                QueryBean bean = new Gson().fromJson(result, QueryBean.class);
                if (bean == null) {
                    queryAdapter = null;
                    mRv.setAdapter(queryAdapter);
                    mRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                } else {
                    List<QueryBean.DataBean> beans = bean.getData();
                    if (beans != null) {
                        //用户总价
                        int p = 0;
                        for (int i = 0; i < beans.size(); i++) {
                            List<QueryBean.DataBean.ListBean> list = beans.get(i).getList();
                            for (int j = 0; j < list.size(); j++) {
                                int selected = list.get(j).getSelected();
                                if (selected == 1) {
                                    int price = list.get(j).getPrice();
                                    int num = list.get(j).getNum();
                                    int i1 = price * num;
                                    p += i1;
                                }
                            }
                        }
                        mTvPrice.setText("￥：" + (float) p);
                        queryAdapter = new QueryAdapter(getActivity(), beans);
                        mRv.setAdapter(queryAdapter);
                        mRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        //更新购物车
                        queryAdapter.setOnUpdateClickListener(new QueryAdapter.OnClickUpdateListener() {
                            @Override
                            public void onItemupdateClick(int uid, int sellerid, int pid, int selected, int num) {
                                setUpdate(uid, sellerid, pid, selected, num);
                            }
                        });
                    }
                }
                //长按删除商品
                queryAdapter.setOnItemLongClickListener(new QueryAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemClick(final int position, final List<QueryBean.DataBean.ListBean> slist) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                        builder.setMessage("确认删除吗？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("test", "下标是； " + position);
                                        final int pid = slist.get(position).getPid();
                                        int uid = getActivity().getSharedPreferences("Login", MODE_PRIVATE).getInt("uid", 0);
                                        String drop_shop = InterFaceUrl.delete_shopping + "?uid=" + uid + "&pid=" + pid;
                                        Log.i("test", "删除购物车的地址: " + drop_shop);
                                        manager.asynStringByDATA(drop_shop, new OkhttpManager.Fun1() {
                                            @Override
                                            public void onResponse(String result) {
                                                getQueryDate();
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
            }

            //更新购物车的方法
            private void setUpdate(int uid, int sellerid, int pid, int selected, int num) {
                manager = new OkhttpManager();
                manager.asynStringByDATA(
                        InterFaceUrl.update_shopping + "?uid=" + uid + "&sellerid=" + sellerid +
                                "&pid=" + pid + "&selected=" + selected + "&num=" + num,
                        new OkhttpManager.Fun1() {
                            @Override
                            public void onResponse(String result) {
                                Log.i("test", "更新的数据: " + result);
                                getQueryDate();
                            }
                        });
            }
        });
    }


    //获取控件Id
    private void initView(View v) {
        url = new InterFaceUrl();
        manager = new OkhttpManager();
        mRv = (RecyclerView) v.findViewById(R.id.rv);
        mCheckbox2 = (CheckBox) v.findViewById(R.id.checkbox2);
        mCheckbox2.setOnClickListener(this);
        mTvPrice = (TextView) v.findViewById(R.id.tv_price);
        mTvNum = (TextView) v.findViewById(R.id.tv_num);
        mTvNum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_num: //点击支付
                setPay();
                break;
            case R.id.checkbox2:
                if (mCheckbox2.isChecked()) {
                    Toast.makeText(getActivity(), "选中", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "未选中", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //支付宝支付
    private void setPay() {


        //对公私进行非空判断.
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            //对话框
            new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            getActivity().finish();
                        }
                    }).show();
            return;
        }

        //生成订单信息,拼接字符串,订单号,公司立马服务器接口(url)
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！对订单信息用我们上面的私钥加密.
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做UTF-8编码,我们发送内容符合支付宝要求,无论是否是UTF编码,我们都转一下.
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息,完全就是为了符合支付宝的要求
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        //在一个子线程中,进行处理,是一个同步操作.网络请求,由客户端,把数据直接传送给支付宝服务器
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getActivity());
                // 调用支付接口，获取支付结果,你要知道传的参数是什么,给你结果是什么,你拿去用就行.
                String result = alipay.pay(payInfo, true);

                //用handler把支付的结果更新到UI界面上.
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号,支付宝使用方法模拟了一个订单编号,实际开发中服务器会给我们一个订单编号,时间具备了唯一性
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径(就是自己服务器如果想知道支付到底有没有成功,当支付宝服务器支付完成后,会调用此链接进行通知,此链接由服务器开发人员设置)
        // 这个接口网址可以改成自己服务器的,不过我们没有,所以就不改了
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     * 使用到了时间戳,和产生随机数的类.来保证订单唯一性(时间戳+随机数),不是我们做的
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        //参数1.内容订单信息    参数2.私钥
        return SignUtils.sign(content, RSA_PRIVATE);
    }


}
