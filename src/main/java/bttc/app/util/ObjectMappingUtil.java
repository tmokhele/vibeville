package bttc.app.util;

import bttc.app.model.FileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ObjectMappingUtil {

    private ObjectMappingUtil() {

    }

    public static Object mapChats(String jsonString, Object targetObject) {
        Gson g = new Gson();
        return g.fromJson(jsonString, targetObject.getClass());
    }

    public static List<FileUpload> mapFileUploadList(@RequestPart("files") List<MultipartFile> file, @RequestPart("fileInfo") String fileInfo, List<FileUpload> files) {
        List<LinkedHashMap> fileUploads;
        try {
            fileUploads = new ObjectMapper().readValue(fileInfo, ArrayList.class);
            for (LinkedHashMap linkedHashMap : fileUploads) {
                FileUpload fileUpload = new FileUpload();
                fileUpload.setDocName((String) linkedHashMap.get("docname"));
                fileUpload.setDocDescription((String) linkedHashMap.get("docdescription"));
                String filename = (String) linkedHashMap.get("filename");
                fileUpload.setFileName(filename);
                fileUpload.setFileType((String) linkedHashMap.get("filetype"));

                for (MultipartFile multipartFile : file) {
                    if (multipartFile.getOriginalFilename().equalsIgnoreCase(filename)) {
                        fileUpload.setFile(multipartFile.getBytes());
                        break;
                    }
                }
                files.add(fileUpload);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
