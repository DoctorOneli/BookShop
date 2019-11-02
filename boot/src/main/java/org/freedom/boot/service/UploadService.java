package org.freedom.boot.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.freedom.boot.FilesConfig;
import org.freedom.boot.bean.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	
	
	@Autowired
    FilesConfig filesConfig;
	
	  /**
     * 文件上传时间
     *
     * @return
     */
    public static String getUploudTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }

    /**
     * 给原文件取新的名称
     *
     * @param fileOriginName
     * @return
     */
    public static String getFileName(String fileOriginName) {
        return UUID.randomUUID().toString().replace("-", "") + fileOriginName.substring(fileOriginName.lastIndexOf("."));
    }

    /**
     * 单文件上传
     *
     * @param file 文件
     * @param path 文件上传路径
     * @return
     * @throws IOException
     */
    public  String uploadOne(MultipartFile file,HttpServletRequest request) throws IOException {
    	String path=filesConfig.getFormalPath()+"upload/";
    	String uploudTime = UploadService.getUploudTime();
        //新的文件存放路径加上新的文件名
        String newPath = path + uploudTime + "/" + UploadService.getFileName(file.getOriginalFilename());
        File file1 = new File(newPath);
        //判断文件目录是否存在
        if (!file1.getParentFile().exists()) {
            //创建文件存放目录
            //无论是几个/，都是创建一个文件夹
            //mkdirs(): 创建多层目录，如：E:/upload/2019
            //mkdir(): 只创建一层目录，如：E:upload
            //如果不加这一行不会创建新的文件夹，会报系统找不到路径
            file1.getParentFile().mkdirs();
        }
        //存储文件
        file.transferTo(file1);
        //去掉目录名，保留文件总体路径，通过该路径访问图片
        String filename = newPath.substring(newPath.lastIndexOf(path)).replace(path, "");
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
        return serverUrl+"/upload/"+filename;
    }
    
    /**
     * 删除文件
     * @param request
     * @return
     */
    public String delFileUrl(HttpServletRequest request) {
    	String path=filesConfig.getFormalPath();
    	String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		String resultInfo = "";
		String key = (String) request.getParameter("key");
		String id = (String) request.getParameter("id");
		String url = (String) request.getParameter("url");
		System.out.println(key);
		System.out.println(id);
		System.out.println(url);

		
		int index = serverUrl.length();
		String fileUrl = url.substring(index+1, url.length());
		fileUrl = path + fileUrl;
		System.out.println(fileUrl);
		 
      return fileUrl;
		
	}
    
    /**
     * 删除本地文件
     * @param request
     * @return
     */
    public String delFile(HttpServletRequest request) {
    	String path=filesConfig.getFormalPath();
    	String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		String resultInfo = "";
		int index = serverUrl.length();
		
		String urlString = (String) request.getParameter("urlString");
		
		String urlArr[]=urlString.split(";");
		 
		
	   for (String url : urlArr) {
		   String fileUrl = url.substring(index+1, url.length());
			fileUrl = path + fileUrl;
			System.out.println(fileUrl);
			File file = new File(fileUrl);
			if (file.exists()) {
				if (file.delete()) {
					resultInfo = "删除成功";
				} else {
					resultInfo = "删除失败";
				}
			} else {
				resultInfo = "文件不存在！";
			}
	}
		
      return resultInfo;
		
	}
}
