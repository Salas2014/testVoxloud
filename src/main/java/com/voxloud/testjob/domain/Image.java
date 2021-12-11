package com.voxloud.testjob.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Builder
@Entity

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @NotNull
    @Column(unique = true)
    private String tag;
    private String imagePath;
    private String imageFileName;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Image() {
    }

    public Image(Long id, String title, String tag, String imagePath, String imageFileName, User user) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.user = user;
    }

    public Image(Long id, String title, String tag, String imagePath, String imageFileName, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Image(String title, String tag, String imagePath, String imageFileName, User user) {
        this.title = title;
        this.tag = tag;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", description='" + tag + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String description) {
        this.tag = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(title, image.title) && Objects.equals(tag, image.tag) && Objects.equals(imagePath, image.imagePath) && Objects.equals(imageFileName, image.imageFileName) && Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tag, imagePath, imageFileName, user);
    }


}
