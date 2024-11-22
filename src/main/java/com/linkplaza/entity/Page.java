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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pages")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(length = 64, unique = true, nullable = false)
    private String url;

    @Column(length = 64)
    private String title;

    @Column(length = 1024)
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String pictureUrl;

    @Column(length = 7)
    private String backgroundColor;

    @Column(length = 7)
    private String fontColor;

    @Column(length = 7)
    private String buttonBackgroundColor;

    @Column(length = 7)
    private String buttonFontColor;

    @Column()
    private boolean buttonRounded;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date dateLastModified;

    @OneToMany(mappedBy = "page", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("position ASC, date_created DESC")
    private List<SocialLink> socialLinks;

    @OneToMany(mappedBy = "page", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("position ASC, date_created DESC")
    private List<CustomLink> customLinks;

}
