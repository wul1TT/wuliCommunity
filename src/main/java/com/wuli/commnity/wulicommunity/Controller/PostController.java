package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.dto.CommentCurrentDTO;
import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.service.CommentService;
import com.wuli.commnity.wulicommunity.service.PostService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @Autowired(required = false)
    private PostMapper postMapper;
    @Autowired(required = false)
    private PostService postService;
    @Autowired(required = false)
    private CommentService commentService;
    @RequestMapping("/post/{id}")
    public  String post(@PathVariable(name = "id")Integer id, Model model)
    {
        PostDTO postDTO=postService.getById(id);
        List<CommentCurrentDTO> commentCurrentDTOS=new ArrayList<>();
        commentCurrentDTOS=commentService.getByPostId(id);
        postService.incView(id);
        model.addAttribute("post",postDTO);
        model.addAttribute("comments",commentCurrentDTOS);
        return "post";
    }

}
