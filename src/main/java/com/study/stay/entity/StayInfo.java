package com.study.stay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer likeCount;
    private Double rating;

    public static StayInfo from(String title, String address1, String address2,
        String areacode, String sigungucode, String firstimage, String firstimage2,
        double mapY, double mapX, int mapLevel, String tel, int likeCount, double rating){
        return StayInfo.builder()
            .title(Objects.requireNonNullElse(title, ""))
            .address1(Objects.requireNonNullElse(address1, ""))
            .address2(Objects.requireNonNullElse(address2, ""))
            .areacode(Objects.requireNonNullElse(areacode, ""))
            .sigungucode(Objects.requireNonNullElse(sigungucode, ""))
            .firstimage(Objects.requireNonNullElse(firstimage, ""))
            .firstimage2(Objects.requireNonNullElse(firstimage2, ""))
            .mapY(mapY)
            .mapX(mapX)
            .mapLevel(mapLevel)
            .tel(Objects.requireNonNullElse(tel, ""))
            .likeCount(likeCount)
            .rating(rating)
            .build();
    }
}
