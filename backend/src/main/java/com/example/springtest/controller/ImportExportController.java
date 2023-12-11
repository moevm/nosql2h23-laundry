package com.example.springtest.controller;

import com.example.springtest.service.ImportExportService;
import io.netty.buffer.ByteBufInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ImportExportController {

    private final ImportExportService importExportService;

    @PostMapping("/import")
    public void importData(@RequestParam("file") MultipartFile file) throws IOException {

        Path path = Paths.get("/home/ilya201232/neo4j/import/import.json");
        Files.write(path, file.getBytes());

        importExportService.importData();
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportData() {

        ByteArrayResource resource = new ByteArrayResource(importExportService.exportData().getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.json");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
