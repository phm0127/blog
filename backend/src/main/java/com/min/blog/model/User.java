package com.min.blog.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String accessToken;	// 네이버 access_token
    @Column
    private String refreshToken;	// 네이버 refresh_token

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private Set<Comment> comments;

    @Builder
    public User(String name, String email, String nickname, Role role, String accessToken, String refreshToken) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }

    public User update(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    protected Set<Comment> commentsInternal(){
        if(this.comments==null){
            this.comments=new HashSet<>();
        }
        return this.comments;
    }

    public void addComment(Comment comment){
        commentsInternal().add(comment);
        comment.setUser(this);
    }

}
