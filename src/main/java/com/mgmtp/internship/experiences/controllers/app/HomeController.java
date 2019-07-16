package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.dto.PageDTO;
import com.mgmtp.internship.experiences.services.ActivityService;
import com.mgmtp.internship.experiences.utils.LazyLoading;
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
        PageDTO pagingInfo = new PageDTO();
        pagingInfo.setTotalRecord(activityService.countTotalRecordActivity());
        pagingInfo.setSizeOfPages(LazyLoading.countPages(pagingInfo.getTotalRecord()));
        model.addAttribute("pagingInfo", pagingInfo);
        return "home/index";
    }

    @GetMapping("more/{currentPage}")
    public String getActivities(@PathVariable("currentPage") int currentPage, Model model) {
        model.addAttribute("activities", activityService.getActivities(currentPage));
        return "activity/fragments/list-activities";
    }
}
