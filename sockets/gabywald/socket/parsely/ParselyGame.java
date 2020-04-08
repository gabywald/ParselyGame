package gabywald.socket.parsely;

import java.io.BufferedReader;

// import gabywald.global.data.TextualInfoFile;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.InputStreamReader;
import java.io.IOException;

public class ParselyGame {

//	private int daemonServerPort;
//	private int clientPort;
//	private String clientHostname;
	
	private static ParselyGame instance;
	
	private ParselyGame() {
//		this.daemonServerPort	= Integer.parseInt(TextualInfoFile.getFrameDaemonContext().getValueOf("port"));
//		this.clientPort			= Integer.parseInt(TextualInfoFile.getFrameClientContext().getValueOf("port"));
//		this.clientHostname		= TextualInfoFile.getFrameClientContext().getValueOf("host");
//		
//		Logger.printlnLog(LoggerLevel.LL_INFO, "\t Daemon port : " + this.daemonServerPort);
//		Logger.printlnLog(LoggerLevel.LL_INFO, "\t Client port : " + this.clientPort);
//		Logger.printlnLog(LoggerLevel.LL_INFO, "\t Client host : " + this.clientHostname);
//		
//		Daemon parsely			= new Daemon(this.daemonServerPort);
//		parsely.start();
//		Logger.printlnLog(LoggerLevel.LL_FORUSER, "Server Started ! [" + parsely.getOutput() + "]");
//		
//		Client local			= new Client(this.clientHostname, this.clientPort);
//		local.start();
//		while(local.getOutput().equals("")) { ; }
//		String prevOutput = local.getOutput();
//		Logger.printlnLog(LoggerLevel.LL_FORUSER, "Client Started ! [" + prevOutput + "]");

		ParselyGameStarter instance = ParselyGameStarter.getInstance();
		String toShow = instance.getContentToShow();
		Logger.printlnLog(LoggerLevel.LL_FORUSER, toShow);
		
		boolean exit = false;
		do {
			Logger.printLog(LoggerLevel.LL_FORUSER, ">"); // '\b'
			/** Read a line ! */
			String readedLine				= null;
			try {
				InputStreamReader reader	= new InputStreamReader(System.in);
				BufferedReader input		= new BufferedReader(reader);
				readedLine					= input.readLine();
			} catch (IOException e) { readedLine = new String(""); }
			
			if (readedLine.equals("")) { ; }
			else {
//				Logger.printlnLog(LoggerLevel.LL_INFO, "to server : " + readedLine);
//				local.input(readedLine);
//				while(local.getOutput().equals(prevOutput)) { ; }
//				String reOutput = local.getOutput();prevOutput = reOutput;
//				Logger.printlnLog(LoggerLevel.LL_INFO, "from server : " + reOutput);
				
				instance.nextStep(readedLine);
				toShow = instance.getContentToShow();
				Logger.printlnLog(LoggerLevel.LL_FORUSER, toShow);
				
				// exit = (readedLine.equals("quit") || readedLine.equals("shutdown"));
			}
		} while( ! exit);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "Good bye !");
	}
	
	public static ParselyGame getInstance() { 
		if (ParselyGame.instance == null) 
			{ ParselyGame.instance = new ParselyGame(); }
		return ParselyGame.instance;
	}
}
