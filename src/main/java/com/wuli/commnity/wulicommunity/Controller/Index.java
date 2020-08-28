package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.dto.PageDTO;
import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Post;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Index {
    @Autowired(required = false)
   private PostService postService;
    @Autowired(required = false)
    private PostMapper postMapper;
    @RequestMapping("/")
    public String mainIndex( Model model
                             , @RequestParam(name="page",defaultValue = "1")Integer page
                             ,@RequestParam(name="size",defaultValue = "2")Integer size)
    {

         if(page<1)
             page=1;
         if(postService.count()%size==0)
         {
             if(page>postService.count()/size)
                 page=postService.count()/size;
         }
         if(postService.count()%size!=0)
    {
        if(page>postService.count()/size+1)
            page=postService.count()/size+1;
    }
        PageDTO postList=postService.list(page,size);
         model.addAttribute("posts",postList);
        //怎么还在激活啊弟弟？
        return "index";
    }
}
