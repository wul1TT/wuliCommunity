package com.wuli.commnity.wulicommunity.mapper;

import com.wuli.commnity.wulicommunity.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Select("select count(1) from notification where receiver=#{id} and status=0")
    Integer getUnreadCount(@Param("id")Integer id);
    @Select("select count(1) from notification where receiver=#{id}")
    Integer getCount(@Param("id") Integer id);
    @Select("select *from notification where receiver=#{id} order by gmt_create desc limit #{size} offset #{offset} ")
    List<Notification> findListById(Integer id, Integer offset, Integer size);
    @Insert("insert into notification (notifier,receiver,outerId,outerTitle,gmt_create,status,type) values(#{notifier},#{receiver},#{outerId},#{outerTitle},#{gmt_create},#{status},#{type})")
    void insert(Notification notification);
    @Update("update notification set status=1 where outerId=#{outerId} and receiver=#{receiver}")
    void setStatus(@Param("outerId") Integer id,@Param("receiver") Integer userId);
}
