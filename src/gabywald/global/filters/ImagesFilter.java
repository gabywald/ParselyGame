package gabywald.global.filters;

import gabywald.global.data.Utils;

public class ImagesFilter extends GenericFileFilter {

	public ImagesFilter() 
		{ super(new String[] { Utils.gif, Utils.jpeg, Utils.jpg,  
								Utils.tif, Utils.tiff, Utils.png}); }
	
	@Override
	public String getDescription() 
		{ return "CellML Files"; }

}
