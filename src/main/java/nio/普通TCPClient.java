package nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.funshion.commons.utils.task.AbstractTask.TaskAdapter;
import com.funshion.commons.utils.task.TaskExcepion;
import com.funshion.commons.utils.task.TaskExecutor;

public class 普通TCPClient {
	public static void main(String []args) {
		for (final String name : new String[]{"xxx", "yyy"}) {
			TaskExecutor.execut(new TaskAdapter() {
				@Override
				public void execute() throws TaskExcepion {
					for (int i = 0; i < 10; i++) {
						try {
				            Socket s=new Socket(InetAddress.getByName(null),6000);//"localhost" "127.0.0.1s"
				            OutputStream os=s.getOutputStream();
				            InputStream is=s.getInputStream();
				            byte []buf=new byte[100];
				            int len=is.read(buf);
				            System.out.println(new String(buf,0,len));  
				            os.write(("Hello,this is " + name + i).getBytes());  
				            Thread.sleep(100);
				            os.close();
				            is.close();
				            s.close();
				        } catch (Exception e) {  
				            e.printStackTrace();  
				        }  
					}
					
					
				}
			});
		}
		
        
    }
}
