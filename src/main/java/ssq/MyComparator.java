package ssq;
import java.util.Comparator;

public class MyComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		Ball ball1=(Ball)arg0;
		Ball ball2=(Ball)arg1;
		return ball2.getValue()-ball1.getValue();
		
	}
}
