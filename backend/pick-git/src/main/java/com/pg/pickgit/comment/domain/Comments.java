package com.pg.pickgit.comment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Comments {

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Comments(){ this(new ArrayList<>()); }

    public Comments(List<Comment> comments){ this.comments = comments; }

    public List<Comment> getComments(){ return comments; }

}
