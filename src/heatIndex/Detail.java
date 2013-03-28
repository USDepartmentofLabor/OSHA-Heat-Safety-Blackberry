package heatIndex;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
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




public class Detail extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;  
    
	//for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public Detail() {
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
        	titleText = "More Detail";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Más Detalles";
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
        
        
        String content1 = "";
        String content2 = "";
        String content3 = "";
        String content4 = "";
        String content5 = "";
        String content6 = "";
        String content7 = "";
        if(MyValues.getMyLanguage() == "English") {
        	 content1 = "Drink water throughout the day.";
             content2 = "\n \u2022 Estimate how much water will be needed and decide who will obtain and check " +
             					"on water supplies.\nGuidelines recommend that outdoor workers drink 4 cups " +
             					"(8 cones) of water every hour, even if they\'re not thirsty.\n" +
             					"\u2022 It is best to drink a small amount of water often, like 1 cup (2 cones) " +
             					"every 15 minutes.\n" +
             					" \u2022 Avoid alcohol and drinks with caffeine or sugar.\n" +
             					" \u2022 Generally, fluid intake should not exceed 6 cups per hour.\n\n";
             content3 = "Know what to do in an emergency. Have a plan in case a worker experiences heat-" +
             					"related illness. Everyone onsite should know the procedures for responding to " +
             					"possible heat illness:\n\n" +
             					" \u2022 What steps to follow if a worker has signs of heat illness.\n" +
             					" \u2022 Who to call for medical help and how to give clear directions to the " +
             					"worksite if calling 911.\n" +
             					" \u2022 Who will provide first aid until the ambulance arrives.\n\n";
             content4 = "Train workers before hot outdoor work begins. Tailor training to cover worksite-" +
             					"specific conditions. Address the following topics:\n\n" +
             					" \u2022 Risk factors for heat-related illness.\n" +
             					" \u2022 Different types of heat-related illness and how to recognize them.\n" +
             					" \u2022 Heat-related illness prevention procedures, including drinking water.\n" +
             					" \u2022 Importance of acclimatization and how your worksite procedures address it.\n" +
             					" \u2022 The importance and method of responding to possible heat-related illness, " +
             					"including immediately reporting signs or symptoms to the supervisor.\n" +
             					" \u2022 Procedures to follow when contacting emergency medical services, including " +
             					"providing clear directions to the worksite.\n\n";
             content5 = "Pace the work and build up gradually to the heaviest work (called acclimatization). " +
             					"People can, to a large extent, adjust to heat. Some important reminders:\n\n" +
             					" \u2022 New workers and those returning from an absence of two or more weeks should " +
             					"have a 5-day minimum adjustment period.\n" +
             					" \u2022 Closely supervise new employees for the first 14 days or until they are fully " +
             					"acclimatized.\n" +
             					" \u2022 Some workers require up to 2-3 weeks to fully acclimatize.\n" +
             					" \u2022 Acclimatization is essential for all workers during a heat wave.\n" +
             					" \u2022 When possible, allow only acclimatized workers to perform the more " +
             					"strenuous tasks.\n\n";
             content6 = "Modify work/rest periods to give the body a chance to get rid of excess heat. Assign " +
             					"new and un-acclimatized workers lighter work and longer rest periods. Shorten work " +
             					"periods and increase rest periods:\n\n" +
             					" \u2022 As temperature, humidity, and sunshine increase.\n" +
             					" \u2022 When there is no air movement or if protective clothing or gear is worn.\n" +
             					" \u2022 For heavier work.\n";
             content7 = "Monitor workers for signs and symptoms of heat illness.\n\n" +
             					" \u2022 Check routinely to make sure workers are making use of water and shade and " +
             					"not experiencing heat-related symptoms.\n" +
             					" \u2022 Monitor new workers and unacclimatized workers more closely.\n" +
             					" \u2022 For workers wearing heavy or non-breathable clothing or during extreme heat " +
             					"conditions, check for physical signs (e.g., body temperature, heart rate) of " +
             					"possible over exposure to the heat.\n\n";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	content1 = "Hay que beber agua durante todo el día. ";
            content2 = "\n \u2022 Calcule cuánta agua se necesita y asigne quién la obtendrá  y verificará las provisiones de aquella.\n" +
            					" \u2022 Las directrices recomiendan que los trabajadores al aire libre beban 4 vasos (8 conos) de agua cada hora, aunque no tengan sed.\n" +
            					" \u2022 Lo mejor es beber una pequeña cantidad de agua a menudo, como un vaso (2 conos) cada 15 minutos.\n" +
            					" \u2022 Evite el alcohol y las bebidas con cafeína o azúcar.\n" +
            					" \u2022 En general, no se debe ingerir más de 6 vasos de líquidos por hora.\n\n";
            content3 = "Sepa qué hacer en caso de una emergencia. Cuente con un plan en caso de que un trabajador sufra de una enfermedad relacionada con el calor. Todos deben conocer los procedimientos para actuar en casos de posibles enfermedades a causa del calor:\n\n" +
            					" \u2022 Qué pasos seguir si un trabajador tiene síntomas de enfermedad a causa del calor.\n" +
            					" \u2022 A quién llamar para obtener ayuda médica y cómo dar instrucciones claras para llegar al lugar de trabajo si están llamando al 911.\n" +
            					" \u2022 Quién proporcionará primeros auxilios hasta que llegue la ambulancia.\n\n";
            content4 = "Capacite a los trabajadores antes de que comiencen el trabajo al aire libre en clima caluroso. Modifique la capacitación para que cubra las condiciones específicas del lugar de trabajo. Aborde los siguientes temas::\n\n" +
            					" \u2022 Factores de riesgo para enfermedades relacionadas al calor. \n" +
            					" \u2022 Los diferentes tipos de enfermedades relacionadas al calor y cómo reconocerlos.\n" +
            					" \u2022 Procedimientos de prevención de enfermedades relacionadas al calor, incluyendo el beber agua.\n" +
            					" \u2022 Importancia de la aclimatización y como los procedimientos en su lugar de trabajo abordan este tema..\n" +
            					" \u2022 La importancia y el método para responder ante posibles enfermedades relacionadas al calor, incluyendo informar inmediatamente al supervisor acerca de cualquier signo o síntomas.\n" +
            					" \u2022 Procedimientos a seguir cuando se contactan a los servicios de emergencia, incluyendo instrucciones claras para que estos lleguen al lugar de trabajo.\n\n";
            content5 = "Limíte el ritmo del trabajo y aumente gradualmente el trabajo más pesado (esto se llama aclimatización). En cierta medida, las personas se pueden adaptar al calor. Algunos recordatorios importantes son: \n\n" +
            					" \u2022 Los trabajadores nuevos y aquellos que regresan después de una ausencia de dos semanas o más deben tener como mínimo un período de 5 días de adaptación.\n" +
            					" \u2022 Supervise de cerca a los nuevos empleados durante los primeros 14 días o hasta que estén completamente aclimatizados.\n" +
            					" \u2022 Algunos trabajadores requieren hasta 2 o 3 semanas para aclimatizarse totalmente.\n" +
            					" \u2022 La aclimatización es esencial para todos los trabajadores durante una ola de calor.\n" +
            					" \u2022 Cuando sea posible, permita que sólo los trabajadores aclimatizados realicen las tareas más agotadoras.\n\n";
            content6 = "Modifique el trabajo/períodos de descanso para darle al cuerpo la oportunidad de deshacerse del exceso de calor. Asigne el trabajo más ligero y más períodos de descanso a los trabajadores nuevos y a quienes no se hayan aclimatizado. Reduzca los períodos de trabajo y aumente los períodos de descanso:\n\n" +
            					" \u2022 A medida que aumenta la temperatura, la humedad y el sol\n" +
            					" \u2022 Cuando no hay movimiento de aire o si se usan prendas o equipo protector\n" +
            					" \u2022 Para trabajos más pesados\n";
            content7 = "Observe a los trabajadores en busca de signos y síntomas de la enfermedad a causa del calor.\n\n" +
            					" \u2022 Compruebe regularmente que los trabajadores estén tomando agua y haciendo uso de la sombra y que no estén presentando síntomas relacionados con el calor.\n" +
            					" \u2022 Vigile más de cerca a los trabajadores nuevos y los no aclimatizados.\n" +
            					" \u2022 Para trabajadores usando ropa pesada o no transpirable, o durante condiciones de calor extremo, monitoree los signos físicos (p.ej., temperatura corporal, pulso) para identificar una posible sobre-exposición al calor.\n\n";
        }
      
     // add content
       
        vfm.add(new LabelField(content1, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content2, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content3, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content4, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content5, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content6, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
        vfm.add(new LabelField(content7, Field.FOCUSABLE){
    		protected void drawFocus(Graphics g, boolean on) {
    			XYRect rect = new XYRect();
    			getFocusRect(rect);
    			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
    		}
    	});
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
	