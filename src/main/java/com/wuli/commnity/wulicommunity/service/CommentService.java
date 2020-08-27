package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.dto.CommentCurrentDTO;
import com.wuli.commnity.wulicommunity.enums.StatusOfNotification;
import com.wuli.commnity.wulicommunity.enums.TypeOfComment;
import com.wuli.commnity.wulicommunity.enums.TypeOfNotification;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import com.wuli.commnity.wulicommunity.mapper.CommentMapper;
import com.wuli.commnity.wulicommunity.mapper.NotificationMapper;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Comment;
import com.wuli.commnity.wulicommunity.model.Notification;
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
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
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
              Notification notification= setNotification(comment,post.getCreator(),post.getId(), TypeOfNotification.REPLY_POST,post.getTitle());
            notificationMapper.insert(notification);
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
                Integer parentId=commentMapper.findParentId(comment1.getId());
                Post post=postMapper.findById(parentId);
                post.setComment_count(post.getComment_count()+1);
                postMapper.updateFullPost(post);//增加文章评论数
                comment1.setComment_count(comment1.getComment_count()+1);
                commentMapper.updateCommentCount(comment1);//增加父级评论数
                commentMapper.insert(comment);//把二级评论放入数据库
                Notification notification=setNotification(comment, comment1.getCommentator(),post.getId(), TypeOfNotification.REPLY_COMMENT,post.getTitle());//创建通知
                notificationMapper.insert(notification);
            }
        }
    }

    private Notification setNotification(Comment comment, Integer parentCommentator, Integer parentId, TypeOfNotification typeOfNotification,String title) {
        Notification notification=new Notification();
        notification.setGmt_create(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(parentCommentator);
        notification.setType(typeOfNotification.getType());
        notification.setStatus(StatusOfNotification.UNREAD.getStatus());
        notification.setOuterTitle(title);
        notification.setOuterId(parentId);
        return notification;
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
