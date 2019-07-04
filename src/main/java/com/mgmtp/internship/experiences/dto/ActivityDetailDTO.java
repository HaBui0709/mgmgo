package com.mgmtp.internship.experiences.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Activity detail.
 *
 * @author httbui.ext
 */
public class ActivityDetailDTO {

    private long id;

    @NotNull(message = "Name may not be null")
    @NotBlank(message = "Name may not be blank")
    @Size(max = 100, message = "You can not write more than 100 characters for name")
    private String name;

    @NotNull(message = "Description may not be null")
    @NotBlank(message = "Description may not be blank")
    @Size(max = 100000, message = "You can not write more than 10000 characters for description")
    private String description;
    private Long imageId;
    private double rating;
    private long createdByUserId;
    private long updatedByUserId;

    public ActivityDetailDTO() {

    }

    public ActivityDetailDTO(long id, String name, String description, double rating, Long imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public long getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(long updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDetailDTO that = (ActivityDetailDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
