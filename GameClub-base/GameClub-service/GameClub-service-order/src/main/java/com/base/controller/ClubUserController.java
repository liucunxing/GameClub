package com.base.controller;

import com.base.myInterface.Authorize;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubUser")
@Api(value = "clubUser",tags = "clubUser")
@Authorize
public class ClubUserController {
}
