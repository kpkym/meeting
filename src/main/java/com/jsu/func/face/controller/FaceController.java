package com.jsu.func.face.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.arcsoft.face.FaceInfo;
import com.jsu.except.UserExceptJSON;
import com.jsu.func.face.base.ImageInfo;
import com.jsu.func.face.dto.ProcessInfo;
import com.jsu.func.face.service.FaceEngineService;
import com.jsu.func.face.util.ImageUtil;
import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import com.jsu.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;


@RestController
public class FaceController {
    @Autowired
    FaceEngineService faceEngineService;

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public Msg register(User user, MultipartFile file) throws IOException, InterruptedException {
            if (file == null) {
                return Msg.failure("文件为空");
            }
        InputStream inputStream = file.getInputStream();

        String s = Base64.getEncoder().encodeToString(file.getBytes());

        ImageInfo imageInfo = ImageUtil.getRGBData(inputStream);

        //人脸特征获取
            byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
            if (bytes == null) {
                throw new UserExceptJSON("未检出到人脸");
            }
        try {
            user.setFace(bytes);
            //人脸特征插入到数据库
            userService.save(user);
            return Msg.success();
        } catch (Exception e) {
            e.printStackTrace();
        }


        throw new UserExceptJSON("未知错误");
    }

    @PostMapping("/faceSearch")
    public Msg faceSearch(String face)
    		throws Exception {

        InputStream inputStream = new ByteArrayInputStream(Base64Utils.decodeFromString(face.trim()));
        BufferedImage bufImage = ImageIO.read(inputStream);
        ImageInfo imageInfo = ImageUtil.bufferedImage2ImageInfo(bufImage);
        if (inputStream != null) {
            inputStream.close();
        }

        //人脸特征获取
        byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
        if (bytes == null) {
            throw new UserExceptJSON("未检出到人脸");
        }
        //人脸比对，获取比对结果
        List<User> userFaceInfoList = faceEngineService.compareFaceFeature(bytes);

        if (CollectionUtil.isNotEmpty(userFaceInfoList)) {
            User faceSearchResDto = userFaceInfoList.get(0);
            List<ProcessInfo> processInfoList = faceEngineService.process(imageInfo);
            if (CollectionUtil.isNotEmpty(processInfoList)) {
                //人脸检测
                List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
                int left = faceInfoList.get(0).getRect().getLeft();
                int top = faceInfoList.get(0).getRect().getTop();
                int width = faceInfoList.get(0).getRect().getRight() - left;
                int height = faceInfoList.get(0).getRect().getBottom()- top;

                //描人脸框
                Graphics2D graphics2D = bufImage.createGraphics();
                graphics2D.setColor(Color.RED);//红色
                BasicStroke stroke = new BasicStroke(5f);
                graphics2D.setStroke(stroke);
                graphics2D.drawRect(left, top, width, height);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufImage, "jpg", outputStream);
                byte[] bytes1 = outputStream.toByteArray();
                faceSearchResDto.setImage("data:face/jpeg;base64," + Base64Utils.encodeToString(bytes1));
                faceSearchResDto.setAge(processInfoList.get(0).getAge());
                faceSearchResDto.setGender(processInfoList.get(0).getGender().equals(1) ? "女" : "男");
            }
            return Msg.success(faceSearchResDto);
        }
        throw new UserExceptJSON("人脸不匹配");
    }



    @PostMapping("/detectFaces")
    public Msg detectFaces(String image) throws IOException {
        byte[] bytes = Base64Utils.decodeFromString(image.trim());

        InputStream inputStream = new ByteArrayInputStream(bytes);
        ImageInfo imageInfo = ImageUtil.getRGBData(inputStream);

        if (inputStream != null) {
            inputStream.close();
        }
        List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
        return Msg.success(faceInfoList);
    }
}
