package com.jinmengzhu.groupdining.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinmengzhu.groupdining.domain.UploadDemoVo;
import com.jinmengzhu.groupdining.service.UploadDemoService;

@Controller
@RequestMapping("/demo/upload")
public class UploadController {
     
    @Autowired
    private UploadDemoService uploadDemoService;
    
	private static String saveDirStr = "";

	private static String root = "/uploadFile";
     
    /**
     * 第一种Spring Mvc上传文件，提交form表单文件到一个frame，刷新该frame，页面打印出返回的结果
     * @param request
     * @param demo
     * @return
     */
    @RequestMapping(value = "firstUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object firstUpload(HttpServletRequest request, UploadDemoVo demo){
       // logger.info("firstUpload info:" + demo.toString());
        boolean flag = false;
        //errorMessage：上传失败，则是错误信息；上传成功，则提示成功以及显示文件上传后的地址
        String errorMessage = "";  
        try{
        	if (StringUtils.isEmpty(saveDirStr)) {
    			saveDirStr = new StringBuffer(request.getServletContext().getRealPath(root)).toString();
    		}
            flag = uploadDemoService.uploadForm(demo,saveDirStr);
            errorMessage += root+ "/" + demo.getImgFile().getOriginalFilename();
        }catch (ServiceException serviceE){
          //  logger.error("firstUpload failed!" , serviceE);
            errorMessage = serviceE.getMessage();
        }catch (Exception e){
        	e.printStackTrace();
         //   logger.error("firstUpload failed!" , e);
            errorMessage = "firstUpload failed!";
        }
        if(flag){
                //上传成功，返回到前台，调用uploadSucced()这个方法
                        return "<script>window.parent.uploadSucced('" + errorMessage + "');</script>";
        }
        //上传失败，返回到前台，调用uploadFailed()这个方法
        return "<script>window.parent.uploadFailed('" + errorMessage + "');</script>";
    }
}
