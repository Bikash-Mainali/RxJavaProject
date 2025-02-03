package com.bm.rxjava.controller;
import com.bm.rxjava.domain.Campaign;
import com.bm.rxjava.domain.Keywords;
import com.bm.rxjava.service.CampaignService;
import com.bm.rxjava.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 7/5/24
 */

@RestController
public class CampaignController {

    //synchronous paradigm
    @GetMapping("campaign1/{campaignId}")
    public Campaign getCampaign1(@PathVariable Long campaignId) {
        Campaign campaign = CampaignService.getCampaign().get(campaignId);
        List<Keywords> keywords = KeywordService.getKeywords().get(campaignId); //blocking and has to wait for previous line to complete
        campaign.setKeywordsList(keywords); //blocking
        return campaign;
    }

    //asynchronous paradigm using Future
    @GetMapping("campaign2/{campaignId}")
    public Campaign getCampaign2(@PathVariable Long campaignId) {

        CompletableFuture<Campaign> campaignFuture = CompletableFuture.supplyAsync(() -> CampaignService.getCampaign().get(campaignId));
        CompletableFuture<List<Keywords>> keywordsFuture = CompletableFuture.supplyAsync(() ->  KeywordService.getKeywords().get(campaignId));

        CompletableFuture bothFuture = CompletableFuture.allOf(campaignFuture, keywordsFuture);

        bothFuture.join();

        Campaign campaign = campaignFuture.join();
        List<Keywords> keywords =  keywordsFuture.join();
        campaign.setKeywordsList(keywords); //blocking
        return campaign;
    }

    //asynchronous paradigm using reactiveX
    @GetMapping("campaign3/{campaignId}")
    public Mono<ResponseEntity<Campaign>> getCampaign3(@PathVariable Long campaignId) {
        System.out.println("main thread " + Thread.currentThread().getName());

        Mono<Campaign> campaignObservable = Mono.just(CampaignService.getCampaign().get(campaignId)).delayElement(Duration.ofSeconds(5));
        Mono<List<Keywords>> keywordsObservable = Mono.just(KeywordService.getKeywords().get(campaignId)).delayElement(Duration.ofSeconds(5));
        System.out.println(campaignObservable.subscribe(x -> System.out.println("campaign id: " + x.getCampaignId())));
        Mono<Campaign> result = campaignObservable.zipWith(keywordsObservable).map(x -> {
            System.out.println("..." + x.getT1().getCampaignName() + " on thread " + Thread.currentThread().getName());
            x.getT1().setKeywordsList(x.getT2());
            return x.getT1();
        });
        System.out.println("............before result.......");
         return  result.map(data -> ResponseEntity.ok().body(data));
    }
}
