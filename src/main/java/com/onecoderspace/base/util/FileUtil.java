package com.onecoderspace.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * Read all the lines in the file
	 * @author yangwk
	 * @time September 12, 2017 9:46:33 AM
	 * @param filePath file full path
	 * @return
	 */
	public static List<String> getLines(String filePath){
		FileInputStream inputStream = null;
		List<String> lines = Lists.newArrayList();
		try {
			inputStream = new FileInputStream(new File(filePath));
			lines = IOUtils.readLines(inputStream);
		} catch (Exception e) {
			logger.error("read file due to error",e);
		}finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("close inputStream due to error",e);
			}
		}
		return lines;
	}

	/**
	 * Write to file
	 * @author yangwk
	 * @time September 14, 2017 1:44:23 PM
	 * @param file
	 * @param lines
	 */
	public static void writeLines(File file, List<String> lines) {
		try {
			org.apache.commons.io.FileUtils.writeLines(file, lines, "\n");
		} catch (IOException e) {
			logger.error("write lines due to error",e);
		}
		
	}
	
}
