package bttc.app.util;

import bttc.app.model.FileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    public static Map<String,String> getMocked()
    {
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("image/hhp.jfif","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/image%2Fhhp.jfif?generation=1548320903452185&alt=media");
        stringMap.put("image/thabsie.jfif","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/image%2Fthabsie.jfif?generation=1548245065968292&alt=media");
        return stringMap;
    }

    public static Map<String, String> getVideo() {
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("image/hhp.jfif","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/video%2FBigBuckBunny_512kb.mp4?generation=1548324285980240&alt=media");
        return stringMap;
    }
}
