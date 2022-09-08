package com.atguigu.ggkt.client.activity;

import com.atguigu.ggkt.model.activity.CouponInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-activity")
public interface CouponInfoFeignClient {

    @ApiOperation(value = "获取优惠券")
    @GetMapping(value = "/api/activity/couponInfo/inner/getById/{couponId}")
    CouponInfo getById(@PathVariable("couponId")Long couponId);

    @ApiOperation(value = "修改优惠券使用状态")
    @PostMapping(value = "/api/activity/couponInfo/inner/updateCouponInfoUserStatus/{couponUserId}/{orderId}")
    Boolean updateCouponInfoUserStatus(@PathVariable ("couponUserId") Long couponuserId,
                                              @PathVariable ("orderId") Long orderId);
}
