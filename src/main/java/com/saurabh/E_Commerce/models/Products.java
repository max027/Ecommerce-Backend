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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long productId;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false,unique = true)
    private String sku;

    private String slug;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    private double price;

    @Column(name = "discount_price")
    private double discountPrice;

    @Column(name = "stock_quantity",nullable = false)
    private int stockQuantity=0;

    @Column(name = "is_active")
    private boolean isActive=true;

    @OneToMany(mappedBy = "products",fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "products",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Review>reviews;
}
