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

    public record Response(Body body) {
    }

    public record Body(
        int totalCount, Items items
    ) {
    }

    public record Items(
        List<Item> item
    ) {
    }

    public record Item (
        long contentid,
        String title,
        String addr1,
        String addr2,
        String areacode,
        String sigungucode,
        String cat3,
        String firstimage,
        String firstimage2,
        double mapy,
        double mapx,
        int mlevel,
        String tel,
        int likeCount,
        double rating
    ) {

    }
}
