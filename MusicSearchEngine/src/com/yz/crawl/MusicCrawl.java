package com.yz.crawl;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class MusicCrawl implements Runnable{

		    // robot url
		    private Set<String> urlSet;
		    // cache url
		    private Queue<String> urlQueue;
		    // define Host
		    public final static String HOST = "http://music.163.com";
		 
		    // constructor
		    public MusicCrawl() {
		        super();
		        // initialization robot's member
		        setUrlSet(new HashSet<String>());
		        setUrlQueue(new LinkedList<String>());
		    }
		 
		    // url
		    public Set<String> getUrlSet() {
		        return urlSet;
		    }
		 
		    public void setUrlSet(Set<String> urlSet) {
		        this.urlSet = urlSet;
		    }
		 
		    // cache
		    public Queue<String> getUrlQueue() {
		        return urlQueue;
		    }
		 
		    public void setUrlQueue(Queue<String> urlQueue) {
		        this.urlQueue = urlQueue;
		    }
		 
		    // Legal link
		    private boolean isURL(String url) {
		        try {
		            // judge url
		            Pattern pattern = Pattern.compile("^[a-zA-z]+://[^\\s]*");
		            Matcher matcher = pattern.matcher(url);
		            if (matcher.matches()) {
		                return true;
		            } else {
		                return false;
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            return false;
		        }
		    }
		 
		    // whether the url is belong to host
		/*    public static boolean isHost(String url) {
		        return url.contains(HOST);
		    }*/
		 
		    // travel all url
//		    public void traverse(String seed) {
		    	public void traverse(String seed) {
		 
//		        for (this.getUrlQueue().add(seed); !this.getUrlQueue().isEmpty();) {
		        for (this.getUrlQueue().poll(); !this.getUrlQueue().isEmpty();) {
		            boolean flag = true;
		            Document document = null;
		            try {
		                document = Jsoup.connect(seed).userAgent("Mozilla")
		              		  .timeout(3000)
		            		  .post();
		            } catch (IOException e) {
		                e.printStackTrace();
		                // whether connect success
		                flag = false;
		            }
		            // whether connect success,then select a tag
		            // add these aTag into queue
		            if (flag) {
		        // get url
		                Elements elements = document.select("a[href]");
		                for (Element e : elements) {
		                    String s = e.attr("abs:href");
		                    // Legal link and belong host
		                    // and url not in list
		                    // then add it
		                    if (isURL(s) && s.contains(HOST)
		                            && (!getUrlQueue().contains(s))
		                            && (!getUrlSet().contains(s))) {
		                    	System.out.println(s);
		                        this.getUrlQueue().add(s);
		                    }
		                }
		            }
		            // get head of queue
		            // and set it seed
		            // travel seed it again
		            seed = this.getUrlQueue().poll();
		            this.getUrlSet().add(seed);
		            // show information
		            // System.out.println("SIZE:" 
		            // + this.getUrlQueue().size() + "---"
		            // + seed + " connect!");
		        }
		    }
		 
		     public static void main(String[] args) {
		  /*   MusicCrawl robot = new MusicCrawl();
		     robot.traverse("http://music.baidu.com/");
		     Set<String> list = robot.getUrlSet();
		     for (String s : list) {
		     System.out.println(s);
		     }*/
		    	 MusicCrawl mCrawl = new MusicCrawl();
		    	 for (int i = 0; i < 4; i++) {
		    		 new Thread(mCrawl,"Thread"+i).start();;
					
				}
		    	 
		     }

			@Override
			public void run() {
				// TODO Auto-generated method stub
				traverse("");
			}
		 
	

}
