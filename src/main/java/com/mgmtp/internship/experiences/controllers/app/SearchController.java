package com.mgmtp.internship.experiences.controllers.app;

import com.mgmtp.internship.experiences.constants.EnumSort;
import com.mgmtp.internship.experiences.dto.PageDTO;
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
 * Search Controller.
 *
 * @author: ttkngo
 */

@Controller
@RequestMapping("/search")
public class SearchController {
    private static final String ACTIVITIES_ATTRIBUTE = "activities";

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public String search(Model model, @RequestParam(name = "searchInfor", required = false, defaultValue = "") String searchInfor,
                         @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST_FIRST") String sortType) {
        PageDTO pagingInfo = new PageDTO();
        if (searchInfor.isEmpty()) {
            model.addAttribute(ACTIVITIES_ATTRIBUTE, activityService.getActivities(1, EnumSort.valueOf(sortType)));
            pagingInfo.setTotalRecord(activityService.countTotalRecordActivity());
        } else {
            model.addAttribute(ACTIVITIES_ATTRIBUTE, activityService.search(searchInfor, 1, EnumSort.valueOf(sortType)));
            pagingInfo.setTotalRecord(activityService.countTotalRecordSearch(searchInfor));
            model.addAttribute("keySearch", searchInfor.trim());
        }
        pagingInfo.setSizeOfPages(LazyLoading.countPages(pagingInfo.getTotalRecord()));
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("sortType", sortType);
        return "search";
    }

    @GetMapping("/more/{currentPage}")
    public String searchMoreActivities(@PathVariable int currentPage, Model model, @RequestParam(name = "searchInfor", required = false, defaultValue = "") String searchInfor,
                                       @RequestParam(name = "sortType", required = false, defaultValue = "NEWEST_FIRST") String sortType) {
        model.addAttribute(ACTIVITIES_ATTRIBUTE, activityService.search(searchInfor, currentPage, EnumSort.valueOf(sortType)));
        return "activity/fragments/list-activities";
    }
}
