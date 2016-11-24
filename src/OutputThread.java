import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
public class OutputThread extends Thread{
	ServerMonitor mon;

	public OutputThread(ServerMonitor mon){

		this.mon = mon;

	}
	public void run(){
		while(true){
//			int mode = mon.getMode();
			try {
				
				mon.sendImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}