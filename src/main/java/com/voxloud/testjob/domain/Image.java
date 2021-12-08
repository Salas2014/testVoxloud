package com.voxloud.testjob.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private String imageFileName;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    public Image() {
    }

    public Image(Long id, String title, String description, String imagePath, String imageFileName, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.user = user;
    }

    public Image(String title, String description, String imagePath, String imageFileName, User user) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.imageFileName = imageFileName;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return Objects.equals(id, image.id) && Objects.equals(title, image.title) && Objects.equals(description, image.description) && Objects.equals(imagePath, image.imagePath) && Objects.equals(imageFileName, image.imageFileName) && Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, imagePath, imageFileName, user);
    }
}
