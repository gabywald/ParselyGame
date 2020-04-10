package gabywald.socket.model;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.Observable;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015, 2020)
 */
public class DaemonClientThreadObserver extends GenericObserver {
	@Override
	public void update(Observable observable, Object object) {
		DaemonClientThread dct = (DaemonClientThread)observable;
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "SERVERSIDE:" + dct.getOutput());
	}
}
