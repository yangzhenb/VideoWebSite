package com.yangz.tank.component;

import com.yangz.tank.entity.Video;
import com.yangz.tank.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class VideoThumbnail implements Runnable {

    private VideoService videoService;
    private Map<String, String> configMap;
    Logger logger;

    public VideoThumbnail(VideoService videoService, Map<String, String> configMap) {
        this.videoService = videoService;
        this.configMap = configMap;
        this.logger = LoggerFactory.getLogger(this.getClass());
        logger.info(">>>--截图线程--<<<");
    }

    @Override
    public void run() {
        //查询带截图的视频
        Video video = videoService.getLatestVideoByState(2);

        if(null != video){
            try{
                String realfileoriPath = configMap.get("realPath") + video.getOriurl();
                String realthumbnailDir = configMap.get("realPath") + configMap.get("folder_thumbnail");

                //Check
                File realthumbnailDirFile =new File(realthumbnailDir);
                if(!realthumbnailDirFile.exists()  && !realthumbnailDirFile.isDirectory()){
                    System.out.println("Directory not exist. Create it.");
                    System.out.println(realthumbnailDirFile);
                    realthumbnailDirFile.mkdir();
                }

                String realthumbnailPath=realthumbnailDir+"/"+video.getId()+".jpg";

                String videothumbnailcommand="cmd /c start ffmpeg -y -i "+"\""+realfileoriPath+"\""+
                        " -ss "+configMap.get("thumbnail_ss")+" -s 220x110 -f image2 -vframes 1 "+"\""+realthumbnailPath+"\"";
                System.out.println(videothumbnailcommand);
                Process process=Runtime.getRuntime().exec(videothumbnailcommand);
                if (process.waitFor() != 0) {
                    if (process.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                        System.err.println("Failed!");
                }

                video.setThumbnailurl(configMap.get("folder_thumbnail")+"/"+video.getId()+".jpg");
            }catch(Exception e){
                logger.info(e.getMessage());
            }
            videoService.updateVideo(video);
        }

    }
}
