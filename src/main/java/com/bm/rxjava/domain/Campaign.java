package com.bm.rxjava.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 7/6/24
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    private Long campaignId;
    private String campaignName;
    List<Keywords> keywordsList = new ArrayList<>();

}
