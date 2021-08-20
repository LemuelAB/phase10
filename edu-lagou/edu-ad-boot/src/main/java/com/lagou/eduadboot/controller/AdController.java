package com.lagou.eduadboot.controller;

import com.lagou.eduadboot.entity.PromotionAd;
import com.lagou.eduadboot.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ad")
@CrossOrigin
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping("getAdsBySpaceId/{spaceid}")
    public List<PromotionAd> getAdsBySpaceId(@PathVariable("spaceid") Integer sid){
        List<PromotionAd> list = adService.getAdsBySpaceId(sid);
        return list;
    }
}
