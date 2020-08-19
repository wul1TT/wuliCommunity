package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.dto.CommentCurrentDTO;
import com.wuli.commnity.wulicommunity.dto.CommentDTO;
import com.wuli.commnity.wulicommunity.enums.TypeOfComment;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import com.wuli.commnity.wulicommunity.mapper.CommentMapper;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Comment;
import com.wuli.commnity.wulicommunity.model.Post;
import com.wuli.commnity.wulicommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired(required = false)
    CommentMapper  commentMapper;
    @Autowired(required = false)
    PostMapper postMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getPost_id()==null||comment.getPost_id()==0)
        {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }
        if (comment.getType()==null|| !TypeOfComment.isExist(comment.getType()))
        {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_WRONG);
        }
        if (comment.getType()==TypeOfComment.POST.getType())//回复文章内容
        {
            Post post=postMapper.findById(comment.getPost_id());
            if (post==null)
            {
                throw new CustomizeException(CustomizeErrorCode.POST_NOT_FOUND);
            }
            else
            {
                post.setComment_count(post.getComment_count()+1);
                postMapper.updateFullPost(post);
                commentMapper.insert(comment);
            }
        }
        else//回复评论
        {
            Comment comment1=commentMapper.findById(comment.getPost_id());
            if (comment1==null)
            {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            else
            {
                comment1.setComment_count(comment1.getComment_count()+1);
                commentMapper.updateCommentCount(comment1);
                commentMapper.insert(comment);
            }
        }
    }

    public List<CommentCurrentDTO> getByPostId(Integer id) {
        List<CommentCurrentDTO> commentCurrentDTOS=new ArrayList<>();
        List<Comment> comments;
        comments=commentMapper.findByPostId(id);
        for (Comment comment : comments) {
            User user=userMapper.findById(comment.getCommentator());
            CommentCurrentDTO commentCurrentDTO=new CommentCurrentDTO();
            BeanUtils.copyProperties(comment,commentCurrentDTO);
            commentCurrentDTO.setUser(user);
            commentCurrentDTOS.add(commentCurrentDTO);
        }
        return commentCurrentDTOS;
    }

    public  List<CommentCurrentDTO> getByCommentId(Integer id) {
        List<CommentCurrentDTO> commentCurrentDTOS=new ArrayList<>();
        List<Comment> comments;
        comments=commentMapper.findByCommentId(id);
        for (Comment comment : comments) {
            User user=userMapper.findById(comment.getCommentator());
            CommentCurrentDTO commentCurrentDTO=new CommentCurrentDTO();
            BeanUtils.copyProperties(comment,commentCurrentDTO);
            commentCurrentDTO.setUser(user);
            commentCurrentDTOS.add(commentCurrentDTO);
        }
        return commentCurrentDTOS;
    }
}
