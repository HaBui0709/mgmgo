package com.mgmtp.internship.experiences.services.impl;

import com.mgmtp.internship.experiences.dto.ImageDTO;
import com.mgmtp.internship.experiences.repositories.ImageRepository;
import com.mgmtp.internship.experiences.repositories.UserRepository;
import com.mgmtp.internship.experiences.services.ImageService;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * Activity Service interface.
 *
 * @author htnguyen
 */

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ImageDTO findImageById(long imageId) {
        return imageRepository.findImageById(imageId);
    }

    @Override
    public Long updateActivityImage(long activityId, byte[] imageData) {
        return imageRepository.updateActivityImage(activityId, imageData);
    }

    @Override
    public boolean checkExistedImageOfActivity(Long activityId) {
        return imageRepository.checkExistedImageOfActitvity(activityId);
    }

    public Long insertImage(byte[] imageData) {
        return imageRepository.insert(imageData);
    }

    @Override
    @Transactional
    public Long updateUserImage(long userId, Long oldImageId, byte[] data) {
        Long imageId = insertImage(data);
        if (imageId == null) throw new DataAccessException("Could not insert image.");
        userRepository.updateImage(userId, imageId);
        if (oldImageId != null) {
            imageRepository.deleteImage(oldImageId);
        }
        return imageId;
    }

    @Override
    public boolean validateProfilePicture(InputStream inputStream) throws IOException {
        BufferedImage img = ImageIO.read(inputStream);
        if (img == null) {
            throw new IOException("File is not an image");
        }
        return img.getWidth() <= 150 && img.getHeight() <= 150;
    }
}
