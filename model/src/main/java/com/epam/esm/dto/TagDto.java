package com.epam.esm.dto;

public class TagDto {
    private long id;
    private String tag_name;

    public TagDto() {
    }

    public TagDto(long id, String tag_name) {
        this.id = id;
        this.tag_name = tag_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
