package com.linkplaza.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 32, nullable = false)
    private String role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateLastModified;

    @Column(nullable = false)
    private boolean emailVerified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("date_last_modified DESC")
    private List<Page> pages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VerificationCode> verificationCodes;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}