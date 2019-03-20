package com.jsu.func.face.service;

import com.arcsoft.face.FaceInfo;
import com.jsu.func.face.base.ImageInfo;
import com.jsu.func.face.dto.ProcessInfo;
import com.jsu.func.login.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface FaceEngineService {

    List<FaceInfo> detectFaces(ImageInfo imageInfo);

    List<ProcessInfo> process(ImageInfo imageInfo);

    /**
     * 人脸特征
     * @param imageInfo
     * @return
     */
    byte[] extractFaceFeature(ImageInfo imageInfo) throws InterruptedException;

    /**
     * 人脸比对
     * @param faceFeature
     * @return
     */
    List<User> compareFaceFeature(byte[] faceFeature) throws InterruptedException, ExecutionException;



}
