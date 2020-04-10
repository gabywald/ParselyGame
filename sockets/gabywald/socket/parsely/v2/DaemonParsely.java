package gabywald.socket.parsely.v2;

import java.io.IOException;
import java.net.Socket;

import gabywald.socket.model.DaemonGeneric;

public class DaemonParsely extends DaemonGeneric<DCTParsely> {

	public DaemonParsely(int atta) {
		super(atta);
	}

	public void run() {
		DCTParsely newDaemonClient	= null;
		try {
			do {
				Socket client		= this.server.accept();
				newDaemonClient		= new DCTParsely(client, this);
				this.addClient(newDaemonClient);
				newDaemonClient.start(); // this.getLastClient().start();
			} while(this.running);
		} catch (IOException e) {
			/** e.printStackTrace(); */
			this.out = new String("Transmission error (daemon:run) !");
			this.running = false;
		} finally {
			if (newDaemonClient != null) {
				this.remClient(newDaemonClient);
			}
		}
	}

}
