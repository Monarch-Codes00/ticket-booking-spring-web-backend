package com.aptechph.ticket_booking_system;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class CloudinaryUploader {
    public static void main(String[] args) {
        try {
            CloudinaryUploader uploader = new CloudinaryUploader();
            List<String> random = uploader.getRandomImageUrls(4);
            System.out.println("Sample random Cloudinary image URLs:");
            for (String url : random) System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves existing event images from Cloudinary and returns their secure URLs.
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllImageUrls() throws Exception {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dvx14gqsa");
        config.put("api_key", "489779135926144");
        config.put("api_secret", "Tx3txTocJXHYhPq6o8H96VrPf80");

        Cloudinary cloudinary = new Cloudinary(config);

        Map<String, Object> result = (Map<String, Object>) cloudinary.api().resources(ObjectUtils.asMap("resource_type", "image"));
        List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");
        List<String> imageUrls = new ArrayList<>();
        if (resources != null) {
            for (Map<String, Object> resource : resources) {
                Object secureUrl = resource.get("secure_url");
                if (secureUrl != null) imageUrls.add(secureUrl.toString());
            }
        }

        return imageUrls;
    }

    /**
     * Return a list of `count` random image URLs (or fewer if not enough images).
     */
    public List<String> getRandomImageUrls(int count) throws Exception {
        List<String> urls = getAllImageUrls();
        if (urls.isEmpty() || count <= 0) return new ArrayList<>();
        Collections.shuffle(urls, new Random());
        if (count >= urls.size()) return new ArrayList<>(urls);
        return new ArrayList<>(urls.subList(0, count));
    }
}
