package com.lagou.eduauthorityboot.service;

import com.lagou.eduauthorityboot.entity.UserDTO;

public interface UserService {
    public UserDTO login(String phone, String password);

    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param password 密码
     * @param nickname 昵称
     * @param headimg 头像
     * @return 受影响的行数
     */
    Integer register( String phone, String password,String nickname,String headimg);

    UserDTO checkToken(String token);

    UserDTO loginPhoneSms(String phoneNumber);
}
