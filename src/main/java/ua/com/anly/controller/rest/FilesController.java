package ua.com.anly.controller.rest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FilesController {
    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/loadFiles")
    public String loadFile(@RequestParam MultipartFile[] files) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (MultipartFile file : files) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                jsonArray.put(resultFilename);
            }
        }

        return jsonArray.toString();
    }

    @PostMapping("/deleteFile")
    public boolean deleteFile(@RequestParam String filename) {
        File file = new File(uploadPath + "/" + filename);

        return file.delete();
    }
}
