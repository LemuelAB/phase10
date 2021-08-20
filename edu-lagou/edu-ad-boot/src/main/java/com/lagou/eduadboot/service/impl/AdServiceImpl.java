package com.lagou.eduadboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.eduadboot.entity.PromotionAd;
import com.lagou.eduadboot.mapper.AdDao;
import com.lagou.eduadboot.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private AdDao adDao;
    @Override
    public List<PromotionAd> getAdsBySpaceId(Integer sid) {
        QueryWrapper<PromotionAd> qw = new QueryWrapper<>();
        qw.eq("space_id", sid); // where comment_id = #{cid}
        return adDao.selectList(qw);
    }
}
