package com.jsu.func.face.service.impl;


import com.jsu.func.face.dao.mapper.UserFaceInfoMapper;
import com.jsu.func.face.domain.UserFaceInfo;
import com.jsu.func.face.service.UserFaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserFaceInfoServiceImpl implements UserFaceInfoService {


    @Autowired
    private UserFaceInfoMapper userFaceInfoMapper;

    @Override
    public int insertSelective(UserFaceInfo userFaceInfo) {
        return userFaceInfoMapper.insertSelective(userFaceInfo);
    }
}
