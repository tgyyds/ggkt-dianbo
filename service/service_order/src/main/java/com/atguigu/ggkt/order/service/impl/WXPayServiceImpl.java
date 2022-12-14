package com.atguigu.ggkt.order.service.impl;

import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.order.service.WXPayService;
import com.atguigu.ggkt.utils.HttpClientUtils;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {

    //微信支付
    @Override
    public Map<String, Object> createJsapi(String orderNo) {

        //封装微信支付需要参数，使用map集合
        Map<String, String> paramMap = new HashMap<>();
        //正式服务号的id
        paramMap.put("appid", "");
        //服务号商户号
        paramMap.put("mch_id", "");
        //生产随机订单号
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //微信支付弹框显示内容
        paramMap.put("body", "test");
        //订单号
        paramMap.put("out_trade_no", orderNo);
        //支付金额
        paramMap.put("total_fee", "1");
        //当前客户端支付的ip
        paramMap.put("spbill_create_ip", "127.0.0.1");
        //支付之后的跳转
        paramMap.put("notify_url", "http://glkt.atguigu.cn/api/order/wxPay/notify");
        //支付类型（弹框，二维码）
        paramMap.put("trade_type", "JSAPI");
        /**
         * 设置参数值当前微信用户openid
         * 目前实现逻辑：1.根据订单号获取userid   2.根据userid获取openid
         *
         * 当前使用当前测试号，测试号不支持支付功能，为了使用正式服务号进行测试，使用下面写法
         * 获取 正式号微信openid
         *通过其他方式获取正式服务号ooenid，直接设置
         */
        paramMap.put("openid","");


        try {
            //微信支付通过httpclient调用微信支付接口
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.ccom/pay/unifiedorder");
            //设置请求参数
            String paramXml = WXPayUtil.generateSignedXml(paramMap, "");
            client.setXmlParam("paramXml");
            client.setHttps(true);
            //请求
            client.post();

            //微信支付接口返回数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //进行封装最终返回
            if(null != resultMap.get("result_code")&& !"SUCCESS".equals(resultMap.get("result_code"))){
                throw new GgktException(20001,"支付失败");
            }

            //再次封装
            Map<String,String> parameterMap = new HashMap<>();
            String prepayId = String.valueOf(resultMap.get("prepay_id"));
            String packages = "prepay_id=" + prepayId;
            parameterMap.put("appId", "");
            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
            parameterMap.put("package", packages);
            parameterMap.put("signType","MD%");
            parameterMap.put("timeStamp",String.valueOf(new Date().getTime()));
            String sign = WXPayUtil.generateSignature(parameterMap,"");

            //返回结果
            Map<String,Object> result = new HashMap<>();
            result.put("appId","");
            result.put("timeStamp",parameterMap.get("timeStamp"));
            result.put("nonceStr",parameterMap.get("nonceStr"));
            result.put("signType",parameterMap.get("MD5"));
            result.put("paySign",sign);
            result.put("package",packages);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据订单号调用微信接口查询支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //封装微信接口需要参数，使用map
        Map paramMap = new HashMap();
//        paramMap.put("appid",wxPayAccountConfig)
        paramMap.put("appid", "");
        paramMap.put("mch_id", "");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

        try {
            //调用接口 httpclient
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ""));
            client.setHttps(true);
            client.post();
            //封装返回参数
            String xml = client.getContent();
            System.out.println(xml);
            Map<String, String> result = WXPayUtil.xmlToMap(xml);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
