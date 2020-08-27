package com.wuli.commnity.wulicommunity.interception;

import com.wuli.commnity.wulicommunity.mapper.NotificationMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies= request.getCookies();
        if(cookies!=null)
        {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    //System.out.println(user);
                    if(user!=null)
                    {
                        Integer  unread= notificationService.getCount(user.getId());
                        request.getSession().setAttribute("myuser",user);
                        request.getSession().setAttribute("myId",user.getId());
                        request.getSession().setAttribute("unread",unread);
                        //System.out.println(user.getName());
                    }
                    break;
                }
            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
