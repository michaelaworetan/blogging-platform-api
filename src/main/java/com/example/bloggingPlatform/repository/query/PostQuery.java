package com.example.bloggingPlatform.repository.query;

public class PostQuery {

    public static final String INSERT_POST = """
            INSERT INTO Post (postTitle, postContent, postCategory, postStatus, postCreatedAt, postUpdatedAt)
            VALUES (:postTitle, :postContent, :postCategory, :postStatus, :postCreatedAt, :postUpdatedAt)
            """;


    public static final String INSERT_POST_TAG = """
            INSERT INTO PostTag (postId, postTag)
            VALUES (:postId, :postTag)
            """;

//    public static final String GET_ALL_POSTS = """
//            SELECT p.*, dbo.ConcatenateTagsForPost(p.postId) as tags
//            FROM Post p
//            WHERE p.postStatus = 'ACTIVE'
//            ORDER BY p.postCreatedAt DESC
//            OFFSET :offset ROWS
//            FETCH NEXT :limit ROWS ONLY
//            """;

    public static final String GET_ALL_POSTS = """
            SELECT p.postId, p.postTitle, p.postContent, p.postCategory, p.postStatus, p.postCreatedAt, p.postUpdatedAt,
                (SELECT STRING_AGG(pt.postTag, ',') FROM PostTag pt WHERE pt.postId = p.postId) as postTags
            FROM Post p
            WHERE p.postStatus = 'ACTIVE'
            ORDER BY p.postCreatedAt DESC
            OFFSET :offset ROWS
            FETCH NEXT :limit ROWS ONLY
            """;

    public static final String GET_POST_BY_ID = """
            SELECT p.*, dbo.ConcatenateTagsForPost(p.postId) as posTags
            FROM Post p
            WHERE p.postId = :postId AND p.postStatus = 'ACTIVE'
            """;

    public static final String UPDATE_POST = """
            UPDATE Post
            SET postTitle = :postTitle, postContent = :postContent, postCategory = :postCategory, postUpdatedAt = :postUpdatedAt
            WHERE postId = :postId
            """;

    public static final String DELETE_POST_TAGS = """
            DELETE FROM PostTag
            WHERE postId = :postId
            """;

    public static final String DELETE_POST = """
            UPDATE Post
            SET postStatus = 'DELETED', postUpdatedAt = :updatedAt
            WHERE postId = :postId
            """;

//    public static final String SEARCH_POSTS = """
//            SELECT p.*, dbo.ConcatenateTagsForPost(p.postId) as tags
//            FROM Post p
//            WHERE p.postStatus = 'ACTIVE'
//            AND (p.postTitle LIKE :term OR p.postContent LIKE :term OR p.postCategory LIKE :term)
//            ORDER BY p.postCreatedAt DESC
//            OFFSET :offset ROWS
//            FETCH NEXT :limit ROWS ONLY
//            """;

    public static final String SEARCH_POSTS = """
            SELECT p.*,
                (SELECT STRING_AGG(pt.postTag, ',') FROM PostTag pt WHERE pt.postId = p.postId) as postTags
            FROM Post p
            WHERE p.postStatus = 'ACTIVE'
            AND (p.postTitle LIKE :term OR p.postContent LIKE :term OR p.postCategory LIKE :term)
            ORDER BY p.postCreatedAt DESC
            OFFSET :offset ROWS
            FETCH NEXT :limit ROWS ONLY
            """;

    public static final String COUNT_POSTS = """
            SELECT COUNT(*) FROM Post
            WHERE postStatus = 'ACTIVE'
            """;

    public static final String COUNT_SEARCH_POSTS = """
            SELECT COUNT(*) FROM Post
            WHERE postStatus = 'ACTIVE'
            AND (postTitle LIKE :term OR postContent LIKE :term OR postCategory LIKE :term)
            """;

}
