package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.cache.TagCache;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Post;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired(required = false)
    private PostMapper postMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private PostService postService;
    @RequestMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,Model model)
    {
        Post post=postMapper.findById(id);
        model.addAttribute("title",post.getTitle());
        model.addAttribute("description",post.getDescription());
        model.addAttribute("id",id);
        model.addAttribute("tag",post.getTag());
        model.addAttribute("caches", TagCache.get());
        //System.out.println(post.getDescription());
        //post.setGmt_modified(System.currentTimeMillis());
       // postMapper.updateGmtModified(post.getGmt_modified(),id);
        return "publish";
    }
    @RequestMapping("/publish")
    public String publish( Model model)
    {
        model.addAttribute("caches",TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String description,
                            @RequestParam("tag")String tag,
                            HttpServletRequest request, Model model,
                            @RequestParam(value = "id",required = false)Integer id)
    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null||title.equals(""))
        {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
       else if(description==null||description.equals(""))
        {
            model.addAttribute("error","内容不能为空");
            return "publish";
        }

        User user=(User)request.getSession().getAttribute("myuser");
        if(user==null)
        {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setCreator(user.getId());
        post.setTag(tag);
       // System.out.println(post.getDescription());
        //postMapper.create(post);
        postService.createOrUpdate(id,post);
        return "redirect:/";
    }
}
