package gabywald.socket.model;

import gabywald.global.json.JSONObject;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.net.Socket;
import java.util.Observable;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015, 2020)
 */
public abstract class DaemonGenericClientThread extends Observable implements Runnable {
	protected DaemonGeneric<?> mot;
	protected Socket soc;
	protected String inn;
	private JSONObject out;
	// private boolean show;

	public DaemonGenericClientThread(Socket s, DaemonGeneric<?> mother)	{
		this.mot	= mother;
		this.soc	= s;
		this.inn	= new String();
		this.out	= new JSONObject();
		// this.show	= true;
		this.addObserver(new DaemonGenericClientThreadObserver());
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Création DaemonClientThread [" + this.soc + ":" + this.mot + "]");
	}
	
	protected void changeAndNotify(JSONObject output) {
		this.out = output;
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t output.toString() --" + output.toString() + "--");
		this.setChanged();
		this.notifyObservers();
	}
	
	protected void changeAndNotify(String output) {
		JSONObject tmpJSONobj = new JSONObject();
		tmpJSONobj.put("chgmsgdaem", output);
		this.changeAndNotify(tmpJSONobj);
	}
	
//	private void changeAndNotifyError(String output) {
//		this.out.put("error", output);
//		this.changeAndNotify(this.out);
//	}
	
	public void start() {
		Thread thrThis = new Thread(this);
		thrThis.start();
	}
	
	public void stop() {
		this.inn = "exit";
	}
	
	public String getOutput() { 
		this.out.put("src", "["+this.getPort()+"]");
		return this.out.toJSON(); 
	}
	
	public String getHost()		
		{ return this.soc.getInetAddress().getHostAddress(); }
	
	public String getPort()		
		{ return this.soc.getPort()+""; }
	
	/**
	 * This method HAS TO be overrided by inheritant classes !
	 */
	public abstract JSONObject treatment();
	
	protected DaemonGeneric<?> getParent() 
		{ return this.mot; }
}
