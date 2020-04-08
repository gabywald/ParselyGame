package gabywald.global.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * This class to manipulate files. 
 * @author Gabriel Chandesris (2011)
 * TODO adding check of validity
 */
public class Fichier {
	/** Content of file (text format). */
	private Vector<String> chaine;
	/** Path and name of file. */
	private String fichier;
	/** Name of file. */
	private String name;
	/** Path of file. */
	private String path;
	
	/**
	 * Default constructor with given name of file. <br>
	 * Automatically read the file. 
	 * @param name (String)
	 */
	public Fichier(String name) {
		this.fichier			= name;
		
		String fileSeparator	= System.getProperty("file.separator");
		String theUnixSeparator	= "/";
		int locaCut				= this.fichier.lastIndexOf(fileSeparator);
		int unixCut				= this.fichier.lastIndexOf(theUnixSeparator);
		/* System.out.println("\t*****"+fileSeparator+"*****"+locaCut+"*****"+unixCut); */
		if (locaCut == -1) { locaCut = unixCut; }
		
		this.name				= this.fichier.substring(locaCut+1);
		this.path				= this.fichier.substring(0, locaCut);
		
		this.chaine				= new Vector<String>(0);
		this.read();
	}
	
	public boolean hasError() {
		if (this.getLine(0).matches("ERROR(.*)")) {
			System.out.println(this.getLine(0));
			return true;
		}
		return false;
	}
	
	/**  Method to put content of file in <i>chaine</i>. */
	public void read() {
		try {
			InputStream ips			= new FileInputStream(this.fichier);
			InputStreamReader ipsr	= new InputStreamReader(ips);
			BufferedReader br		= new BufferedReader(ipsr);
			String line;
			while ((line = br.readLine()) != null) { 
				if (line.endsWith("\n")) { line = line.substring(0, line.length()-1); } /** chomp */
				this.chaine.add(line); 
			} /** END "while ((line = br.readLine()) != null)" */
			br.close(); 
		} 
		catch (FileNotFoundException e) 
			{ this.chaine.add("ERROR : File not found '"+this.fichier+"'"); } 
		catch (IOException e) 
			{ this.chaine.add("ERROR : IO '"+this.fichier+"'"); } 
	}
	
	/** Method to write the content of file. */
	public void write() {
		try {
			FileWriter fw				= new FileWriter(this.fichier);
			BufferedWriter bw			= new BufferedWriter (fw);
			PrintWriter fichierSortie	= new PrintWriter (bw); 
			fichierSortie.print(this.getChaine()); 
			fichierSortie.close();
		} 
		catch (IOException e)			{ e.printStackTrace(); }
		catch (NoClassDefFoundError e)	
			{ System.out.println("AppEngine Reaction to Fichier.write()"); }
	}
	
	public void delete() {
		File rm = new File(this.fichier);
		rm.delete();
	}
	
	/** 
	 * Add a String at end of <i>chaine</i>. 
	 * @param line (String)
	 */
	public void addLine(String line) { this.chaine.add(line); }
	
	public void addString(String content) {
		String[] more = content.split("\n");
		this.chaine.ensureCapacity(this.chaine.size()+more.length);
		for (int i = 0 ; i < more.length ; i++) 
			{ this.chaine.add(more[i]); }
	}
	
	public void setLine(int i, String line) {
		if ( (i >= 0) && (i < this.chaine.size()) ) 
			{ this.chaine.set(i, line); }
	}
	
	public void empty() { this.chaine = new Vector<String>(0); }
	
	public String getLine(int i)  	{ 
		if ( (i >= 0) && (i < this.chaine.size()) )
			{ return this.chaine.get(i); } 
		else { return null; }
	}
	public int getNbLines()			{ return this.chaine.size(); }
	
	public String toString()
		{ return new String("\t"+this.fichier+"\n")+this.getChaine(); }
	
	public boolean equals(Fichier toCompare) {
		/** TODO comparaison du contenu... */
		if (!this.chaine.equals(toCompare.getChaine()))
			{ return false; }	// System.out.println("Same content...");
		if (!this.fichier.equals(toCompare.getFichier()))
			{ return false; }
		if (!this.name.equals(toCompare.getName()))
			{ return false; }
		if (!this.path.equals(toCompare.getPath()))
			{ return false; }
		return true;
	}

	/**
	 * Method to get <i>chaine</i> in String format, each line separated by '\n'. 
	 * @return (String)
	 */
	public String getChaine()	{
		String result = new String("");
		for (int i = 0 ; i < this.chaine.size() ; i++) 
			{ result += this.chaine.elementAt(i)+"\n"; }
		return result;
	}
	
	/**
	 * Method to get <i>chaine</i> in String format, in a table. 
	 * @return (String)
	 */
	public String[] getTable() {
		String table[] = new String[this.chaine.size()];
		for (int i = 0 ; i < this.chaine.size() ; i++) 
			{ table[i] = this.chaine.elementAt(i); }
		return table;
	}

	/**  
	 * Method to get the (path+name) access to the file.
	 * @return (String)
	 */
	public String getFichier()				{ return this.fichier; }
	public String getName()					{ return this.name; }
	public String getPath()					{ return this.path; }
	
	/**
	 * To change path and name of the current instance of file. 
	 * @param fichier (String)
	 */
	public void setFichier(String fichier)	{ 
		this.fichier	= fichier;
		this.name		= this.fichier.substring(this.fichier.lastIndexOf("/")+1);
	}
	
}


