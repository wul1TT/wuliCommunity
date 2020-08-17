package com.wuli.commnity.wulicommunity.Controller;

import com.wuli.commnity.wulicommunity.dto.CommentDTO;
import com.wuli.commnity.wulicommunity.dto.ResultDTO;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.mapper.CommentMapper;
import com.wuli.commnity.wulicommunity.model.Comment;
import com.wuli.commnity.wulicommunity.model.User;
import com.wuli.commnity.wulicommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired(required = false)
    CommentMapper commentMapper;
    @Autowired(required = false)
    CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public  Object postComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request)
    {
        User user=(User)request.getSession().getAttribute("myuser");
        if (user==null)
        {
          return  ResultDTO.errorOf(CustomizeErrorCode.NO_LOG_IN);
        }
        if(commentDTO==null||commentDTO.getContent()==null||commentDTO.getContent().equals(""))
        {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment=new Comment();
        comment.setType(commentDTO.getType());
        comment.setPost_id(commentDTO.getPost_id());
        comment.setContent(commentDTO.getContent());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
