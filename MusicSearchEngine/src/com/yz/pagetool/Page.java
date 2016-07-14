package com.yz.pagetool;

/**
 * 当前页和总记录数可设置即currentPage rowCount
 * pageSize自己配置
 * totalPage可由rowCount pageSize计算出
 * 必须先设置rowCount 再设置currentPage
 * @author YZ
 *
 */
public class Page {
	 
	    private int pageSize;//每页记录数
	    private int rowCount;//总记录数
	    private int totalPage;//总页数
	     
	    private int currentPage=1;//当前页
	     
	    private int first;//数据库查询起始位
	    private int count;//数据库查询数量
	     
	    private int prevPage;//前一页
	    private int nextPage;//下一页
	     
	    private int paginationNum;//页码显示数量
	    private int paginationBegin=0;//开始页码
	    private int paginationEnd=0;//结束页码
	     
	    /**
	     * @param pageSize 每页记录数
	     * @param rowCount 总记录数
	     * @param paginationNum  页码显示数量
	     * 默认当前页设置为1
	     */
	    public Page(int pageSize,int rowCount,int paginationNum)
	    {
	         this(pageSize,rowCount,1,paginationNum);
	         
	    }
	    /**
	     * @param pageSize 每页记录数
	     * @param paginationNum  页码显示数量
	     * 需自己指定rowCount currentPage
	     * 先set rowCount 再set currentPage
	     */
	    public Page(int pageSize,int paginationNum)
	    {
	    	this.pageSize=pageSize;
	    	this.paginationNum=paginationNum;	         
	    }
	    /**
	     * @param pageSize 每页记录数
	     * @param rowCount 总记录数
	     * @param paginationNum  页码显示数量
	     * 指定当前页
	     */
	    public Page(int pageSize,int rowCount,int currentPage,int paginationNum)
	    {
	    	this(pageSize, paginationNum);
	    	this.rowCount=rowCount;
	    	//设置总记录数并计算总页数
	    	setRowCount(rowCount);  
	    	setCurrentPage(currentPage);
	    	
	    }
	 
	  /**
	   * 设置当前页同时计算出prevPage nextPage first count
	   * paginationBegain paginationEnd
	   * if(page<1)this.currentPage=1
	   * @param page
	   */
	    public void setCurrentPage(int page) {
	            if(page<1)this.currentPage=1;
	            else if(page>totalPage)this.currentPage=totalPage;
	            else this.currentPage=page;
	             
	            first=(this.currentPage-1)*pageSize;//数据库起始位
	            count=currentPage<totalPage?pageSize:(rowCount-(totalPage-1)*pageSize);//数据库count
	             
	             
	               prevPage=this.currentPage-1;  
	               nextPage=this.currentPage+1;  
	             if(prevPage<1)prevPage=1;  
	             if(nextPage>totalPage)nextPage=totalPage;  
	              
	              
	              
	             int p1=paginationNum/2;
	             int p2=paginationNum-p1-1;
	              
	             
	            if(totalPage<=paginationNum)//页数不足paginationNum
	            {                       
	                paginationBegin=1;
	                paginationEnd=totalPage;
	            }
	            else//页数大于paginationNum
	             {                      
	                if(this.currentPage>p1&&this.currentPage<=totalPage-p2)
	                {
	                    paginationBegin=this.currentPage-p1;
	                    paginationEnd=this.currentPage+p2;
	                }
	                else if(this.currentPage<=p1)
	                {
	                    paginationBegin=1;
	                    paginationEnd=paginationNum;
	                 
	                }else if(this.currentPage>totalPage-p2)
	                {
	                    paginationBegin=totalPage-paginationNum+1;
	                    paginationEnd=totalPage;
	                 
	                }
	              
	             }
	    }
	     
	    /**
	     * 设置总记录数并计算总页数
	     * @return 
	     * void 
	     */
	    public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		totalPage=(rowCount+pageSize-1)/pageSize;  
    	if(totalPage<1)  
    		totalPage=1;
	}

		/**
	     * 得到当前页
	     * @return 
	     * int 
	     */
	    public int getCurrentPage() {
	        return currentPage;
	    }
	     
	    /**
	     * 得到每页记录数
	     * @return
	     * int
	     */
	    public int getPageSize() {
	        return pageSize;
	    }
	 
	     
	 
	    /**
	     * 得到总记录数
	     * @return
	     * int
	     */
	    public int getRowCount() {
	        return rowCount;
	    }
	 
	     
	 
	    /**
	     * 得到总页数
	     * @return
	     * int
	     */
	    public int getTotalPage() {
	        return totalPage;
	    }
	 
	    /**
	     * 得到数据库起始位
	     * @return
	     * int
	     */
	    public int getFirst() {
	        return first;
	    }
	 
	    /**
	     * 得到数据库查询长度
	     * @return
	     * int
	     */
	    public int getCount() {
	        return count;
	    }
	 
	    /**
	     * 得到前一页页码
	     * @return
	     * int
	     */
	    public int getPrevPage() {
	        return prevPage;
	    }
	 
	    /**
	     * 得到后一页页码
	     * @return
	     * int
	     */
	    public int getNextPage() {
	        return nextPage;
	    }
	 
	    /**
	     * 得到页码显示数量
	     * @return
	     * int
	     */
	    public int getPaginationNum() {
	        return paginationNum;
	    }
	 
	    /**
	     * 得到页码显示开始
	     * @return
	     * int
	     */
	    public int getPaginationBegin() {
	        return paginationBegin;
	    }
	 
	    /**
	     * 得到页码显示结束
	     * @return
	     * int
	     */
	    public int getPaginationEnd() {
	        return paginationEnd;
	    }
		@Override
		public String toString() {
			return "Page [pageSize=" + pageSize + ", rowCount=" + rowCount
					+ ", totalPage=" + totalPage + ", currentPage="
					+ currentPage + ", first=" + first + ", count=" + count
					+ ", prevPage=" + prevPage + ", nextPage=" + nextPage
					+ ", paginationNum=" + paginationNum + ", paginationBegin="
					+ paginationBegin + ", paginationEnd=" + paginationEnd
					+ "]";
		}

	 
	     
	}


