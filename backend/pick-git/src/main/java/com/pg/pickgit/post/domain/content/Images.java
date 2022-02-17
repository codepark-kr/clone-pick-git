package com.pg.pickgit.post.domain.content;

import com.pg.pickgit.post.domain.Post;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class Images {

    @OneToMany(
        mappedBy = "post",
        fetch = FetchType.LAZY,
        cascade = { CascadeType.PERSIST, CascadeType.ALL }
    )
    private List<Image> images = new ArrayList<>();

    protected Images(){}

    public Images(List<Image> images){ this.images = images; }

    public void belongTo(Post post) { images.forEach(image -> image.belongTo(post)); }

    public List<String> getUrls(){
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getImageUrls(){
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.toUnmodifiableList());
    }
}
