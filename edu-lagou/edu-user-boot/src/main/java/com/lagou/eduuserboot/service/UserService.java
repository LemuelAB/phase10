package com.lagou.eduuserboot.service;

import com.lagou.eduuserboot.entity.User;

public interface UserService {
    /**
     * 修改昵称
     * @param userid    用户编号
     * @param newName 新昵称
     */
    void updateUser(Integer userid,String newName,String imgfileId);

    /**
     * 修改密码
     * @param userid    用户编号
     * @param newPwd 新密码
     */
    void updatePassword(Integer userid,String newPwd);

}
