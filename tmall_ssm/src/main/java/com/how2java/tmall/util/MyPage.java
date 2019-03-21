package com.how2java.tmall.util;
/** 
* @author Jhowe
* @version 2019年3月21日 上午5:30:01
* tmall_ssm
*/
public class MyPage {
	private int start;
	private int count;
	private long total;
	private int current = 1;//当前页
	private  static final int DEFAULT_COUNT = 5;
	public MyPage() {
		count = DEFAULT_COUNT;
	}
	
	public MyPage(int start, int count,long total) {
		super();
		Boolean isValidate = total>start*count;
		this.start = isValidate?start:0;
		this.count = isValidate?count:0;
		this.total = total;
	}
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start < 1 ? 1:start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		Boolean higherRange = current>getTotalPage();
		Boolean lowerRange = current<1;
		this.current = higherRange?getTotalPage():current;
		this.current = lowerRange?1:current;
		this.start = (current-1)*count;
	}

    public boolean isHasPreviouse(){
        if(start==0)
            return false;
        return true;
    }
    public boolean isHasNext(){
        if(start==getLast())
            return false;
        return true;
    }
    public int getTotalPage(){
        int totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if(0==count) return 0;
        if (0 == total % count)
            totalPage = (int) total /count;
            // 假设总数是51，不能够被5整除的，那么就有11页
        else
            totalPage = (int) total / count + 1;

        if(0==totalPage)
            totalPage = 1;
        return totalPage;

    }

    public int getLast(){
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = (int)total - count;
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = (int)(total - total % count);
        last = last<0?0:last;
        return last;
    }
	
    public int getEnd() {
    	int endIndex=getStart() + count;
    	endIndex = endIndex>total?(int)total:endIndex;
    	return endIndex;
    }
}
