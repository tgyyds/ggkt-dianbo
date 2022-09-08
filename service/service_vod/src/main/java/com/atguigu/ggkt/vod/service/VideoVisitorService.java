package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface VideoVisitorService extends IService<VideoVisitor> {

    ////课程统计的接口
    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
