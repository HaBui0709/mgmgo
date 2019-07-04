package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.dto.ActivityDetailDTO;
import com.mgmtp.internship.experiences.services.ActivityService;
import com.mgmtp.internship.experiences.services.UserService;
import com.mgmtp.internship.experiences.utils.StringReplaceWhitespaceEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Activity Controller
 *
 * @author: vhduong
 */

@Controller
@RequestMapping("/activity")
public class ActivityController {

    private static final String ACTIVITY_INFO_ATTRIBUTE = "activityInfo";
    private static final String ERROR_VIEW = "error";
    private static final String REDIRECT_UPDATE_URL = "redirect:/activity/update/";
    private static final String REDIRECT_CREATE_URL = "redirect:/activity/create";


    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @GetMapping("/{activityId}")
    public String getActivity(Model model, @PathVariable("activityId") long activityId) {
        ActivityDetailDTO activityDetailDTO = activityService.findById(activityId);
        if (activityDetailDTO != null) {
            model.addAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
            return "activity/detail";
        }
        return ERROR_VIEW;
    }

    @GetMapping("/update/{activityId}")
    public String getUpdateActivity(Model model, @PathVariable("activityId") long activityId) {
        if (!model.containsAttribute(ACTIVITY_INFO_ATTRIBUTE)) {
            ActivityDetailDTO activityDetailDTO = activityService.findById(activityId);
            if (activityDetailDTO == null) {
                return ERROR_VIEW;
            }
            model.addAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
        }
        return "activity/update";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringReplaceWhitespaceEditor(true));

    }

    @PostMapping("/update")
    public String updateActivity(@ModelAttribute(ACTIVITY_INFO_ATTRIBUTE) @Valid ActivityDetailDTO activityDetailDTO,
                                 final BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ERROR_VIEW, bindingResult.getFieldError().getDefaultMessage());
            redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
            return REDIRECT_UPDATE_URL + activityDetailDTO.getId();
        }

        try {
            CustomUserDetails user = userService.getCurrentUser();
            activityDetailDTO.setUpdatedByUserId(user.getId());
            if (activityService.checkExistName(activityDetailDTO.getName())) {
                redirectAttributes.addFlashAttribute(ERROR_VIEW, "This name already exists");
                redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
                return REDIRECT_UPDATE_URL + activityDetailDTO.getId();
            }
            activityService.update(activityDetailDTO);
            return "redirect:/activity/" + activityDetailDTO.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ERROR_VIEW, "Can't update Activity. Try again!");
            redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
            return REDIRECT_UPDATE_URL + activityDetailDTO.getId();
        }
    }

    @GetMapping("/create")
    public String getCreateActivity(Model model) {
        if (!model.containsAttribute(ACTIVITY_INFO_ATTRIBUTE)) {
            model.addAttribute(ACTIVITY_INFO_ATTRIBUTE, new ActivityDetailDTO());
        }
        return "activity/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(ACTIVITY_INFO_ATTRIBUTE) @Valid ActivityDetailDTO activityDetailDTO,
                         final BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ERROR_VIEW, bindingResult.getFieldError().getDefaultMessage());
            redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
            return REDIRECT_CREATE_URL;
        }
        try {
            CustomUserDetails user = userService.getCurrentUser();
            activityDetailDTO.setCreatedByUserId(user.getId());
            if (activityService.checkExistName(activityDetailDTO.getName())) {
                redirectAttributes.addFlashAttribute(ERROR_VIEW, "This name already exists");
                redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
                return REDIRECT_CREATE_URL;
            }
            activityService.create(activityDetailDTO);
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ERROR_VIEW, "Can't create Activity. Try again!");
            redirectAttributes.addFlashAttribute(ACTIVITY_INFO_ATTRIBUTE, activityDetailDTO);
            return REDIRECT_CREATE_URL;
        }
    }

}
