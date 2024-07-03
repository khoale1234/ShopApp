package com.project.shopApp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import com.project.shopApp.models.productImage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
@Builder
@Table(name = "products")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name" ,nullable = false,length = 350)
    private String name;
    private float price;
    @Column(name = "thumbnail" ,nullable = true,length = 300)
    private String thumbnail;
    @Column(name = "description")
    private String description ;
    @ManyToOne
    @JoinColumn (name = "category_id")
    private category category;
    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<productImage> productImages;


}
