package com.jinmengzhu.groupdining.service;

import org.springframework.web.multipart.MultipartFile;

import com.jinmengzhu.groupdining.domain.UploadDemoVo;

public interface UploadDemoService {
    
    /**
     * 上传文件到指定路径
     * destinationDir 目标路径
     * 2014年6月10日
     */
    public boolean uploadFile(String destinationDir, MultipartFile file, String filename) throws Exception;

	boolean uploadForm(UploadDemoVo demo, String saveDirStr) throws Exception;
 
}
