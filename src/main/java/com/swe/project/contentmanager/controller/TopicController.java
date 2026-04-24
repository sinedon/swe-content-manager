package com.swe.project.contentmanager.controller;

import com.swe.project.contentmanager.dto.CreateTopicRequest;
import com.swe.project.contentmanager.dto.HotspotRequest;
import com.swe.project.contentmanager.service.ContentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final ContentService service;

    public TopicController(ContentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> getAllTopics() {
        return service.getAllTopics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTopic(@PathVariable String id) {
        return service.getTopic(id);
    }

    @PostMapping
    public ResponseEntity<String> createTopic(@RequestBody CreateTopicRequest request) {
        return service.createTopic(request);
    }

    @PostMapping("/{id}/hotspots")
    public ResponseEntity<String> addHotspot(@PathVariable String id, @RequestBody HotspotRequest request) {
        return service.addHotspot(id, request);
    }

    @PutMapping("/{id}/hotspots/{label}")
    public ResponseEntity<String> updateHotspot(@PathVariable String id,
                                                @PathVariable String label,
                                                @RequestBody HotspotRequest request) {
        return service.updateHotspot(id, label, request);
    }

    @DeleteMapping("/{id}/hotspots/{index}")
    public ResponseEntity<String> deleteHotspot(@PathVariable String id, @PathVariable int index) {
        return service.deleteHotspot(id, index);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable String id) {
        return service.deleteTopic(id);
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable String id,
                                              @RequestPart("file") MultipartFile file) {
        return service.uploadImage(id, file);
    }

    @PostMapping(value = "/{id}/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAudio(@PathVariable String id,
                                              @RequestPart("file") MultipartFile file) {
        return service.uploadAudio(id, file);
    }

    @GetMapping("/media/images/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        return service.getImage(filename);
    }

    @GetMapping("/media/audio/{filename:.+}")
    public ResponseEntity<byte[]> getAudio(@PathVariable String filename) {
        return service.getAudio(filename);
    }
}
