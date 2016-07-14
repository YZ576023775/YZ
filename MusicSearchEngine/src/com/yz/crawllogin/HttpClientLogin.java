package com.yz.crawllogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.HeaderTokenizer.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
 
public class HttpClientLogin {
    @Test
    // 获取一个HTML页面的内容，一个简单的get应用
    public void grabPageHTML() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "GBK");
 
        // releaseConnection等同于reset，作用是重置request状态位，为下次使用做好准备。
        // 其实就是用一个HttpGet获取多个页面的情况下有效果；否则可以忽略此方法。
        httpget.releaseConnection();
 
        System.out.println(html);
    }
 
    // 下载一个文件到本地（本示范中为一个验证码图片）
    @Test
    public void downloadFile() throws Exception {
        String url = "http://www.lashou.com/account/captcha";
        File dir = new File("D:\\TDDOWNLOAD");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String destfilename = "D:\\TDDOWNLOAD\\yz.png";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        File file = new File(destfilename);
        if (file.exists()) {
            file.delete();
        }
 
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[2048];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp);
            }
            fout.close();
        } finally {
            // 在用InputStream处理HttpEntity时，切记要关闭低层流。
            in.close();
        }
 
        httpget.releaseConnection();
    }
 
    @Test
    // Post方法，模拟表单提交参数登录到网站。
    // 结合了上面两个方法：grabPageHTML/downloadFile，同时增加了Post的代码。
    public void login2Lashou() throws Exception {
    	String url = "http://jwxt.wust.edu.cn/whkjdx/verifycode.servlet";
    	String destfilename = "D:/TDDOWNLOAD/wkdimg.gif";
//        HttpClient httpclient = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(url);
    	File file = new File(destfilename);
    	if (file.exists()) {
    		file.delete();
    	}
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpResponse response = httpclient.execute(httpget);
    	HttpEntity entity = response.getEntity();
    	InputStream in = entity.getContent();
    	try {
    		FileOutputStream fout = new FileOutputStream(file);
    		int l = -1;
    		byte[] tmp = new byte[2048];
    		while ((l = in.read(tmp)) != -1) {
    			fout.write(tmp);
    		}
    		fout.close();
    	} finally {
    		in.close();
    	}
    	httpget.releaseConnection();
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("请输入下载下来的验证码中显示的数字...");
    	String yan = br.readLine();
    	
    	
    	
   /* 	HttpClient httpclient = new DefaultHttpClient();
//    	HttpGet HttpGet = new HttpGet("http://www.kuwo.cn");
    	HttpGet HttpGet = new HttpGet("http://music.163.com/weapi/login/cellphone/");
    	HttpContext httpContext = new BasicHttpContext();
    	HttpResponse response = httpclient.execute(HttpGet,httpContext);
    	HttpEntity entity = response.getEntity();
    	Object user_token= httpContext.getAttribute(ClientContext.USER_TOKEN);
    	System.out.println(user_token);
    	// 在这里可以用Jsoup之类的工具对返回结果进行分析，以判断登录是否成功
    	String postResult = EntityUtils.toString(entity, "utf-8");
//    	System.out.println(postResult);
    	// 我们这里只是简单的打印出当前Cookie值以判断登录是否成功。
    	CookieStore cookieStore = ((AbstractHttpClient) httpclient).getCookieStore();
    	List<Cookie> cookies = ((AbstractHttpClient) httpclient)
    			.getCookieStore().getCookies();
    	for (Cookie cookie : cookies)
    		System.out.println("cookie begin***\n" + cookie + "\n cookie end");
    	HttpGet.releaseConnection();*/
        HttpPost httppost = new HttpPost("http://jwxt.wust.edu.cn/whkjdx/Logon.do?method=logon");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("USERNAME","201212237035"));
        params.add(new BasicNameValuePair("PASSWORD", "201212237035"));
        params.add(new BasicNameValuePair("useDogCode", ""));
        params.add(new BasicNameValuePair("useDogCode", ""));
        params.add(new BasicNameValuePair("RANDOMCODE", yan));
        params.add(new BasicNameValuePair("x", "0"));
        params.add(new BasicNameValuePair("y", "0"));
        httppost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
        
//        HttpContext context = new BasicHttpContext();
//         HttpResponse response1 = httpclient.execute(httppost,context);
         HttpResponse response1 = httpclient.execute(httppost);
         
        int statusCode = response1.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpGet HttpGet=null;
        HttpEntity entity1 = null;
        CookieStore cs=null;
//        if (statusCode==301||statusCode==302) {
   /*   //获得重定向之后的主机地址信息
        HttpHost targetHost =
        (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        System.out.println(targetHost); // http://localhost:8080
        //获得实际的请求对象的URI（即重定向之后的
//        "/cms/backend/login.jsp"）
        HttpUriRequest actualRequest = (HttpUriRequest)
        context.getAttribute(ExecutionContext.HTTP_REQUEST);
        System.out.println(actualRequest.getURI());*/
        httppost.releaseConnection();
        
        HttpGet = new HttpGet("http://jwxt.wust.edu.cn/whkjdx/framework/main.jsp");
//        ((AbstractHttpClient)httpclient).setCookieStore(cookieStore1);
        response1 = httpclient.execute(HttpGet);
         entity1 = response1.getEntity();
        if(entity1 != null){
        //获得响应的字符集编码信息
        //即获取HTTP HEAD的： ContentType:text/html;charset=UTF-8中的字符集信息
        String charset =
        EntityUtils.getContentCharSet(entity1);
        InputStream is = entity1.getContent();
        //使用响应中的编码来解释响应的内容
        BufferedReader br1 = new BufferedReader(new
        InputStreamReader(is,charset));
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/TDDOWNLOAD/wkdredirect.html"),charset));
        String line = null;
        while((line = br1.readLine()) != null){
        	bWriter.write(line);
        }
         cs  =((AbstractHttpClient)httpclient).getCookieStore();
    	List<Cookie> cookies1 = ((AbstractHttpClient) httpclient)
    			.getCookieStore().getCookies();
    	for (Cookie cookie : cookies1)
    		System.out.println("cookie begin***\n" + cookie + "\n cookie end");
        bWriter.close();
        is.close();
      /*  //释放所有的链接资源，一般在所有的请求处理完成之后，才需要释放
        httpclient.getConnectionManager().shutdown();*/
        } 
        
        // 在这里可以用Jsoup之类的工具对返回结果进行分析，以判断登录是否成功
 
    /*    // 第三步：打开会员页面以判断登录成功（未登录用户是打不开会员页面的）
        String memberpage = "http://www.lashou.com/account/orders/";
        httpget = new HttpGet(memberpage);
        response = httpclient.execute(httpget); // 必须是同一个HttpClient！
        entity = response.getEntity();
        String html = EntityUtils.toString(entity, "GBK");
        httpget.releaseConnection();
 
        System.out.println(html);*/
   /* }else  {
    	entity1 = response1.getEntity();
        	String postResult1 = EntityUtils.toString(entity1, "utf-8");
        	System.out.println(postResult1);
        	FileWriter ops = new FileWriter("D:/TDDOWNLOAD/wkd.html"); 
        	BufferedWriter bufferedWriter = new BufferedWriter(ops);
        	bufferedWriter.write(postResult1);
        	bufferedWriter.flush();
        	bufferedWriter.close();
        	// 我们这里只是简单的打印出当前Cookie值以判断登录是否成功。
        	 cs = ((AbstractHttpClient) httpclient).getCookieStore();
        	List<Cookie> cookies1 = ((AbstractHttpClient) httpclient)
        			.getCookieStore().getCookies();
        	for (Cookie cookie : cookies1)
        		System.out.println("cookie begin***\n" + cookie + "\n cookie end");
        	httppost.releaseConnection();
        	
        }*/
        
    /*     HttpGet = new HttpGet("http://music.163.com/user/home?id=76539865");
         ((AbstractHttpClient)httpclient).setCookieStore(cs);
         response = httpclient.execute(HttpGet);
         entity = response.getEntity();
         InputStream is = entity.getContent();
         String charset = EntityUtils.getContentCharSet(entity);
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,charset));
//        String html = EntityUtils.toString(entity);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/TDDOWNLOAD/主页.html")));
        String line =null;
        while((line=bufferedReader.readLine())!=null){
        	bufferedWriter.write(line);
        	
        }
        bufferedWriter.close();
        bufferedReader.close();
        // releaseConnection等同于reset，作用是重置request状态位，为下次使用做好准备。
        // 其实就是用一个HttpGet获取多个页面的情况下有效果；否则可以忽略此方法。
        HttpGet.releaseConnection();*/
 
//        System.out.println(html);
        httpclient.getConnectionManager().shutdown();
    }
/*    @Test
    // Post方法，模拟表单提交参数登录到网站。
    // 结合了上面两个方法：grabPageHTML/downloadFile，同时增加了Post的代码。
    public void login2Lashou() throws Exception {
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpGet HttpGet = new HttpGet("http://xn.ijiapei.com/index.aspx");
    	HttpResponse response = httpclient.execute(HttpGet);
    	HttpEntity entity = response.getEntity();
    	// 在这里可以用Jsoup之类的工具对返回结果进行分析，以判断登录是否成功
    	String postResult = EntityUtils.toString(entity, "utf-8");
    	// 我们这里只是简单的打印出当前Cookie值以判断登录是否成功。
    	CookieStore cookieStore = ((AbstractHttpClient) httpclient).getCookieStore();
    	List<Cookie> cookies = ((AbstractHttpClient) httpclient)
    			.getCookieStore().getCookies();
    	for (Cookie cookie : cookies)
    		System.out.println("cookie begin***\n" + cookie + "\n cookie end");
    	HttpGet.releaseConnection();
    	// 第一步：先下载验证码到本地
    	String url = "http://xn.ijiapei.com/bmp.aspx";
    	String destfilename = "D:\\TDDOWNLOAD\\yz.png";
//        HttpClient httpclient = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(url);
    	File file = new File(destfilename);
    	if (file.exists()) {
    		file.delete();
    	}
    	
    	response = httpclient.execute(httpget);
    	entity = response.getEntity();
    	InputStream in = entity.getContent();
    	try {
    		FileOutputStream fout = new FileOutputStream(file);
    		int l = -1;
    		byte[] tmp = new byte[2048];
    		while ((l = in.read(tmp)) != -1) {
    			fout.write(tmp);
    		}
    		fout.close();
    	} finally {
    		in.close();
    	}
    	httpget.releaseConnection();
    	
    	// 第二步：用Post方法带若干参数尝试登录，需要手工输入下载验证码中显示的字母、数字
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("请输入下载下来的验证码中显示的数字...");
    	String yan = br.readLine();
    	
    	HttpPost httppost = new HttpPost("http://xn.ijiapei.com/index.aspx?r=0.17349779070354998");
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("__EVENTTARGET",""));
    	params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
    	params.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTEwNjY3MTA3NGRkxwFixHnkDy9P+fLA4UCCkEJscGm5FMquwlUDLnNU3A8="));
    	params.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEdAAYQEx6V2RBzVsbXlMSqty9SDcZ/mYit9OC5FYcxtAH0C5nUDBCvVAZZ9K1xrpoOZbiTV0ZYqz5Z9WLriyaS7DMIxhpCsEwb8H1LfbxE6I1ljcKacmievJVm2xs9t8ReocsGwxdlqvhcjKBf5mmko9Bo/UIuWZgnqhpRq6IXcg70og=="));
    	params.add(new BasicNameValuePair("log_name", "x116495918"));
    	params.add(new BasicNameValuePair("log_pwd", "576023"));
    	params.add(new BasicNameValuePair("Yz_code", yan));
    	params.add(new BasicNameValuePair("log_btn", "登录"));
    	params.add(new BasicNameValuePair("hf_value", ""));
    	httppost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
//        ((AbstractHttpClient) httpclient).setCookieStore(cookieStore);
    	
    	HttpContext context = new BasicHttpContext();
    	response = httpclient.execute(httppost,context);
    	int statusCode = response.getStatusLine().getStatusCode();
    	System.out.println(statusCode);
    	
    	CookieStore cs=null;
    	if (statusCode==301||statusCode==302) {
    		//获得重定向之后的主机地址信息
    		HttpHost targetHost =
    				(HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
    		System.out.println(targetHost); // http://localhost:8080
    		//获得实际的请求对象的URI（即重定向之后的
//        "/cms/backend/login.jsp"）
    		HttpUriRequest actualRequest = (HttpUriRequest)
    				context.getAttribute(ExecutionContext.HTTP_REQUEST);
    		System.out.println(actualRequest.getURI());
    		httppost.releaseConnection();
    		
    		HttpGet = new HttpGet(targetHost.toString()+"/Main/PracticeChoose.aspx");
//        ((AbstractHttpClient)httpclient).setCookieStore(cookieStore1);
    		response = httpclient.execute(HttpGet);
    		entity = response.getEntity();
    		if(entity != null){
    			//获得响应的字符集编码信息
    			//即获取HTTP HEAD的： ContentType:text/html;charset=UTF-8中的字符集信息
    			String charset =
    					EntityUtils.getContentCharSet(entity);
    			InputStream is = entity.getContent();
    			//使用响应中的编码来解释响应的内容
    			BufferedReader br1 = new BufferedReader(new
    					InputStreamReader(is,charset));
    			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/TDDOWNLOAD/redirect.html"),charset));
    			String line = null;
    			while((line = br1.readLine()) != null){
    				bWriter.write(line);
    			}
    			cs  =((AbstractHttpClient)httpclient).getCookieStore();
    			List<Cookie> cookies1 = ((AbstractHttpClient) httpclient)
    					.getCookieStore().getCookies();
    			for (Cookie cookie : cookies1)
    				System.out.println("cookie begin***\n" + cookie + "\n cookie end");
    			bWriter.close();
    			is.close();
    			  //释放所有的链接资源，一般在所有的请求处理完成之后，才需要释放
        httpclient.getConnectionManager().shutdown();
    		} 
    		
    		// 在这里可以用Jsoup之类的工具对返回结果进行分析，以判断登录是否成功
    		
    		    // 第三步：打开会员页面以判断登录成功（未登录用户是打不开会员页面的）
        String memberpage = "http://www.lashou.com/account/orders/";
        httpget = new HttpGet(memberpage);
        response = httpclient.execute(httpget); // 必须是同一个HttpClient！
        entity = response.getEntity();
        String html = EntityUtils.toString(entity, "GBK");
        httpget.releaseConnection();
 
        System.out.println(html);
    	}else  {
    		entity = response.getEntity();
    		String postResult1 = EntityUtils.toString(entity, "utf-8");
    		FileWriter ops = new FileWriter("D:/TDDOWNLOAD/ok.html"); 
    		BufferedWriter bufferedWriter = new BufferedWriter(ops);
    		bufferedWriter.write(postResult1);
    		bufferedWriter.flush();
    		bufferedWriter.close();
    		// 我们这里只是简单的打印出当前Cookie值以判断登录是否成功。
    		cs = ((AbstractHttpClient) httpclient).getCookieStore();
    		List<Cookie> cookies1 = ((AbstractHttpClient) httpclient)
    				.getCookieStore().getCookies();
    		for (Cookie cookie : cookies1)
    			System.out.println("cookie begin***\n" + cookie + "\n cookie end");
    		httppost.releaseConnection();
    		
    	}
    	
    	httpget = new HttpGet("http://xn.ijiapei.com/Main/NFreePracticeV2.aspx?pc_cd=1");
    	((AbstractHttpClient)httpclient).setCookieStore(cs);
    	response = httpclient.execute(httpget);
    	entity = response.getEntity();
    	InputStream is = entity.getContent();
    	String charset = EntityUtils.getContentCharSet(entity);
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,charset));
//        String html = EntityUtils.toString(entity);
    	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/TDDOWNLOAD/TEST.html")));
    	String line =null;
    	while((line=bufferedReader.readLine())!=null){
    		bufferedWriter.write(line);
    		
    	}
    	bufferedWriter.close();
    	bufferedReader.close();
    	// releaseConnection等同于reset，作用是重置request状态位，为下次使用做好准备。
    	// 其实就是用一个HttpGet获取多个页面的情况下有效果；否则可以忽略此方法。
    	httpget.releaseConnection();
    	
//        System.out.println(html);
    	httpclient.getConnectionManager().shutdown();
    }
*/ 
    @Test
    public void testSystemIn() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        String hello = reader.readLine();
        System.out.println(hello);
    }
 
    @Test
    // 设置已获取的cookie，查看需要登录后才能查看的页面
    public void testGetinfoByLoginCookie() throws Exception, IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie1 = new BasicClientCookie("ThinkID","5s4tmqem08gh091v3egoa7sqf7");
        cookie1.setDomain(".lashou.com");
        BasicClientCookie cookie2 = new BasicClientCookie("city_b","2419");
        cookie2.setDomain("lashou.com");
        BasicClientCookie cookie3 = new BasicClientCookie("client_key","1425104707wd157b4b24ff70adcb875a");
        cookie3.setDomain("lashou.com");
        BasicClientCookie cookie4 = new BasicClientCookie("login_name2","testuser007");
        cookie4.setDomain("lashou.com");
        BasicClientCookie cookie5 = new BasicClientCookie("pwd2","4c88a4062736c26572d3ec382868fa2b");
        cookie5.setDomain("lashou.com");
        cookieStore.addCookie(cookie1);
        cookieStore.addCookie(cookie2);
        cookieStore.addCookie(cookie3);
        cookieStore.addCookie(cookie4);
        cookieStore.addCookie(cookie5);
        List<Cookie> cookies = new ArrayList<Cookie>();
        httpclient.setCookieStore(cookieStore);
         
        List<Cookie> cookieList = httpclient.getCookieStore().getCookies();
        for(int i=0;i<cookieList.size();i++){
            System.out.println("cookie"+i+":"+cookieList.get(i));
        }
         
        // 设置已登录的cookie
        String memberpage = "http://www.lashou.com/account/orders/";
        HttpGet httpget = new HttpGet(memberpage);
        HttpResponse response = httpclient.execute(httpget); // 必须是同一个HttpClient！
        HttpEntity entity = response.getEntity();
        entity = response.getEntity();
        String html = EntityUtils.toString(entity, "GBK");
        httpget.releaseConnection();
 
        System.out.println(html);
    }
}