package gabywald.socket.parsely.v2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import gabywald.global.json.JSONException;
import gabywald.global.json.JSONObject;
import gabywald.socket.model.DaemonGenericClientThread;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class DCTParsely extends DaemonGenericClientThread {
	
	private ParselyGameStarter instance;

	public DCTParsely(Socket s, DaemonParsely mother) {
		super(s, mother);
		this.instance	= ParselyGameStarter.getInstance();
	}
	
	public void run() {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonGenericClientThread.run(); BEGIN");
		try {
			String clientAddr	= this.soc.getInetAddress().getHostAddress();
			
			this.changeAndNotify("Connexion a partir de: "+clientAddr);
			
			InputStream cis		= this.soc.getInputStream();
			OutputStream cos	= this.soc.getOutputStream();
			
			JSONObject firstOutput = new JSONObject();
			firstOutput.put("toclient", this.instance.getContentToShow());
			cos.write(firstOutput.toJSON().getBytes());
			cos.flush();
			
			byte[] readBuffer	= null;
			do {
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t data awaiting...");
				
				// Alternate awaiting data...
				byte[] b1		= new byte[1];
				int nbRead		= cis.read(b1);
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t nbRead: (" + nbRead + ")");
				
				if (nbRead <= 0) 
					{ Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t break !");break; }
				int available	= cis.available();
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t available: (" + available + ")");

				readBuffer		= new byte[available + 1];
				readBuffer[0]	= b1[0];
				if (available > 0) {
					cis.read(readBuffer, 1, available);
				}
				this.inn		= new String(readBuffer);
				
				// ***** ***** ***** ***** ***** 
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "this.inn: //*" + this.inn + "*//");
				
				this.changeAndNotify(this.soc+" a envoyé : '"+this.inn+"'");
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t init treatment...");
				JSONObject toSendToClient = this.treatment();
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t end treatment...");
				// toSendToClient.put("returning", "Bien reçu : ["+this.inn+"]");
				cos.write(toSendToClient.toJSON().getBytes());
				cos.flush();
			} while ( (!"exit".equalsIgnoreCase(this.inn)) 
							&& (!"shutdown".equalsIgnoreCase(this.inn)) );
			if ("exit".equalsIgnoreCase(this.inn)) {
				cos.close();
				this.changeAndNotify("Connexion fermée: "+this.soc);
			} // END "if ("quit".equalsIgnoreCase(this.inn))"s
			if ("shutdown".equalsIgnoreCase(this.inn)) {
				this.changeAndNotify("Serveur arrêté. ");
				System.exit(0);
			} // END "if ("shutdown".equalsIgnoreCase(this.inn))"
		} catch (IOException e) { e.printStackTrace(); }
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonClientThread.run(); END");
	}
	
	@Override
	public JSONObject treatment() {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonClientThread.treatment()...");
		String toReturn				= "... treament ...";
		
		String toTreat				= null;
		try {
			JSONObject received		= new JSONObject(this.inn);
			toTreat					= received.get("query").toString();
		} catch (JSONException e) {
			Logger.printlnLog(LoggerLevel.LL_WARNING, e.getMessage());
			Logger.printlnLog(LoggerLevel.LL_WARNING, this.inn);
			// e.printStackTrace();
		}
		
		JSONObject outputJSONobj	= new JSONObject();
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "toTreat: {" + toTreat + "}");
		
		this.instance.nextStep(toTreat);
		toReturn = this.instance.getContentToShow();
		
		outputJSONobj.put("toclient", toReturn);
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "TO THE CLIENT: ///" + outputJSONobj.toJSON() + "///");
		
		return outputJSONobj;
	}

}
