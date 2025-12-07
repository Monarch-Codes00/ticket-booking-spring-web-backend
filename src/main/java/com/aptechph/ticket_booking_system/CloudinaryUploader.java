package com.aptechph.ticket_booking_system;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudinaryUploader {

    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dvx14gqsa");
        config.put("api_key", "489779135926144");
        config.put("api_secret", "Tx3txTocJXHYhPq6o8H96VrPf80");

        Cloudinary cloudinary = new Cloudinary(config);

        String[] imageFiles = {
            "124e1a5a6973d3b7ce8573d2f44d8997.jpg",
            "20210725093508.jpeg",
            "b38c06b09451a3a5da09eb19183b0e62.jpg",
            "bc911466780114572ad91af5ad853876.jpg"
        };

        for (String fileName : imageFiles) {
            try (FileInputStream inputStream = new FileInputStream(fileName)) {
                Map<?,?> uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
                String secureUrl = (String) uploadResult.get("secure_url");
                System.out.println("Uploaded " + fileName + " successfully. URL: " + secureUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Uploads a predefined set of event images to Cloudinary and returns their secure URLs.
     * This mirrors the logic in the main method but returns the collected URLs.
     */
    public List<String> uploadEventImages() throws IOException {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dvx14gqsa");
        config.put("api_key", "489779135926144");
        config.put("api_secret", "Tx3txTocJXHYhPq6o8H96VrPf80");

        Cloudinary cloudinary = new Cloudinary(config);

        String[] imageFiles = {
            "124e1a5a6973d3b7ce8573d2f44d8997.jpg",
            "20210725093508.jpeg",
            "b38c06b09451a3a5da09eb19183b0e62.jpg",
            "bc911466780114572ad91af5ad853876.jpg"
        };

        List<String> uploadedUrls = new ArrayList<>();
        for (String fileName : imageFiles) {
            try (FileInputStream inputStream = new FileInputStream(fileName)) {
                Map<?,?> uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
                String secureUrl = (String) uploadResult.get("secure_url");
                if (secureUrl != null) uploadedUrls.add(secureUrl);
            }
        }

        return uploadedUrls;
    }
}
