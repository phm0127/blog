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
@Table(name="subCategorys")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private int sequenceNo;

    @ManyToOne
    @JoinColumn(name = "mainCategory_id")
    @JsonBackReference
    private MainCategory mainCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCategory")
    @JsonManagedReference
    private Set<Board> boards;

    protected Set<Board> boardsInternal(){
        if(this.boards==null){
            this.boards=new HashSet<>();
        }
        return this.boards;
    }

    public void addBoard(Board board){
        boardsInternal().add(board);
        board.setSubCategory(this);
    }

    public Board removeBoard(Board board){
        boardsInternal().remove(board);
        board.setSubCategory(null);
        return board;
    }

}
