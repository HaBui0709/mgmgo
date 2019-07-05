package com.mgmtp.internship.experiences.controllers.api;

import com.mgmtp.internship.experiences.config.security.CustomUserDetails;
import com.mgmtp.internship.experiences.exceptions.ApiException;
import com.mgmtp.internship.experiences.services.RatingService;
import com.mgmtp.internship.experiences.services.UserService;
import org.jooq.tools.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Rating rest controller.
 *
 * @author thuynh
 */
@RestController
@RequestMapping("/rating")
public class RatingRestController extends BaseRestController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @GetMapping("/activity/{activityId}")
    public Object getRate(@PathVariable("activityId") long activityId) {
        CustomUserDetails user = userService.getCurrentUser();
        if (user == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Please login to perform this operation.");
        }
        JSONObject result = new JSONObject();
        result.put("rating", ratingService.getRateByUserId(activityId, user.getId()));
        return result;
    }

    @PostMapping("/activity/{activityId}")
    public Object editRateOfUser(@PathVariable("activityId") long activityId,
                                 @RequestParam("rating")
                                 @Min(value = 1, message = "Rating has to be greater than or equal to 1 ")
                                 @Max(value = 5, message = "Rating has to be less than or equal to 5 ")
                                         int rating) {
        CustomUserDetails user = userService.getCurrentUser();
        if (user == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Please login to perform this operation.");
        }
        if (ratingService.editRateByUserId(activityId, user.getId(), rating) == 1) {
            JSONObject result = new JSONObject();
            result.put("rating", ratingService.getRate(activityId));
            return result;
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "Something went wrong! Please try again.");
    }
}
