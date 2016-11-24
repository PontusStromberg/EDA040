import skeleton.client.JPEGHTTPClient;

public class Client extends Thread {
	
	public Client(){
		
		
	}
	
		public void run() {
			JPEGHTTPClient.main(new String[] {"localhost", "6077"});
		}
	}
