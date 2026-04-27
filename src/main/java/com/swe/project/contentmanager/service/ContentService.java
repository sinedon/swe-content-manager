package com.swe.project.contentmanager.service;

import com.swe.project.contentmanager.client.ContentAccessClient;
import com.swe.project.contentmanager.dto.CreateTopicRequest;
import com.swe.project.contentmanager.dto.HotspotRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ContentService {

    private final ContentAccessClient client;

    //Circuit Breaker Code
    //this should be replaced with a structured cache that keeps requests like ArrayList<Topic> I think
    //Might need to modify it for 
    private String cachedTopics = "[]";

    public ContentService(ContentAccessClient client) {
        this.client = client;
    }

    @CircuitBreaker(name = "contentAccess", fallbackMethod = "fallbackTest")
    public ResponseEntity<String> getAllTopics() {
        return executeGetAllTopics();
    }

    public ResponseEntity<String> executeGetAllTopics() {
        ResponseEntity<String> response = client.getAllTopics();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            cachedTopics = response.getBody(); 
        }

        return response;
    }

    public ResponseEntity<String> fallbackTest(Throwable t) {
        System.out.println("FALLBACK TRIGGERED: " + t.getMessage());
        return ResponseEntity.ok(cachedTopics);
    }

    //End Circuit Breaker Code (Might need to make one for hotspot)

    public ResponseEntity<String> getTopic(String id) {
        return client.getTopic(id);
    }

    public ResponseEntity<String> createTopic(CreateTopicRequest request) {
        return client.createTopic(request);
    }

    public ResponseEntity<String> addHotspot(String id, HotspotRequest request) {
        return client.addHotspot(id, request);
    }

    public ResponseEntity<String> updateHotspot(String id, String label, HotspotRequest request) {
        return client.updateHotspot(id, label, request);
    }

    public ResponseEntity<String> deleteHotspot(String id, int index) {
        return client.deleteHotspot(id, index);
    }

    public ResponseEntity<String> deleteTopic(String id) {
        return client.deleteTopic(id);
    }

    public ResponseEntity<String> uploadImage(String id, MultipartFile file) {
        return client.uploadImage(id, file);
    }

    public ResponseEntity<String> uploadAudio(String id, MultipartFile file) {
        return client.uploadAudio(id, file);
    }

    public ResponseEntity<byte[]> getImage(String filename) {
        return client.getImage(filename);
    }

    public ResponseEntity<byte[]> getAudio(String filename) {
        return client.getAudio(filename);
    }
}
