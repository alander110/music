package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.AdminMapper;
import com.javaclimb.music.domain.Admin;
import com.javaclimb.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员service实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证密码是否正确
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username, password) > 0;
    }

    @Override
    public Admin getAdmin(String username, String password) {
        return adminMapper.getAdmin(username,  password);
    }

    @Override
    public Admin getAdminWithPhoneNum(String phoneNum) {
        return adminMapper.getAdminWithPhoneNum(phoneNum);
    }
}
