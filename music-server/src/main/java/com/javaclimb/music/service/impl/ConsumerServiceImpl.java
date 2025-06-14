package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.ConsumerMapper;
import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javaclimb.music.utils.Consts.FEMALE_DEFAULT;
import static com.javaclimb.music.utils.Consts.MALE_DEFAULT;

/**
 * 前端用户service实现类
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    /**
     * 增加
     *
     * @param consumer
     */
    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    /**
     * 修改
     *
     * @param consumer
     */
    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return consumerMapper.delete(id) > 0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Consumer selectByPrimaryKey(Integer id) {
        return consumerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<Consumer> allConsumer() {
        List<Consumer> consumers = consumerMapper.allConsumer();
        for(Consumer consumer:consumers){
            if(consumer.getAvator().isEmpty()){
                if(consumer.getSex()==0){
                    consumer.setAvator(FEMALE_DEFAULT);
                }else{
                    consumer.setAvator(MALE_DEFAULT);
                }
            }
        }
        return consumers;
    }

    /**
     * 查看密码是否正确
     *
     * @param username
     * @param password
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username, password) > 0;
    }

    /**
     * 根据账号查询
     *
     * @param username
     */
    @Override
    public Consumer getByUsername(String username) {
        return consumerMapper.getByUsername(username);
    }

    /**
     * 使用帐号密码登录
     *
     * @param username : 帐号
     * @param password : 密码
     * @return com.javaclimb.music.domain.Consumer
     * @since 2023/3/3 13:49
     */
    @Override
    public Consumer getUserWithAccount(String username, String password) {
        return consumerMapper.getUserWithAccount(username, password);
    }

    /**
     * 第三方登录  使用手机号码
     *
     * @param phoneNum :
     * @return com.javaclimb.music.domain.Consumer
     * @since 2023/3/3 14:23
     */
    @Override
    public Consumer getUserWithPhoneNum(String phoneNum) {
        return consumerMapper.getUserWithPhoneNum(phoneNum);
    }

    /**
     * 根据用户id和是否是会员的情况修改用户数据
     *
     * @param id        :  用户id
     * @param isVipUser : 修改后的会员状态
     * @return int
     * @since 2023/3/3 22:47
     */
    @Override
    public int updVipStatus(String id, Boolean isVipUser) {
        return consumerMapper.updVipStatus(id, isVipUser);
    }
}
