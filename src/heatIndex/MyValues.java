package heatIndex;

public class MyValues {
	public static double myHeatIndex;
	public static String myTime;
	public static String myRiskLevel;
	public static String myLanguage;
	
	public MyValues() {
		myHeatIndex = 0;
		myTime = "";
		myRiskLevel = "";
		myLanguage = "English";
	}
	
	public static void setMyValues(double val){
		myHeatIndex = val;		
	}
	
	public static double getMyValues(){
		return myHeatIndex;		
	}
	
	public static void setMyTime(String val){
		myTime = val;		
	}
	
	public static String getMyTime(){
		return myTime;		
	}
	
	public static void setMyRiskLevel(String val){
		myRiskLevel = val;		
	}
	
	public static String getMyRiskLevel(){
		return myRiskLevel;		
	}
	
	public static String getMyLanguage(){
		return myLanguage;
	}
	
	public static void setMyLanguage(String val){
		myLanguage = val;
	}
	
	public static void resetMyValues() {
		myHeatIndex = 0;
		myTime = "";
		myRiskLevel = "";
	}
}
