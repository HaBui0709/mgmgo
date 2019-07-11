package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.ImageDTO;
import com.mgmtp.internship.experiences.model.tables.tables.records.ImageRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.mgmtp.internship.experiences.model.tables.tables.ActivityImage.ACTIVITY_IMAGE;
import static com.mgmtp.internship.experiences.model.tables.tables.Image.IMAGE;

/**
 * Activity Service interface.
 *
 * @author htnguyen
 */

@Component
public class ImageRepository {

    @Autowired
    private DSLContext dslContext;

    public ImageDTO findImageById(long imageId) {
        ImageRecord image = dslContext
                .selectFrom(IMAGE)
                .where(IMAGE.ID.eq(imageId))
                .fetchOne();

        if (image == null) {
            return null;
        }

        return image.into(ImageDTO.class);
    }

    @Transactional
    public Long updateActivityImage(long activityId, byte[] newImageData) {
        deleteActivityImage(activityId);
        return addActivityImage(activityId, newImageData);
    }

    private void deleteActivityImage(long activityId) {
        dslContext.deleteFrom(IMAGE)
                .where(IMAGE.ID.in(
                        dslContext.select(ACTIVITY_IMAGE.IMAGE_ID)
                                .from(ACTIVITY_IMAGE)
                                .where(ACTIVITY_IMAGE.ACTIVITY_ID.eq(activityId))
                )).execute();
    }

    private Long addActivityImage(long activityId, byte[] imageData) {
        long imageId = insert(imageData);
        try {
            return dslContext.insertInto(ACTIVITY_IMAGE, ACTIVITY_IMAGE.ACTIVITY_ID, ACTIVITY_IMAGE.IMAGE_ID)
                    .values(activityId, imageId)
                    .returning(ACTIVITY_IMAGE.IMAGE_ID)
                    .fetchOne().getValue(ACTIVITY_IMAGE.IMAGE_ID);
        } catch (UncategorizedSQLException | DataIntegrityViolationException e) {
            return null;
        }
    }

    public Long insert(byte[] imageData) {
        return dslContext.insertInto(IMAGE, IMAGE.IMAGE_DATA)
                .values(imageData)
                .returning(IMAGE.ID)
                .fetchOne().getId();
    }

    public int deleteImage(Long oldImageId) {
        return dslContext.deleteFrom(IMAGE)
                .where(IMAGE.ID.eq(oldImageId))
                .execute();
    }
    public boolean checkExistedImageOfActitvity(Long activityId){
        return dslContext.fetchExists(dslContext.selectFrom(ACTIVITY_IMAGE).where(ACTIVITY_IMAGE.ACTIVITY_ID.eq(activityId)));
    }
}
