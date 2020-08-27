package com.wuli.commnity.wulicommunity.dto;

import com.wuli.commnity.wulicommunity.model.Notification;
import com.wuli.commnity.wulicommunity.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
private Notification notification;
private User user;
}
