package gabywald.parsely.launcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gabywald.socket.parsely.ParselyGame;
import gabywald.utilities.argument.Argument;
import gabywald.utilities.argument.ArgumentParser;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Main Class
 * @author Gabriel Chandesris
 */
public class ParselyMain {

	public static final String CONFIG_SOCKET_PROPERTIES = "resources/datas/ClientDaemon.properties";

	public static void main(String[] args) {

		ArgumentParser parser	= new ArgumentParser();

		String abbrevLocal	= "local|l";
		Argument local		= new Argument("parsely", "To launch Parsely Game in 'local mode'...", 
				abbrevLocal.split("|"));
		// local.setHidden(true);
		parser.addArgument(local);

		boolean start	= parser.parseArguments(args);

		if (!start)		{ return; }

		if (local.isPresent()) {
			ParselyGame.getInstance();
		} else {

			String host		= null;
			Integer port	= null;

			// TODO change to make properties file loadable from a JAR 
			try (InputStream input = new FileInputStream(ParselyMain.CONFIG_SOCKET_PROPERTIES)) {

				Properties prop = new Properties();
				prop.load(input);
				// System.out.println(prop.getProperty("server.host"));
				// System.out.println(prop.getProperty("server.port"));

				host = prop.getProperty("server.host");
				port = Integer.valueOf(prop.getProperty("server.port"));

			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}

			if ( (host != null) && (port != null) ) {

				// Logger.setLogLevel(LoggerLevel.LL_DEBUG);

				gabywald.socket.parsely.v2.ParselyGame pg = 
						new gabywald.socket.parsely.v2.ParselyGame(host, port);

				Logger.printlnLog(LoggerLevel.LL_INFO, "Launching parsely...");
				pg.execution();
				Logger.printlnLog(LoggerLevel.LL_INFO, "... LAUNCHED !");
			}

		}

	}

}
