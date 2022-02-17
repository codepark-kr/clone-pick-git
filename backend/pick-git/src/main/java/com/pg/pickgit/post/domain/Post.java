package com.pg.pickgit.post.domain;

import com.pg.pickgit.post.domain.content.Image;
import com.pg.pickgit.post.domain.content.Images;
import com.pg.pickgit.post.domain.content.PostContent;
import com.pg.pickgit.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private Images images;

    @Embedded
    private PostContent content;

    @Embedded
    private Likes likes;

    @Embedded
    private Comments comments;

    @Embedded
    private PostTags postTags;

    private String githubRepoUrl;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Post() {
    }

    public Post(
            Long id,
            User user,
            Images images,
            PostContent content,
            Likes likes,
            Comments comments,
            PostTags postTags,
            String githubRepoUrl,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.user = user;
        this.images = images;
        this.likes = likes;
        this.content = content;
        this.comments = comments;
        this.postTags = postTags;
        this.githubRepoUrl = githubRepoUrl;
        this.createAt = createdAt;
        this.updatedAt = updatedAt;

        images.belongTo(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void like(User user) {
        Like like = new Like(this, user);
        likes.add(like);
    }

    public void unlike(User user) {
        Like like = new Like(this, user);
        likes.remove(like);
    }

    public boolean isLikedBy(User user) {
        Like like = new Like(this, user);
        return likes.contains(like);
    }

    public boolean isNotWrittenBy(User user) {
        return !this.user.equals(user);
    }

    public void updateContent(String content) {
        this.content = new PostContent(content);
    }

    public void updateTags(List<Tag> tags) {
        postTags.clear();
        addTags(tags);
    }

    public void addTags(List<Tag> tags) {
        postTags.addAll(this, tags);
    }

    public Long getId() {
        return id;
    }

    public List<String> getImageUrls() {
        return images.getUrls();
    }

    public String getGithubRepoUrl() {
        return githubRepoUrl;
    }

    public int getLikeCounts() {
        return likes.getCounts();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content.getContent();
    }

    public String getAuthorName() {
        return user.getName();
    }

    public String getAuthorProfileImage() {
        return user.getImage();
    }

    public List<Comment> getComments() {
        return comments.getComments();
    }

    public List<String> getTagNames() {
        return postTags.getTagNames();
    }

    public Likes getLikes() {
        return likes;
    }

    public List<User> getLikeUsers() {
        return likes.getLikeUsers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(id, post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Builder() {
        private Long id;
        private User author;
        private Images images = new Images(List.of());
        private PostContent content;
//        private Likes likes = new Likes();
//        private Comments comments = new Comments();
//        private PostTags postTags = new PostTags();
        private String githubRepoUrl;
        private LocalDateTime createdAt = null;
        private LocalDateTime updatedAt = null;

//        private List<Tag> tags = new ArrayList<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder author(User user) {
            this.author = user;
            return this;
        }

        public Builder images(List<String> imageUrls) {
            List<Image> images = imageUrls.stream()
                    .map(Image::new)
                    .collect(toList());
        this.images = new Images(images);
        return this;
        }

    public Builder images(Images images) {
        this.images = images;
        return this;
    }

    public Builder content(String content) {
        this.content = new PostContent(content);
        return this;
    }

    public Builder comments(List<Comment> comments) {
        this.comments = new Comments(comments);
        return this;
    }

    public Builder tags(Tag... tags) {
        tags(List.of(tags));
        return this;
    }

    public Builder tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Builder githubRepoUrl(String githubRepoUrl) {
        this.githubRepoUrl = githubRepoUrl;
        return this;
    }

    @Deprecated
    public Builder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Deprecated
    public Builder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Post build() {
        Post post = new Post(
                id,
                author,
                images,
                content,
                likes,
                comments,
                postTags,
                githubRepoUrl,
                createdAt,
                updatedAt
        );

        post.addTags(tags);

        return post;
        }
    }
}