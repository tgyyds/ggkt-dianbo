package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.atguigu.ggkt.vo.vod.VideoVisitorCountVo;
import com.atguigu.ggkt.vod.mapper.VideoVisitorMapper;
import com.atguigu.ggkt.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoVisitorServiceImpl extends ServiceImpl <VideoVisitorMapper,VideoVisitor>implements VideoVisitorService {

    //课程统计的接口
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVoList =
                baseMapper.findCount(courseId, startDate, endDate);
        Map<String ,Object>map = new HashMap<>();
        //封装数据
        //创建两个list集合
        //一个代表所有日期，
        List<String > dateList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        //一个代表日期对应数量
        List<Integer> countList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());
        //放到map集合
        map.put("xData", dateList);
        map.put("Ydata", countList);
        return map;
    }
}
