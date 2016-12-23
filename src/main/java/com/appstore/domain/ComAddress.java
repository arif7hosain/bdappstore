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

import com.appstore.domain.enumeration.AddressType;

/**
 * A ComAddress.
 */
@Entity
@Table(name = "com_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "comaddress")
public class ComAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "house")
    private String house;

    @Column(name = "road_no")
    private String RoadNo;

    @Column(name = "city")
    private String city;

    @Column(name = "created_date")
    private LocalDate CreatedDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "is_active")
    private Integer isActive;

    @ManyToOne
    @JoinColumn(name = "com_branch_id")
    private ComBranch comBranch;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoadNo() {
        return RoadNo;
    }

    public void setRoadNo(String RoadNo) {
        this.RoadNo = RoadNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(LocalDate CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public ComBranch getComBranch() {
        return comBranch;
    }

    public void setComBranch(ComBranch comBranch) {
        this.comBranch = comBranch;
    }

    public Upazila getUpazila() {
        return upazila;
    }

    public void setUpazila(Upazila upazila) {
        this.upazila = upazila;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComAddress comAddress = (ComAddress) o;
        return Objects.equals(id, comAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ComAddress{" +
            "id=" + id +
            ", addressType='" + addressType + "'" +
            ", officePhone='" + officePhone + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", postalCode='" + postalCode + "'" +
            ", house='" + house + "'" +
            ", RoadNo='" + RoadNo + "'" +
            ", city='" + city + "'" +
            ", CreatedDate='" + CreatedDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
