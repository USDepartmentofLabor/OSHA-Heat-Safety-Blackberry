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


import net.rim.device.api.gps.*;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
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

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.ui.image.*;
import net.rim.device.api.ui.toolbar.*;
import net.rim.device.api.util.*;
import net.rim.device.api.system.*;
import net.rim.device.api.command.*;




public class FirstAid extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;  
    
	//for accessibility
    private IconToolbarComponent _iconToolbar;

	public FirstAid() {
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
        	titleText = "Illness: First Aid";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Primeros Auxilios";
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
        
        String headerText = "";
    	String illness1 = "";
     	String firstaid1 = "";
     	String illness2 = "";
     	String firstaid2 = "";
     	String illness3 = "";
     	String firstaid3 = "";
     	String illness4 = "";
     	String firstaid4 = "";
     	String firstaid5 = "";
     	
        if(MyValues.getMyLanguage() == "English") {
        	headerText = " Illness               First Aid*";
        	illness1 = " Heat \nstroke";
         	firstaid1 = " \u2022 HEAT STROKE IS A MEDICAL EMERGENCY. Call 911 While waiting for help:\n" +
         						" \u2022 Place worker in shady, cool area\n" +
         						" \u2022 Loosen clothing, remove outer clothing\n" +
         						" \u2022 Fan air on worker; cool packs in armpits\n" +
         						" \u2022 Wet worker with cool water; apply ice packs, cool compresses, or ice if available\n" +
         						" \u2022 Provide fluids (preferably water) as soon as possible\n" +
         						" \u2022 Stay with worker until help arrives";
         	illness2 = " Heat\n exhaustion";
         	firstaid2 = " \u2022 Have worker sit or lie down in a cool, shady area\n" +
         						" \u2022 Give worker plenty of water or other cool beverages to drink\n" +
         						" \u2022 Cool worker with cold compresses/ice packs\n" +
         						" \u2022 Take to clinic or emergency room for medical evaluation and treatment if signs or " +
         						"symptoms worsen or do not improve within 60 minutes\n" +
         						" \u2022 Do not return to work that day";
         	illness3 = " Heat \ncramps";
         	firstaid3 = " \u2022 Have worker rest in shady, cool area\n" +
         						" \u2022 Have worker drink water or other cool beverages\n" +
         						" \u2022 Wait a few hours before allowing worker to return to heavy work\n" +
         						" \u2022 Seek medical attention if cramps don't go away";
         	illness4 = " Heat rash";
         	firstaid4 = " \u2022 Try to work in a cooler, less humid environment when possible\n" +
         						" \u2022 Keep the affected area dry\n\n";
         	firstaid5 = "* Remember, if you are not a medical professional, use this information as a guide only to help protect workers in need.\n";
         	
         	
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	headerText = " Enfermedad     Primeros Auxilios*";
        	illness1 = " Insolación";
         	firstaid1 = " \u2022 LA INSOLACIÓN ES UNA EMERGENCIA. Llame al 911. Mientras espera por ayuda:\n" +
         						" \u2022 Coloque al trabajador en la sombra, en un área fresca\n" +
         						" \u2022 Afloje la ropa, quite la ropa exterior\n" +
         						" \u2022 Dé aire al trabajador, coloque paquetes de hielo en las axilas\n" +
         						" \u2022 Moje al trabajador con agua fría, aplique compresas frías o hielo si está disponible\n" +
         						" \u2022 Proporcione líquidos (preferentemente agua) tan pronto como sea posible\n" +
         						" \u2022 Quédese con el trabajador hasta que  llegue ayuda";
         	illness2 = " Agotamiento\n por el \ncalor";
         	firstaid2 = " \u2022 Procure que el trabajador se siente o se acueste en la sombra en un área fresca\n" +
         						" \u2022 Dele a beber agua u otras bebidas frescas en cantidades abundantes\n" +
         						" \u2022 Refresque al trabajador con compresas de agua fría/hielo\n" +
         						" \u2022 Llévelo a una clínica o sala de emergencias para una evaluación y tratamiento médico si los signos o síntomas empeoran o no mejoran en 60 minutos\n" +
         						" \u2022 El trabajador no debe volver al trabajo ese día";
         	illness3 = " Calambres\n por \ncalor";
         	firstaid3 = " \u2022 Procure que el trabajador descanse en la sombra, en un área fresca \n" +
         						" \u2022 Procure que el trabajador tome agua u otra bebida fría\n" +
         						" \u2022 Espere unas horas antes de permitir que el trabajador vuelva al trabajo pesado\n" +
         						" \u2022 Busque atención médica si los calambres no desaparecen";
         	illness4 = " Sarpullido";
         	firstaid4 = " \u2022 Si es posible, trate de trabajar en un lugar más fresco y menos húmedo\n" +
         						" \u2022 Mantenga seca la zona afectada \n\n";
         	firstaid5 = "* Recuerde, si usted no es un profesional de la salud, use esta información solamente como una guía para ayudar a proteger a los trabajadores en caso de necesidad.";
        }
        
        add(new LabelField(headerText, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        add(new SeparatorField());
        
        HorizontalFieldManager hfm1 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm1L = new VerticalFieldManager(Manager.FIELD_LEFT);
        VerticalFieldManager vfm1R = new VerticalFieldManager(Manager.FIELD_RIGHT);
        hfm1.add(vfm1L);
        hfm1.add(vfm1R);
        
     	BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("heatstroke.png"));

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
     	vfm1R.add(new LabelField(firstaid1, Field.FOCUSABLE){
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
     	vfm2R.add(new LabelField(firstaid2, Field.FOCUSABLE){
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
     	vfm3R.add(new LabelField(firstaid3, Field.FOCUSABLE){
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
     	vfm4R.add(new LabelField(firstaid4, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
     	vfm.add(hfm4);
  	
     	//added * text
     	vfm.add(new SeparatorField());
     	HorizontalFieldManager hfm5 = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
        VerticalFieldManager vfm5L = new VerticalFieldManager(Manager.FIELD_LEFT);
        hfm5.add(vfm5L);
        
     	vfm5L.add(new LabelField(firstaid5, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    		/*protected void layout(int width, int height) {
    			super.layout(width, height);
    			this.setExtent(135, this.getHeight());
    		}*/
    	});
     	
     	vfm.add(hfm5);//added end
     	
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
	