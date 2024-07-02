package samsolutions.site.tour.services;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samsolutions.site.tour.entities.Tour;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SolrService {
    @Autowired
    private TourService tourService;

    private HttpSolrClient solr;

    public SolrService(){
        String urlString = "http://localhost:8983/solr/tours";
        solr = new HttpSolrClient.Builder(urlString).build();
        solr.setParser(new XMLResponseParser());
    }

    public void index() throws SolrServerException, IOException {
        List<Tour> tours = tourService.getTours();
        for (Tour tour:tours) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", tour.getId());
            document.addField("country", tour.getCountry());
            document.addField("endDate", tour.getEndDate());
            document.addField("startDate", tour.getStartDate());
            document.addField("name", tour.getName());
            document.addField("price", tour.getPrice());
            solr.add(document);
        }
        solr.commit();

    }

    public List<Tour> getByCountry(String country) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery("country:"+country);
        QueryResponse response = solr.query(query);
        SolrDocumentList documents = response.getResults();
        List<Tour> tours = new ArrayList<>();
        for (SolrDocument document : documents) {
            Tour tour = new Tour();

            if (document.get("id") != null) {
                tour.setId(Integer.parseInt(document.get("id").toString()));
            }

            if (document.get("country") != null) {
                tour.setCountry(document.get("country").toString());
            }

            if (document.get("name") != null) {
                tour.setName(document.get("name").toString());
            }

            if (document.get("price") != null) {
                tour.setPrice(Double.parseDouble(document.get("price").toString()));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            if (document.get("endDate") != null) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(document.get("endDate").toString(), formatter);
                Instant instant = zonedDateTime.toInstant();
                Date date = Date.from(instant);
                tour.setEndDate(date);
            }

            if (document.get("startDate") != null) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(document.get("startDate").toString(), formatter);
                Instant instant = zonedDateTime.toInstant();
                Date date = Date.from(instant);
                tour.setStartDate(date);
            }

            tours.add(tour);
        }
        return tours;
    }
}
