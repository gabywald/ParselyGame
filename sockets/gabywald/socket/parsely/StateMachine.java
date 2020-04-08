package gabywald.socket.parsely;

/**
 * This abstract class defines a State Machine using a current type of object. 
 * @author Gabriel Chandesris (2013)
 */
public abstract class StateMachine<T extends Object> {
	private int state;
	private T currentObjectT;
	
	public StateMachine()	{ 
		this.state			= 0;
		this.currentObjectT	= null;
	}
	
	protected void setState(int newState)	{ this.state = newState; }
	protected int getState()				{ return this.state; }
	
	protected void setObject(T obj)			{ this.currentObjectT = obj; }
	protected T getObject()					{ return this.currentObjectT; }
	
	public abstract void nextStep(String command);
	public abstract String getContentToShow();
}
