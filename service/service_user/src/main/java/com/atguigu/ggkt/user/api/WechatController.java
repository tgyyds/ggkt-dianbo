package com.atguigu.ggkt.user.api;

import com.alibaba.fastjson.JSON;
import com.atguigu.ggkt.model.user.UserInfo;
import com.atguigu.ggkt.user.service.UserInfoService;
import com.atguigu.ggkt.utils.JWTHelper;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/user/wechat")
public class WechatController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    //授权跳转
    @GetMapping("authorize")
    public String authorize(@RequestParam ("returnUrl") String returnUrl,
                            HttpServletRequest request){
//        wxMpService.getOAuth2Service().buildAuthorizationUrl(returnUrl,Wxconsts.OAUTH2_)
        //weixin-java-mp    2.7.0
        String url = wxMpService.oauth2buildAuthorizationUrl(userInfoUrl,
                WxConsts.OAUTH2_SCOPE_USER_INFO,
                URLEncoder.encode(returnUrl.replace("guiguketan","#")));//转码
        return "redirect:"+url;
    }

    @GetMapping("userInfo")
    public String userInfo(@RequestParam ("code") String code,
                           @RequestParam ("state") String returnUrl){
        try {
            //拿着code请求
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            //获取openid
            String openId = wxMpOAuth2AccessToken.getOpenId();
            System.out.println("openId:"+openId);

            //获取微信信息
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            System.out.println("wxMpUser:"+ JSON.toJSONString(wxMpUser));

            //获取微信信息添加数据库
            UserInfo userInfo = userInfoService.getUserInfoOpenid(openId);
            if (userInfo == null){
                userInfo = new UserInfo();
                userInfo.setOpenId(openId);
                userInfo.setNickName(wxMpUser.getNickname());
                userInfo.setAvatar(wxMpUser.getHeadImgUrl());   //头像id
                userInfo.setSex(wxMpUser.getSexId());           //性别
                userInfo.setProvince(wxMpUser.getProvince());   //省
                userInfoService.save(userInfo);
            }

            //授权完成之后，跳转具体功能界面
            //生产token，按照一定规则生产字符串，可以包含用户信息
            String token = JWTHelper.createToken(userInfo.getId(),userInfo.getNickName());
            //localhost:8080/weixin?a=1&token=22
            if(returnUrl.indexOf("?")== -1){    //returnUrl中没有？
                System.out.println("redirect"+returnUrl+"?token="+token);
                return "redirect:"+returnUrl+"?token="+token;
            }else {
                System.out.println("redirect"+returnUrl+"?token="+token);
                return "redirect:"+returnUrl+"&token="+token;
            }


        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

}
