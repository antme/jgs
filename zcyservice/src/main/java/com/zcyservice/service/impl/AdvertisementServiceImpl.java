package com.zcyservice.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zcy.bean.EntityResults;
import com.zcy.dbhelper.DataBaseQueryBuilder;
import com.zcy.exception.ResponseException;
import com.zcy.service.AbstractService;
import com.zcy.util.EcUtil;
import com.zcyservice.bean.Advertisement;
import com.zcyservice.service.IAdvertisementService;

@Service(value = "adService")
public class AdvertisementServiceImpl extends AbstractService implements IAdvertisementService {

	@Override
    public void addAdvertisement(Advertisement adv) {	
	    dao.insert(adv);   
    }

	@Override
    public EntityResults<Advertisement> listAdvertisement() {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(Advertisement.TABLE_NAME);
	    return dao.listByQueryWithPagnation(builder, Advertisement.class);
    }

	@Override
    public String upload(Map<String, Object> params) {
	    InputStream is = (InputStream) params.get("inputStream");
	    String wwwPath = (String) params.get("webPath");
	    String fileName = (String) params.get("fileName");
	    BufferedInputStream bis = null;
	    FileOutputStream fos = null;
	    String relativeFilePath = genRandomRelativePath(fileName);
	    try {
	        bis = new BufferedInputStream(is);
	        
	        File file = new File(wwwPath+relativeFilePath);
	        File folder = file.getParentFile();
			if (!folder.exists()){
				folder.mkdirs();
			}
	        fos = new FileOutputStream(file);
	        
	        byte[] buf = new byte[1024];    
	        int size = 0;
	        
	        while ( (size = bis.read(buf)) != -1){
	        	fos.write(buf, 0, size);
	        }
	        
	        if(bis != null)
        		bis.close();
        	
        	if (fos != null)
        		fos.close();
	        
        } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    return relativeFilePath;
    }
	
	public String genRandomRelativePath(String fileName){
		Random r = new Random();
		int n = r.nextInt(101);
		String ms = Long.toString(new Date().getTime());
		StringBuffer sb = new StringBuffer("/");
		sb.append("upload/").append(n).append("/").append(ms).append("/").append(fileName);
		return sb.toString();
	}

	@Override
	public String getUploadImage(HttpServletRequest request, String name, String errorMsg) {
		if(!(request instanceof MultipartHttpServletRequest)){
			return null;
		}
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		if(mRequest.getContentLength() > 10 *1024 *1024){
			throw new ResponseException("附件上传大小限制10M以内");
		}
		MultipartFile uploadFile = mRequest.getFile(name);
		
		if(uploadFile == null){
			return null;
		}
		
		if (uploadFile.getName() == null && EcUtil.isValid(errorMsg)) {
			throw new ResponseException(errorMsg);
		}
		String webPath = request.getSession().getServletContext().getRealPath("/");
		String imgFileName = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");

		if(imgFileName.trim().isEmpty()){
			return null;
		}
		String filePath = null;
		if (imgFileName.endsWith("gif") || imgFileName.endsWith("jpg") || imgFileName.endsWith("png") | imgFileName.endsWith("jpeg")) {
			InputStream inputStream = null;
			try {
				inputStream = uploadFile.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("inputStream", inputStream);
			map.put("webPath",  webPath);
			map.put("fileName", uploadFile.getOriginalFilename().trim().replaceAll(" ", ""));
			filePath = upload(map);
		} else {
			throw new ResponseException("注：图片格式只支持（GIF|JPG|PNG|JPEG）格式!");
		}
		return filePath;
    }
}
