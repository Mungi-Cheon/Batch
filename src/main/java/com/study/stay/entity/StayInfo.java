package com.study.stay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StayInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String address1;
    private String address2;
    private String areacode;
    private String sigungucode;
    private String firstimage;
    private String firstimage2;
    private Double mapY;
    private Double mapX;
    private Integer mapLevel;
    private String tel;
    private int likeCount;
    private Double rating;
}
