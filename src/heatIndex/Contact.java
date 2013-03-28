package heatIndex;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
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




public class Contact extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;
    
  //for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public Contact() {
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
        //MyValues.setMyLanguage("E");
        if(MyValues.getMyLanguage() == "English") {
        	titleText = "Contact OSHA";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Contacte a OSHA";
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
        
     // add content
    	String content1 = "";
    	String content2 = "";
    	String content3 = "";  
    	String linkText2 = "";
    	String linkText3 = "";
    	String text_e = "";
    	String text_s = "";
    	if(MyValues.getMyLanguage() == "English") {
    		/*content1 = "If you have questions, call OSHA. It's confidential. Call 1-800-321-OSHA (6742) " +
				"or visit www.osha.gov to learn more about heat illness.\n\n";
    		content2 = "For worker fact sheets, worksite posters, training materials, and other resources " +
				"on preventing heat illness in outdoor workers, in both English and Spanish, see " +
				"OSHA's heat illness web page.\n\n";
    		content3 = "For other valuable worker protection information, " +
				"such as Workers' Rights, Employer Responsibilities, and other services OSHA offers, " +
				"visit OSHA's Workers' web page.\n\n";
    		linkText2 = "OSHA's heat illness web page";
    		linkText3 = "OSHA's Workers' page";*/
    		text_e = "If you have questions, call OSHA. It's confidential. Call 1-800-321-6742 (OSHA) or visit www.osha.gov to learn more about heat illness.\n\nFor worker fact sheets, worksite posters, training materials, and other resources on preventing heat illness in outdoor workers, in both English and Spanish, see OSHA's heat illness web page at www.osha.gov/SLTC/heatillness/index.html.\n\nFor other valuable worker protection information,such as Workers' Rights, Employer Responsibilities, and other services OSHA offers, visit OSHA's Workers' web page at www.osha.gov/workers.html.";
    		
    		vfm.add(new ActiveRichTextField(text_e,Field.FOCUSABLE));
    		
    		
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	/*content1 = "Si tiene preguntas, llame a OSHA. Es confidencial. Llame al 1-800-321-OSHA (6742) o visite www.osha.gov para conocer más acerca de las enfermedades a causa del calor.\n\n";
			content2 = "Para encontrar hojas informativas para el trabajador, afiches para el lugar de trabajo, materiales de capacitación y otros recursos para prevenir la enfermedad a causa del calor en trabajadores al aire libre, tanto en inglés como en español, visite la página web de OSHA sobre la enfermedad a causa del calor\n\n";
			content3 = "Para obtener más información valiosa sobre la protección de los trabajadores, tales como los Derechos de los Trabajadores, Responsabilidades del Empleador y otros servicios ofrecidos por OSHA, visite la Página para Trabajadores\n\n";
			linkText2 = "OSHA sobre la enfermedad a causa del calor";
			linkText3 = "Página para Trabajadores";*/
			text_s = "Si tiene preguntas, llame a OSHA. Es confidencial. Llame al 1-800-321-6742 (OSHA) o visite www.osha.gov para conocer más acerca de las enfermedades a causa del calor.\n\nPara encontrar hojas informativas para el trabajador, afiches para el lugar de trabajo, materiales de capacitación y otros recursos para prevenir la enfermedad a causa del calor en trabajadores al aire libre, tanto en inglés como en español, visite la página web de OSHA sobre la enfermedad a causa del calor a www.osha.gov/SLTC/heatillness/index.html.\n\nPara obtener más información valiosa sobre la protección de los trabajadores, tales como los Derechos de los Trabajadores, Responsabilidades del Empleador y otros servicios ofrecidos por OSHA, visite la Página para Trabajadores a www.osha.gov/workers.html.";
				
			vfm.add(new ActiveRichTextField(text_s,Field.FOCUSABLE));
        }
        
    	
    	
        //ButtonField link1 = new ButtonField( "www.osha.gov", ButtonField.CONSUME_CLICK | ButtonField.HCENTER);
        //ButtonField link2 = new ButtonField( linkText2, ButtonField.CONSUME_CLICK | ButtonField.HCENTER);
        //ButtonField link3 = new ButtonField( linkText3, ButtonField.CONSUME_CLICK | ButtonField.HCENTER);
        
        /*vfm.add(new LabelField(content1, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(link1);
        vfm.add(new LabelField(content2, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(link2);
        vfm.add(new LabelField(content3, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(link3);*/
    	
        add(vfm);
        
        /*link1.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	BrowserSession bSession = Browser.getDefaultSession();
            	bSession.displayPage("http://www.osha.gov");
            }                 
        });
        
        link2.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	BrowserSession bSession = Browser.getDefaultSession();
            	bSession.displayPage("http://www.osha.gov/SLTC/heatillness/index.html");
            }                 
        });
        
        link3.setChangeListener( new FieldChangeListener() {
            public void fieldChanged( Field arg0, int arg1 ) {
            	BrowserSession bSession = Browser.getDefaultSession();
            	bSession.displayPage("http://www.osha.gov/workers.html");
            }                 
        });*/

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
	