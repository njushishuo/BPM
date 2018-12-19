package com.github.florent37.materialviewpager.sample.rest;

import android.util.Log;

import com.github.florent37.materialviewpager.sample.model.Paper;
import com.github.florent37.materialviewpager.sample.model.PaperList;
import com.github.florent37.materialviewpager.sample.util.RestInfo;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestInfo(path = "Paper", object = Paper.class, array = PaperList.class)
public class PaperDAO extends BaseDAO<Paper, PaperList> {

    public List<Paper> getPapersByRecruit(String recruitId) {
        try {
            // The URL for making the GET request
            RestInfo annotation = this.getClass().getAnnotation(RestInfo.class);
            final String url = BASE_URL + "/" + annotation.path() + "/?" + annotation.path() + ".recruit_id=" + recruitId;

            // Add the gzip Accept-Encoding header to the request
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Perform the HTTP GET request
            ResponseEntity<PaperList> response = (ResponseEntity<PaperList>) restTemplate.exchange(url, HttpMethod.GET,
                    new HttpEntity<Object>(requestHeaders), annotation.array(), "SpringSource");

            return response.getBody().getList();
        } catch (Exception e) {
            Log.e("HttpGetCondition Error", e.getMessage(), e);
        }

        return null;
    }
}
