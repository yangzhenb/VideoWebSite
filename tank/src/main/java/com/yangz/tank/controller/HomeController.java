package com.yangz.tank.controller;

import com.yangz.tank.entity.Video;
import com.yangz.tank.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("/home")
    public String home(Model model){
        List<Video> videos = videoService.getAllVideo();
        model.addAttribute("videos",videos);
        return "home";
    }
}
