package com.linkplaza.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_links")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id", nullable = false)
    private Page page;

    @ManyToOne
    @JoinColumn(name = "social_platform_id", referencedColumnName = "id", nullable = false)
    private SocialPlatform socialPlatform;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateCreated;

    @OneToMany(mappedBy = "socialLink", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SocialLinkClick> socialLinkClicks;
}