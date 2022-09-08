package com.atguigu.ggkt.user.service;

import com.atguigu.ggkt.model.user.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-24
 */
public interface UserInfoService extends IService<UserInfo> {

    //openid查询信息
    UserInfo getUserInfoOpenid(String openId);
}
