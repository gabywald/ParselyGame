package gabywald.socket.model;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015)
 */
public abstract class GenericObserver implements Observer {
	@Override
	public abstract void update(Observable observable, Object object);
}
