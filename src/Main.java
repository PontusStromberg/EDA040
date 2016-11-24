import java.io.IOException;

import skeleton.client.JPEGHTTPClient;
//import skeleton.demo.JPEGHTTPDemo.Client;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
	

	
		
		ServerMonitor mon = new ServerMonitor(6078);
		
		InputThread it = new InputThread(mon);
		OutputThread ot = new OutputThread(mon);

		ot.start();
		it.start();

		
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Client2 c = new Client2();
		c.start();



		
		
	}
	
	private static class Client2 extends Thread {
		public void run() {
			JPEGHTTPClient.main(new String[] {"localhost", "6078"});
		}
	}
}



