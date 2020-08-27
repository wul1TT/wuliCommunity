package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.dto.PageDTO;
import com.wuli.commnity.wulicommunity.mapper.NotificationMapper;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.NotificationService;
import com.wuli.commnity.wulicommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private PostMapper postMapper;
    @Autowired(required = false)
    private PostService postService;
    @Autowired(required = false)
    private NotificationService notificationService;
     @RequestMapping("/profile/{action}")
    public  String profile(@PathVariable(name="action") String action, Model model, HttpServletRequest request
             , @RequestParam(name="page",defaultValue = "1")Integer page
             ,@RequestParam(name="size",defaultValue = "1")Integer size,
                           @RequestParam(name = "unread",defaultValue = "0") Integer read)
    {


        User user=(User)request.getSession().getAttribute("myuser");
        if(user==null)
        {
            return "redirect:/";
        }
        if(page<1)
            page=1;
        if(postMapper.findCountById(user.getId())%size==0)
        {
            if(page>postMapper.findCountById(user.getId())/size)
                page=postMapper.findCountById(user.getId())/size;
        }
        if(postMapper.findCountById(user.getId())%size!=0)
        {
            if(page>postMapper.findCountById(user.getId())/size+1)
                page=postMapper.findCountById(user.getId())/size+1;
            System.out.println(page);
        }
        if("myposts".equals(action))
        {
            model.addAttribute("action","myposts");
            model.addAttribute("actionname","我发布的内容");
            PageDTO postList=postService.list(user.getId(),page,size);
            model.addAttribute("posts",postList);
        }
        else if ("myforks".equals(action))
        {
            model.addAttribute("action","myforks");
            model.addAttribute("actionname","我关注的内容");

        }
        else if("myreplies".equals(action))
        {
            model.addAttribute("action","myreplies");
            model.addAttribute("actionname","我收到的回复");
          //  model.addAttribute("unread",)
            Integer unread= notificationService.getCount(user.getId());
            model.addAttribute("unread",unread);
            PageDTO pageList=notificationService.list(user.getId(),page,7);
            model.addAttribute("notifications",pageList);
        }

        return "profile";
    }
}
