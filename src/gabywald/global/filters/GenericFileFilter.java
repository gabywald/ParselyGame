package gabywald.global.filters;

import gabywald.global.data.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;


public /*abstract*/ class GenericFileFilter extends FileFilter {
	private List<String> extensions;
	
	public GenericFileFilter(String anExtension) 
		{ this.init();this.extensions.add(anExtension); }
	
	public GenericFileFilter(String[] extensions) {
		this.init();
		for (int i = 0 ; i < extensions.length ; i++)
			 { this.extensions.add(extensions[i]); }
	}
	
	public GenericFileFilter(List<String> extensions) 
		{ this.init();this.extensions.addAll(extensions); }
	
	private void init() 
		{ this.extensions = new ArrayList<String>(); }
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory())	{ return true; }

		String extension = Utils.getExtension(f);
		if (extension != null) {
			boolean isPresent = false;
			for (int i = 0 ; (i < this.extensions.size()) 
								&& (!isPresent) ; i++) 
				{ isPresent = extension.equals(this.extensions.get(i)); }
			
			if (isPresent)	{ return true; } 
			else			{ return false; }
		}
		return false;
	}
	
	public String toString() {
		String toReturn = new String("");
		for (int i = 0 ; i < this.extensions.size() ; i++) 
			{ toReturn += this.extensions.get(i)+"\t"; }
		return toReturn;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
