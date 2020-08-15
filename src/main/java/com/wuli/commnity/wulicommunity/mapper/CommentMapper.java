package com.wuli.commnity.wulicommunity.mapper;

import com.wuli.commnity.wulicommunity.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (post_id,type,commentator,gmt_create,content) values(#{post_id},#{type},#{commentator},#{gmt_create},#{content})")
     Integer insert(Comment comment);
    @Select("select *from comment where id=#{id}")
    Comment findById(@Param("id") Integer post_id);
}
