package com.bunge.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/review")
public class ReviewController {

    @Value("${review.savefolder}")
    public String saveFolder;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);


}
