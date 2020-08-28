package com.min.blog.model;

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
@Table(name="mainCategorys")
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private BoardType type;

    @Column
    private String name;

    @Column
    private int sequenceNo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainCategory")
    @JsonManagedReference
    private Set<SubCategory> subcategories;

    protected Set<SubCategory> getSubCategoryInternal(){
        if(this.subcategories ==null){
            this.subcategories =new HashSet<SubCategory>();
        }
        return this.subcategories;
    }

    public void addSubCategory(SubCategory subCategory){
        getSubCategoryInternal().add(subCategory);
        subCategory.setMainCategory(this);
    }
}
