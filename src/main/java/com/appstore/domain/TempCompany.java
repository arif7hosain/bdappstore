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

import com.appstore.domain.enumeration.CompanyType;

import com.appstore.domain.enumeration.AddressType;

/**
 * A TempCompany.
 */
@Entity
@Table(name = "temp_company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tempcompany")
public class TempCompany implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_type")
    private String branchType;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "company_information")
    private String companyInformation;

    @Column(name = "business_description")
    private String businessDescription;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "google_plus")
    private String googlePlus;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "linkedin")
    private String linkedin;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "website")
    private String website;

    @Column(name = "city")
    private String city;

    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

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
    private String roadNo;

    @Column(name = "created_date")
    private LocalDate CreatedDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "active_status")
    private Integer activeStatus;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "service_category_id")
    private ServiceCategory serviceCategory;

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    private Upazila upazila;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String BranchType) {
        this.branchType = BranchType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
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
        return roadNo;
    }

    public void setRoadNo(String RoadNo) {
        this.roadNo = RoadNo;
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

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
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
        TempCompany tempCompany = (TempCompany) o;
        return Objects.equals(id, tempCompany.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TempCompany{" +
            "id=" + id +
            ", username='" + username + "'" +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            ", branchName='" + branchName + "'" +
            ", BranchType='" + branchType + "'" +
            ", companyName='" + companyName + "'" +
            ", shortName='" + shortName + "'" +
            ", companyInformation='" + companyInformation + "'" +
            ", businessDescription='" + businessDescription + "'" +
            ", facebook='" + facebook + "'" +
            ", googlePlus='" + googlePlus + "'" +
            ", youtube='" + youtube + "'" +
            ", linkedin='" + linkedin + "'" +
            ", twitter='" + twitter + "'" +
            ", website='" + website + "'" +
            ", city='" + city + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
            ", companyType='" + companyType + "'" +
            ", addressType='" + addressType + "'" +
            ", officePhone='" + officePhone + "'" +
            ", contactNumber='" + contactNumber + "'" +
            ", postalCode='" + postalCode + "'" +
            ", house='" + house + "'" +
            ", RoadNo='" + roadNo + "'" +
            ", CreatedDate='" + CreatedDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", activeStatus='" + activeStatus + "'" +
            '}';
    }
}
