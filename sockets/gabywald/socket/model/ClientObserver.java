package gabywald.socket.model;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.util.Observable;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015)
 */
public class ClientObserver extends GenericObserver {
	@Override
	public void update(Observable observable, Object object) {
		Client client = (Client)observable;
		Logger.printlnLog(LoggerLevel.LL_FORUSER, client.getOutput());
		Logger.printLog(LoggerLevel.LL_FORUSER, "$");
	}
}
