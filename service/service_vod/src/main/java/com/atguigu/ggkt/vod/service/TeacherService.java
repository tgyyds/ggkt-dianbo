package com.atguigu.ggkt.vod.service;


import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-07-06
 */
public interface TeacherService extends IService<Teacher> {
    Result findPage(long current,long limit, TeacherQueryVo teacherQueryVo);

}
