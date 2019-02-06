package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import bttc.app.util.ObjectMappingUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@Api(value = "DocumentsController")
public class DocumentsController {
    @Autowired
    StorageService storageService;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity uploadFiles(@RequestPart("files") List<MultipartFile> file, @RequestPart("fileInfo") String fileInfo) {
        List<FileUpload> files = new ArrayList<>();
        return ResponseEntity.ok(new ApiResponse(true, "Files Uploaded Successfully", storageService.upLoadCloudinaryFiles(ObjectMappingUtil.mapFileUploadList(file, fileInfo, files))));
    }

    @GetMapping("/{documentType}")
    public ResponseEntity getFiles(@PathVariable String documentType) {
//        Map<String, String> files;
//        if (documentType.equalsIgnoreCase("video")) {
//            files = ObjectMappingUtil.getVideo();
//        } else if (documentType.equalsIgnoreCase("audio")) {
//            files = ObjectMappingUtil.getAudio();
//        } else {
//            files = ObjectMappingUtil.getMocked();
//        }
        return ResponseEntity.ok(storageService.getCloudinaryFiles(documentType));
    }
}
