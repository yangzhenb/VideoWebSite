package com.yangz.tank.component;

import com.yangz.tank.entity.Video;
import com.yangz.tank.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Map;

public class VideoTranscoder implements Runnable {


    private VideoService videoService;
    private Map<String, String> configMap;
    Logger logger;

    public VideoTranscoder(VideoService videoService, Map<String, String> configMap) {
        this.videoService = videoService;
        this.configMap = configMap;
        this.logger = LoggerFactory.getLogger(this.getClass());
        logger.info(">>>--转码线程--<<<");
    }

    @Override
    public void run() {
        //查询待转码的视频
        Video video = videoService.getLatestVideoByState(3);
        if(null != video){
            try{

                StringBuffer filePath = new StringBuffer();
                filePath.append(configMap.get("folder_video"));
                filePath.append("/");
                filePath.append(video.getId());
                filePath.append(".");
                filePath.append(configMap.get("transcoder_outfmt"));
                video.setUrl(filePath.toString());
                StringBuffer realfilePath = new StringBuffer();
                realfilePath.append(configMap.get("realPath"));
                realfilePath.append(video.getUrl());
                StringBuffer realfileoriginalPath = new StringBuffer();
                realfileoriginalPath.append(configMap.get("realPath"));
                realfileoriginalPath.append(video.getOriurl());
                String watermarkFile = StringUtils.getFilename(configMap.get("transcoder_watermark_url"));
                String watermarkDir = StringUtils.delete(configMap.get("transcoder_watermark_url"), watermarkFile);
                //转码命令如下所示
                //ffmpeg -i xxx.mkv -ar 22050 -b 600k -vcodec libx264
                //-vf scale=w=640:h=360:force_original_aspect_ratio=decrease,pad=w=640:h=360:x=(ow-iw)/2:y=(oh-ih)/2[aa];
                //movie=watermark.png[bb];[aa][bb]overlay=5:5 yyy.flv
                //AVFilter参数作用如下所示
                //scale:视频拉伸滤镜。force_original_aspect_ratio用于强制保持宽高比
                //pad:用于加黑边，四个参数含义分别为：处理后宽，处理后高，输入图像左上角x坐标，输入视频左上角Y坐标。
                //其中ow,oh为输出（填充后）视频的宽高；iw,ih为输入（填充前）视频的宽高。
                //movie：用于指定需要叠加的水印Logo（PNG文件）。
                //overlay:用于叠加水印Logo和视频文件
                //命令行不同的执行方式
                //cmd /c xxx 是执行完xxx命令后关闭命令窗口。
                //cmd /k xxx 是执行完xxx命令后不关闭命令窗口。
                //cmd /c start xxx 会打开一个新窗口后执行xxx指令，原窗口会关闭。

                StringBuffer videotranscodecmd = new StringBuffer();
                videotranscodecmd.append("cmd");
                videotranscodecmd.append(" /c start");
                videotranscodecmd.append(" ffmpeg -y");
                videotranscodecmd.append(" -i ");
                videotranscodecmd.append("\"");
                videotranscodecmd.append(realfileoriginalPath);
                videotranscodecmd.append("\"");
                videotranscodecmd.append(" -vcodec "+configMap.get("transcoder_vcodec"));
                videotranscodecmd.append(" -b:v "+configMap.get("transcoder_bv"));
                videotranscodecmd.append(" -r "+configMap.get("transcoder_framerate"));
                videotranscodecmd.append(" -acodec "+configMap.get("transcoder_acodec"));
                videotranscodecmd.append(" -b:a "+configMap.get("transcoder_ba"));
                videotranscodecmd.append(" -ar "+configMap.get("transcoder_ar"));
                videotranscodecmd.append(" -vf");
                videotranscodecmd.append(" scale=w="+configMap.get("transcoder_scale_w"));
                videotranscodecmd.append(":h="+configMap.get("transcoder_scale_h"));
                if("true".equals(configMap.get("transcoder_keepaspectratio"))){
                    videotranscodecmd.append(":");
                    videotranscodecmd.append("force_original_aspect_ratio=decrease,pad=w=");
                    videotranscodecmd.append(configMap.get("transcoder_scale_w"));
                    videotranscodecmd.append(":h=");
                    videotranscodecmd.append(configMap.get("transcoder_scale_h"));
                    videotranscodecmd.append(":x=(ow-iw)/2:y=(oh-ih)/2");
                }
                videotranscodecmd.append("[aa]");
                if("true".equals(configMap.get("transcoder_watermarkuse"))){
                    videotranscodecmd.append(";movie=");
                    videotranscodecmd.append(watermarkFile);
                    videotranscodecmd.append("[bb];");
                    videotranscodecmd.append("[aa][bb]");
                    videotranscodecmd.append("overlay=x=");
                    videotranscodecmd.append(configMap.get("transcoder_watermark_x"));
                    videotranscodecmd.append(":y=");
                    videotranscodecmd.append(configMap.get("transcoder_watermark_y"));
                    videotranscodecmd.append("");
                }
                videotranscodecmd.append(" \"");
                videotranscodecmd.append(realfilePath);
                videotranscodecmd.append("\"");
                System.out.println(videotranscodecmd);
                File realwatermarkDirFile= new File(configMap.get("realPath")+watermarkDir);
                if(!realwatermarkDirFile.exists()  && !realwatermarkDirFile.isDirectory()){
                    System.out.println("Directory not exist. Create it.");
                    System.out.println(realwatermarkDirFile);
                    realwatermarkDirFile.mkdir();
                }
                Process process = Runtime.getRuntime().exec(videotranscodecmd.toString(), null, realwatermarkDirFile);
                if (process.waitFor() != 0) {
                    if (process.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
                        System.err.println("Failed!");
                }
                video.setVideostateid(4);
                videoService.updateVideo(video);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }
}
