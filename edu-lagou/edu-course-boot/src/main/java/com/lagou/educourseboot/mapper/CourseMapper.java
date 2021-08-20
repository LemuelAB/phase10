package com.lagou.educourseboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.educourseboot.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @BelongsProject: edu-lagou
 * @Author: GuoAn.Sun
 * @CreateTime: 2020-10-16 16:15
 * @Description:
 */
@Component
public interface CourseMapper extends BaseMapper<Course> {

}
