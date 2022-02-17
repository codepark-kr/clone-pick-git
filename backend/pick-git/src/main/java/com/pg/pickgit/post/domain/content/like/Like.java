package com.pg.pickgit.post.domain.content.like;

import com.pg.pickgit.post.domain.Post;
import com.pg.pickgit.user.domain.User;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "LIKES")
@Entity
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Like(){}

    public Like(Post post, User user){ this(null, post, user); }

    public Like(Long id, Post post, User user){
        this.id = id;
        this.post = post;
        this.user = user;
    }

    private Post getPost(){ return post; }

    public User getUser(){ return user; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null || getClass() != o.getClass()){ return false; }
        Like like = (Like) o;
        return Objects.equals(post, like.getPost()) &&
                Objects.equals(user, like.getUser());
    }

    @Override
    public int hashCode(){ return Objects.hash(post, user); }
}
