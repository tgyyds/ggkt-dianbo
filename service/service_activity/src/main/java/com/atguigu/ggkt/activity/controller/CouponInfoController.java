package com.atguigu.ggkt.activity.controller;


import com.atguigu.ggkt.activity.service.CouponInfoService;
import com.atguigu.ggkt.model.activity.CouponInfo;
import com.atguigu.ggkt.model.activity.CouponUse;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.activity.CouponUseQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-08-23
 */
@RestController
@RequestMapping("admin/activity/couponInfo")
public class CouponInfoController {

    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation("获取优惠券")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        return Result.ok(couponInfo);
    }

    @ApiOperation("新增优惠券")
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation("修改优惠券")
    @GetMapping("update")
    public Result updateById(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return Result.ok(null);
    }

    @ApiOperation("删除优惠券")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        couponInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("根据id列表删除优惠券")
    @GetMapping("batchRemove")
    public Result get(@RequestBody List<String> idList) {
        couponInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    //优惠券分页列表方法
    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit) {

        Page<CouponInfo> PageParam = new Page<>(page, limit);
        IPage<CouponInfo> pageModel = couponInfoService.page(PageParam);
        return Result.ok(pageModel);
    }

    //获取已使用优惠券列表（条件查询分页）
    @ApiOperation("获取分页列表")
    @GetMapping("couponUse/{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        CouponUseQueryVo couponUseQueryVo) {
        Page<CouponUse> pageParam = new Page<>(page, limit);
        IPage<CouponUse> pageModel =
                couponInfoService.selectCouponUsepage(pageParam, couponUseQueryVo);
        return Result.ok(pageModel);
    }
}



