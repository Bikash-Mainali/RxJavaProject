package com.bm.rxjava.service;

import com.bm.rxjava.domain.Campaign;

import java.util.HashMap;
import java.util.Map;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 7/6/24
 */

public class CampaignService {

    public static Map<Long, Campaign>  getCampaign(){
        System.out.println("This is inside getCampaign() " + " Thread " + Thread.currentThread().getName());

        Map<Long, Campaign> map = new HashMap<>();
        try {
            map = Map.of(1L, Campaign.builder().campaignId(1L).campaignName("campaign1").build(),
                    2L, Campaign.builder().campaignId(2L).campaignName("campaign2").build());
        }
        catch (Exception e){

        }
        return  map;
    }
}
