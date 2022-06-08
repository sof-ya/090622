package ru.sof_ya.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductEntity
{
    private int id;
    private String title;
    private String type;
    private String articleNum;
    private String description;
    private String imagePath;
    private int prsonCount;
    private int workshopNum;
    private double minCost;

    public ProductEntity(String title, String type, String articleNum, String description, String imagePath, int prsonCount, int workshopNum, double minCost) {
        this(-1, title, type, articleNum, description, imagePath, prsonCount, workshopNum, minCost);
    }
}
