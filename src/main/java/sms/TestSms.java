package sms;

import java.net.URLEncoder;

import com.bestv.lego.utils.HttpClientUtil;
import com.bestv.lego.utils.MessageDigestUtil;


public class TestSms {
	
	public static void main(String[] args) throws Exception{
		String smsSendBatchUrl = "http://sdk2.entinfo.cn:8061/webservice.asmx/mdsmssend";
		String sn = "SDK-SKY-010-01656";
		String pwd = "194947";
		String mobile = "15972957263";
		String url = new StringBuilder(smsSendBatchUrl).append("?sn=").append(sn).append("&pwd=")
				.append(MessageDigestUtil.getMD5(sn.concat(pwd)).toUpperCase()).append("&mobile=")
				.append(mobile).append("&content=")
				.append(URLEncoder.encode("你好", "UTF-8")).append("&ext=&rrid=&stime=&msgfmt=").toString();
		System.out.println(url);
		String response = HttpClientUtil.httpGet(url);
		System.out.println(response);
	}

}
