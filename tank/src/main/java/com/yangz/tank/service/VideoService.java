package com.yangz.tank.service;

import com.yangz.tank.dao.VideoMapper;
import com.yangz.tank.entity.Video;
import com.yangz.tank.entity.VideoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    public List<Video> getAllVideo(){
        VideoExample example = new VideoExample();
        example.setOrderByClause("id asc");
        return videoMapper.selectByExample(example);
    }

    public List<Video> getVideosByState(Integer state){
        VideoExample example = new VideoExample();
        VideoExample.Criteria criteria = example.createCriteria();
        criteria.andVideostateidEqualTo(state);
        return videoMapper.selectByExample(example);
    }

    public Video getLatestVideoByState(Integer state){
        VideoExample example = new VideoExample();
        example.setOrderByClause("edittime desc");
        VideoExample.Criteria criteria = example.createCriteria();
        criteria.andVideostateidEqualTo(state);
        List<Video> list = videoMapper.selectByExample(example);
        if(null != list && list.size()>0){
            Video video = list.get(0);
            if(state == 2){
                video.setVideostateid(3);
            }else if(state == 3){
                video.setVideostateid(4);
            }
            example.clear();
            VideoExample.Criteria criteria1 = example.createCriteria();
            criteria1.andEdittimeEqualTo(video.getEdittime());
            criteria1.andIdEqualTo(video.getId());
            int res = videoMapper.updateByExample(video, example);
            if(res > 0)
                return video;
            else
                return null;
        }
        return null;
    }

    public int updateVideo(Video video){
        return videoMapper.updateByPrimaryKeySelective(video);
    }
}
