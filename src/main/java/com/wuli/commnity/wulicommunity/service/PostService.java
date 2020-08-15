package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.dto.PageDTO;
import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Post;
import com.wuli.commnity.wulicommunity.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired(required = false)
    private PostMapper postMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size)
    {
        Integer offset =size*(page-1);
        List <Post>postlist =postMapper.list(offset,size);
        List<PostDTO> postDTOList=new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Post post : postlist) {
           User user= userMapper.findById(post.getCreator());
            PostDTO postDTO=new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        Integer totalCount=postMapper.count();
        pageDTO.setPosts(postDTOList);
        pageDTO.setPagination(totalCount,page,size);
        return pageDTO;
    }

    public PageDTO list(Integer id, Integer page, Integer size) {
        Integer offset =size*(page-1);
        Integer totalCount=postMapper.findCountById(id);
        List <Post>postlist;
        if (totalCount==0)
        {
            postlist=new ArrayList<>();
        }

        else
        {
            postlist=postMapper.findListById(id,offset,size);
        }
        List<PostDTO> postDTOList=new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        User user= userMapper.findById(id);
        for (Post post : postlist) {
            PostDTO postDTO=new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
            //System.out.println("jijijijijijijijijijiji");
           // System.out.println(postDTO.getDescription());
        }
        pageDTO.setPosts(postDTOList);
        pageDTO.setPagination(totalCount,page,size);
        return pageDTO;
    }

    public PostDTO getById(Integer id) {
        Post post=postMapper.getById(id);
        if(post==null)
        {
            throw new CustomizeException(CustomizeErrorCode.POST_NOT_FOUND);
        }
        PostDTO postDTO=new PostDTO();
        BeanUtils.copyProperties(post,postDTO);
        Integer creator=postMapper.findCreatorById(id);
        User user=userMapper.findById(creator);
        postDTO.setUser(user);
        return postDTO;
    }

    public void createOrUpdate(Integer id, Post post) {
        if(id==null)
        {
            post.setGmt_create(System.currentTimeMillis());
            post.setGmt_modified(post.getGmt_create());
            postMapper.create(post);
        }
        else
        {
            post.setGmt_modified(System.currentTimeMillis());
           int updated= postMapper.updatePost(id,post.getTitle(),post.getDescription(),post.getGmt_modified());
           if(updated==0)
               throw new CustomizeException(CustomizeErrorCode.POST_NOT_FOUND);
           //System.out.println(post.getDescription());
        }
    }

    public void incView(Integer id) {
        Post post=postMapper.findById(id);
        post.setView_count(post.getView_count()+1);
        postMapper.updateFullPost(post);
       // System.out.println(post.getView_count());

    }
}
