package samsolutions.site.tour.controllers;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samsolutions.site.tour.services.SolrService;
import samsolutions.site.tour.services.TourService;

import java.io.IOException;

@RestController
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private TourService tourService;

    @Autowired
    private SolrService solrService;

    @GetMapping
    public String indexing() throws SolrServerException, IOException {
        solrService.index();
        return "123";
    }
}
