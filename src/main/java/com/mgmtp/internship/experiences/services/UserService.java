package com.mgmtp.internship.experiences.services;


import com.mgmtp.internship.experiences.config.security.CustomLdapUserDetails;
import com.mgmtp.internship.experiences.model.tables.tables.records.UserRecord;

import com.mgmtp.internship.experiences.dto.UserProfileDTO;

/**
 * User service interface.
 *
 * @author thuynh
 */
public interface UserService {
    CustomLdapUserDetails getCurrentUser();

    int insertUser(String username);

    boolean checkUsernameAvailable(String username);

    UserRecord findUserByUserName(String username);

    boolean updateProfile(long userId, UserProfileDTO profile);

    boolean checkExitDisplayName(String displayName, long id);
}
