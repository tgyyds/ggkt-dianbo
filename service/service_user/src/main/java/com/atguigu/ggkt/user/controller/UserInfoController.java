package com.atguigu.ggkt.user.controller;


import com.atguigu.ggkt.model.user.UserInfo;
import com.atguigu.ggkt.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-24
 */
@RestController
@RequestMapping("admin/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    //获取用户信息
    @ApiOperation("获取用户信息")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id){
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;

    }
}

