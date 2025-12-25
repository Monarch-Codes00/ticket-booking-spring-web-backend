package com.aptechph.ticket_booking_system.events.init;

import com.aptechph.ticket_booking_system.CloudinaryUploader;
import com.aptechph.ticket_booking_system.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class EventImageSeeder implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(EventImageSeeder.class);

    private final CloudinaryUploader cloudinaryUploader;
    private final EventService eventService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            int eventCount = eventService.getAllEvents().size();
            if (eventCount == 0) {
                logger.info("No events found; skipping Cloudinary image seeding.");
                return;
            }

            logger.info("Seeding {} events with Cloudinary images...", eventCount);
            List<String> imageUrls = cloudinaryUploader.getRandomImageUrls(eventCount);
            if (imageUrls == null || imageUrls.isEmpty()) {
                logger.warn("No Cloudinary images were found; skipping update.");
                return;
            }

            eventService.updateEventImages(imageUrls);
            logger.info("Assigned {} image(s) to events.", imageUrls.size());
        } catch (Exception e) {
            logger.error("Failed to seed event images at startup", e);
        }
    }
}
