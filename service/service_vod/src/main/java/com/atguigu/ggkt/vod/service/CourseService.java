package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vo.vod.CourseVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-08-10
 */
public interface CourseService extends IService<Course> {

    //点播课程列表
    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    //添加课程基本信息
    Long saveCourseInfo(CourseFormVo courseFormVo);

    //根据id获取课程信息
    CourseFormVo getCourseInfoByid(Long id);

    //修改课程信息
    void updateCourseInfo(CourseFormVo courseFormVo);

    //根据课程id查询发布课程信息
    CoursePublishVo getCoursePublishVo(Long id);

    //课程最终发表
    void removeCourse(Long id);

    //删除课程
    void publishCourse(Long id);

    //根据课程分类查询课程列表
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    //根据课程id查询课程详情
    Map<String, Object> getInfoById(Long courseId);
}
