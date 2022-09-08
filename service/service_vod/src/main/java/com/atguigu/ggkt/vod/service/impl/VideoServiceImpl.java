package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-10
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    //删除小节 同时删除小节里的视频
    @Override
    public void removeVideoById(Long id) {
        //id查询小节
        Video video = baseMapper.selectById(id);
        //获取video里的视频id
        String videoSourceId = video.getVideoSourceId();
        //判断视频id是否为空
        if(!StringUtils.isEmpty(videoSourceId)){
            //视频id不为空，根据视频id删除腾讯云视频
            vodService.removeVideo(videoSourceId);
        }
        //根据id删除小节
        baseMapper.deleteById(id);
    }

    //根据课程id删除小节    删除所有小节视频
    @Override
    public void removeVideoByCourseId(Long id) {
        //根据课程id查询课程所有小节
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Video> videoList = baseMapper.selectList(wrapper);

        //遍历所有小节集合得到每个小节，获取每个小节视频id
        videoList.forEach(video -> {
            String videoSourceId = video.getVideoSourceId();
            //判断视频id是否为空
            if(!StringUtils.isEmpty(videoSourceId)){
                //视频id不为空，根据视频id删除腾讯云视频
                vodService.removeVideo(videoSourceId);
            }
        });

        //根据课程id删除课程所有小节
        baseMapper.delete(wrapper);
    }
}
