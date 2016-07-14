package com.yz.crawl;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 /*
  * 这段代码主要是，开启10个爬取线程，取爬取网页，开启2个写线程，对爬取的内容进行保存（这里是通过文件的方式保存）
  */
public class Crawl1 implements Runnable{
    /**
     * 需要爬取的url
     */
    public static BlockingQueue<String> crawlQueue;
    /**
     * 爬取后的内容
     */
    public static BlockingQueue<String> saveQueue;
    /**
     * 已经爬取过的url
     */
    public static Set<String> visitedSet;
    /**
     * url提取正则
     */
    public static Pattern urlPattern = Pattern.compile("href\\s*=\\s*\"?(.*?)[\"|>]");
    /**
     * title提取正则
     */
    public static Pattern titlePattern = Pattern.compile("<title>.*</title>");
    /**
     * 开始url
     */
    public static String StartUrl = "http://www.duowan.com";
    /**
     * 爬取深度
     */
    public static AtomicInteger crawlSize = new AtomicInteger(50);
     
    static{
        crawlQueue = new LinkedBlockingQueue<String>();
        saveQueue = new LinkedBlockingQueue<String>();
        crawlQueue.add(StartUrl);
        visitedSet =  Collections.synchronizedSet( new HashSet<String>() );
    }
     
    public void crawl(){
        String urlStr = null;
        String pageStr = null;
        try {
            while( true ){
                urlStr = crawlQueue.take();
                visitedSet.add(urlStr);
                pageStr = downLoadPage(urlStr);
                if( pageStr!=null )
                    pageHandel(pageStr,urlStr);
                crawlSize.decrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 页面下载
     * @param urlStr
     * @return
     * @throws Exception
     */
    private String downLoadPage(String urlStr) throws Exception{
        if( urlStr==null )
            return null;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        connHandel(conn);
        conn.setDoInput(true);
        conn.connect();
        String pageStr = null;
        int status = conn.getResponseCode();
        //这里只对200做处理
        if( status==200 ){
            //这里只对HTML做处理
            if( isHtml(conn) ){
                String charset = getCharset(conn);
                InputStream is = conn.getInputStream();
                pageStr = stream2String(is,charset);
                 
            }
        }
        return pageStr;
         
    }
    /**
     * 判断是否是html页面
     * @param conn
     * @return
     */
    private boolean isHtml(HttpURLConnection conn){
        boolean b = false;
        String tc = getContentType(conn);
        if( tc!=null&&tc.contains("html") )
            b = true;
        return b;
    }
    /**
     * 获取字符编码
     * @param conn
     * @return
     */
    private String getCharset(HttpURLConnection conn){
        String charset = "utf-8";
        String tc = getContentType(conn);
        if( tc!=null ){
            String[] charsets = tc.split("=");
            if( charset!=null&&charsets.length>=2 ){
                charset = charsets[1];
            }
        }
        return charset;
    }
    /**
     * 获取ContentType
     * @param conn
     * @return
     */
    private String getContentType(HttpURLConnection conn){
        List<String> tc = conn.getHeaderFields().get("Content-Type");
        if( tc!=null&&!tc.isEmpty() )
            return tc.get(0);
        else
            return null;
         
    }
    /**
     * 页面处理
     * @param pageStr
     * @param urlStr
     * @throws InterruptedException
     */
    private void pageHandel(String pageStr,String urlStr) throws InterruptedException{
        if( crawlSize.get()>0 )
            urlHandel(pageStr,urlStr);
        titleHandel(pageStr,urlStr);
        //这里可以进行其他操作，如页面内容提取等
         
    }
    /**
     * 标题处理
     * @param pageStr
     * @param urlStr
     * @throws InterruptedException
     */
    private void titleHandel(String pageStr,String urlStr) throws InterruptedException{
        Matcher titleMatcher = titlePattern.matcher(pageStr);
        while( titleMatcher.find() ){
            String titleStr = titleStrHandel(titleMatcher.group());
            System.out.println(Thread.currentThread().getName()+"  "+crawlQueue.size()+" "+saveQueue.size()+" "+titleStr);
            String saveStr = titleStr+"        "+urlStr;
            saveQueue.put(saveStr);
        }
    }
    /**
     * url处理
     * @param pageStr
     * @param pUrlStr
     * @throws InterruptedException
     */
    private void urlHandel(String pageStr,String pUrlStr) throws InterruptedException{
        Matcher urlM = urlPattern.matcher(pageStr);
        String cUtlStr = null;
        while( urlM.find() ){
            cUtlStr = urlM.group().replace("href=", "").replace("\"", "");
            if( cUtlStr.startsWith("http://")||cUtlStr.startsWith("https://") ){
                if( !visitedSet.contains(cUtlStr) ){
                    crawlQueue.put(cUtlStr);
                }
            }else if( cUtlStr.startsWith("/") ){
                cUtlStr = childUrlHandel(cUtlStr,pUrlStr);
                if( !visitedSet.contains(cUtlStr) ){
                    crawlQueue.put(cUtlStr);
                }
            }
        }
    }
    /**
     * 子url处理
     * @param cUrlStr
     * @param pUrlStr
     * @return
     */
    private String childUrlHandel(String cUrlStr,String pUrlStr){
        String nameSpace = getUrlNameSpace(pUrlStr);
        if( nameSpace!=null ){
            return nameSpace+cUrlStr;
        }else
            return null;
    }
    /**
     * 获取一级域名
     * @param urlStr
     * @return
     */
    private String getUrlNameSpace(String urlStr){
        String nameSpace = null;
        String[] strs = null;
        String protocol = "http://";
        if( urlStr.startsWith("http://") ){
            strs = urlStr.replace("http://", "").split("/");
        }else if( urlStr.startsWith("https://") ){
            strs = urlStr.replace("https://", "").split("/");
            protocol = "https://";
        }else if( urlStr.startsWith("www") ){
            strs = urlStr.split("/");
        }
        if( strs.length>=1 ){
            nameSpace = protocol+strs[0];
        }
        return nameSpace;
    }
    /**
     * 标题处理
     * @param titleStr
     * @return
     */
    private String titleStrHandel(String titleStr){
        return titleStr.replace("<title>", "").replace("</title>", "");
    }
    /**
     * 流转字符串
     * @param is
     * @param charset
     * @return
     * @throws IOException
     */
    private String stream2String(InputStream is,String charset) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        if( is!=null ){
            byte[] buff = new byte[1024];
            int len = 0;
            while( (len = is.read(buff))!=-1 ){
                bos.write(buff, 0, len);
            }
        }
        return bos.toString(charset);
    }
    /**
     * 请求连接处理
     * @param conn
     */
    private void connHandel(HttpURLConnection conn){
        conn.setReadTimeout(6000);
        conn.setConnectTimeout(6000);
        //TODO 设置请求头
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        conn.setRequestProperty("Connection", "Keep-Alive");
    }
 
    @Override
    public void run() {
        this.crawl();
    }
    /**
     * 保存类
     * @author rimi
     *
     */
    public static class WritePage implements Runnable{
         
        private String fileName;
         
        private PrintWriter pw;
         
        public WritePage(String fileName) {
            this.fileName = fileName;
            this.init();
        }
         
        private void init(){
            try {
                pw = new PrintWriter(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        public void run() {
            String str = null;
            while( true ){
                try {
                    str = saveQueue.take();
                    pw.println(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
             
        }
    }
     
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(12);
        ScheduledExecutorService sc = Executors.newScheduledThreadPool(1);
        for( int i=0;i<10;i++ ){
            es.execute(new Crawl1());
        }
        for( int i=0;i<2;i++ ){
            es.execute(new WritePage("file"+i+".txt"));
        }
        es.shutdown();
    }
 
}