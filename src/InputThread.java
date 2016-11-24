import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class InputThread extends Thread {
	// By convention, these bytes are always sent between lines
	// (CR = 13 = carriage return, LF = 10 = line feed)
	private static final byte[] CRLF      = { 13, 10 };

	ServerMonitor mon;
	public InputThread(ServerMonitor mon) {

		this.mon = mon;
	}
	public void run() {
		
		try {
			mon.init();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				
				mon.handleRequest();
				//här ska vi nog skicka till monitorn
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}