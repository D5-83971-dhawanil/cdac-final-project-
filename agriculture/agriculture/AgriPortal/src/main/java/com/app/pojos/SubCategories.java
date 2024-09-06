package com.app.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubCategories extends BaseEntity implements Serializable {

    // Remove the @Id and @GeneratedValue annotations from this field
    // as it's already defined in BaseEntity
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;  // Ensure this matches BaseEntity's id type

    @Column(name = "sub_category_name", length = 50, nullable = false)
    private String subCategoryName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ct_id")
    private Categories categories;

    // No need to override getId() or setId() as they are inherited from BaseEntity
    // If needed, add specific methods related to SubCategories only
}
