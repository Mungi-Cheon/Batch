package com.study.openapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiResponse {
    private Response response;

    @Setter
    @Getter
    public static class Response {
        private Body body;

    }

    @Setter
    @Getter
    public static class Body {
        private int totalCount;
        private Items items;

    }

    @Setter
    @Getter
    public static class Items {
        private List<Item> item;

    }

    @Getter
    public static class Item {
        private Long contentid;
        private String title;
        private String addr1;
        private String addr2;
        private String areacode;
        private String sigungucode;
        private String cat3;
        private String firstimage;
        private String firstimage2;
        private Double mapy;
        private Double mapx;
        private Integer mlevel;
        private String tel;
        private int likeCount;
        private Double rating;
    }
}
