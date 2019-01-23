package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import bttc.app.util.ObjectMappingUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage(), null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Files Uploaded Successfully", null));
    }

    @GetMapping("/{documentType}")
    public ResponseEntity getFiles(@PathVariable String documentType) {
        try {
           return ResponseEntity.ok(storageService.getFiles(documentType));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
