package com.pg.pickgit.post.domain.tag;

import com.pg.pickgit.post.domain.Post;
import com.pg.pickgit.tag.domain.Tag;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PostTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected PostTag(){}

    public PostTag(Post post, Tag tag){
        this.post = post;
        this.tag = tag;
    }

    public boolean hasSameTag(Tag tag){ return Objects.equals(this.tag, tag); }

    public Tag getTag(){ return tag; }

    public Long getId(){ return id; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null || getClass() != o.getClass()){ return false; }

        PostTag postTag = (PostTag) o;
        return Objects.equals(id, postTag.getId());
    }

    @Override
    public int hashCode(){ return Objects.hash(id); }
}
