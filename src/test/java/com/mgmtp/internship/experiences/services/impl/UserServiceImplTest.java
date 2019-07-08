package com.mgmtp.internship.experiences.services.impl;

import com.mgmtp.internship.experiences.config.security.CustomLdapUserDetails;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import static org.mockito.Mockito.*;

/**
 * Unit test for user service.
 *
 * @author thuynh
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long USER_ID = 1L;
    private static final long IMAGE_ID = 1l;
    private static final String DISPLAY_NAME = "name";
    private static final UserProfileDTO USER_PROFILE_DTO = new UserProfileDTO(IMAGE_ID, DISPLAY_NAME);
    private static final LdapUserDetails LDAP_USER_DETAILS = mock(LdapUserDetails.class);
    private static final CustomLdapUserDetails EXPECTED_USER = new CustomLdapUserDetails(USER_ID, USER_PROFILE_DTO, LDAP_USER_DETAILS);

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnCustomUserDetail() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(EXPECTED_USER);

        CustomLdapUserDetails actualUser = userService.getCurrentUser();

        Assert.assertEquals(EXPECTED_USER, actualUser);
    }

    @Test
    public void shouldReturnNullIfNotLogged() {
        CustomLdapUserDetails expectedUser = null;
        Authentication authentication = null;
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        CustomLdapUserDetails actualUser = userService.getCurrentUser();

        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void shouldReturnTrueIfUpdateProfileSuccess() {
        boolean expectedResult = true;
        when(userRepository.updateProfile(USER_ID, USER_PROFILE_DTO)).thenReturn(expectedResult);
        doReturn(EXPECTED_USER).when(userService).getCurrentUser();

        boolean actualResult = userService.updateProfile(USER_ID, USER_PROFILE_DTO);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnFalseIfUpdateProfileFailed() {
        boolean expectedResult = false;
        when(userRepository.updateProfile(USER_ID, USER_PROFILE_DTO)).thenReturn(expectedResult);

        boolean actualResult = userService.updateProfile(USER_ID, USER_PROFILE_DTO);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnFalseOnUpdateProfileIfNotLogged() {
        boolean expectedResult = false;
        when(userRepository.updateProfile(USER_ID, USER_PROFILE_DTO)).thenReturn(true);
        doReturn(null).when(userService).getCurrentUser();

        boolean actualResult = userService.updateProfile(USER_ID, USER_PROFILE_DTO);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnFalseIfDisplayNameNotExit() {
        boolean expectedResult = false;
        when(userRepository.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), USER_ID)).thenReturn(false);

        boolean actualResult = userService.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), USER_ID);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnTrueIfDisplayNameExit() {
        boolean expectedResult = true;
        when(userRepository.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), USER_ID)).thenReturn(true);

        boolean actualResult = userService.checkExitDisplayName(USER_PROFILE_DTO.getDisplayName(), USER_ID);

        Assert.assertEquals(expectedResult, actualResult);
    }
}

