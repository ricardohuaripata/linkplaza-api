package com.linkplaza.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_link_clicks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLinkClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "social_link_id", referencedColumnName = "id", nullable = false)
    private SocialLink socialLink;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private Date dateCreated;
}
