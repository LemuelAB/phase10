package com.lagou.educourseboot.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "edu-order-boot",path = "order")
public interface OrderRemoteService {
    @GetMapping("getOKOrderCourseIds")
    List<Object> getOKOrderCourseIds(@RequestParam(value = "userid") Integer userid);
}
