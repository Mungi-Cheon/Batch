package com.study.stay.dto;

import com.study.stay.entity.StayInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StayInfoResponse {

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

    public static StayInfoResponse from(long id, String title, String address1, String address2,
        String areacode, String sigungucode, String firstimage, String firstimage2,
        double mapY, double mapX, int mapLevel, String tel, int likeCount, double rating){
        return StayInfoResponse.builder()
            .id(id)
            .title(title)
            .address1(address1)
            .address2(address2)
            .areacode(areacode)
            .sigungucode(sigungucode)
            .firstimage(firstimage)
            .firstimage2(firstimage2)
            .mapY(mapY)
            .mapX(mapX)
            .mapLevel(mapLevel)
            .tel(tel)
            .likeCount(likeCount)
            .rating(rating)
            .build();
    }
}
