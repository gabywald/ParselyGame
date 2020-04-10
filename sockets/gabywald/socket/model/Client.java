package gabywald.socket.model;

import gabywald.global.json.JSONException;
import gabywald.global.json.JSONObject;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015, 2020)
 */
public class Client extends Observable implements Runnable {
	private String hostname;
	private int port;
	private ConcurrentLinkedQueue<String> inn;
	private JSONObject out;
	
	private Socket socket;
	
	public Client(String host, int atta) {
		this.hostname	= host;
		this.port		= atta;
		this.inn		= new ConcurrentLinkedQueue<String>();
		this.out		= new JSONObject();
		this.socket		= new Socket();
		/** this.br		= new BufferedReader(new InputStreamReader(System.in)); */
		// this.inputOutput("This is a test !");
		// this.input("This is a test !");
		// this.addObserver(new ClientObserver());
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Création client [" + this.hostname + ":" + this.port + "]");
	}
	
	private void changeAndNotify(JSONObject output) {
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "CLIENT RECEIVE (2): ///" + output + "///");
		
		this.out = output;
		this.setChanged();
		this.notifyObservers();
	}
	
	private void changeAndNotify(String output) {
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "CLIENT RECEIVE (1): ///" + output + "///");
		String insider = output; 
		
		JSONObject tmpJSONobj;
		try {
			tmpJSONobj = new JSONObject( insider );
		} catch (JSONException e) {
			
			Logger.printlnLog(LoggerLevel.LL_ERROR, e.getMessage() + "\n" + insider);
			// Logger.printlnLog(LoggerLevel.LL_ERROR, insider);
			
			tmpJSONobj = new JSONObject();
			tmpJSONobj.put( "error", "Received JSON could not be interpreted !" );
		}
		// tmpJSONobj.put("chgmsgclie", output);
		this.changeAndNotify(tmpJSONobj);
	}
	
	public void finalize() { 
		try { this.socket.close(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void input(String txt) {
		this.inn.add(txt);
		Logger.printlnLog(LoggerLevel.LL_DEBUG, txt + " => " + this.inn.peek());
	}
	
	public String getOutput() { 
		this.out.put("src", "[" + this.hostname + ":" + this.port + "]");
		return this.out.toJSON(); 
	}

//	/**
//	 * 
//	 * @param txt
//	 * @deprecated !!
//	 */
//	public void inputOutput(String txt) {
//		this.inn = txt;
//		try {
//			this.sos.write(this.inn.getBytes());
//			this.sis = this.socket.getInputStream();
//			int available = this.sis.available();
//			while (available == 0)
//				{ available = this.sis.available(); }
//			byte[] readbuffer	= new byte[available];
//			this.sis.read(readbuffer);
//			this.changeAndNotify(new String(readbuffer));
//		} catch (IOException e) {
//			// e.printStackTrace();
//			this.changeAndNotify("Transmission error (client:inputOutput) !");
//		}
//		/** this.output(); */
//		if (!"quit".equalsIgnoreCase(txt) 
//				&& !"shutdown".equalsIgnoreCase(txt)) 
//			{ this.finalize();System.exit(0); }
//	}

	public void start() {
		Thread thrThis = new Thread(this);
		thrThis.start();
	}
	
	public void stop() {
		this.inn.add("exit");
	}
	
	public void run() {
		try {
			this.socket				= new Socket();
			SocketAddress sockAddr	= new InetSocketAddress(this.hostname, this.port);
			this.socket.connect(sockAddr);
			InputStream sis			= this.socket.getInputStream();
			OutputStream sos		= this.socket.getOutputStream();
			byte[] readbuffer		= null;
			boolean exit			= false;
			do {
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, this.inn.size() + "*");
				
				while ( (this.inn == null) || (this.inn.size() == 0) ) 
					{ ; }
				if ( (this.inn != null) && (this.inn.size() != 0) ) {
					
					String data = this.inn.poll();
					
					Logger.printlnLog(LoggerLevel.LL_DEBUG, "data: ---" + data + "---");
					
					sos.write(data.getBytes());
					int available	= sis.available();
					while (available == 0)
						{ available = sis.available(); }
					readbuffer		= new byte[available];
					sis.read(readbuffer);
					this.changeAndNotify(new String(readbuffer));
					exit = ("quit".equalsIgnoreCase(data) 
							|| "shutdown".equalsIgnoreCase(data));
				} // END "if (!this.inn.equals(""))"
			} while ( ! exit );
			this.socket.close();
			//this.finalize();System.exit(0);
		} catch (IOException e)			{ 
			// e.printStackTrace();
			this.out.put("error", "Transmission error (client:input) !");
			this.changeAndNotify(this.out);
		}
		
		this.finalize();System.exit(0);
	}
}