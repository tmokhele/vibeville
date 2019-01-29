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

    public static Map<String, String> getMocked() {
        Map<String, String> stringMap = new HashMap<>();
//        stringMap.put("image/hhp.jfif", "https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/image%2Fhhp.jfif?generation=1548320903452185&alt=media");
//        stringMap.put("image/thabsie.jfif", "https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/image%2Fthabsie.jfif?generation=1548245065968292&alt=media");
        stringMap.put("image/BigBuckBunny.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg");
        stringMap.put("image/ElephantsDream.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg");
        stringMap.put("image/ForBiggerBlazes.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg");
        stringMap.put("image/ForBiggerEscapes.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg");
        stringMap.put("image/ForBiggerFun.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg");
        stringMap.put("image/ForBiggerJoyrides.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg");
        stringMap.put("image/ForBiggerMeltdowns.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg");
        stringMap.put("image/Sintel.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg");
        stringMap.put("image/SubaruOutbackOnStreetAndDirt.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg");
        stringMap.put("image/TearsOfSteel.jpg","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg");
        return stringMap;
    }

    public static Map<String, String> getVideo() {
        Map<String, String> stringMap = new HashMap<>();
//        stringMap.put("video/BigBuckBunny_512kb.mp4","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/video%2FBigBuckBunny_512kb.mp4?generation=1548324285980240&alt=media");
//        stringMap.put("video/file_example_MP4_1280_10MG.mp4","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/video%2Ffile_example_MP4_1280_10MG.mp4?generation=1548681611666829&alt=media");
        stringMap.put("video/file_example_MP4_1280_10MG.mp4", "http://techslides.com/demos/sample-videos/small.mp4");
        stringMap.put("video/BigBuckBunny_512kb.mp4", "https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        stringMap.put("video/ElephantsDream.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        stringMap.put("video/ForBiggerBlazes.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        stringMap.put("video/ForBiggerEscapes.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
        stringMap.put("video/ForBiggerFun.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4");
        return stringMap;
    }

    public static Map<String, String> getAudio() {
        Map<String, String> audioFiles = new HashMap<>();
        audioFiles.put("audio/glenn lewis - all this love (128  kbps) (imp3juices.com).mp3", "http://static.videogular.com/assets/audios/videogular.mp3");
        audioFiles.put("audio/Tchaikovsky_Rococo_Var_orch.mp3", "http://www.hochmuth.com/mp3/Tchaikovsky_Rococo_Var_orch.mp3");
        audioFiles.put("audio/Haydn_Cello_Concerto_D-1.mp3", "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3");
        audioFiles.put("audio/Bloch_Prayer.mp3", "http://www.hochmuth.com/mp3/Bloch_Prayer.mp3");
        audioFiles.put("audio/Haydn_Adagio.mp3", "http://www.hochmuth.com/mp3/Haydn_Adagio.mp3");
        audioFiles.put("audio/Boccherini_Concerto_478-1.mp3", "http://www.hochmuth.com/mp3/Boccherini_Concerto_478-1.mp3");
//        audioFiles.put("audio/glenn lewis - all this love (128  kbps) (imp3juices.com).mp3","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/audio%2Fglenn%20lewis%20-%20all%20this%20love%20(128%20%20kbps)%20(imp3juices.com).mp3?generation=1548689359647105&alt=media");
//        audioFiles.put("audio/file_example_MP3_700KB.mp3","https://www.googleapis.com/download/storage/v1/b/bttc-cb6f6.appspot.com/o/audio%2Ffile_example_MP3_700KB.mp3?generation=1548689361223591&alt=media");
        return audioFiles;
    }
}
