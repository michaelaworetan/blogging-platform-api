package com.example.bloggingPlatform.repository.implementation;

import com.example.bloggingPlatform.mapper.PostRowMapper;
import com.example.bloggingPlatform.model.entity.Post;
import com.example.bloggingPlatform.repository.Interface.PostRepository;
import com.example.bloggingPlatform.repository.query.PostQuery;
import org.springframework.dao.EmptyResultDataAccessException;
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
        int offset = page * size;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("offset", offset)
                .addValue("limit", size);
        return jdbcTemplate.query(PostQuery.GET_ALL_POSTS, params, new PostRowMapper());
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource("postId", postId);
            Post post = jdbcTemplate.queryForObject(PostQuery.GET_POST_BY_ID, params, new PostRowMapper());
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
    public List<Post> searchPosts(String term, int page, int size) {
        int offset = page * size;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("term", "%" + term + "%")
                .addValue("offset", offset)
                .addValue("limit", size);
        return jdbcTemplate.query(PostQuery.SEARCH_POSTS, params, new PostRowMapper());
    }

    @Override
    public Long countPosts() {
        return 0L;
    }

    @Override
    public Long countSearchPosts(String term) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("term", "%" + term + "%");
        return jdbcTemplate.queryForObject(PostQuery.COUNT_SEARCH_POSTS, params, Long.class);
    }
}
