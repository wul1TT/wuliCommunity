package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.dto.NotificationDTO;
import com.wuli.commnity.wulicommunity.dto.PageDTO;
import com.wuli.commnity.wulicommunity.mapper.NotificationMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Notification;
import com.wuli.commnity.wulicommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    public Integer getCount(Integer id) {
        Integer count=notificationMapper.getUnreadCount(id);
        return count;
    }

    public PageDTO list(Integer id, Integer page, Integer size) {

        Integer offset = size * (page - 1);
        Integer totalCount = notificationMapper.getCount(id);
        List<Notification> notificationList;
        if (totalCount == 0) {
            notificationList = new ArrayList<>();
        } else {
            notificationList = notificationMapper.findListById(id, offset, size);
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotification(notification);
            User user = userMapper.findById(notification.getNotifier());
            notificationDTO.setUser(user);
            notificationDTOS.add(notificationDTO);
            //System.out.println("jijijijijijijijijijiji");
            // System.out.println(postDTO.getDescription());
        }
        pageDTO.setTList(notificationDTOS);
        pageDTO.setPagination(totalCount, page, size);
        return pageDTO;
    }

    public void clearUnread(Integer id,Integer userId) {
        notificationMapper.setStatus(id,userId);
    }
}
