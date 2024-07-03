package samsolutions.site.tour;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import samsolutions.site.tour.services.SolrService;
import samsolutions.site.tour.services.TourService;

import java.io.IOException;

@Component
public class ScheduledTasks {
    @Autowired
    private SolrService solrService;

    @Scheduled(fixedRate = 60*1000)
    public void reportCurrentTime() throws SolrServerException, IOException {
        solrService.index();
        System.out.println("Database indexed");
    }
}
