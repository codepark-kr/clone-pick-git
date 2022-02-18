package com.pg.pickgit.tag.domain;

import com.pg.pickgit.exception.post.TagFormatException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tag {

    private static final int MAX_TAG_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_TAG_LENGTH)
    private String name;

    protected Tag(){}

    public Tag(String name){
        if(isNotValidTag(name)){ throw new TagFormatException(); }
        this.name = name.toLowerCase();
    }

    private boolean isNotValidTag(String name){
        return Objects.isNull(name)
                || name.isBlank()
                || name.length() > MAX_TAG_LENGTH;
    }

    public Long getId(){ return id; }

    public String getName(){ return name; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(!(o instanceof Tag)){ return false; }

        Tag tag = (Tag) o;
        return Objects.equals(name, tag.getName());
    }

    @Override
    public int hashCode(){ return Objects.hash(getName()); }
}
