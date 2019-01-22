package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@Api(value = "DocumentsController")
public class DocumentsController {
    @Autowired
    StorageService storageService;

    @PostMapping
    public ResponseEntity uploadFiles(@RequestBody List<FileUpload> fileUploadList)
    {
        try {
            storageService.uploadFiles(fileUploadList);
        } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,e.getMessage(),null));
        }
        return  ResponseEntity.ok(new ApiResponse(true,"Files Uploaded Successfully",null));
    }
}
