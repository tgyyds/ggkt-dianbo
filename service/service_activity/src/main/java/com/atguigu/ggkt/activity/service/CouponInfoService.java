package com.atguigu.ggkt.activity.service;

import com.atguigu.ggkt.model.activity.CouponInfo;
import com.atguigu.ggkt.model.activity.CouponUse;
import com.atguigu.ggkt.vo.activity.CouponUseQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-23
 */
public interface CouponInfoService extends IService<CouponInfo> {

    //获取已使用优惠券列表（条件查询分页）
    IPage<CouponUse> selectCouponUsepage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);

    //更新优惠券
    void updateCouponInfoUserStatus(Long couponuserId, Long orderId);
}
