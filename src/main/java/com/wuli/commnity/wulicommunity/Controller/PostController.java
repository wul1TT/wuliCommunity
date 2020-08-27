package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.dto.CommentCurrentDTO;
import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.CommentService;
import com.wuli.commnity.wulicommunity.service.NotificationService;
import com.wuli.commnity.wulicommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @Autowired(required = false)
    private NotificationService notificationService;
    @Autowired(required = false)
    private PostService postService;
    @Autowired(required = false)
    private CommentService commentService;
    @RequestMapping("/post/{id}")
    public  String post(@PathVariable(name = "id")Integer id, Model model, HttpServletRequest request)
    {
        PostDTO postDTO=postService.getById(id);
        //PostDTO rePostDTO=new PostDTO(postDTO);
        List<PostDTO> relatedPosts=new ArrayList<>();
        relatedPosts=postService.getRelated(postDTO);
        List<CommentCurrentDTO> commentCurrentDTOS=new ArrayList<>();
        commentCurrentDTOS=commentService.getByPostId(id);
        postService.incView(id);
        model.addAttribute("post",postDTO);
        model.addAttribute("comments",commentCurrentDTOS);
        model.addAttribute("relatedposts",relatedPosts);
        Integer userId= (Integer) request.getSession().getAttribute("myId");
        notificationService.clearUnread(id,userId);
        return "post";
    }

}
