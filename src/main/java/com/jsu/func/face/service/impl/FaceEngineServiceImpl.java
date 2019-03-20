package com.jsu.func.face.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.ImageFormat;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.jsu.func.face.base.ImageInfo;
import com.jsu.func.face.dto.FaceUserInfo;
import com.jsu.func.face.dto.ProcessInfo;
import com.jsu.func.face.factory.FaceEngineFactory;
import com.jsu.func.face.service.FaceEngineService;
import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Service
public class FaceEngineServiceImpl implements FaceEngineService {

    public final static Logger logger = LoggerFactory.getLogger(FaceEngineServiceImpl.class);

    @Value("${config.freesdk.app-id}")
    public String appId;

    @Value("${config.freesdk.sdk-key}")
    public String sdkKey;

    @Value("${config.freesdk.thread-pool-size}")
    public Integer threadPoolSize;


    private Integer passRate = 80;

    private ExecutorService executorService;

    private LoadingCache<Integer, List<FaceUserInfo>> faceGroupCache;

    private GenericObjectPool<FaceEngine> extractFaceObjectPool;
    private GenericObjectPool<FaceEngine> compareFaceObjectPool;

    @Autowired
    IUserService userService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(threadPoolSize);
        poolConfig.setMaxTotal(threadPoolSize);
        poolConfig.setMinIdle(threadPoolSize);
        poolConfig.setLifo(false);
        extractFaceObjectPool = new GenericObjectPool(new FaceEngineFactory(appId, sdkKey, FunctionConfiguration.builder().supportFaceDetect(true).supportFaceRecognition(true).supportAge(true).supportGender(true).build()), poolConfig);//底层库算法对象池
        compareFaceObjectPool = new GenericObjectPool(new FaceEngineFactory(appId, sdkKey, FunctionConfiguration.builder().supportFaceRecognition(true).build()), poolConfig);//底层库算法对象池

    }



    private int plusHundred(Float value) {
        BigDecimal target = new BigDecimal(value);
        BigDecimal hundred = new BigDecimal(100f);
        return target.multiply(hundred).intValue();

    }

    /***
     * 人脸识别
     */

    @Override
    public List<FaceInfo> detectFaces(ImageInfo imageInfo) {
        FaceEngine faceEngine = null;
        try {
            //获取引擎对象
            faceEngine = extractFaceObjectPool.borrowObject();

            //人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();

            //人脸检测
            faceEngine.detectFaces(imageInfo.getRgbData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
            return faceInfoList;
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                //释放引擎对象
                extractFaceObjectPool.returnObject(faceEngine);
            }

        }

        return null;
    }
    
    /**
     * 识别过程
     */

    @Override
    public List<ProcessInfo> process(ImageInfo imageInfo){
        FaceEngine faceEngine = null;
        try {
            //获取引擎对象
            faceEngine = extractFaceObjectPool.borrowObject();
            //人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            //人脸检测
            faceEngine.detectFaces(imageInfo.getRgbData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
            int processResult = faceEngine.process(imageInfo.getRgbData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList, FunctionConfiguration.builder().supportAge(true).supportGender(true).build());
            List<ProcessInfo> processInfoList=Lists.newLinkedList();

            List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
            //性别提取
            int genderCode = faceEngine.getGender(genderInfoList);
            //年龄提取
            List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
            int ageCode = faceEngine.getAge(ageInfoList);
            for (int i = 0; i <genderInfoList.size() ; i++) {
                ProcessInfo processInfo=new ProcessInfo();
                processInfo.setGender(genderInfoList.get(i).getGender());
                processInfo.setAge(ageInfoList.get(i).getAge());
                processInfoList.add(processInfo);
            }
            return processInfoList;

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                //释放引擎对象
                extractFaceObjectPool.returnObject(faceEngine);
            }
        }

        return null;

    }
    /**
     * 人脸特征提取
     * @param imageInfo
     * @return
     */
    @Override
    public byte[] extractFaceFeature(ImageInfo imageInfo) throws InterruptedException {

        FaceEngine faceEngine = null;
        try {
            //获取引擎对象
            faceEngine = extractFaceObjectPool.borrowObject();

            //人脸检测得到人脸列表
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();

            //人脸检测
            int i = faceEngine.detectFaces(imageInfo.getRgbData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);

            if (CollectionUtil.isNotEmpty(faceInfoList)) {
                FaceFeature faceFeature = new FaceFeature();
                //提取人脸特征
                faceEngine.extractFaceFeature(imageInfo.getRgbData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);

                return faceFeature.getFeatureData();
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (faceEngine != null) {
                //释放引擎对象
                extractFaceObjectPool.returnObject(faceEngine);
            }

        }

        return null;
    }
    /**
     *特征匹配 
     */
    @Override
    public List<User> compareFaceFeature(byte[] faceFeature)
    		throws InterruptedException, ExecutionException {
        List<User> resultFaceInfoList = Lists.newLinkedList();//识别到的人脸列表

        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature);

        List<User> users = userService.list();


        List<List<User>> faceUserInfoPartList = Lists.partition(users, 1000);//分成1000一组，多线程处理
        CompletionService<List<User>> completionService = new ExecutorCompletionService<>(executorService);
        for (List<User> part : faceUserInfoPartList) {
            completionService.submit(new CompareFaceTask(part, targetFaceFeature));
        }
        for (int i = 0; i < faceUserInfoPartList.size(); i++) {
            List<User> faceUserInfoList = completionService.take().get();
            if (CollectionUtil.isNotEmpty(users)) {
                resultFaceInfoList.addAll(faceUserInfoList);
            }
        }

        resultFaceInfoList.sort((h1, h2) -> h2.getSimilarValue().compareTo(h1.getSimilarValue()));//从大到小排序

        return resultFaceInfoList;
    }


    private class CompareFaceTask implements Callable<List<User>> {

        private List<User> faceUserInfoList;
        private FaceFeature targetFaceFeature;


        public CompareFaceTask(List<User> faceUserInfoList, FaceFeature targetFaceFeature) {
            this.faceUserInfoList = faceUserInfoList;
            this.targetFaceFeature = targetFaceFeature;
        }

        @Override
        public List<User> call() throws Exception {
            FaceEngine faceEngine = null;
            List<User> resultFaceInfoList = Lists.newLinkedList();//识别到的人脸列表
            try {
                faceEngine = compareFaceObjectPool.borrowObject();
                for (User faceUserInfo : faceUserInfoList) {
                    FaceFeature sourceFaceFeature = new FaceFeature();
                    sourceFaceFeature.setFeatureData(faceUserInfo.getFace());
                    FaceSimilar faceSimilar = new FaceSimilar();
                    faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                    Integer similarValue = plusHundred(faceSimilar.getScore());//获取相似值
                    if (similarValue > passRate) {//相似值大于配置预期，加入到识别到人脸的列表
                        faceUserInfo.setSimilarValue(similarValue);
                        resultFaceInfoList.add(faceUserInfo);
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (faceEngine != null) {
                    compareFaceObjectPool.returnObject(faceEngine);
                }
            }

            return resultFaceInfoList;
        }

    }
}
