package com.onecoderspace.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.onecoderspace.base.util.DateUtils;
import com.onecoderspace.base.util.RandomUtils;

@Api(value = "upload image", tags = { "upload image" })
@RestController
@RequestMapping("/upload")
public class UploadController {

	private static Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Value("${upload.file.path}")
	private String uploadFilePath;//Value is similar/var/www/html/upload
	
	@Value("${upload.file.path.project.name}")
	private String projectName; //Value is similar to dmp

	/**
	 * upload image
	 * @author yangwk
	 * @time 2017年7月28日 下午3:42:41
	 * @param files
	 * @return
	 */
	@ApiOperation(value = "upload image", notes = "upload image")
	@ApiImplicitParam(paramType = "query", name = "files", value = "upload picture", required = true, dataType = "MultipartFile")
	@RequestMapping(value = "/img", method = RequestMethod.POST)
	public List<String> img(@RequestParam("file") MultipartFile[] files) {
		String savePath = String.format("/%s/img/%s/%s",projectName, RandomUtils.randomNum(2),RandomUtils.randomNum(2));
		List<String> paths = Lists.newArrayList();
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String suffix = "";
			if(suffix.lastIndexOf(".") != -1){
				suffix = name.substring(name.lastIndexOf("."));
			}
			if (StringUtil.isBlank(suffix)) {
				suffix = ".jpg";
			}
			name = String.format("%s-%s%s",DateUtils.getCurrentTime("yyyyMMdd"), getRandomUniqName(), suffix);
			boolean success = saveFile(savePath, name, file);
			if (success) {
				paths.add(String.format("%s/%s", savePath, name));
			} else {
				paths.add("");
			}
		}
		return paths;
	}
	
	/**
	 * upload image
	 * @author yangwk
	 * @time 2017年7月28日 下午3:42:41
	 * @param files
	 * @return
	 */
	@ApiOperation(value = "upload files", notes = "upload image")
	@ApiImplicitParam(paramType = "query", name = "files", value = "upload picture", required = true, dataType = "MultipartFile")
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public List<String> file(@RequestParam("file") MultipartFile[] files) {
		String savePath = String.format("/%s/file/%s/%s",projectName, RandomUtils.randomNum(2),RandomUtils.randomNum(2));
		List<String> paths = Lists.newArrayList();
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String suffix = "";
			if(suffix.lastIndexOf(".") != -1){
				suffix = name.substring(name.lastIndexOf("."));
			}
			name = String.format("%s-%s%s",DateUtils.getCurrentTime("yyyyMMdd"),getRandomUniqName(), suffix);
			boolean success = saveFile(savePath, name, file);
			if (success) {
				paths.add(String.format("%s/%s", savePath, name));
			} else {
				paths.add("");
			}
		}
		return paths;
	}
	
	/**
	 * Get random values: within 10w, it is guaranteed to be unique, but not absolutely not unique.
	 * @author yangwk
	 * @time 2017年8月16日 下午3:18:18
	 * @return
	 */
	private static String getRandomUniqName(){
		String prefix = RandomUtils.randomNum(1);//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 Represents the front to add 0
        // 4 Representative length is 4
        // d Representative parameter is positive
        return String.format("%s%015d",prefix, hashCodeV);
	}
	
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>(100000);
		for(int i=0;i<100;i++){
			String value = getRandomUniqName();
			if(set.contains(value)){
				System.err.println(value);
			}
			set.add(value);
		}
	}

	private boolean saveFile(String savePath, String name, MultipartFile file) {
		String path = String.format("%s%s", uploadFilePath, savePath);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 保存文件
		try {
			file.transferTo(new File(String.format("%s/%s", path, name)));
		} catch (IOException e) {
			logger.error("save file due to error", e);
			return false;
		}
		return true;
	}
}
