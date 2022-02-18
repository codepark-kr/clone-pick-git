package com.pg.pickgit.user.domain.follow;


import com.pg.pickgit.exception.user.SameSourceTargetUserException;
import com.pg.pickgit.user.domain.User;

import javax.persistence.*;
import java.util.Objects;

@Table(
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"source_id", "target_id"})
        }
)
@Entity
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private User source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private User target;

    protected Follow(){}

    public Follow(User source, User target){
        validateDifferentSourceTarget(source, target);
        this.source = source;
        this.target = target;
    }

    private void validateDifferentSourceTarget(User source, User target){
        if(source.equals(target)){ throw new SameSourceTargetUserException(); }
    }

    public boolean isFollowing(User targetUser) { return this.target.equals(targetUser); }

    public Long getId(){ return id; }

    public User getSource(){ return source; }

    public User getTarget(){ return target; }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }
        if(o == null || getClass() != o.getClass()){ return false; }

        Follow follow = (Follow) o;
        return Objects.equals(getSource(), follow.getSource())
        && Objects.equals(getTarget(), follow.getTarget());
    }

    @Override
    public int hashCode(){ return Objects.hash(getSource(), getTarget()); }

}
