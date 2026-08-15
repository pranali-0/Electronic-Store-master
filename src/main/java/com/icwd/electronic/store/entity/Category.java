package com.icwd.electronic.store.entity;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "category_title")
    private String title;
    @Column(name = "category_description")
    private String description;
    @Column(name = "category_cover_image")
    private String coverImage;
    @OneToMany(mappedBy = "categories",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product>products=new ArrayList<>();
}
