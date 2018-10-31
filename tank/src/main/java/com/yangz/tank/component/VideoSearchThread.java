package com.yangz.tank.component;

import com.yangz.tank.service.VideoService;

public class VideoSearchThread implements Runnable {

    private VideoService videoService;

    public VideoSearchThread(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public void run() {

    }
}
