package com.lagou.educourseboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.educourseboot.entity.Course;
import com.lagou.educourseboot.entity.CourseMedia;
import org.springframework.stereotype.Component;

@Component
public interface MediaMapper extends BaseMapper<CourseMedia> {
}
