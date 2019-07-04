package com.mgmtp.internship.experiences.controllers.api;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.ImageDTO;
import com.mgmtp.internship.experiences.exceptions.ApiException;
import com.mgmtp.internship.experiences.services.ImageService;
import com.mgmtp.internship.experiences.services.UserService;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Quote api controller.
 *
 * @author htnguyen
 */
@RestController
@RequestMapping("api/image")
public class ImageRestController extends BaseRestController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") long id) {
        ImageDTO imageDTO = imageService.findImageById(id);

        if (imageDTO == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Image not found.");
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(3600 * 24 * 30, TimeUnit.SECONDS))
                .body(imageDTO.getImage());
    }

    @PostMapping(value = "/user")
    public Object addUserImage(@RequestParam("photo") MultipartFile photo) throws IOException {
        byte[] imageData = photo.getBytes();

        try {
            if (!imageService.validateProfilePicture(photo.getInputStream()))
                throw new ApiException(HttpStatus.BAD_REQUEST, "The image is too large.");
        } catch (IOException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Can not process the image.");
        }

        CustomUserDetails userDetails = userService.getCurrentUser();
        try {
            Long imageId = imageService.updateUserImage(userDetails.getId(), userDetails.getUserProfile().getImageId(), imageData);
            userDetails.getUserProfile().setImageId(imageId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", imageId);
            return jsonObject;
        } catch (RuntimeException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Server error.");
        }
    }


}
