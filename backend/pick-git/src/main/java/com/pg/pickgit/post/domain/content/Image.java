package com.pg.pickgit.post.domain.content;

import com.pg.pickgit.post.domain.Post;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Image(){}

    public Image(String url){ this.url = url; }

    public void belongTo(Post post){ this.post = post; }

    public String getUrl(){ return url; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null || getClass() != o.getClass()){ return false; }
        Image image = (Image) o;
        return Objects.equals(url, image.getUrl());
    }

    @Override
    public int hashCode(){ return Objects.hash(url); }
}
