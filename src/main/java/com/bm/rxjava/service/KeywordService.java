package com.bm.rxjava.service;

import com.bm.rxjava.domain.Keywords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 7/6/24
 */

public class KeywordService {

    public static Map<Long, List<Keywords>>  getKeywords(){
        System.out.println("This is inside getKeywords() " + " Thread " + Thread.currentThread().getName());

        Map<Long, List<Keywords>> map = new HashMap<>();
        try {
            map =  Map.of(1L, List.of(Keywords.builder().keywordId(1L).keyword("keyword1").build()),
                    2L, List.of(Keywords.builder().keywordId(2L).keyword("keyword1").build()));
        }
        catch (Exception e){

        }
        return  map;
    }
}
