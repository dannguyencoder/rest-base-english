package com.onecoderspace.base.util.security.xss;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
  * xss illegal label filtering
  * {@link http://www.jianshu.com/p/32abc12a175a?nomobile=yes}
  * @author yangwenkui
  * @version v2.0
  * @time April 27, 2017 5:47:09 PM
  */
public class JsoupUtil {

	/**
	 * Use the built-in basicWithImages whitelist
	 * Allowed notes are a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, span,
	 * strike,strong,sub,sup,u,ul,img
	 * and the href of the a tag, the src, align, alt, height, width, title attributes of the img tag
	 */
	private static final Whitelist whitelist = Whitelist.basicWithImages();
	/** Configure filter parameters without formatting the code */
	private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
	static {
		// 富文本编辑时一些样式是使用style来进行实现的
		// 比如红色字体 style="color:red;"
		// 所以需要给所有标签添加style属性
		whitelist.addAttributes(":all", "style");
	}

	public static String clean(String content) {
		return Jsoup.clean(content, "", whitelist, outputSettings);
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String text = "<a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss";
		System.out.println(clean(text));
	}

}
