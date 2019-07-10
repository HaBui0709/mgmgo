package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller.
 *
 * @author: thuynh
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public String getHome(Model model) {
        model.addAttribute("activities", activityService.getActivities(1));
        model.addAttribute("sizeOfPages", activityService.countPages());
        model.addAttribute("currentPage", 1);
        if (model.containsAttribute("successTemp")) {
            model.addAttribute("success", "Created activity successfully!");
        }
        return "home/index";
    }

    @GetMapping("more/{currentPage}")
    public String getActivities(@PathVariable int currentPage, Model model) {
        model.addAttribute("activities", activityService.getActivities(currentPage));
        return "activity/fragments/list-activities";
    }
}
