import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.fakecamera.*;

public class ServerMonitor {
	// By convention, these bytes are always sent between lines
	// (CR = 13 = carriage return, LF = 10 = line feed)
	private static final byte[] CRLF = { 13, 10 };
	private final int IDLE = 0;
	private final int MOVIE = 1;
	private int currentMode = 0;
	private int myPort;
	private boolean detected;
	private boolean newPicture;
	private boolean newRequest;
	ServerSocket sock;
	Socket clientSocket;
	OutputStream os;
	InputStream is;


	private AxisM3006V myCamera;

	public ServerMonitor(int port) throws IOException {
		detected = false;
		newPicture = false;
		newRequest = false;
		myPort = port;
		myCamera = new AxisM3006V();
		myCamera.init();
		myCamera.setProxy("argus-1.student.lth.se", myPort);
		sock = new ServerSocket(myPort);

		
	}
	
	public synchronized void init() throws IOException{
		
		clientSocket = sock.accept();
		os = clientSocket.getOutputStream();
		is = clientSocket.getInputStream();
	}

	public synchronized void handleRequest() throws IOException {
		// String header;
		// String mode;
		// header = getLine(is);
		// mode = getLine(is);
	
		
		
		while (newPicture) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		String request = getLine(is);
		String temp = getLine(is);
		System.out.println(request);
		System.out.println(temp);
		
		System.out.println("IP1");
		System.out.println(newPicture);
		
		System.out.println("IP2");
		if (request.substring(0, 4).equals("GET ")) {
			// Means 'end of header'

			System.out.println("IP3");
			newPicture = true;
			newRequest = false;
		}


		notifyAll();

	}

	public synchronized void sendImage() throws IOException {



		
		System.out.println("OP1");
		while (!newPicture) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];


		System.out.println("OP2");
		int len = myCamera.getJPEG(jpeg, 0);

		putLine(os, "HTTP/1.0 200 OK");
		putLine(os, "Content-Type: image/jpeg");
		putLine(os, "Pragma: no-cache");
		putLine(os, "Cache-Control: no-cache");
		putLine(os, "");
		
		String temp = "" + len;
		System.out.println(temp);
		putLine(os, temp);
		
		os.write(jpeg, 0, len);
		newPicture = false;
		

		notifyAll();

	}

	public int getMode() {
		return currentMode;
	}

	/**
	 * Read a line from InputStream 's', terminated by CRLF. The CRLF is not
	 * included in the returned string.
	 */
	private static String getLine(InputStream s) throws IOException {
		boolean done = false;
		String result = "";
		while (!done) {
			int ch = s.read(); // Read
			if (ch <= 0 || ch == 10) {
				// Something < 0 means end of data (closed socket)
				// ASCII 10 (line feed) means end of line
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}
		return result;
	}

	/**
	 * Send a line on OutputStream 's', terminated by CRLF. The CRLF should not
	 * be included in the string str.
	 */
	private static void putLine(OutputStream s, String str) throws IOException {
		s.write(str.getBytes());
		s.write(CRLF);
	}
}
