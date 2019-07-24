package com.mgmtp.internship.experiences.dto;

import java.util.List;

/**
 * Activity DTO.
 *
 * @author thuynh
 */
public class ActivityDTO {
    private long id;
    private String name;
    private Long imageId;
    private List<TagDTO> tags;
    private String address;

    public ActivityDTO() {
    }

    public ActivityDTO(long id, String name, Long imageId, List<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.tags = tags;
    }

    public ActivityDTO(long id, String name, Long imageId, List<TagDTO> tags, String address) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.tags = tags;
        this.address = address;
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
