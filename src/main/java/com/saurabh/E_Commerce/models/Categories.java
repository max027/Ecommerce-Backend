package com.saurabh.E_Commerce.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categories extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long categoryId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "is_active")
    private boolean isActive=true;

    private String slug;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories parentId;

    @OneToMany(mappedBy = "categories",fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<Products> products;
}
