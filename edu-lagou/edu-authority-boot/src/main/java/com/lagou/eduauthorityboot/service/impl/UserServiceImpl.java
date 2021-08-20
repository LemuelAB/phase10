package com.lagou.eduauthorityboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.eduauthorityboot.entity.EduConstant;
import com.lagou.eduauthorityboot.entity.User;
import com.lagou.eduauthorityboot.entity.UserDTO;
import com.lagou.eduauthorityboot.mapper.UserMapper;
import com.lagou.eduauthorityboot.service.UserService;
import com.lagou.eduauthorityboot.tools.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public UserDTO login(String phone, String password) {
        UserDTO dto = new UserDTO();
        // 创建条件构造
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        // 编写具体的查询条件
        queryWrapper.eq("phone", phone);

        Integer i1 = userMapper.selectCount(queryWrapper);
        if (i1 == 0) {  // 手机号不存在
            dto.setState(EduConstant.ERROR_NOT_FOUND_PHONE_CODE);
            dto.setMessage(EduConstant.ERROR_NOT_FOUND_PHONE);
            dto.setContent(null);
            return dto;
        } else {
            queryWrapper.eq("password", password);
            // 调用mp的查询方法selectOne
            User user = userMapper.selectOne(queryWrapper);
            if (user == null) { // 帐号密码不匹配
                dto.setState(EduConstant.ERROR_PASSWORD_CODE);
                dto.setMessage(EduConstant.ERROR_PASSWORD);
                dto.setContent(null);
                return dto;
            } else {
                // 登录成功
                dto.setState(EduConstant.LOGIN_SUCCESS_CODE);
                dto.setMessage(EduConstant.LOGIN_SUCCESS);
                //dto.setContent(user);
                String token = JwtUtil.createToken(user);
                dto.setToken(token);

                // 将token和用户信息存在redis中
                redisTemplate.opsForValue().set(token, token, 15, TimeUnit.SECONDS);
                System.out.println("token = " + token);
                return dto;
            }
        }
    }

    @Override
    public Integer register(String phone, String password, String nickname, String headimg) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setName(nickname);
        user.setPortrait(headimg);
        return userMapper.insert(user);
    }

    @Override
    public UserDTO checkToken(String token) {
        int i = JwtUtil.isVerify(token);
        UserDTO dto = new UserDTO();
        if (i == 0) {
            dto.setState(EduConstant.TOKEN_SUCCESS_CODE);
            dto.setMessage(EduConstant.TOKEN_SUCCESS);
            // 校验通过，重新设置过期时间
            redisTemplate.opsForValue().set(token, token, 15 * 1000, TimeUnit.SECONDS);
        } else if (i == 1) {
            dto.setState(EduConstant.TOKEN_TIMEOUT_CDOE);
            dto.setMessage(EduConstant.TOKEN_TIMEOUT);
        } else if (i == 2) {
            dto.setState(EduConstant.TOKEN_NULL_CODE);
            dto.setMessage(EduConstant.TOKEN_ERROR1);
        } else {
            dto.setState(EduConstant.TOKEN_ERROR_CDOE);
            dto.setMessage(EduConstant.TOKEN_ERROR2);
        }
        return dto;
    }

    @Override
    public UserDTO loginPhoneSms(String phoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phoneNumber);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){ //手机号不存在
            // 先注册
            register(phoneNumber,phoneNumber,"手机新用户","xxx");
            return loginPhoneSms(phoneNumber);
        }
        System.out.println("user = " + user);
        // 创建token
        String token = JwtUtil.createToken(user);
        // 封装DTO
        UserDTO dto = new UserDTO();
        dto.setState(EduConstant.LOGIN_SUCCESS_CODE);
        dto.setMessage(EduConstant.LOGIN_SUCCESS);
        dto.setToken(token);
        return dto;
    }
}
