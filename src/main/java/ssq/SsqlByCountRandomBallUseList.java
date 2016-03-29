package ssq;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class SsqlByCountRandomBallUseList {
	static Timer timer = new Timer();
	static Date myBirth;
	static Date babyBirth;
	static Date today;
	static HashMap<Integer,Integer> dbReds=new  HashMap<Integer,Integer>();
	static HashMap<Integer,Integer> dbBlues=new  HashMap<Integer,Integer>();
	static ArrayList<Ball> reds = new ArrayList<Ball>();
	static ArrayList<Ball> blues = new ArrayList<Ball>(); 
	static ArrayList<Integer> redEntity = new ArrayList<Integer>();
	static ArrayList<Integer> blueEntity = new ArrayList<Integer>();
	static int [] loopArray={19,88,12,22,19,88,7,18};
	static int loopCount=0;
	static int redCount=33;
	
	SsqlByCountRandomBallUseList(){
		for (int i = 1; i <= 33; i++) {
			reds.add(new Ball(i,0));
		}
		for (int i = 1; i <= 16;i++) {
			blues.add(new Ball(i,0));
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			myBirth=sdf.parse("1988-07-18");
			babyBirth=sdf.parse("1988-12-22");
			today=new Date();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initEntity();
	}
	public static void initEntity(){
		
		for (int a = 1; a <= 33; a++) {
			redEntity.add(a);
		}
		for (int a = 1; a <= 16; a++) {
			blueEntity.add(a );
		}
		redCount=33;
	}
	public static void init(){
		reds.clear();
		blues.clear();
		redEntity.clear();
		blueEntity.clear();
		for (int i = 1; i <= 33; i++) {
			reds.add(new Ball(i,0));
		}
		for (int i = 1; i <= 16;i++) {
			blues.add(new Ball(i,0));
		}
		for (int a = 1; a <= 33; a++) {
			redEntity.add(a);
		}
		for (int a = 1; a <= 16; a++) {
			blueEntity.add(a );
		}
		redCount=33;
	}
	public static void initDBBall(){
		for (int i = 1; i <= 33; i++) {
			dbReds.put(i, 0);
		}
		for (int i = 1; i <= 16;i++) {
			dbBlues.put(i, 0);
		}
	}
	public static void main(String[] args) {
		initDBBall();
		int bb=0;
		MyComparator comparator=new MyComparator();
		while(true){
			init();
			if(loopCount==loopArray.length)
				loopCount=0;
			for(int i=0;i<loopArray[loopCount];i++){
				for(int a=0;a<6;a++){
				//	int r=(int) ((Math.random() ) * babyBirth.getTime()*today.getTime()/9999% 33) + 1;
					int r=(int) ((Math.random() ) * 33) + 1;
					redEntity.remove(new Integer(r));
					for(int j=0;j<58;j++){
						if(r==(j+1)){
							Ball ball=reds.get(j);
							int value=ball.getValue();
							ball.setValue(++value);
							break;
						}
					}
				}
				//int b = (int) ((Math.random() ) * myBirth.getTime()*today.getTime()/9999 % 16) + 1;
				int b = (int) ((Math.random() ) *16) + 1;
				for(int j=0;j<16;j++){
					if(b==(j+1)){
						Ball ball=blues.get(j);
						int value=ball.getValue();
						ball.setValue(++value);
						break;
					}
				}
				initEntity();
				
			}
			loopCount++;
			Collections.sort(reds,comparator);
			Collections.sort(blues,comparator);
			if(checkWant()){
				for(int i=0;i<6;i++){
					int index=reds.get(i).getIndex();
					System.out.print(index+" ,");
				}
				bb=blues.get(0).getIndex();
				System.out.println("++++"+blues.get(0).getIndex());
				
				break;
			}
		}
		
		System.exit(0);
	}
	public static boolean checkWant(){
			
//		String iWant="3,9,26";
		String iWant="";
		if (iWant != null && !iWant.equals("")) {
			String []iWantList=iWant.split(",");
			String result=",";
			for(int i=0;i<6;i++)
				result+=reds.get(i).getIndex()+",";
			for(String want:iWantList){
				if(!result.contains(","+want+",")){
					return false;
				}
			}
		}

		return checkBlueWant();
	}
	
	public static boolean checkBlueWant() {
		int want = 4;
		if (want != 0) {
			if (blues.get(0).getIndex() == want) {
				return true;
			}
		}
		return false;
	}
	
	
}
