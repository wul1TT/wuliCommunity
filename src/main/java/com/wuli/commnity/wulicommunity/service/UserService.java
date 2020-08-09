package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
      // User dbUser=userMapper.findByAccountId(user.getAccount_id());
       Integer num=userMapper.countByAccountId(user.getAccount_id());
        if (num==0)
        {
            userMapper.insert(user);
            System.out.println("1111111");
        }
        else
        {
            user.setTime_update(System.currentTimeMillis());
            userMapper.updateUser(user.getAccount_id(),user.getToken(),user.getTime_update());
        }
    }
}
