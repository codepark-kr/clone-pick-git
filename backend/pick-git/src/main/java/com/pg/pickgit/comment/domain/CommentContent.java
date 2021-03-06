package com.pg.pickgit.comment.domain;

import com.pg.pickgit.exception.post.CommentFormatException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public class CommentContent {

    private static final int MAX_COMMENT_CONTENT_LENGTH = 100;

    @Column(nullable = false, length = MAX_COMMENT_CONTENT_LENGTH)
    @Lob
    private String content;

    protected CommentContent(){}

    public CommentContent(String content){
        if(isNotValidContent(content)){
            throw new CommentFormatException();
        }
        this.content = content;
    }

    private boolean isNotValidContent(String content){
        return Objects.isNull(content)
                || content.isBlank()
                || content.length() >= MAX_COMMENT_CONTENT_LENGTH;
    }

    public String getContent(){ return content; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null || getClass() != o.getClass()){ return false; }

        CommentContent that = (CommentContent) o;
        return Objects.equals(content, that.getContent());
    }

    @Override
    public int hashCode(){ return Objects.hash(content); }
}
