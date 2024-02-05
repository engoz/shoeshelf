package com.shoeshelf.dto.customer;


import com.shoeshelf.dto.BaseDto;

import java.util.Objects;

public class CustomerDto extends BaseDto {
    // Encapsulating the id,firstName,lastName,email,address,comments
    // only approachable and used
    // only access getters and setters methods defined
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String comments;
    public CustomerDto(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CustomerDto(Integer id, String firstName, String lastName, String email, String address, String comments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        if (Objects.equals(getId(), that.getId())) if (Objects.equals(getFirstName(), that.getFirstName()))
            if (Objects.equals(getLastName(), that.getLastName())) if (Objects.equals(getEmail(), that.getEmail()))
                if (Objects.equals(getAddress(), that.getAddress()))
                    if (Objects.equals(getComments(), that.getComments())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getAddress(), getComments());
    }
}
