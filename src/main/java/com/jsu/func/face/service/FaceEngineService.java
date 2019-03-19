package com.jsu.func.face.service;

import com.arcsoft.face.FaceInfo;
import com.jsu.func.face.base.ImageInfo;
import com.jsu.func.face.dto.FaceUserInfo;
import com.jsu.func.face.dto.ProcessInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface FaceEngineService {

    void addFaceToCache(Integer groupId, FaceUserInfo userFaceInfo) throws ExecutionException;


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
     * @param groupId
     * @param faceFeature
     * @return
     */
    List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Integer groupId) throws InterruptedException, ExecutionException;



}
