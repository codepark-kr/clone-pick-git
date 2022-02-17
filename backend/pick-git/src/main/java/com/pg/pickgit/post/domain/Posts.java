package com.pg.pickgit.post.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Posts {

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts;

    public Posts(){ this(new ArrayList<>()); }

    public Posts(List<Post> posts){ this.posts = posts; }

    public int count(){ return posts.size(); }

    public List<Post> getPosts(){ return posts; }
}
