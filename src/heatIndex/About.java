package heatIndex;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
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




public class About extends MainScreen {
	private BasicEditField hi_result;
    private BasicEditField time_result;      
    private BasicEditField risk_result;  
    
  //for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public About() {
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
        	titleText = "About This App";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Sobre la aplicación";
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
    	if(MyValues.getMyLanguage() == "English") {
        	content1 = "The U.S. Department of Labor (DOL) is providing this App as a public service. " +
			"This App is advisory in nature and informational in content. It is not a standard " +
			"or regulation, and it neither creates new legal obligations nor alters existing " +
			"obligations created by OSHA standards or the Occupational Safety and Health Act. " +
			"Pursuant to the OSH Act, employers must comply with safety and health standards " +
			"and regulations issued and enforced either by OSHA or by an OSHA-approved State " +
			"Plan. In addition, the Act's General Duty Clause, Section 5(a)(1), requires " +
			"employers to provide their employees with a workplace free from recognized hazards " +
			"likely to cause death or serious physical harm.\n\nThis App is a service that is " +
			"continually under development and it does not include every possible heat-related " +
			"situation encountered in the workplace. The user should be aware that, while OSHA " +
			"will try to keep the information timely and accurate, there may be a delay between " +
			"official publication of OSHA materials and the modification of this App. The " +
			"Federal Register and the Code of Federal Regulations remain the official sources " +
			"for regulatory information published by OSHA. Further, the calculations performed " +
			"by this App rely on the accuracy of the data provided by the user. Therefore, OSHA " +
			"cannot guarantee that these calculations accurately reflect conditions at a " +
			"particular worksite.\n\nThis App can, at the user's discretion, obtain weather-" +
			"related data directly from the National Oceanic and Atmospheric Administration " +
			"(NOAA) based on the user's current location (for GPS-enabled phones). The " +
			"information transmitted to NOAA through this App, including the user's location, " +
			"is only used to generate data to transmit to the user through the App and is not " +
			"retransmitted to OSHA or any other entity for any other purpose.\n\n";;
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	content1 = "El Departamento de Trabajo provee esta aplicación como un servicio público. Esta aplicación es de carácter informativo y de contenido informativo.  No es una norma o reglamento, no crea nuevas obligaciones legales ni modifica las ya existentes de acuerdo con las normas de OSHA o la Ley de Seguridad y Salud Ocupacional.  En conformidad con la Ley de la OSHA, los empleadores deben cumplir con las normas de salud y seguridad así como los reglamentos vigentes promulgados e implementados ya sea por OSHA o por un Plan Estatal aprobado por OSHA.  Además, la cláusula del deber general de la Ley , artículo 5 (a) (1) establece que se requiere que los empleadores provean a sus empleados un lugar de trabajo libre de riesgos serios reconocidos, los cuales puedan probablemente causar muerte o daños físicos severos.\n\n" +
        	"Esta Aplicación constituye un servicio en continuo desarrollo y no incluye todas las posibles situaciones relacionadas al calor que pueden ocurrir en el lugar de trabajo. El usuario debe estar consciente de que, aunque OSHA tratará de mantener la información actualizada y precisa, podría haber un retraso entre la publicación oficial de los materiales de OSHA y la modificación de esta aplicación. El Registro Federal y el Código de Reglamentos Federales siguen siendo las fuentes oficiales de información sobre normas publicadas por OSHA. Más aun, los cálculos realizados por esta aplicación se basan en la exactitud de los datos facilitados por el usuario. Por lo tanto, OSHA no puede garantizar que estos cálculos reflejen con precisión las condiciones en algún lugar de trabajo específico.\n\n" +
        	"Esta aplicación puede, a discreción del usuario, obtener datos meteorológicos directamente de la Administración Nacional Oceánica y Atmosférica (NOAA), con base en la ubicación actual del usuario (para teléfonos con GPS).  La información enviada a NOAA a través de esta aplicación, incluyendo la ubicación del usuario, se utiliza únicamente para la generación de datos transmitidos al usuario a través de la aplicación, y no es enviada a OSHA o cualquier otra entidad para ningún otro propósito.";
        }	
        
        vfm.add(new LabelField(content1, Field.FOCUSABLE){
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
                        return "More information"; 
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
                        return "Home screen"; 
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
	