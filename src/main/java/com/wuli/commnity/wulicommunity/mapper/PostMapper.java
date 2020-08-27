package com.wuli.commnity.wulicommunity.mapper;

import com.wuli.commnity.wulicommunity.dto.PostDTO;
import com.wuli.commnity.wulicommunity.model.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into post (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    public void create(Post post);

    @Select("select *from post order by gmt_modified desc limit #{size} offset #{offset} ")
    List<Post> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select(" select count(1) from post;")
    Integer count();

    @Select("select count(1) from post where creator=#{userId}")
    Integer findCountById(@Param("userId") Integer userId);

    @Select("select *from post where creator=#{userId} order by gmt_modified desc limit #{size} offset #{offset} ")
    List<Post> findListById(@Param("userId") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from post where id=#{id}")
    Post getById(@Param("id") Integer id);
    @Select("select creator from post where id=#{id}")
    Integer findCreatorById(@Param("id") Integer id);
    @Select("select * from post where id=#{id}")
    Post findById(@Param("id") Integer id);
    @Update("update post set gmt_modified=#{gmt_modified} where id=#{id}")
    void updateGmtModified(@Param("gmt_modified") long gmt_modified,@Param("id") Integer id);
    @Update("update post set description=#{description},title=#{title},gmt_modified=#{gmt_modified} where id=#{id}")
    Integer updatePost(@Param("id") Integer id, @Param("title") String title,@Param("description") String description,@Param("gmt_modified") long gmt_modified);
    @Update("update post set title=#{title},description=#{description},gmt_create=#{gmt_create},gmt_modified=#{gmt_modified},creator=#{creator},tag=#{tag},view_count=#{view_count},like_count=#{like_count},comment_count=#{comment_count} where id=#{id}")
    Integer updateFullPost(Post post);
    @Select("select * from post where tag regexp #{tag} and id !=#{id}")
    List<PostDTO> findRelated(PostDTO post);

}
