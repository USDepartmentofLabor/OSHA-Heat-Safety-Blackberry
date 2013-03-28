package heatIndex;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYRect;
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
import net.rim.device.api.ui.component.SeparatorField;
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




public class Symptoms extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;  
    
	//for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public Symptoms() {
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
        	titleText = "Heat Illness: Signs and Symptoms";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Signos y Síntomas";
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
        
    	String firstAidLink = "";
    	String headerText = "";
    	String illness1 = "";
     	String symptoms1 = "";
     	String illness2 = "";
     	String symptoms2 = "";
     	String illness3 = "";
     	String symptoms3 = "";
     	String illness4 = "";
     	String symptoms4 = "";
        if(MyValues.getMyLanguage() == "English") {
        	firstAidLink = "First Aid";
        	headerText = " Illness               Symptoms";
        	illness1 = " Heat \nstroke";
         	symptoms1 = " \u2022 Red, hot, dry skin or excessive sweating\n" +
         						" \u2022 Very high body temperature\n" +
         						" \u2022 Confusion\n" +
         						" \u2022 Seizures\n" +
         						" \u2022 Fainting";
         	illness2 = " Heat\n exhaustion";
         	symptoms2 = " \u2022 Cool, moist skin\n" +
         						" \u2022 Heavy sweating\n" +
         						" \u2022 Headache\n" +
         						" \u2022 Nausea or vomiting\n" +
         						" \u2022 Dizziness\n" +
    							" \u2022 Light headedness\n" +
    							" \u2022 Weakness\n" +
    							" \u2022 Thirst\n" +
    							" \u2022 Irritability\n" +
    							" \u2022 Fast heart beat";
         	illness3 = " Heat \ncramps";
         	symptoms3 = " \u2022 Muscle spasms\n" +
         						" \u2022 Pain\n" +
         						" \u2022 Usually in abdomen, arms, or legs";
         	illness4 = " Heat rash";
         	symptoms4 = " \u2022 Clusters of red bumps on skin\n" +
         						" \u2022 Often appears on neck, upper chest, folds of skin\n\n";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	firstAidLink = "Más";
        	headerText = " Enfermedad     Síntomas";
        	illness1 = " Insolación";
         	symptoms1 = " \u2022 Piel enrojecida, caliente y seca o sudoración excesiva\n" +
         						" \u2022 Temperatura corporal muy alta\n" +
         						" \u2022 Confusión\n" +
         						" \u2022 Convulsiones\n" +
         						" \u2022 Desmayo";
         	illness2 = " Agotamiento\n por el \ncalor";
         	symptoms2 = " \u2022 Piel fría y húmeda\n" +
         						" \u2022 Sudoración profusa\n" +
         						" \u2022 Dolor de cabeza\n" +
         						" \u2022 Náuseas o vómitos\n" +
         						" \u2022 Mareo\n" +
    							" \u2022 Aturdimiento\n" +
    							" \u2022 Debilidad\n" +
    							" \u2022 Sed\n" +
    							" \u2022 Irritabilidad\n" +
    							" \u2022 Pulso rápidas";
         	illness3 = " Calambres\n por \ncalor";
         	symptoms3 = " \u2022 Espasmos musculares\n" +
         						" \u2022 Dolor\n" +
         						" \u2022 Por lo general, en el abdomen, los brazos o las piernas";
         	illness4 = " Sarpullido";
         	symptoms4 = " \u2022 Pequeños grupos de ampollas en la piel \n" +
         						" \u2022 Aparece a menudo en el cuello, parte superior del pecho, pliegues de la piel\n\n";
        }

    	
        add(new LabelField(headerText));
        add(new SeparatorField());
        
        HorizontalFieldManager hfm1 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm1L = new VerticalFieldManager(Manager.FIELD_LEFT);
        VerticalFieldManager vfm1R = new VerticalFieldManager(Manager.FIELD_RIGHT);
        hfm1.add(vfm1L);
        hfm1.add(vfm1R);
        
        BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("heatstroke.png"));
        ButtonField firstAidLink1 = new ButtonField(firstAidLink, ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT );
        
     	vfm1L.add(new LabelField(illness1, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    		protected void layout(int width, int height) {
    			super.layout(width, height);
    			this.setExtent(135, this.getHeight());
    		}
    	});
     	vfm1L.add(image1);
     	vfm1L.add(firstAidLink1);
     	vfm1R.add(new LabelField(symptoms1, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
     	vfm.add(hfm1);
     	vfm.add(new SeparatorField());
     	
     	HorizontalFieldManager hfm2 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm2L = new VerticalFieldManager(Manager.FIELD_LEFT);
        VerticalFieldManager vfm2R = new VerticalFieldManager(Manager.FIELD_RIGHT);
        hfm2.add(vfm2L);
        hfm2.add(vfm2R);
        
        BitmapField image2 = new BitmapField(Bitmap.getBitmapResource("heatexhaustion.png"));
        ButtonField firstAidLink2 = new ButtonField(firstAidLink, ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT );

     	vfm2L.add(new LabelField(illness2, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    		protected void layout(int width, int height) {
    			super.layout(width, height);
    			this.setExtent(135, this.getHeight());
    		}
    	});
     	vfm2L.add(image2);
     	vfm2L.add(firstAidLink2);
     	vfm2R.add(new LabelField(symptoms2, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
     	vfm.add(hfm2);
     	vfm.add(new SeparatorField());     	
     	
     	HorizontalFieldManager hfm3 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm3L = new VerticalFieldManager(Manager.FIELD_LEFT);
        VerticalFieldManager vfm3R = new VerticalFieldManager(Manager.FIELD_RIGHT);
        hfm3.add(vfm3L);
        hfm3.add(vfm3R);
        
        BitmapField image3 = new BitmapField(Bitmap.getBitmapResource("heatcramps.png"));
        ButtonField firstAidLink3 = new ButtonField(firstAidLink, ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT );

     	vfm3L.add(new LabelField(illness3, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    		protected void layout(int width, int height) {
    			super.layout(width, height);
    			this.setExtent(135, this.getHeight());
    		}
    	});
     	vfm3L.add(image3);
     	vfm3L.add(firstAidLink3);
     	vfm3R.add(new LabelField(symptoms3, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
     	vfm.add(hfm3);
     	vfm.add(new SeparatorField());
     	
     	HorizontalFieldManager hfm4 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm4L = new VerticalFieldManager(Manager.FIELD_LEFT);
        VerticalFieldManager vfm4R = new VerticalFieldManager(Manager.FIELD_RIGHT);
        hfm4.add(vfm4L);
        hfm4.add(vfm4R);
        
        ButtonField firstAidLink4 = new ButtonField(firstAidLink, ButtonField.CONSUME_CLICK | ButtonField.FIELD_LEFT );

     	vfm4L.add(new LabelField(illness4, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    		protected void layout(int width, int height) {
    			super.layout(width, height);
    			this.setExtent(135, this.getHeight());
    		}
    	});
     	vfm4L.add(firstAidLink4);
     	vfm4R.add(new LabelField(symptoms4, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
     	vfm.add(hfm4);
 
     	add(vfm);
     	
     	firstAidLink1.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
                UiApplication.getUiApplication().pushScreen(new FirstAid());
            }                 
        } );
     	firstAidLink2.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
                UiApplication.getUiApplication().pushScreen(new FirstAid());
            }                 
        } );
     	firstAidLink3.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
                UiApplication.getUiApplication().pushScreen(new FirstAid());
            }                 
        } );
     	firstAidLink4.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
                UiApplication.getUiApplication().pushScreen(new FirstAid());
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
	 public boolean onSavePrompt()
	 {
	     return true;
	 }  
}
	