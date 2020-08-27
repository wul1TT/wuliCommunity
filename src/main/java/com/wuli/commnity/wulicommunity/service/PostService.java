package com.wuli.commnity.wulicommunity.service;

import com.wuli.commnity.wulicommunity.dto.PageDTO;
import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.exception.CustomizeErrorCode;
import com.wuli.commnity.wulicommunity.exception.CustomizeException;
import com.wuli.commnity.wulicommunity.mapper.PostMapper;
import com.wuli.commnity.wulicommunity.mapper.UserMapper;
import com.wuli.commnity.wulicommunity.model.Post;
import com.wuli.commnity.wulicommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired(required = false)
    private PostMapper postMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Post> postlist = postMapper.list(offset, size);
        List<PostDTO> postDTOList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Post post : postlist) {
            User user = userMapper.findById(post.getCreator());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        Integer totalCount = postMapper.count();
        pageDTO.setTList(postDTOList);
        pageDTO.setPagination(totalCount, page, size);
        return pageDTO;
    }

    public PageDTO list(Integer id, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        Integer totalCount = postMapper.findCountById(id);
        List<Post> postlist;
        if (totalCount == 0) {
            postlist = new ArrayList<>();
        } else {
            postlist = postMapper.findListById(id, offset, size);
        }
        List<PostDTO> postDTOList = new ArrayList<>();
        PageDTO<PostDTO> pageDTO = new PageDTO<>();
        User user = userMapper.findById(id);
        for (Post post : postlist) {
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
            //System.out.println("jijijijijijijijijijiji");
            // System.out.println(postDTO.getDescription());
        }
        pageDTO.setTList(postDTOList);
        pageDTO.setPagination(totalCount, page, size);
        return pageDTO;
    }

    public PostDTO getById(Integer id) {
        Post post = postMapper.getById(id);
        if (post == null) {
            throw new CustomizeException(CustomizeErrorCode.POST_NOT_FOUND);
        }
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        Integer creator = postMapper.findCreatorById(id);
        User user = userMapper.findById(creator);
        postDTO.setUser(user);
        return postDTO;
    }

    public void createOrUpdate(Integer id, Post post) {
        if (id == null) {
            post.setGmt_create(System.currentTimeMillis());
            post.setGmt_modified(post.getGmt_create());
            postMapper.create(post);
        } else {
            Post currentPost=new Post();
            currentPost=postMapper.findById(id);
            currentPost.setGmt_modified(System.currentTimeMillis());
            currentPost.setTag(post.getTag());
            currentPost.setTitle(post.getTitle());
            currentPost.setDescription(post.getDescription());
            currentPost.setId(id);
            //System.out.println(post);
            int updated = postMapper.updateFullPost(currentPost);
            if (updated == 0)
                throw new CustomizeException(CustomizeErrorCode.POST_NOT_FOUND);
            //System.out.println(post.getDescription());
        }
    }

    public void incView(Integer id) {
        Post post = postMapper.findById(id);
        post.setView_count(post.getView_count() + 1);
        postMapper.updateFullPost(post);
        // System.out.println(post.getView_count());

    }

    public List<PostDTO> getRelated(PostDTO postDTO) {
        if (StringUtils.isEmpty(postDTO.getTag())) {
            return new ArrayList<>();
        }
        List<PostDTO> releatedPosts = new ArrayList<>();
        String[] tags = postDTO.getTag().split(" ");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        PostDTO rePostDTO=new PostDTO();
        rePostDTO.setTag(regexpTag);
        rePostDTO.setId(postDTO.getId());
        releatedPosts = postMapper.findRelated(rePostDTO);
        return releatedPosts;
    }
}
