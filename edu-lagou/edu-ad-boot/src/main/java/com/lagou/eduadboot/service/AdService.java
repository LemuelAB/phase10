package com.lagou.eduadboot.service;

import com.lagou.eduadboot.entity.PromotionAd;

import java.util.List;

public interface AdService {
    List<PromotionAd> getAdsBySpaceId(Integer sid);
}
