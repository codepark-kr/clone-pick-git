package com.pg.pickgit.post.domain.tag;

import com.pg.pickgit.exception.post.CannotAddTagException;
import com.pg.pickgit.post.domain.Post;
import com.pg.pickgit.tag.domain.Tag;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Embeddable
public class PostTags {

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<PostTag> postTags;

    public PostTags(){ this(new ArrayList<>()); }

    public PostTags(List<PostTag> postTags){ this.postTags = postTags; }

    public void add(Post post, Tag tag){ addAll(post, List.of(tag)); }

    public void addAll(Post post, List<Tag> tags){
        validateDuplicateTag(tags);
        tags.forEach(this::validateDuplicateTagAlreadyExistsInPost);

        tags.stream()
                .map(tag -> new PostTag(post, tag))
                .forEach(postTags::add);
    }

    private void validateDuplicateTag(List<Tag> tags){
        long distinctCountOfNewTags = tags.stream()
                .map(Tag::getName)
                .distinct()
                .count();

        if (distinctCountOfNewTags != tags.size()){
            throw new CannotAddTagException();
        }
    }

    public void validateDuplicateTagAlreadyExistsInPost(Tag tag){
        boolean isDuplicate = postTags.stream()
                .anyMatch(postTag -> postTag.hasSameTag(tag));

        if(isDuplicate){
            throw new CannotAddTagException();
        }
    }

    public void clear(){ postTags.clear(); }

    public List<Tag> getTags(){
        return postTags.stream()
                .map(PostTag::getTag)
                .collect(toList());
    }

    public List<String> getTagNames(){
        return getTags().stream()
                .map(Tag::getName)
                .collect(toList());
    }
}
