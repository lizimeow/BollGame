package bollGame;


public class TimeCount {
	long initMsec;
	long curMsec;
	long res;
	int addnum;
	
	public TimeCount(){
		initMsec = System.currentTimeMillis();
		res = 0;
	}
	
	//重写update
	public void update(){
		curMsec=System.currentTimeMillis();
		res = curMsec-initMsec;	
	}
	
	public String showTime(){
		String time ;
		time = String.format("%.1f″", res/1000.0);
		return time;
	}
	
}
