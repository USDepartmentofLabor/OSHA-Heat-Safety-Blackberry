package heatIndex;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.StandardTitleBar;
import net.rim.device.api.util.StringProvider;
import net.rim.device.api.ui.component.TextField;


import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.gps.*;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.image.Image;
import net.rim.device.api.ui.image.ImageFactory;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;

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




public class MainMenu extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;  
    
	//for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public MainMenu() {
		super( MainScreen.NO_VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR );		
		
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
        
        String titleText = "";
        if(MyValues.getMyLanguage() == "English") {
        	titleText = "More Info";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Más información";
        }   
        
        LabelField title = new LabelField(titleText, LabelField.USE_ALL_WIDTH | DrawStyle.HCENTER | LabelField.VCENTER | DrawStyle.VCENTER){ 
        	public void paint(Graphics g){ 
        		g.setBackgroundColor(3766485); 
        		g.setColor(Color.WHITE);
        		g.clear();
        		super.paint(g); 
        		//subpaint(g);
        	}
        	protected void layout(int width, int height) {
                super.layout(width, height);
                this.setExtent(this.getWidth(), 27);
            }
        };
        title.setMargin(0,0,5,0);
    	add(title);
    	
    	VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLLBAR | Manager.VERTICAL_SCROLL | Manager.USE_ALL_HEIGHT);
		
		//override ButtonField class to extend width
		class MyButtonField extends ButtonField
		{
		    private int width;

		    MyButtonField( String label, int Width)
		    {   super( label);
		        width = Width;
		    }
		    public int getPreferredWidth()
		    {   return width;
		    }
		}
		
		String buttonMore1 = "";
        String buttonMore2 = "";
        String buttonMore3 = "";
        String buttonMore4 = "";
        String buttonMore5 = "";
        if(MyValues.getMyLanguage() == "English") {
        	buttonMore1 = "Heat Illness: Signs and Symptoms";
            buttonMore2 = "Heat Illness: First Aid";
            buttonMore3 = "More Detail";
            buttonMore4 = "Contact OSHA";
            buttonMore5 = "About This App";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	buttonMore1 = "Signos y Síntomas";
            buttonMore2 = "Primeros Auxilios";
            buttonMore3 = "Más Detalles";
            buttonMore4 = "Comuníquese con OSHA";
            buttonMore5 = "Información sobre esta aplicación";
        }   
		
     // "Heat Illness: Sign and Symptoms" - Add button and actions
        MyButtonField symptoms_submit = new MyButtonField( buttonMore1, (int) (MyButtonField.CONSUME_CLICK | MyButtonField.FIELD_HCENTER | MyButtonField.USE_ALL_WIDTH) );
        vfm.add( symptoms_submit );
        
        symptoms_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {               
                UiApplication.getUiApplication().pushScreen(new Symptoms());
            }                 
        } );
        
     // "Heat Illness: First Aid" - Add button and actions
        MyButtonField firstAid_submit = new MyButtonField( buttonMore2, (int) (MyButtonField.CONSUME_CLICK | MyButtonField.FIELD_HCENTER | MyButtonField.USE_ALL_WIDTH) );
        vfm.add( firstAid_submit );
        
        firstAid_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {               
                UiApplication.getUiApplication().pushScreen(new FirstAid());
            }                 
        } );
        
     // "More Detail" - Add button and actions
        MyButtonField detail_submit = new MyButtonField( buttonMore3, (int) (MyButtonField.CONSUME_CLICK | MyButtonField.FIELD_HCENTER | MyButtonField.USE_ALL_WIDTH) );
        vfm.add( detail_submit );
        
        detail_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {               
                UiApplication.getUiApplication().pushScreen(new Detail());
            }                 
        } );
        
     // "Contact OSHA" - Add button and actions
        MyButtonField contact_submit = new MyButtonField( buttonMore4, (int) (MyButtonField.CONSUME_CLICK | MyButtonField.FIELD_HCENTER | MyButtonField.USE_ALL_WIDTH) );
        vfm.add( contact_submit );
        
        contact_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {               
                UiApplication.getUiApplication().pushScreen(new Contact());
            }                 
        } );
        
     // "About This App" - Add button and actions
        MyButtonField about_submit = new MyButtonField( buttonMore5, (int) (MyButtonField.CONSUME_CLICK | MyButtonField.FIELD_HCENTER | MyButtonField.USE_ALL_WIDTH) );
        vfm.add( about_submit );
        
        about_submit.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {               
                UiApplication.getUiApplication().pushScreen(new About());
            }                 
        } );
        
        add(vfm);        

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
	 public boolean onSavePrompt()
	 {
	     return true;
	 }  
}