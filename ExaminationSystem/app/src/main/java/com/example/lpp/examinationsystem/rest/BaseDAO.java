package com.example.lpp.examinationsystem.rest;

import android.util.Log;

import com.example.lpp.examinationsystem.model.BaseMBO;
import com.example.lpp.examinationsystem.model.BaseMBOList;
import com.example.lpp.examinationsystem.model.LabelList;
import com.example.lpp.examinationsystem.util.RestInfo;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class BaseDAO<T extends BaseMBO, E extends BaseMBOList> {

    public static final String BASE_URL = "http://47.107.241.57:8080/Entity/Uf47c4842079e/SmartHR";

    public T getObject(String id) {
        try {
            // The URL for making the GET request
            RestInfo annotation = this.getClass().getAnnotation(RestInfo.class);
            final String url = BASE_URL + "/" + annotation.path() + "/" + id;

            // Add the gzip Accept-Encoding header to the request
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Perform the HTTP GET request
            ResponseEntity<T> response = (ResponseEntity<T>) restTemplate.exchange(url, HttpMethod.GET,
                    new HttpEntity<Object>(requestHeaders), annotation.object(), "SpringSource");

            return (T) response.getBody();
        } catch (Exception e) {
            Log.e("HttpGet Error", e.getMessage(), e);
        }

        return null;
    }

    public List<E> getList() {
        try {
            // The URL for making the GET request
            RestInfo annotation = this.getClass().getAnnotation(RestInfo.class);
            final String url = BASE_URL + "/" + annotation.path();

            // Add the gzip Accept-Encoding header to the request
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Perform the HTTP GET request
            LabelList sss = new LabelList();
            ResponseEntity<E> response = (ResponseEntity<E>) restTemplate.exchange(url, HttpMethod.GET,
                    new HttpEntity<Object>(requestHeaders), annotation.array(), "SpringSource");

            return ((E) response.getBody()).getList();
        } catch (Exception e) {
            Log.e("HttpGet Error", e.getMessage(), e);
        }

        return null;
    }
}
