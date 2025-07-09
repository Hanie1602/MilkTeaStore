package com.example.milkteastore.model;

public class Category
{
    private int id;
    private String categoryName;
    private String description;
    private String createdTime;
    private String updatedTime;
    private String deletedTime;

    public Category(int id, String categoryName, String description, String createdTime, String updatedTime, String deletedTime) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.deletedTime = deletedTime;
    }

    // Getter và Setter
    public int getId() { return id; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public String getCreatedTime() { return createdTime; }
    public String getUpdatedTime() { return updatedTime; }
    public String getDeletedTime() { return deletedTime; }

    public void setId(int id) { this.id = id; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedTime(String createdTime) { this.createdTime = createdTime; }
    public void setUpdatedTime(String updatedTime) { this.updatedTime = updatedTime; }
    public void setDeletedTime(String deletedTime) { this.deletedTime = deletedTime; }
}
