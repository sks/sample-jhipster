package io.github.sks.samplejhipster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MyTestEntity.
 */
@Entity
@Table(name = "my_test_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyTestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(max = 100)
    @Column(name = "company", length = 100, nullable = false)
    private String company;

    @NotNull
    @Size(min = 2, max = 3)
    @Column(name = "country", length = 3, nullable = false)
    private String country;

    @Column(name = "tag")
    private String tag;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @NotNull
    @Column(name = "modifed_on", nullable = false)
    private ZonedDateTime modifedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getModifedOn() {
        return modifedOn;
    }

    public void setModifedOn(ZonedDateTime modifedOn) {
        this.modifedOn = modifedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyTestEntity myTestEntity = (MyTestEntity) o;
        if (myTestEntity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myTestEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyTestEntity{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", age='" + age + "'" +
            ", email='" + email + "'" +
            ", company='" + company + "'" +
            ", country='" + country + "'" +
            ", tag='" + tag + "'" +
            ", createdOn='" + createdOn + "'" +
            ", modifedOn='" + modifedOn + "'" +
            '}';
    }
}