package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.dto.PageDTO;
import com.mgmtp.internship.experiences.constants.EnumSort;
import com.mgmtp.internship.experiences.services.ActivityService;
import com.mgmtp.internship.experiences.utils.LazyLoading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getHome(Model model, @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST_FIRST") String sortType) {
        model.addAttribute("activities", activityService.getActivities(1, EnumSort.valueOf(sortType)));
        PageDTO pagingInfo = new PageDTO();
        pagingInfo.setTotalRecord(activityService.countTotalRecordActivity());
        pagingInfo.setSizeOfPages(LazyLoading.countPages(pagingInfo.getTotalRecord()));
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("sortType", sortType);
        return "home/index";
    }

    @GetMapping("more/{currentPage}")
    public String getActivities(Model model, @PathVariable int currentPage,
                                @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST_FIRST") String sortBy) {
        model.addAttribute("activities", activityService.getActivities(currentPage, EnumSort.valueOf(sortBy)));
        return "activity/fragments/list-activities";
    }
}
