package com.yangz.tank.component;


import com.yangz.tank.entity.Configure;
import com.yangz.tank.service.ConfigureService;
import com.yangz.tank.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

//@Component
public class ServerStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private ConfigureService configureService;
    private VideoService videoService;
    Logger logger;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        logger.info("--->>启动后台截图转码线程<<---");
        configureService = context.getBean(ConfigureService.class);
        videoService = context.getBean(VideoService.class);
        WebApplicationContext context1 = (WebApplicationContext)context;
        String realPath = context1.getServletContext().getRealPath("/").replace("\\","/");
        Map<String, String> configMap = getConfigMap();
        configMap.put("realPath", realPath);

        //Folder of Watermark
        String[] watermarkstrlist=configMap.get("transcoder_watermark_url").split("/");
        String watermarkDir="";
        String watermarkFile=watermarkstrlist[watermarkstrlist.length-1];
        for(int i=0;i<watermarkstrlist.length-1;i++){
            watermarkDir+=watermarkstrlist[i]+"/";
        }
        configMap.put("", watermarkDir);
        System.out.println(configMap);

        VideoThumbnail thumbnail = new VideoThumbnail(videoService, configMap);
        VideoTranscoder transcoder = new VideoTranscoder(videoService, configMap);

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10,threadFactory);
        service.scheduleAtFixedRate(thumbnail,0,500, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(transcoder,0,1000, TimeUnit.MILLISECONDS);
    }

    private Map<String, String>getConfigMap(){
        List<Configure> list = configureService.getConfigures();
        Map<String, String> map = new HashMap<>();
        for(Configure configure : list){
            map.put(configure.getName(), configure.getVal());
        }
        return map;
    }
}
