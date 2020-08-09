package com.wuli.commnity.wulicommunity.mapper;

import com.wuli.commnity.wulicommunity.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where token= #{token}")
     User findByToken(@Param("token") String token);
    @Insert("insert into user (account_id,name,token,time_create,time_update,avatarUri) values (#{account_id},#{name},#{token},#{time_create},#{time_update},#{avatarUri})")
    public void insert(User user);
     @Select("select *from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select distinct *from user where account_id=#{account_id}")
    User findByAccountId(@Param("account_id") String account_id);
    @Update("update user set token=#{token},time_update=#{time_update} where account_id=#{account_id}")
    void updateUser(@Param("account_id") String account_id,@Param("token") String token,@Param("time_update") long time_update);
    @Select("select count(1) from user where account_id=#{account_id}")
    Integer countByAccountId(@Param("account_id") String account_id);
}
