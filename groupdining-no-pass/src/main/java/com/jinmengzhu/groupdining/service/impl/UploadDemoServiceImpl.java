package com.jinmengzhu.groupdining.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jinmengzhu.groupdining.domain.UploadDemoVo;
import com.jinmengzhu.groupdining.service.UploadDemoService;

@Service
public class UploadDemoServiceImpl implements UploadDemoService {

	@Override
	public boolean uploadForm(UploadDemoVo demo,String saveDirStr) throws Exception {
		demo.validateFile();
		uploadFile(saveDirStr, demo.getImgFile(), demo.getImgFile()
				.getOriginalFilename());
		return true;
	}

	public boolean uploadFile(String destinationDir, MultipartFile file,
			String filename) throws Exception {
		/*
		 * logger.info("文件长度: " + file.getSize()); logger.info("文件类型: " +
		 * file.getContentType()); logger.info("文件名称: " + file.getName());
		 * logger.info("文件原名: " + file.getOriginalFilename());
		 * logger.info("========================================");
		 */
		try {
			SaveFileFromInputStream(file.getInputStream(), destinationDir,
					filename);
		} catch (IOException e) {
			// logger.info(e.getMessage());
			return false;
		}
		return true;
	}
	
	public String getCurrentPath(){  
	       //取得根目录路径  
	       String rootPath=getClass().getResource("/").getFile().toString();  
	       //<a href="https://www.baidu.com/s?wd=%E5%BD%93%E5%89%8D%E7%9B%AE%E5%BD%95&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y3nhu9nHNBnjuBPWu9mvn30AP8IA3qPjfsn1bkrjKxmLKz0ZNzUjdCIZwsrBtEXh9GuA7EQhF9pywdQhPEUiqkIyN1IA-EUBtkPHT1P1b3P1DsnHf4P101Pjms" target="_blank" class="baidu-highlight">当前目录</a>路径  
	       //String currentPath1=getClass().getResource(".").getFile().toString();  
	       String currentPath2=getClass().getResource("").getFile().toString();  
	       //<a href="https://www.baidu.com/s?wd=%E5%BD%93%E5%89%8D%E7%9B%AE%E5%BD%95&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y3nhu9nHNBnjuBPWu9mvn30AP8IA3qPjfsn1bkrjKxmLKz0ZNzUjdCIZwsrBtEXh9GuA7EQhF9pywdQhPEUiqkIyN1IA-EUBtkPHT1P1b3P1DsnHf4P101Pjms" target="_blank" class="baidu-highlight">当前目录</a>的上级目录路径  
	       String parentPath=getClass().getResource("../").getFile().toString();  
	          
	       return rootPath;         
	   
	   }

	/**
	 * 保存文件
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public void SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(path + "/"
				+ filename);
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = stream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, byteCount);
		}
		outputStream.close();
		stream.close();
	}

}