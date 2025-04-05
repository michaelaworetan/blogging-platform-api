package com.example.bloggingPlatform.repository.implementation;

import com.example.bloggingPlatform.model.entity.Post;
import com.example.bloggingPlatform.repository.Interface.PostRepository;
import com.example.bloggingPlatform.repository.query.PostQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createPost(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("postTitle", post.getPostTitle())
                .addValue("postContent", post.getPostContent())
                .addValue("postCategory", post.getPostCategory())
                .addValue("postStatus", post.getPostStatus())
                .addValue("postCreatedAt", post.getPostCreatedAt())
                .addValue("postUpdatedAt", post.getPostUpdatedAt());

        jdbcTemplate.update(PostQuery.INSERT_POST, params, keyHolder);
        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    @Override
    public void addPostTags(Long postId, List<String> postTags) {
        if (postTags != null && !postTags.isEmpty()) {
            for (String tag : postTags) {
                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("postId", postId)
                        .addValue("postTag", tag);
                jdbcTemplate.update(PostQuery.INSERT_POST_TAG, params);
            }
        }

    }

    @Override
    public List<Post> getAllPosts(int page, int size) {
        return List.of();
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return Optional.empty();
    }

    @Override
    public int updatePost(Long postId, Post post) {
        return 0;
    }

    @Override
    public int deletePostById(Long postId) {
        return 0;
    }

    @Override
    public List<Post> searchPosts(String searchTerm, int page, int size) {
        return List.of();
    }

    @Override
    public Long countPosts() {
        return 0L;
    }

    @Override
    public Long countSearchPosts(String searchTerm) {
        return 0L;
    }
}
