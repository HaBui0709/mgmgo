package com.mgmtp.internship.experiences.services.impl;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.model.tables.tables.records.UserRecord;
import com.mgmtp.internship.experiences.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    private static final long EXPECTEDID = 1L;
    private static final String EXPECTEDUSERNAME = "username";
    private static final String EXPECTEDDISPLAYNAME = "displayname";
    private static final long EXPECTEDIMAGEID = 1;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void shouldReturnUserDetails() {
        UserRecord expectedUserRecord = new UserRecord(EXPECTEDID, EXPECTEDUSERNAME, EXPECTEDDISPLAYNAME, Mockito.anyString(), EXPECTEDIMAGEID);
        UserDetails expectedUserDetails = new CustomUserDetails(expectedUserRecord.getId(), new UserProfileDTO(expectedUserRecord.getImageId(), expectedUserRecord.getDisplayName()), expectedUserRecord.getUsername(), expectedUserRecord.getPassword(), Collections.emptyList());
        Mockito.when(userRepository.findUserByUsername(EXPECTEDUSERNAME)).thenReturn(expectedUserRecord);

        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(EXPECTEDUSERNAME);

        Assert.assertEquals(expectedUserDetails, actualUserDetails);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldReturnUsernameNotFoundException() {
        Mockito.when(userRepository.findUserByUsername(EXPECTEDUSERNAME)).thenReturn(null);

        userDetailsService.loadUserByUsername(EXPECTEDUSERNAME);
    }
}
