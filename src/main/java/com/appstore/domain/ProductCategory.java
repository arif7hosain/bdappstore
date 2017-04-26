package com.appstore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productcategory")
public class ProductCategory extends  AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "software_category_id")
    private SoftwareCategory softwareCategory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SoftwareCategory getSoftwareCategory() {
        return softwareCategory;
    }

    public void setSoftwareCategory(SoftwareCategory softwareCategory) {
        this.softwareCategory = softwareCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCategory productCategory = (ProductCategory) o;
        return Objects.equals(id, productCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + id +
            ", comments='" + comments + "'" +
//            ", CreatedDate='" + CreatedDate + "'" +
//            ", updatedDate='" + updatedDate + "'" +
//            ", createBy='" + createBy + "'" +
//            ", updatedBy='" + updatedBy + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
