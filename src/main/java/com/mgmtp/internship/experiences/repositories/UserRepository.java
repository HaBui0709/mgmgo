package com.mgmtp.internship.experiences.repositories;

import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.model.tables.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mgmtp.internship.experiences.model.tables.tables.User.USER;

/**
 * UserRepository for login.
 *
 * @author ttkngo
 */
@Component
public class UserRepository {

    @Autowired
    private DSLContext dslContext;

    public UserRecord findUserByUsername(String userName) {
        return dslContext
                .selectFrom(USER)
                .where(USER.USERNAME.eq(userName))
                .fetchOne();
    }

    public boolean updateImage(long userId, Long imageId) {
        return dslContext
                .update(USER)
                .set(USER.IMAGE_ID, imageId)
                .where(USER.ID.eq(userId))
                .execute() == 1;
    }

    public boolean updateProfile(long userId, UserProfileDTO profile) {
        return dslContext.update(USER)
                .set(USER.DISPLAY_NAME, profile.getDisplayName())
                .where(USER.ID.eq(userId))
                .execute() == 1;
    }

    public boolean checkExitDisplayName(String displayName, long id) {
        return dslContext
                .fetchExists(dslContext.selectFrom(USER)
                .where(USER.DISPLAY_NAME.likeIgnoreCase(displayName)
                        .and(USER.ID.notEqual(id))));
    }

    public UserRecord getReputationScoreById(Long id) {
        return dslContext
                .selectFrom(USER)
                .where(USER.ID.eq(id))
                .fetchOne();
    }

    public int updateReputationScoreById(Long id, int reputationScore) {
        return dslContext
                .update(USER)
                .set(USER.REPUTATION_SCORE, reputationScore)
                .where(USER.ID.eq(id))
                .execute();
    }


    public int insertUser(String username) {
        return dslContext
                .insertInto(USER, USER.USERNAME, USER.DISPLAY_NAME)
                .values(username, username)
                .execute();
    }

    public boolean checkUsernameAvailable(String username) {
        return dslContext
                .selectFrom(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne() != null;
    }

}

