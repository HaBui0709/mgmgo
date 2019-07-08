package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Search Controller.
 *
 * @author: ttkngo
 */

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public String search(Model model, @RequestParam(name = "searchInfor", required = false, defaultValue = "") String searchInfor) {
        if (searchInfor.isEmpty()) {
            model.addAttribute("activities", activityService.findAll());
        } else {
            model.addAttribute("keySearch", searchInfor.trim());
            model.addAttribute("activities", activityService.search(searchInfor));
        }
        return "search";
    }
}
