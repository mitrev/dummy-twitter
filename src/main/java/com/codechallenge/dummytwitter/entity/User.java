package com.codechallenge.dummytwitter.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @With
    private String login;
    @Column(insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private Instant createdDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            inverseJoinColumns = @JoinColumn(name = "followed_user_id"),
            joinColumns = @JoinColumn(name = "follower_user_id")
    )
    private Set<User> followedUsers = new HashSet<>();

    public void addFollowedUser( User user ) {
        followedUsers.add( user );
    }
}
