package heatIndex;

public class calIndex{	
	public static double heatIndexCal(double F, double rh) {
		double Hindex;
		
	    Hindex = -42.379 + 2.04901523*F + 10.14333127*rh;
	    Hindex = Hindex - 0.22475541*F*rh - 6.83783*getPowNeg(10,-3)*F*F;
	    Hindex = Hindex	- 5.481717*getPowNeg(10,-2)*rh*rh;
	    Hindex = Hindex	+ 1.22874*getPowNeg(10,-3)*F*F*rh ;
	    Hindex = Hindex	+ 8.5282*getPowNeg(10,-4)*F*rh*rh ;
	    Hindex = Hindex	- 1.99*getPowNeg(10,-6)*F*F*rh*rh;    	   
	    
	    Hindex = roundOff(Hindex);    
	
	    return Hindex;	
	}
	
	//utility functions
	public static double  roundOff(double value)
	{   		
		value = roundDouble(value);
		return value;
	}
	
	
	//sample pow(10,-2)
	public static double getPowNeg(int x, int y)
	{	  				
		    y = y * (-1); // make exponent positive
		    double result = (double) 1/powCalcInt(x,y);
			return result;	 
	}
	
	//sample pow(10,2)
	public static int powCalcInt( int x, int y) 
	/*we define the power method with         base x and power y (i.e., x^y)*/ 
	{   int z = x;      
		for( int i = 1; i < y; i++ )
			z *= x;      
		return z; 
	}  		
   
   public static double roundDouble(double d)
	{
	 //round to 1 decimal point
	   double number = (double)(int)((d+0.05)*10.0)/10.0;
	   return number;	  
	}

}
