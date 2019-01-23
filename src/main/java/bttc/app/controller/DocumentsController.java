package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import bttc.app.util.ObjectMappingUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@Api(value = "DocumentsController")
public class DocumentsController {
    @Autowired
    StorageService storageService;

    @PostMapping
    public ResponseEntity uploadFiles(@RequestPart("files") List<MultipartFile> file, @RequestPart("fileInfo") String fileInfo) {

        List<FileUpload> files = new ArrayList<>();
        try {
            storageService.uploadFiles(ObjectMappingUtil.mapFileUploadList(file, fileInfo, files));
        } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,e.getMessage(),null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Files Uploaded Successfully", null));
    }


}
