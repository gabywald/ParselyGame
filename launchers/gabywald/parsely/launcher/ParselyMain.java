package gabywald.parsely.launcher;

import gabywald.socket.parsely.ParselyGame;
import gabywald.utilities.argument.Argument;
import gabywald.utilities.argument.ArgumentParser;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Main Class
 * @author chandesris
 */
public class ParselyMain {

	public static void main(String[] args) {

		ArgumentParser parser	= new ArgumentParser();
		
		String abbrevParsely	= "parsely|p";
		Argument parsely		= new Argument("parsely", "To launch Parsely Game...", abbrevParsely.split("|"));
		parsely.setHidden(true);
		parser.addArgument(parsely);
		
		boolean start	= parser.parseArguments(args);
		
		if (!start)		{ return; }
		
		if (parsely.isPresent()) { 
			Logger.printlnLog(LoggerLevel.LL_INFO, "Launching parsely...");
			/** ParselyGame pg = */ParselyGame.getInstance();
			// printlnLog(LoggerLevel.LL_INFO, "... LAUNCHED !");
		} else {
			ParselyGame.getInstance();
		}

	}

}
