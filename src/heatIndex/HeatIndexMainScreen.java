package heatIndex;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.AutoCompleteField;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.util.StringProvider;
import net.rim.device.api.ui.component.TextField;


import net.rim.device.api.collection.util.BasicFilteredList;
import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.gps.*;
import net.rim.device.api.i18n.Locale;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.image.Image;
import net.rim.device.api.ui.image.ImageFactory;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;
//import net.rim.device.api.ui.AccessibleContext;
import net.rim.device.api.ui.accessibility.AccessibilityManager;

import javax.microedition.location.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.file.FileConnection;


import java.io.DataInputStream;

import net.rim.device.api.system.Characters;


import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.image.*;
//import net.rim.device.api.ui.toolbar.*;
import net.rim.device.api.util.*;
//import net.rim.device.api.system.*;
import net.rim.device.api.command.*;


public class HeatIndexMainScreen extends MainScreen {
	
	//geo location
	 private double _latitude;
	 private double _longitude;
	 private int _modeUsed;
	 private String _mode;
	    
    private BasicEditField edit_input_temp;
    private BasicEditField edit_input_humid;    
    
    private BasicEditField hi_result;
    private BasicEditField time_result;         
    private LabelField risk_result;  
    
    //private TextField errorNotify;  
    private String testIndex;
    private String myLattest;
    private String myLongtest;   
    
    
    private double HIdouble = 0;		
	
	private double valueTemp = 0;  //will be displayed in the two boxes for current and day max
	private double valueHumid = 0;
	
	private int buttonClicked = 1; // 1=current, 2=max	
	
	private String myMaxTime="";	 
	private int showTime = 0;	// 0 = not show time for manual entry, 1 = show time for current and max
	
	private String myRiskLevel="";	//risk level shown on the screen
	private double[] locData = new double[2];   
	
	
	//for accessibility
    private IconToolbarComponent _iconToolbar;    
    
    private int dataValid=1;    //check if the current and max temp lower than 80
	
    
    public HeatIndexMainScreen() {
    	super( MainScreen.NO_VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );   	  	
    	
    	 if(MyValues.getMyLanguage() == null ) {
    		 MyValues.setMyLanguage("English");
    	 }   	        
    	 
    	 
    	 //banner
    	 if(MyValues.getMyLanguage() == "English") {
             _iconToolbar = new IconToolbarComponent();
             Bitmap bitmap1 = Bitmap.getBitmapResource("banner.png");                 
            _iconToolbar.addIcon(bitmap1, " OSHA Heat Safety Tool ");
            add(_iconToolbar);  
            
         } else if(MyValues.getMyLanguage() == "Spanish") {
             _iconToolbar = new IconToolbarComponent();
             Bitmap bitmap1 = Bitmap.getBitmapResource("banner-sp.png");                 
            _iconToolbar.addIcon(bitmap1, " OSHA Seguridad en el Calor ");
            add(_iconToolbar);  
         }  
    	     
        String buttonMax = "";
        String buttonCurrent = "";
        if(MyValues.getMyLanguage() == "English") {
        	buttonCurrent = "    Get Current    ";
        	buttonMax = "    Get Today Max    ";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	buttonCurrent = "    Temp Actual    ";
        	buttonMax = "    Temp Máxima    ";
        }       
        
        VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLLBAR | Manager.VERTICAL_SCROLL | Manager.USE_ALL_HEIGHT);
        //get current/get max buttons
        HorizontalFieldManager hfm = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        ButtonField current_submit = new ButtonField(buttonCurrent, ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT) ;
        vfm.add(hfm); 
        hfm.add( current_submit );
        ButtonField max_submit = new ButtonField(buttonMax, ButtonField.CONSUME_CLICK | ButtonField.FIELD_RIGHT) ;
        hfm.add( max_submit );
        add(vfm);
        
        String manual1 = "";
        String manual2 = "";
        String manual3 = "";
        String manual4 = "";
        if(MyValues.getMyLanguage() == "English") {
        	manual1 = " Or Enter Numbers:";
            manual2 = " Temperature: ";
            manual3 = "       Humidity: ";
            manual4 = "Calculate";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	manual1 = " O Ingrese los Datos:";
            manual2 = " Temperatura: ";
            manual3 = "      Humedad: ";
            manual4 = "Calcular";
        }   
        
        vfm.add(new LabelField(manual1));
        
        SeparatorField sepfield = new SeparatorField(){
			protected void paint(Graphics g){									   
				g.setColor(Color.WHITE);						   
				g.clear();									
				super.paint(g);
				}
        		};
        	add( sepfield );

        HorizontalFieldManager hfm2 = new HorizontalFieldManager();
             
        edit_input_temp = new BasicEditField(manual2, "", 5, BasicEditField.FILTER_REAL_NUMERIC){
        public void paint( Graphics g ){                      
              //drawing a box around the TextField     
                    g.drawRect( this.getFont().getAdvance(this.getLabel()) - 2, 0, 84, 40 );
        	 		super.paint( g );
                    
              }          
             
        protected void layout( int width, int height ){
                    super.layout( this.getFont().getAdvance(this.getLabel()) +
                                4 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                    setExtent( this.getFont().getAdvance(this.getLabel()) +
                                4 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                    }       
              }; 
              
             
            	  LabelField myLabel1 = new LabelField("°F"){
                  public void paint( Graphics g ){                      
                        //drawing a box around the TextField     
                              g.drawRect( this.getFont().getAdvance("°F") - 2, 0, 0, this.getFont().getHeight() );
                              super.paint( g );               
                        }          
                       
                  protected void layout( int width, int height ){
                              super.layout( this.getFont().getAdvance("°F") +
                                          0 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                              setExtent( this.getFont().getAdvance("°F") +
                                          0 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                              }       
                        }; 
              
                        
                        hfm2.add( edit_input_temp );  
                        hfm2.add( myLabel1 );                       
                        vfm.add( hfm2 );
                        
                        
              HorizontalFieldManager hfm3 = new HorizontalFieldManager();
                   
              edit_input_humid = new BasicEditField(manual3, " ", 5, BasicEditField.FILTER_REAL_NUMERIC ){
                  public void paint( Graphics g ){                      
                        //drawing a box around the TextField 
                	  	g.drawRect( this.getFont().getAdvance(this.getLabel()) - 2, 0, 84, this.getFont().getHeight() );
                      
                	  super.paint( g );
                        }          
                       
                  protected void layout( int width, int height ){
                              super.layout( this.getFont().getAdvance(this.getLabel()) +
                                          4 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                              setExtent( this.getFont().getAdvance(this.getLabel()) +
                                          4 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                              }       
                        }; 

                        LabelField myLabel2 = new LabelField("%"){
                            public void paint( Graphics g ){                      
                                  //drawing a box around the TextField     
                                        g.drawRect( this.getFont().getAdvance("%") - 2, 0, 0, this.getFont().getHeight() );
                                        super.paint( g );               
                                  }          
                                 
                            protected void layout( int width, int height ){
                                        super.layout( this.getFont().getAdvance("%") +
                                                    0 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                                        setExtent( this.getFont().getAdvance("%") +
                                                    0 * this.getFont().getAdvance("W"), this.getFont().getHeight() );
                                        }       
                                  }; 
        
        hfm3.add( edit_input_humid );
        hfm3.add( myLabel2 );
        vfm.add( hfm3 );           
       

        ButtonField cmd_submit = new ButtonField( manual4, ButtonField.CONSUME_CLICK | ButtonField.FIELD_RIGHT );
        vfm.add( cmd_submit );  
        
        String result1 = "";
        String result2 = "";
        String result3 = "";
        if(MyValues.getMyLanguage() == "English") {
        	result1 = " Heat Index: ";
        	result2 = " Risk Level: ";
        	result3 = "  Precautions  ";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	result1 = " Índice de Calor: ";
        	result2 = " Nivel de Riesgo: ";
        	result3 = "  Precauciones  ";
        }   
        
        hi_result = new BasicEditField( result1, "", 100, BasicEditField.READONLY );
        vfm.add( hi_result );
        
        SeparatorField sepfield5 = new SeparatorField(){
			protected void paint(Graphics g){									   
				g.setColor(Color.WHITE);						   
				g.clear();									
				super.paint(g);
				}
        		};
        	add( sepfield5 );
        
        time_result = new BasicEditField( "", "", 100, BasicEditField.READONLY );
        vfm.add( time_result );
        
        SeparatorField sepfield6 = new SeparatorField(){
			protected void paint(Graphics g){									   
				g.setColor(Color.WHITE);						   
				g.clear();									
				super.paint(g);
				}
        		};
        	add( sepfield6 );        
       
        risk_result = new LabelField( result2 );
        vfm.add( risk_result );
        
        SeparatorField sepfield7 = new SeparatorField(){
			protected void paint(Graphics g){									   
				g.setColor(Color.WHITE);						   
				g.clear();									
				super.paint(g);
				}
        		};
        	add( sepfield7 );
        
        
        ButtonField precaution_submit = new ButtonField( result3, ButtonField.CONSUME_CLICK | ButtonField.FIELD_RIGHT );
        vfm.add( precaution_submit );  
        
        SeparatorField sepfield8 = new SeparatorField(){
			protected void paint(Graphics g){									   
				g.setColor(Color.WHITE);						   
				g.clear();									
				super.paint(g);
				}
        		};
        	vfm.add( sepfield8 );
        	
        SeparatorField sepfield9 = new SeparatorField(){
     			protected void paint(Graphics g){									   
     				g.setColor(Color.WHITE);						   
     				g.clear();									
     				super.paint(g);
     				}
             		};
             vfm.add( sepfield9 );      
        
        HorizontalFieldManager languages = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        ButtonField language_english = new ButtonField( "English", ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT );
        ButtonField language_spanish = new ButtonField( "Español", ButtonField.CONSUME_CLICK | ButtonField.FIELD_RIGHT );
        languages.add(language_english);
        languages.add(language_spanish);
        vfm.add(languages);
        
        language_english.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	//changetoEnglish();
            	MyValues.setMyLanguage("English");
            	UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
            	UiApplication.getUiApplication().pushScreen(new HeatIndexMainScreen());            	
            }      
        }       
        );
        
        language_spanish.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	//changetoSpanish();
            	MyValues.setMyLanguage("Spanish");
            	UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
            	UiApplication.getUiApplication().pushScreen(new HeatIndexMainScreen());
            }            
        });
        
      //gps dummy values
        locData[0]=0;
        locData[1]=0;
        
        findLocation();
        
        //errorNotify = new TextField();
        //vfm.add( errorNotify );     
        //errorNotify.setText("  main  " + testIndex +  String.valueOf(locData[0]) + "  " + String.valueOf(locData[1]));
        //errorNotify.setText("  main  " + testIndex);          
                    
        
        //manual data entry
        cmd_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	MyValues.resetMyValues();  //clean up the variables in MyValues object	
            	
            	showTime = 0;
            	
            	int errorFound = 0;
                String tempInput = edit_input_temp.getText();
                String humidInput = edit_input_humid.getText();
                
                double tempD=0;
                double humidD=0;               
                
                String modal1 = "";
                String modal2 = "";
                String modal3 = "";
                if(MyValues.getMyLanguage() == "English") {
                	modal1 = "Please enter both temperature and humidity to calculate.";
                    modal2 = "A heat index value can not be calculated for temperatures less than 80 degree Fahrenheit.";
                    modal3 = "A heat index value can not be calculated for relative humidity greater than 100%.";
                } else if(MyValues.getMyLanguage() == "Spanish") {
                	modal1 = "Ingrese un número en la casilla de temperatura y en la casilla de humedad relativa";
                    modal2 = "Un valor de índice de calor no se puede calcular para temperaturas de menos 80 grados Fahrenheit.";
                    modal3 = "Un valor de índice de calor no se puede calcular de la humedad relativa superior al 100%.";
                } 
                
                if (tempInput.equalsIgnoreCase("") || humidInput.equalsIgnoreCase("")){			
            		errorFound = 1;
					Dialog.alert(modal1);					 					
				}              
                
                if (errorFound ==0){
	                 tempD = Double.parseDouble(tempInput);
	                 humidD = Double.parseDouble(humidInput);   
	                
	            	if (tempD<80){			
	            		errorFound = 1;
						Dialog.alert(modal2);					 					
					}
	            	else {            	
		            	if (humidD>100){			
		            		errorFound = 1;
		            		Dialog.alert(modal3);					
						}
	            	}
                }
                
            	if (errorFound==0){
	                HIdouble = calIndex.heatIndexCal(tempD, humidD);  
	                myMaxTime="";	
	                setUIforResults(HIdouble);	   
            	}
            	else {
            		setUIforManualLow();
            	}
            }
        } );
        
        //get current 
        current_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {    
            	MyValues.resetMyValues();  //clean up the variables in MyValues object
            	buttonClicked=1;
    			int getGPS = 1;
    			dataValid = 1;
    			
    			//findLocation();
    			double myLongValue = locData[1];    					
    			
    			String modal4 = "";
                if(MyValues.getMyLanguage() == "English") {
                	modal4 = "Can not get your location information.";
                } else if(MyValues.getMyLanguage() == "Spanish") {
                	modal4 = "No se puede obtener su información de ubicación.";
                } 
                
                String modal5 = "";
                if(MyValues.getMyLanguage() == "English") {
                	modal5 = "The NOAA weather data are currently not available, please enter your temperature and relative humidity manually.";
                } else if(MyValues.getMyLanguage() == "Spanish") {
                	modal5 = "Los datos climáticos de NOAA no están disponibles en este momento, por favor ingrese manualmente su temperatura y nivel de humedad.";
                }                
    		
				if (myLongValue == 0){
					getGPS = 0;
					Dialog.alert(modal4);	
					
					setUIforCleanup();
				}
			    
				if (getGPS==1) {    			    			
	    			try { 
	    				setUIforCleanup();
	    				
	    				showTime = 1;
	    				myMaxTime="";  
	    				
	    				 //add alert to get current
	                    String modal6 = "";
	                    String modal7 = "";
	                    if(MyValues.getMyLanguage() == "English") {
	                        modal6 = "A heat index value can not be calculated for temperatures less than 80 degree Fahrenheit.";
	                        modal7 = "A heat index value can not be calculated for relative humidity greater than 100%.";
	                    } else if(MyValues.getMyLanguage() == "Spanish") {
	                    	modal6 = "Un valor del índice de calor no se puede calcular para temperaturas menores de 80 grados Fahrenheit.";
	                        modal7 = "Un valor de índice de calor no se puede calcular de la humedad relativa superior al 100%.";
	                    } 
	    				
	    				//GPS info
	    				String myLat = String.valueOf(locData[0]);
	    				String myLong = String.valueOf(locData[1]);	    				
	    				
	    				//hardcoded gas info for testing
	    			    //String myLat = "42.46";
	    				//String myLong = "-71.25";    
	    				
	    				myLattest= myLat;
	    				myLongtest = myLong;
	    				
	    				//build url   
	    				//	<Insert Web service call URL (Weather Web Service Call to NOAA Web Site). To use service, contact www.noaa.gov>
	    					    				
	    				double myValue;	    			
	    				
	    				myValue = getNOAAData(myUrl);   
	    				
	    				
	    				if (dataValid==0){
	    					Dialog.alert(modal6);
	    				}
	    				else {	    				
		    				if (myValue ==0){
		    					Dialog.alert(modal5);	
		    				}
		    				else {
			    				HIdouble = myValue;
			    		        
			    		        setUIforResults(HIdouble);	
		
			    		        //reset the input fields
			    		        Double myResult1 = new Double(valueTemp);
			    		        Double myResult2 = new Double(valueHumid);
			    		        edit_input_temp.setText(myResult1.toString());
			    				edit_input_humid.setText(myResult2.toString());		    				
		    				} 
	    				} 
	    				
	    				//clean up the screen for dataValid==0
	    				if (dataValid==0){
	    					setUIforCurrentMAXDataNotValid();
	    				}
	    				
	    	            	
	    				}
	    		        catch (Exception e) {	    		            
	    		    } 
				} //end of check GPS 
				//errorNotify.setText("  click current  " + testIndex +  String.valueOf(locData[0]) + "  " + String.valueOf(locData[1]));
	               
				//errorNotify.setText("  click current  " + testIndex);
				           
            }
        } );
        
        //get max
        max_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	MyValues.resetMyValues();  //clean up the variables in MyValues object
            	buttonClicked=2;
            	dataValid = 1;
            	
    			int getGPS = 1;
    			
    			//findLocation();    			 
    			double myLongValue = locData[1];    
				
				String modal4 = "";
                if(MyValues.getMyLanguage() == "English") {
                	modal4 = "Can not get your location information.";
                } else if(MyValues.getMyLanguage() == "Spanish") {
                	modal4 = "No se puede obtener su información de ubicación.";
                } 
                
                String modal5 = "";
                if(MyValues.getMyLanguage() == "English") {
                	modal5 = "The NOAA weather data are currently not available, please enter your temperature and relative humidity manually.";
                } else if(MyValues.getMyLanguage() == "Spanish") {
                	modal5 = "Los datos climáticos de NOAA no están disponibles en este momento, por favor ingrese manualmente su temperatura y nivel de humedad.";
                } 
    		
				if (myLongValue == 0){
					getGPS = 0;
					Dialog.alert(modal4);	
					setUIforCleanup();
				}
			    
				if (getGPS==1) {    			    			
	    			try { 
	    				setUIforCleanup();
	    				
	    				showTime = 1;
	    				myMaxTime="";  
	    				
	    				 //add alert to get max
	    				String modal6 = "";
	                    String modal7 = "";     
	                    if(MyValues.getMyLanguage() == "English") {
	                        modal6 = "A heat index value can not be calculated for temperatures less than 80 degree Fahrenheit.";
	                        modal7 = "A heat index value can not be calculated for relative humidity greater than 100%.";
	                    } else if(MyValues.getMyLanguage() == "Spanish") {
	                    	modal6 = "Un valor del índice de calor no se puede calcular para temperaturas menores de 80 grados Fahrenheit.";
	                        modal7 = "Un valor de índice de calor no se puede calcular de la humedad relativa superior al 100%.";
	                    } 
	                    
	    				
	    				//GPS info
	    				String myLat = String.valueOf(locData[0]);
	    				String myLong = String.valueOf(locData[1]);	    				
	    				
	    				//hardcoded gas info for testing
	    				//String myLat = "42.46";
	    				//String myLong = "-71.25";
	    				
	    				myLattest= myLat;
	    				myLongtest = myLong;
	    				
	    				//build url   
	    				
				//	<Insert Web service call URL (Weather Web Service Call to NOAA Web Site). To use service, contact www.noaa.gov>

	    				double myValue;	    			
	    				
	    				myValue = getNOAAData(myUrl);    
	    				if (dataValid==0){
	    					Dialog.alert(modal6);
	    				}
	    				else {	
		    				if (myValue ==0){
		    					Dialog.alert(modal5);	
		    				}
		    				else {
			    				HIdouble = myValue;
			    		        
			    		        setUIforResults(HIdouble);	
		
			    		        //reset the input fields
			    		        Double myResult1 = new Double(valueTemp);
			    		        Double myResult2 = new Double(valueHumid);	
			    		        edit_input_temp.setText(myResult1.toString());
			    				edit_input_humid.setText(myResult2.toString());		    				
		    				} 
	    				} 
	    				//clean up the screen for dataValid==0
	    				if (dataValid==0){
	    					setUIforCurrentMAXDataNotValid();
	    				}
	    				}
	    		        catch (Exception e) {	    		            
	    		    } 
				} //end of check GPS   
				//errorNotify.setText("  click max  " + testIndex +  String.valueOf(locData[0]) + "  " + String.valueOf(locData[1]));
				//errorNotify.setText("  click max  " + testIndex);
            }
        } );
        
        
        
        precaution_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {  
            	//set the values in MyValues object for passing them to the precaution page
                MyValues.setMyValues(HIdouble);
                MyValues.setMyTime(myMaxTime);
                MyValues.setMyRiskLevel(myRiskLevel);
                
                //UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
                UiApplication.getUiApplication().pushScreen(new Precaution());
            }                 
        } );             
        	
		        String footer1 = "";
		        String footer2 = "";
		        String footer3 = "";
		        if(MyValues.getMyLanguage() == "English") {
		        	footer1 = "Home";
		        	footer2 = "Back";
		        	footer3 = "More Info";
		        } else if(MyValues.getMyLanguage() == "Spanish") {
		        	footer1 = "Inicio";
		        	footer2 = "Regresar";
		        	footer3 = "Más Info";
		        }           

		     // Footer toolbar  
		        if (ToolbarManager.isToolbarSupported()) {            
		            ToolbarManager manager = new ToolbarManager();
		            setToolbar(manager);

		            try {
		             // More Info button
		            	Bitmap moreBit = null;
		            	if(MyValues.getMyLanguage() == "English") {
		            		moreBit = Bitmap.getBitmapResource("button-more-wide.png");
		         	    } else if(MyValues.getMyLanguage() == "Spanish") {
		         	    	moreBit = Bitmap.getBitmapResource("button-more-wide-sp.png");
		         	    }
		                Image moreImage = ImageFactory.createImage(moreBit);
		                
		                ToolbarButtonField button1 = new ToolbarButtonField(moreImage, new StringProvider(footer3));
		                button1.setCommandContext(new Object() {
		                    public String toString() {
		                        return "More Info"; 
		                    }          
		                });
		                
		                button1.setCommand(new Command(new CommandHandler() {         
		                    public void execute(ReadOnlyCommandMetadata metadata, Object context) {
		                    	UiApplication.getUiApplication().pushScreen(new MainMenu());
		                    }           
		                }));
		                
		             // Home button
		                Bitmap homeBit = null;
		            	if(MyValues.getMyLanguage() == "English") {
		            		homeBit = Bitmap.getBitmapResource("button-home-wide.png");
		         	    } else if(MyValues.getMyLanguage() == "Spanish") {
		         	    	homeBit = Bitmap.getBitmapResource("button-home-wide-sp.png");
		         	    }
		                Image homeImage = ImageFactory.createImage(homeBit);
		                
		                ToolbarButtonField button2 = new ToolbarButtonField(homeImage, new StringProvider(footer1));
		                button2.setCommandContext(new Object() {
		                    public String toString() {
		                        return "Home"; 
		                    }          
		                });
		                
		                button2.setCommand(new Command(new CommandHandler() {         
		                    public void execute(ReadOnlyCommandMetadata metadata, Object context) {
		                    	UiApplication.getUiApplication().pushScreen(new HeatIndexMainScreen());
		                    }           
		                }));	
		                 
		                //manager.add(new ToolbarSpacer(0));
		                manager.add(button2);
		                manager.add(button1);
		            }
		            catch (Exception e) {
		                System.out.println(e.getMessage());
		            }
		        } 
		        else 
		        {
		            //Dialog.alert("The Toolbar is not supported on this device.");
		            // Footer buttons
		            HorizontalFieldManager footerhfm = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		                    
		                 // Home button                  
		                    ButtonField button1 = new ButtonField(footer1, ButtonField.FIELD_HCENTER);
		                    button1.setCommandContext(new Object() {
		                        public String toString() {
		                            return "Home"; 
		                        }          
		                    });
		                    
		                    button1.setCommand(new Command(new CommandHandler() {         
		                        public void execute(ReadOnlyCommandMetadata metadata, Object context) {
		                        	UiApplication.getUiApplication().pushScreen(new HeatIndexMainScreen());
		                        }           
		                    }));		                    
		                
		            
		                    // More Info button                   
		                    ButtonField button3 = new ButtonField(footer3, ButtonField.FIELD_HCENTER);
		                    button3.setCommandContext(new Object() {
		                        public String toString() {
		                            return "More Info"; 
		                        }   
		                    });
		                    
		                    button3.setCommand(new Command(new CommandHandler() {         
		                        public void execute(ReadOnlyCommandMetadata metadata, Object context) {
		                        	UiApplication.getUiApplication().pushScreen(new MainMenu());
		                        }           
		                    }));
		                    
		                    footerhfm.add( button1 );
		                    footerhfm.add( button3 );
		                    vfm.add(footerhfm);
		        }
        
    }  
	
    public double getNOAAData(String urlP) {
        try {     
        
        double myValue=0;   //variable for storing the HI value shown on the screen later
        	
        testIndex=testIndex + "  (2)  In get NOAA Date:" + "Lat=" + myLattest +  " Long=" + myLongtest + " ";
        	
        //testIndex= testIndex + urlP;
        //testIndex= testIndex + "before querying web";   
      
        
        //get XML file
        String response = "";        
        //notes: for real phone, deviceside=true, 
        //means to use DirectTCP which goes over the cell phone company's network
        //urlP = urlP + ";"+ "ConnectionTimeout=1200000;deviceside=true";
        
        //notes: for simulator, deviceside=false
        urlP = urlP + ";"+ "ConnectionTimeout=1200000;deviceside=true";
        StreamConnection s = (StreamConnection)Connector.open(urlP);        
        InputStream input = s.openInputStream();    
                
		byte[] data = new byte[256];
		int len = 0;
		StringBuffer raw = new StringBuffer();
		while( -1 != (len = input.read(data))) {
			raw.append(new String(data, 0, len));
		}
		response = raw.toString();			
        
      
        //testIndex= testIndex + "after querying web";   	
        
		
        //Get a SAXParser from the SAXPArserFactory. 
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();   
               
        //Create a new ContentHandler and apply it to the XML-Reader
        HandlerWeather myExampleHandler = new HandlerWeather();        
        
        //testIndex= testIndex + "   before sp.parse ";          
        
        InputStream xml = new ByteArrayInputStream(response.getBytes());
        sp.parse(xml, myExampleHandler);
        
        //testIndex= testIndex + "   after sp.parse ";          

        //Our ExampleHandler now provides the parsed data to us. 
        ParsedDataSetWeather parsedExampleDataSet = myExampleHandler.getParsedData();  
       
        input.close();        
		s.close();	
		
        //get today's date
		Calendar mycalendar = Calendar.getInstance();		
		int myDay = mycalendar.get(Calendar.DAY_OF_MONTH);
		
		//get current hour			
		int myHour =mycalendar.get(Calendar.HOUR_OF_DAY); 
		
		testIndex = testIndex + "phone dateandtime    	myDay=" + myDay + "  myHour=" + myHour +"\n";
	
		//the size of the data for today's calculation
		int myVecSize = 24-myHour;
        
        //temperature vector, humid vector and time vector for storing the data from XML
        Vector myTempVec = parsedExampleDataSet.gettemperature();
        Vector myHumidVec = parsedExampleDataSet.gethumidity();	
        Vector myTimeVec = parsedExampleDataSet.getmaxtime();
        
        testIndex= testIndex + "  " + myTempVec.size();   
        
        //results HeatIndex Array
        double[] myHIarray = new double[myTempVec.size()];
        
        //put vector values to array for calculation later       
        double[] myTempArray = new double[myTempVec.size()];
        for (int i=0; i<myTempVec.size(); i++){
            String val = (String)myTempVec.elementAt(i);
            myTempArray[i] = Double.parseDouble(val);    	
        }
        
        double[] myHumidArray = new double[myHumidVec.size()];
        for (int i=0; i<myHumidVec.size(); i++){
            String val = (String)myHumidVec.elementAt(i);
            myHumidArray[i] = Double.parseDouble(val);   
        }	           
        
        //the following code is handling the cache in the getting weather data
        //pick up the data for current hour + 1
        //if there is cache, skip those cached values       
        
        int myCurrentIndex = 0;      //the index of the first data for our calculation
        for (int i=0; i<myTempVec.size(); i++){
            String val = (String)myTimeVec.elementAt(i);
            String getDay = val.substring(8,10);
            String getTime = val.substring(11,13);    
           
            if (myDay == Integer.parseInt(getDay) &&  myHour<Integer.parseInt(getTime)){
            	myCurrentIndex = i;            
            	testIndex = testIndex + "myCurrentIndex" + "=" + i+ "\n";
            	testIndex = testIndex + "    	getDay=" + getDay + "  myDay=" + myDay +"\n";
            	testIndex = testIndex + "    	getHour=" + getTime + "  myHour=" + myHour+"\n";
            	testIndex = testIndex + "  		myVecSize=" + myVecSize;
            	break;
            }
        }    
        
        testIndex = testIndex + "sarah here myCurrentIndex" + "=" + myCurrentIndex+ "\n";
        
        //the useful data for our calculation
        //excluding the cached data for previous days/hours and data for future days
        double[] myTempArray2 = new double[myVecSize];    
        for (int i=0; i<myVecSize; i++){           
            myTempArray2[i] = myTempArray[i+ myCurrentIndex];              
        }
        
        double[] myHumidArray2 = new double[myVecSize];
        for (int i=0; i<myVecSize; i++){           
            myHumidArray2[i] = myHumidArray[i+ myCurrentIndex];     
            
        }	 
        
        int size = myTempArray2.length;
        
        //calculate heatindex and put them in myHIarray
        for (int i=0; i<size; i++){
        	testIndex = testIndex + " i=  " + i + "  " +  String.valueOf(myTempArray2[i]);
        	testIndex = testIndex + "  " + String.valueOf(myHumidArray2[i]);
        	
        	
        	myHIarray[i]= calIndex.heatIndexCal(myTempArray2[i], myHumidArray2[i]);  
        	
        	testIndex = testIndex + "  " + String.valueOf(myHIarray[i]);
        }        
        
        //current button is clicked
        if (buttonClicked==1 && size>0){		 
		  valueTemp = myTempArray2 [0];
		  valueHumid = myHumidArray2 [0];
		  myValue=myHIarray[0];
		  
		  if (valueTemp<80){
			  dataValid = 0;			 
		  }
		  
        }
        
        //find the max heat index for day max
        int mycount=0;    //index for the max HeatIndex
        if (buttonClicked==2 && size>0){
	        for (int i=0; i<size; i++){
	        	if (i==0){
	        		myValue = myHIarray[i];
	        		mycount=0;
	        	}
	        	else {
	        		if (myValue<myHIarray[i]){
	        			myValue = myHIarray[i];
	        			mycount++;
	        		}
	        	}
	        }  
        }   
		
		//new code, check day max set
		//if all the temps are lower than 80, then it is datavalid=0
		int myCountMax =0;
		if (buttonClicked==2 && size>0){
	        for (int i=0; i<size; i++){
	        	if (myTempArray2[i]<80) {
	        		myCountMax=myCountMax+1;
	        	}	        	
	        } 
			
			if (myCountMax == size){
				dataValid = 0;   //invalid, pick the current temp and humidity
				valueTemp = myTempArray2 [0];
				valueHumid = myHumidArray2 [0];
			}
        } 
		
		
		
        //processing the values for MAX button clicking
		if (buttonClicked==2 && size>0){
			valueTemp = myTempArray2[mycount];
			valueHumid = myHumidArray2[mycount];
			
			 if (valueTemp<80){
				  dataValid = 0;	//invalid, pick the current temp and humidity
				  valueTemp = myTempArray2 [0];
		          valueHumid = myHumidArray2 [0];
			  }
			  
		}

		if (size>0){
	        String myMaxTimeStr ="";
	        
	        if (buttonClicked==1){	        	
	        	myMaxTimeStr = (String)myTimeVec.elementAt(myCurrentIndex);
	        }
	        if (buttonClicked==2){
	        	myMaxTimeStr = (String)myTimeVec.elementAt(mycount+myCurrentIndex);
	        }	      
	        //sample format from XML 2011-06-09T17:00:00-04:00
	        myMaxTimeStr = myMaxTimeStr.substring(11,13);
	            
	        //format to 8am, 2pm etc
	        int intHourValue;
	        intHourValue = Integer.parseInt(myMaxTimeStr);
	        String ampm = "am";
	        
	        if (intHourValue<10){
	        	myMaxTimeStr = myMaxTimeStr.substring(1);         	
	        	myMaxTimeStr = myMaxTimeStr + " " + "am";
	        }
	        else{
	        	if (intHourValue>12){
	        		intHourValue = intHourValue-12;
	        		ampm = "pm";        	
	        	}
	        	if (intHourValue==12){        		
	        		ampm = "pm";        	
	        	}
	        	myMaxTimeStr = String.valueOf(intHourValue) + " " + ampm;
	        }     
	                      
	        	myMaxTime = myMaxTimeStr;
	        	
	        	if (dataValid ==0){
	        		  myValue=0;
					  myMaxTime="";
				}        	
	        	
	        	
		}        	
		else{
        		myValue=0;
        		myMaxTime="";
        }    
        
            
            return myValue;
        }
        catch (Exception e) {
        	showException(e);
            return 0;
        }        
    }   
    
    public void setUIforCleanup() {  
    	edit_input_temp.setText("");
		edit_input_humid.setText("");
        hi_result.setText("");         
        time_result.setText(""); 
        risk_result.setText(" Risk Level: ");           
        Background bg= BackgroundFactory.createSolidBackground(Color.WHITE);
        risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);        
   }  
   
   
   //new method for showing temp and humidity when current temp is too low or all day is too low.
   public void setUIforCurrentMAXDataNotValid() {  
    	//reset the input fields
		Double myResult1 = new Double(valueTemp);
	    Double myResult2 = new Double(valueHumid);
	    edit_input_temp.setText(myResult1.toString());
		edit_input_humid.setText(myResult2.toString());	
		
		//clear HI and Time
        hi_result.setText("");         
        time_result.setText(""); 
        
		//set risk level
		myRiskLevel = "LOWER (CAUTION)";
	        		
	    if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	    } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: MÁS BAJO (PRECAUCIÓN)");
	    }
	    Background bg= BackgroundFactory.createSolidBackground(Color.YELLOW); 
	    risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
   }
   
   
   
   public void setUIforManualLow() {      	
		//set risk level
		myRiskLevel = "LOWER (CAUTION)";
	        		
	    if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	    } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: MÁS BAJO (PRECAUCIÓN)");
	    }
	    Background bg= BackgroundFactory.createSolidBackground(Color.YELLOW); 
	    risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
   }
   
   
   
    
    public void setUIforResults(double HIdoubleP) {
    	 
    	 //display on the screen    	
          Double myResult = new Double(HIdoubleP);
          hi_result.setText(myResult.toString()+" °F");            
          
          
	        if (showTime == 1){
	        	 //set NOAA time 
	        	if(MyValues.getMyLanguage() == "English") {
	        		time_result.setText(" NOAA Data Time: " + myMaxTime);
	            } else if(MyValues.getMyLanguage() == "Spanish") {
	            	time_result.setText(" Hora de los Datos de NOAA: " + myMaxTime);
	            }
	        	  	        	
	        }
	        else {
	        	 //hide time label here ???? to do
	        	time_result.setText("");  	     
	        }
	        
	    	if (HIdoubleP>115){      
	    		myRiskLevel = "VERY HIGH TO EXTREME";
	    		if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	            } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: MUY ALTO A EXTREMO");
	            }
	        	Background bg= BackgroundFactory.createSolidBackground(Color.RED);
	        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
	    		
	        }	   
	        if (HIdoubleP>103 && HIdoubleP<=115){  
	        	myRiskLevel = "HIGH";
	        	if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	            } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: ALTO");
	            }
	        	Background bg= BackgroundFactory.createSolidBackground(Color.ORANGE); //rgb 247, 141, 0
	        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
	        }	        
	        if (HIdoubleP>=91 &&  HIdoubleP<=103){  
	        	myRiskLevel = "MODERATE";
	        	if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	            } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: MODERADO");
	            }
	        	Background bg= BackgroundFactory.createSolidBackground(Color.PEACHPUFF); //rgb 255, 211, 155
	        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
	        }
	        if (HIdoubleP<91){        	
	        	myRiskLevel = "LOWER (CAUTION)";
	        		
	        	if(MyValues.getMyLanguage() == "English") {
	    			risk_result.setText(" Risk Level: " + myRiskLevel);
	            } else if(MyValues.getMyLanguage() == "Spanish") {
	            	risk_result.setText(" Nivel de Riesgo: MÁS BAJO (PRECAUCIÓN)");
	            }
	        	Background bg= BackgroundFactory.createSolidBackground(Color.YELLOW); 
	        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
	        }     
    }  
    
  

public boolean checkIfNumber(String in) {
        
        try {
            Double.parseDouble(in);        
        } catch (NumberFormatException ex) {
            return false;
        }        
        return true;
    } 

 
 private void findLocation()
 {    
	
	 if(GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_AUTONOMOUS))
     {
		 testIndex=testIndex + "  gps is available";
     }	 
	 
     Thread locThread = new Thread() 
     {
         public void run() 
         {
             try
             {
                 BlackBerryCriteria myCriteria = new BlackBerryCriteria();
                 myCriteria.enableGeolocationWithGPS(BlackBerryCriteria.FASTEST_FIX_PREFERRED);
                 //myCriteria.enableGeolocationWithGPS();
                 
                 
                 try
                 {
                     BlackBerryLocationProvider myProvider = (BlackBerryLocationProvider)LocationProvider.getInstance(myCriteria);
                     
                     
                     try
                     {
                         BlackBerryLocation myLocation =(BlackBerryLocation)myProvider.getLocation(-1);
                         _longitude = myLocation.getQualifiedCoordinates().getLongitude();
                         _latitude = myLocation.getQualifiedCoordinates().getLatitude();
                         _modeUsed = myLocation.getGPSMode();
                                                     
                         switch (_modeUsed)
                         {
                             case LocationInfo.GEOLOCATION_MODE:
                             case LocationInfo.GEOLOCATION_MODE_CELL:
                             case LocationInfo.GEOLOCATION_MODE_WLAN:
                                 _mode = "Geolocation";
                                 break;
                             default:
                                 _mode = "GPS";
                         }    
                             
                         StringBuffer sb = new StringBuffer();
                         sb.append("Longitude: ");
                         sb.append(_longitude);
                         sb.append("\n");
                         sb.append("Latitude: ");
                         sb.append(_latitude);
                         sb.append("\n");
                         sb.append("Mode: ");
                         sb.append(_mode);
                         String msg = sb.toString();
                         //showResults(msg);
                         locData[0]=_latitude;
                         locData[1]=_longitude;
                         
                         testIndex = testIndex  + "    (1)in the find location method " + _latitude + " " + _longitude;
                     }
                     catch (InterruptedException e)
                     {
                         showException(e);
                         testIndex=testIndex  + "error 1";
                     }
                     catch (LocationException e)
                     {
                         showException(e);
                         testIndex=testIndex  + "error 2";
                     }
                 }
                 catch (LocationException e)
                 {
                     showException(e);
                     testIndex=testIndex  + "error 3";
                 }
             } 
             catch (UnsupportedOperationException e) 
             {
                showException(e);
                testIndex=testIndex  + "error 4";
             }
         }
     };
     locThread.start();    
 } 
  
 private void showException(final Exception e) 
 {
     Application.getApplication().invokeLater(new Runnable()
     {
         public void run()
         {
             //Dialog.alert(e.getMessage());
         }
     });
 }
 

 public boolean onSavePrompt()
 {
     return true;
 }  


}

