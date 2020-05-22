package gabywald.socket.parsely.v2;

import java.io.BufferedReader;

import gabywald.global.json.JSONObject;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.InputStreamReader;
import java.io.IOException;

public class ParselyGame {
	
	private ClientParsely client	= null;
	
	private boolean exit			= false;

	public ParselyGame(String host, Integer port) {
		
		// ***** Server Part !!
		DaemonParsely dp	= new DaemonParsely(port.intValue());
		dp.start();
		Logger.printlnLog(LoggerLevel.LL_DEBUG, dp.getOutput());
		
		// ***** Client Part !!
		ClientParsely cp			= new ClientParsely(host, port);
		ClientParselyObserver co	= new ClientParselyObserver();
		cp.addObserver(co);
		cp.start();
		
		this.client = cp;
		
//		cp.input("{ \"query\" : \"commande1\" }");
//		this.client.input("commande2");
//		JSONObject toSend = new JSONObject();
//		toSend.put("query", "commande3");
//		this.client.input( toSend.toJSON() );

	}
	
	public void execution() {
		
		this.exit = this.sendInput( "start" );
		
		do {
			/** Read a line ! */
			String readedLine				= null;
			try {
				InputStreamReader reader	= new InputStreamReader(System.in);
				BufferedReader input		= new BufferedReader(reader);
				readedLine					= input.readLine();
			} catch (IOException e) { readedLine = new String(""); }
			
			if (readedLine.equals("")) { ; }
			else 
				{ this.exit = this.sendInput(readedLine); }
		} while( ! this.exit);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "Good bye !");
	}
	
	private boolean sendInput(String line) {
		JSONObject toSend = new JSONObject();
		toSend.put("query", line);
		this.client.input(toSend.toString());
		return (line.equals("quit") || line.equals("shutdown"));
	}
	
}
