package com.appstore.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceCategory.
 */
@Entity
@Table(name = "service_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servicecategory")
public class ServiceCategory extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "short_description")
    private String shortDescription;


    @Column(name = "is_active")
    private Integer isActive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceCategory serviceCategory = (ServiceCategory) o;
        return Objects.equals(id, serviceCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServiceCategory{" +
            "id=" + id +
            ", serviceName='" + serviceName + "'" +
            ", serviceDescription='" + serviceDescription + "'" +
            ", shortDescription='" + shortDescription + "'" +
//            ", CreatedDate='" + CreatedDate + "'" +
//            ", updatedDate='" + updatedDate + "'" +
//            ", createBy='" + createBy + "'" +
//            ", updatedBy='" + updatedBy + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
