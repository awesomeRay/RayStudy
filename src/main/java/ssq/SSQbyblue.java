package ssq;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class SSQbyblue {
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
	static int [] loopArray={12,22,06,05,7,18};
	static int loopCount=0;
	static int redCount=33;
	
	SSQbyblue(){
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
				//if(checkWant()){
					for(int i=0;i<6;i++){
						int index=reds.get(i).getIndex();
						System.out.print(index+" ,");
					}
					bb=blues.get(0).getIndex();
					System.out.println("++++"+blues.get(0).getIndex());
					
					break;
				//}
			}
			if(bb==16){
				break;
			}
		}
		
		System.exit(0);
	}
	public static boolean checkWant(){
		String iWant="8,22,28";
		String []iWantList=iWant.split(",");
		String result=",";
		for(int i=0;i<6;i++)
			result+=reds.get(i).getIndex()+",";
		for(String want:iWantList){
			if(!result.contains(","+want+",")){
				return false;
			}
		}
		
		return true;
	}
	
	
	
}
/*	     params                   assess                        result
 * 03,14,17,23,28,29 +14����
 * 03,14,17,23,28,29 +14      2 ,25 ,28 ,5 ,6 ,8 ,++++5
 * 03 10 11 13 14 22 +09      21 ,24 ,30 ,1 ,4 ,5 ,++++2
 * 03,14,17,23,28,29 +09      18 ,20 ,11 ,21 ,23 ,32 ,++++10
 * 04 10 11 14 22 23:09
 * ------------------
 *  04,10,11,14,22,23:09 (���˶�)     5 ,9 ,30 ,32 ,33 ,1 ,++++3
 *  03,14,17,23,28,29 +14            27 ,11 ,31 ,2 ,4 ,5 ,++++2
 *  02,05 ,06,08,25,28:05            3 ,33 ,1 ,5 ,8 ,15 ,++++7
 *  -------------
 *  01,03,05,8,15,33:07              31 ,2 ,6 ,11 ,1 ,3 ,++++8
 *  04, 09, 16, 17, 20, 24:07        6 ,7 ,11 ,29 ,30 ,8 ,++++5
 *  03,14,17,23,28,29 +14            31 ,6 ,20 ,1 ,4 ,12 ,++++1
 *  8 ,1 ,6 ,20 ,24 ,7 ,++++6   7 ,8 ,20 ,5 ,6 ,9 ,++++12 24 ,25 ,8 ,9 ,10 ,12 ,++++2
 */
