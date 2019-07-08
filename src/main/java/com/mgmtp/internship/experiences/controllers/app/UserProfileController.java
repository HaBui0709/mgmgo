package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.config.security.CustomLdapUserDetails;
import com.mgmtp.internship.experiences.constants.ApplicationConstant;
import com.mgmtp.internship.experiences.dto.UserProfileDTO;
import com.mgmtp.internship.experiences.services.UserService;
import com.mgmtp.internship.experiences.utils.StringReplaceByRegexEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Activity Controller.
 *
 * @author: thuynh
 */

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private static final String USER_PROFILE_MODEL_TAG = "userProfileDTO";
    private static final String USERNAME_MODEL_TAG = "username";

    @Autowired
    UserService userService;

    @GetMapping()
    public String profile(Model model) {
        CustomLdapUserDetails user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute(USER_PROFILE_MODEL_TAG, user.getUserProfileDTO());
        model.addAttribute(USERNAME_MODEL_TAG, user.getUsername());
        return "user/profile";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringReplaceByRegexEditor(true, ApplicationConstant.REGEX_ALL_WHITESPACE_ENTER_TAB));
    }

    @PostMapping()
    public String updateProfile(@ModelAttribute(USER_PROFILE_MODEL_TAG) @Valid UserProfileDTO profile, final BindingResult bindingResult, Model model) {
        CustomLdapUserDetails user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        if (!bindingResult.hasErrors()) {
            if (userService.checkExitDisplayName(profile.getDisplayName(), user.getId())) {
                bindingResult.rejectValue("displayName", "error." + USER_PROFILE_MODEL_TAG, "This display name is already in use.");
            } else if (userService.updateProfile(user.getId(), profile)) {
                model.addAttribute("success", "Update profile success.");
            } else {
                model.addAttribute("error", "Can't update profile.");
            }
        }
        profile.setImageId(user.getUserProfileDTO().getImageId());
        profile.setReputatinScore(user.getUserProfileDTO().getReputationScore());
        model.addAttribute(USER_PROFILE_MODEL_TAG, profile);
        model.addAttribute(USERNAME_MODEL_TAG, userService.getCurrentUser().getUsername());
        return "user/profile";
    }
}
