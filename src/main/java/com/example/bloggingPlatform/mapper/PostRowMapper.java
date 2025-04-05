package com.example.bloggingPlatform.mapper;

import com.example.bloggingPlatform.model.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PostRowMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<String> postTags = null;
        String tagsString = rs.getString("postTags");

        if (tagsString != null && !tagsString.isEmpty()) {
            postTags = Arrays.asList(tagsString.split(","));

        } else {
            postTags = Collections.emptyList();
        }

        return Post.builder()
                .postId(rs.getLong("postId"))
                .postTitle(rs.getString("postTitle"))
                .postContent(rs.getString("postContent"))
                .postCategory(rs.getString("postCategory"))
                .postTags(postTags)
                .postStatus(rs.getString("status"))
                .postCreatedAt(rs.getTimestamp("postCreatedAt"))
                .postUpdatedAt(rs.getTimestamp("postCreatedAt"))
                .build();
    }
}
