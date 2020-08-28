package com.min.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "boards")
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subCategory_id")
    @JsonBackReference
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    @Column
    private BoardType type;

    @Column
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")    //16MB TEXT
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    @JsonManagedReference
    private Set<Comment> comments;

    protected Set<Comment> commentsInternal(){
        if(this.comments==null){
            this.comments=new HashSet<>();
        }
        return this.comments;
    }

    public void addComment(Comment comment){
        commentsInternal().add(comment);
        comment.setBoard(this);
    }




}
