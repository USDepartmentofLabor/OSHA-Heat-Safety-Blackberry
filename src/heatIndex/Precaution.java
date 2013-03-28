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
import net.rim.device.api.ui.component.table.SimpleList;
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




public class Precaution extends MainScreen {
	private LabelField hi_result;
    private LabelField time_result;      
    private LabelField risk_result;  
    
	//for accessibility
    private IconToolbarComponent _iconToolbar;
    
	public Precaution() {
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
        String headerText1 = "";
        String headerText2 = "";
        String headerText3 = "";
        if(MyValues.getMyLanguage() == "English") {
        	titleText = "Precautions";
        	headerText1 = "Heat Index: ";
            headerText2 = "Time: ";
            headerText3 = "Risk Level: ";
        } else if(MyValues.getMyLanguage() == "Spanish") {
        	titleText = "Precauciones";
        	headerText1 = "Índice de Calor: ";
            headerText2 = "Hora: ";
            headerText3 = "Nivel de Riesgo: ";
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
        
        hi_result = new LabelField( headerText1, LabelField.USE_ALL_WIDTH );
        add( hi_result );        
        
        double HIdoubleP = MyValues.getMyValues();        
        Double myResult = new Double(HIdoubleP);
        hi_result.setText(headerText1 + myResult.toString()+" °F");  
        
        if(MyValues.getMyTime() != "") {
        	time_result = new LabelField( headerText2, LabelField.USE_ALL_WIDTH);
            add( time_result );        
            String myTime =  MyValues.getMyTime();
            time_result.setText(headerText2 + myTime);
        }
        
        risk_result = new LabelField( headerText3, LabelField.USE_ALL_WIDTH);
        add( risk_result );
         
        String myRiskLevel;
        //risk_result.setText(headerText3 + myRiskLevel);
        if(MyValues.getMyRiskLevel() == "") { myRiskLevel = "Risk Level: LOWER (CAUTION)"; }
        else { myRiskLevel = headerText3 + MyValues.getMyRiskLevel(); } 
        
        if(MyValues.getMyRiskLevel() == "VERY HIGH TO EXTREME") {
        	Background bg= BackgroundFactory.createSolidBackground(Color.RED);
        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
        	
        	String intro1 = "";
			String intro2 = "";
			String intro3 = "";
			String intro4 = "";
			String title1 = "";
			String list1 = "";
			String title2 = "";
			String list2 = "";
			String title3 = "";
			String list3 = "";
			String title4 = "";
			String list4 = "";
			String title5 = "";
			String list5 = "";
            if(MyValues.getMyLanguage() == "English") {
            	intro1 = "\nConditions are dangerous! Heat illness can develop faster and be more serious at " + 
								"this risk level. Extra measures are recommended.\n\n";
				intro2 = "Reschedule all non-essential work for days when the heat index is lower. Move " + 
								"essential work to the coolest part of the day - start earlier, split the work " + 
								"shift, or work evening/night shifts.\n\n";
				intro3 = "If emergency or essential work must be done at this heat index, then a designated " + 
								"knowledgeable person should be on-site to modify work activities and set work/rest " + 
								"schedules. STOP WORK if protective measures are not possible.\n\n";
				intro4 = "Important reminders (see OSHA's heat illness campaign site for more information).\n\n";
				title1 = "Water and Shade";
				list1 = "\n \u2022 Drinking water must be on site. Establish a water drinking schedule (about" +
								"4 cups/hour).\n" +
								" \u2022 Set up cool, shaded rest areas.\n\n";
				title2 = "Emergency planning and response";
				list2 = "\n \u2022 Make sure medical help (clinic, hospital, emergency services) is nearby - " +
								"if not, first aid must be on site.\n" +
								" \u2022 Call 911 and cool a worker who is unconscious, confused, or uncoordinated." +
								"This may be heat stroke, which is a medical emergency.\n" + 
								" \u2022 Know where you are in case you need to call 911.\n\n";
				title3 = "Work and rest";
				list3 = "\n \u2022 Gradually build up the workload and allow more breaks over a week (acclimatize " +
								"workers).\n" +
								" \u2022 Establish and follow a work/rest schedule (amount of work time and rest time " +
								"each work hour)\n" +
								"    \u25CB Take routine rest breaks in cool, shaded area.\n" +
								"    \u25CB Remove protective clothing (if possible) while resting.\n" +
								"    \u25CB Increase rest time as heat index rises or if workers show signs of heat " +
								"illness.\n" +
								" \u2022 Adjust work activities\n" + 
								"    \u25CB Do heavy tasks earlier in the day\n" +
								"    \u25CB Decrease the pace of heavy job tasks\n" +
								"    \u25CB Look for other ways to adjust work activities (add moreworkers, rotate " +
								"jobs).\n" +
								" \u2022 Provide cooling measures (cool vests, cool mist stations).\n" +
								" \u2022 Use more precautions for work in layers of protective clothing and in the " +
								"direct sun - see OSHA's heat illness campaign site.\n" +
								" \u2022 Use sunscreen and wear light-colored clothing.\n\n";
				title4 = "Training";
				list4 = "\n \u2022 Alert all workers of extreme heat hazard.\n" +
								" \u2022 Review heat-related illness topics before work begins.\n" +
								"    \u25CB How to recognize heat-related illness.\n" +
								"    \u25CB How to prevent it.\n" +
								"    \u25CB What to do if someone gets sick.\n\n";
				title5 = "Watching out for heat illness";
				list5 = "\n \u2022 Check heart rate, temperature, or other physical signs of heat exposure at rest " +
								"breaks.\n" +
								" \u2022 Watch out for each other.\n" +
								" \u2022 Set up a buddy system.\n" +
								" \u2022 Supervisors should maintain communications at all times with crew and watch for " +
								"signs of heat illness.\n\n";
            } else if(MyValues.getMyLanguage() == "Spanish") {
            	myRiskLevel = "Nivel de Riesgo: MUY ALTO A EXTREMO";
            	intro1 = "\n¡Las condiciones son peligrosas! La enfermedad a causa del calor puede desarrollarse más rápido y ser más grave en este nivel de riesgo. Se recomienda tomar medidas de precaución adicionales.\n\n";
				intro2 = "Reprograme todo el trabajo que no sea esencial para realizarlo los días en que el índice de calor sea menor. Mueva  el trabajo esencial para la parte más fresca del día – comience antes, divida los turnos de trabajo o trabaje en turnos de la tarde/noche.\n\n";
				intro3 = "Si debe realizarse trabajo de emergencia o esencial en este índice de calor, entonces una persona designada y bien informada debe estar en el lugar de trabajo para modificar las actividades de trabajo y asignar los horarios de trabajo/descansos. DETENGA EL TRABAJO si no es posible tomar las medidas de protección.\n\n";
				intro4 = "Avisos importantes (visite el sitio web de la campaña para prevenir la enfermedad a causa del calor de OSHA para obtener más información y recursos):\n\n";
				title1 = "Agua y sombra:";
				list1 = "\n \u2022 Debe haber agua potable en el lugar de trabajo. Asigne un horario para beber agua (aproximadamente 4 vasos cada hora).\n" +
								" \u2022 Establezca áreas de descanso frescas y bajo la sombra.\n\n";
				title2 = "Planes para responder a emergencias:";
				list2 = "\n \u2022 Asegúrese de que asistencia médica (clínica, hospital, servicios de emergencia) esté cerca, si no es así, se debe contar con primeros auxilios en el lugar de trabajo.\n" +
								" \u2022 Llame al 911 y refresque al trabajador que está inconsciente, confundido o que le falte coordinación. Podría tratarse de insolación, la cual es una emergencia médica.\n" + 
								" \u2022 Sepa dónde está ubicado en caso de que necesite llamar al 911.\n\n";
				title3 = "Trabajo y descansos:";
				list3 = "\n \u2022 Aumente gradualmente la carga de trabajo y permita más descansos durante una semana (aclimatíze a los trabajadores).\n" +
								" \u2022 Establezca y siga un horario de trabajo/descansos (cantidad de tiempo de trabajo y descanso en cada hora de trabajo)\n" +
								"    \u25CB Programe descansos rutinarios en un lugar fresco y a la sombra.\n" +
								"    \u25CB Quítese las prendas de protección (si es posible) mientras descansa\n" +
								"    \u25CB Aumente el tiempo de descanso a medida que aumenta el índice de calor o si los trabajadores muestran signos de enfermedad a causa del calor\n" +
								" \u2022 Ajuste las actividades de trabajo\n" + 
								"    \u25CB Programe las tareas pesadas al principio del día \n" +
								"    \u25CB Disminuya el ritmo para las tareas de trabajo pesado\n" +
								"    \u25CB Añada más trabajadores a las tareas de trabajo pesado\n" +
								"    \u25CB Rote a los trabajadores entre tareas pesadas y ligeras\n" +
								" \u2022 Proporcione medios de enfriamiento (chalecos de enfriamiento, estaciones con humidificadores de aire frío)\n" +
								" \u2022 Tome más precauciones cuando se trabaja usando varias prendas de protección en capas o bajo el sol directo - ver el sitio web de la campaña para prevenir la enfermedad a causa del calor de OSHA\n" +
								" \u2022 Use protector solar y ropa de colores claros.\n\n";
				title4 = "Capacitación:";
				list4 = "\n \u2022 Alerte a todos los trabajadores sobre el peligro del calor extremo.\n" +
								" \u2022 Repase con los trabajadores los temas relacionados a la enfermedad causada por el calor antes de comenzar el trabajo\n" +
								"    \u25CB ¿Cómo reconocer la enfermedad a causa del calor? \n" +
								"    \u25CB ¿Cómo prevenirla?\n" +
								"    \u25CB ¿Qué hacer si alguien se enferma?\n\n";
				title5 = "Estar pendiente de los síntomas de enfermedad a causa del calor: ";
				list5 = "\n \u2022 Revise el pulso, la temperatura u otros signos físicos de exposición al calor durante los descansos.\n" +
								" \u2022 Los trabajadores deben estar atentos unos de otros. \n" +
								" \u2022 Prepare un sistema de apoyo entre pares de compañeros. \n" +
								" \u2022 Los supervisores deben estar en comunicación en todo momento con la cuadrilla y estar atentos a los síntomas de enfermedad a causa del calor.\n\n";
            } 

        	String intros = intro1 + intro2 + intro3 + intro4;
        	
        	LabelField header1 = new LabelField(title1, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.RED);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header1.setMargin(15,0,10,0);
        	
        	BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("img8.png"));
        	
        	LabelField header2 = new LabelField(title2, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.RED);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header2.setMargin(15,0,10,0);
        	
        	BitmapField image2 = new BitmapField(Bitmap.getBitmapResource("img9.png"));
        	
        	LabelField header3 = new LabelField(title3, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.RED);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header3.setMargin(15,0,10,0);
        	
        	BitmapField image3 = new BitmapField(Bitmap.getBitmapResource("img2.png"));
        	
        	LabelField header4 = new LabelField(title4, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.RED);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header4.setMargin(15,0,10,0);
        	
        	BitmapField image4 = new BitmapField(Bitmap.getBitmapResource("img7.png"));
        	
        	LabelField header5 = new LabelField(title5, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.RED);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header5.setMargin(15,0,10,0);
        	
        	BitmapField image5 = new BitmapField(Bitmap.getBitmapResource("img3.png"));
        	
        	risk_result.setText(myRiskLevel);
        	vfm.add(new LabelField(intros,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(header1);
        	vfm.add(new LabelField(list1,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image1);
        	vfm.add(header2);
        	vfm.add(new LabelField(list2,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image2);
        	vfm.add(header3);
        	vfm.add(new LabelField(list3,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image3);
        	vfm.add(header4);
        	vfm.add(new LabelField(list4,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image4);
        	vfm.add(header5);
        	vfm.add(new LabelField(list5,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image5);
        	add(vfm);
        	
        } else if(MyValues.getMyRiskLevel() == "HIGH") {
        	Background bg= BackgroundFactory.createSolidBackground(Color.ORANGE); //rgb 247, 141, 0
        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
        	
        	String intro1 = "";
			String intro2 = "";
			String title1 = "";
			String list1 = "";
			String title2 = "";
			String list2 = "";
			String title3 = "";
			String list3 = "";
			String title4 = "";
			String list4 = "";
			String title5 = "";
			String list5 = "";
            if(MyValues.getMyLanguage() == "English") {
            	intro1 = "\nConditions are hazardous. Extra measures are recommended. Alert everyone of high " +
								"risk conditions and review protective measures. A designated knowledgeable person " + 
								"should be on site to modify work activities and set work/test schedules.\n\n";
				intro2 = "Reminders to help keep workers safe (see OSHA's heat illness campaign site for more " +
								"information and resources):\n\n";
				title1 = "Water and Shade";
				list1 = "\n \u2022 Drinking water must be on site.\n" + 
								" \u2022 Drinking plenty of water, even if you're not thirsty - every 15 minutes " +
								"(about 4 cups/hour).\n" +
								" \u2022 Set up cool, shaded rest areas.\n\n";
				title2 = "Emergency planning and response";
				list2 = "\n \u2022 Make sure medical help (clinic, hospital, emergency services) is nearby - " +
								"if not, first aid must be on site.\n" +
								" \u2022 Call 911 and cool a worker who is unconscious, confused, or uncoordinated." +
								"This may be heat stroke, which is a medical emergency.\n" + 
								" \u2022 Know where you are in case you need to call 911.\n\n";
				title3 = "Work and rest";
				list3 = "\n \u2022 Gradually build up the workload and allow more breaks over a week (acclimatize " +
								"workers).\n" +
								" \u2022 Establish and follow a work/rest schedule (amount of work time and rest time " +
								"each work hour)\n" +
								"    \u25CB Take routine rest breaks in cool, shaded area.\n" +
								"    \u25CB Remove protective clothing (if possible) while resting.\n" +
								"    \u25CB Increase rest time as heat index rises or if workers show signs of heat " +
								"illness.\n" +
								" \u2022 Adjust work activities\n" + 
								"    \u25CB Do heavy tasks earlier in the day\n" +
								"    \u25CB Decrease the pace of heavy job tasks\n" +
								"    \u25CB Look for other ways to adjust work activities (add moreworkers, rotate " +
								"jobs).\n" +
								" \u2022 Use more precautions for work in layers of protective clothing and in the " +
								"direct sun - see OSHA's heat illness campaign site.\n" +
								" \u2022 Use sunscreen and wear light-colored clothing.\n\n";
				title4 = "Training";
				list4 = "\n \u2022 Review heat-related illness topics before work begins.\n" +
								"    \u25CB How to recognize heat-related illness.\n" +
								"    \u25CB How to prevent it.\n" +
								"    \u25CB What to do if someone gets sick.\n\n";
				title5 = "Watching out for heat illness";
				list5 = "\n \u2022 Watch out for each other.\n" +
								" \u2022 Set up a buddy system.\n" +
								" \u2022 Supervisors should maintain communications at all times with crew and watch for " +
								"signs of heat illness.";
            } else if(MyValues.getMyLanguage() == "Spanish") {
            	myRiskLevel = "Nivel de Riesgo: ALTO";
            	intro1 = "\nLas condiciones son peligrosas. Se recomienda tomar medidas de precaución adicionales. Alerte a todos acerca de las condiciones de alto riesgo y repase las medidas de protección. Una persona designada que este informada debe estar en el lugar de trabajo para modificar las actividades de trabajo y asignar los horarios de trabajo/descansos.\n\n";
				intro2 = "Avisos para ayudar a mantener la seguridad de los trabajadores (visite el sitio web de la campaña para prevenir la enfermedad a causa del calor de OSHA para obtener más información y recursos):\n\n";
				title1 = "Agua y sombra:";
				list1 = "\n \u2022 Debe haber agua potable en el lugar de trabajo.\n" +
								" \u2022 Beba cantidades abundantes de agua fría, incluso si no tiene sed – cada 15 minutos (aproximadamente 4 vasos por hora).\n" +
								" \u2022 Establezca áreas de descanso frescas y bajo la sombra.\n\n";
				title2 = "Planes para responder a emergencias:";
				list2 = "\n \u2022 Asegúrese de que asistencia médica (clínica, hospital, servicios de emergencia) esté cerca, si no es así, se debe contar con primeros auxilios en el lugar de trabajo.\n" +
								" \u2022 Llame al 911 y refresque al trabajador que está inconsciente, confundido, o que le falte coordinación. Podría tratarse de insolación, la cual es una emergencia médica.\n" + 
								" \u2022 Sepa dónde está ubicado en caso de que necesite llamar al 911.\n\n";
				title3 = "Trabajo y descansos:";
				list3 = "\n \u2022 Aumente gradualmente la carga de trabajo y permita más descansos durante una semana (aclimatíze a los trabajadores).\n" +
								" \u2022 Establezca y siga un horario de trabajo/descansos (cantidad de tiempo de trabajo y descanso en cada hora de trabajo)\n" +
								"    \u25CB Programe descansos rutinarios en un lugar fresco y a la sombra.\n" +
								"    \u25CB Quítese las prendas de protección (si es posible) mientras descansa\n" +
								"    \u25CB Aumente el tiempo de descanso a medida que aumenta el índice de calor o si los trabajadores muestran signos de enfermedad a causa del calor \n" +
								" \u2022 Ajuste las actividades de trabajo\n" + 
								"    \u25CB Programe las tareas pesadas más temprano en el día\n" +
								"    \u25CB Disminuya el ritmo de las tareas de trabajo pesado\n" +
								"    \u25CB Busque otras formas de ajustar las actividades de trabajo (agregue más trabajadores, rote puestos de trabajo)\n" +
								" \u2022 Tome más precauciones cuando se trabaja usando varias prendas de protección en capas o bajo el sol directo - ver el sitio web de la campaña para prevenir la enfermedad a causa del calor de OSHA\n" +
								" \u2022 Use protector solar y ropa de colores claros.\n\n";
				title4 = "Capacitación:";
				list4 = "\n \u2022 Repase los temas sobre la enfermedad causada por el calor antes de comenzar el trabajo\n" +
								"    \u25CB ¿Cómo reconocer la enfermedad a causa del calor?  \n" +
								"    \u25CB ¿Cómo prevenirla?\n" +
								"    \u25CB ¿Qué hacer si alguien se enferma?\n\n";
				title5 = "Estar pendiente de los síntomas de enfermedad a causa del calor: ";
				list5 = "\n \u2022 Los trabajadores deben estar atentos unos de otros.\n" +
								" \u2022 Los trabajadores deben estar atentos unos de otros. \n" +
								" \u2022 Prepare un sistema de apoyo entre pares de compañeros. \n" +
								" \u2022 Los supervisores deben estar en comunicación con la cuadrilla en todo momento y estar atentos a los síntomas de enfermedad a causa del calor.\n\n";
            } 
        	
        	String intros = intro1 + intro2;
        	
        	LabelField header1 = new LabelField(title1, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.ORANGE);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header1.setMargin(15,0,10,0);
        	
        	BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("img8.png"));
        	
        	LabelField header2 = new LabelField(title2, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.ORANGE);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header2.setMargin(15,0,10,0);
        	
        	BitmapField image2 = new BitmapField(Bitmap.getBitmapResource("img9.png"));
        	
        	LabelField header3 = new LabelField(title3, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.ORANGE);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header3.setMargin(15,0,10,0);
        	
        	BitmapField image3 = new BitmapField(Bitmap.getBitmapResource("img2.png"));
        	
        	LabelField header4 = new LabelField(title4, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.ORANGE);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header4.setMargin(15,0,10,0);
        	
        	BitmapField image4 = new BitmapField(Bitmap.getBitmapResource("img7.png"));
        	
        	LabelField header5 = new LabelField(title5, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.ORANGE);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header5.setMargin(15,0,10,0);
        	
        	BitmapField image5 = new BitmapField(Bitmap.getBitmapResource("img3.png"));
        	
        	risk_result.setText(myRiskLevel);
        	vfm.add(new LabelField(intros,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(header1);
        	vfm.add(new LabelField(list1,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image1);
        	vfm.add(header2);
        	vfm.add(new LabelField(list2,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image2);
        	vfm.add(header3);
        	vfm.add(new LabelField(list3,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image3);
        	vfm.add(header4);
        	vfm.add(new LabelField(list4,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image4);
        	vfm.add(header5);
        	vfm.add(new LabelField(list5,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image5);
        	add(vfm);
        	
        } else if(MyValues.getMyRiskLevel() == "MODERATE") {
        	Background bg= BackgroundFactory.createSolidBackground(Color.PEACHPUFF); //rgb 247, 141, 0
        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
        	
        	String intro = "";
			String title1 = "";
			String list1 = "";
			String title2 = "";
			String list2 = "";
			String title3 = "";
			String list3a = "";
			String list3b = "";
			String title4 = "";
			String list4 = "";
			String title5 = "";
			String list5 = "";
            if(MyValues.getMyLanguage() == "English") {
            	intro = "\nWorkers can be at increased risk for heat illness. Important reminders.";
				title1 = "Water and Shade";
				list1 = " \u2022 Drinking water must be on site.\n" + 
								" \u2022 Drinking plenty of water, even if you're not thirsty - every 15 minutes " +
								"(about 4 cups/hour).\n" +
								" \u2022 Set up cool, shaded rest areas.\n\n";
				title2 = "Emergency planning and response";
				list2 = " \u2022 Make sure medical help (clinic, hospital, emergency services) is nearby - " +
								"if not, first aid must be on site.\n" +
								" \u2022 Call 911 and cool a worker who is unconscious, confused, or uncoordinated." +
								"This may be heat stroke, which is a medical emergency.\n" + 
								" \u2022 Know where you are in case you need to call 911.\n\n";
				title3 = "Work and rest";
				list3a = "\n \u2022 Schedule frequent breaks in cool, shaded area.\n";
				list3b = "\n \u2022 Gradually build up the workload and allow more breaks over a week (acclimatize " +
								"workers).\n" +
								" \u2022 Establish and follow a work/rest schedule (amount of work time and rest time " +
								"each work hour)\n" +
								"    \u25CB Take routine rest breaks in cool, shaded area.\n" +
								"    \u25CB Remove protective clothing (if possible) while resting.\n" +
								"    \u25CB Increase rest time as heat index rises or if workers show signs of heat " +
								"illness.\n" +
								" \u2022 Adjust work activities\n" + 
								"    \u25CB Do heavy tasks earlier in the day\n" +
								"    \u25CB Decrease the pace of heavy job tasks\n" +
								"    \u25CB Look for other ways to adjust work activities (add moreworkers, rotate " +
								"jobs).\n" +
								" \u2022 Use more precautions for work in layers of protective clothing and in the " +
								"direct sun - see OSHA's heat illness campaign site.\n" +
								" \u2022 Use sunscreen and wear light-colored clothing.\n\n";
				title4 = "Training";
				list4 = "\n \u2022 Review heat-related illness topics before work begins.\n" +
								"    \u25CB How to recognize heat-related illness.\n" +
								"    \u25CB How to prevent it.\n" +
								"    \u25CB What to do if someone gets sick.\n\n";
				title5 = "Watching out for heat illness";
				list5 = "\n \u2022 Watch out for each other.\n" +
								" \u2022 Set up a buddy system.\n\n";
            } else if(MyValues.getMyLanguage() == "Spanish") {
            	myRiskLevel = "Nivel de Riesgo: MODERADO";
            	intro = "\nLos trabajadores pueden estar en mayor riesgo de sufrir enfermedad a causa del calor. Avisos importantes:\n\n";
				title1 = "Agua y sombra:";
				list1 = "\n \u2022 Debe haber agua potable en el lugar de trabajo.\n" +
								" \u2022 Beba agua en cantidades abundantes, incluso si no tiene sed – cada 15 minutos (aproximadamente 4 vasos cada hora).\n" +
								" \u2022 Establezca áreas de descanso frescas y bajo sombra.\n\n";
				title2 = "Planes para responder a emergencias:";
				list2 = "\n \u2022 Asegúrese de que asistencia médica (clínica, hospital, servicios de emergencia) esté cerca, si no es así, se debe contar con primeros auxilios en el lugar de trabajo.\n" +
								" \u2022 Llame al 911 y refresque al trabajador (LINK to first aid for heat stroke) que está inconsciente, confundido o que le falte coordinación. Podría tratarse de, la cual es una emergencia médica.\n" + 
								" \u2022 Sepa dónde está ubicado en caso de que necesite llamar al 911.\n\n";
				title3 = "Trabajo y descansos:";
				list3a = "\n \u2022 Programe descansos frecuentes en un lugar fresco y a la sombra.\n" +
								" \u2022 Aumente gradualmente la carga de trabajo y permita más descansos durante la primera semana a los trabajadores nuevos o aquellos que regresan al trabajo (aclimatíze a los trabajadores).\n" +
								" \u2022 Tome más precauciones en caso de trabajos pesados, el uso de prendas protectoras en capas o trabajo bajo el sol directo.\n" + 
								"    \u25CB Programe las actividades para un momento en el cual el índice de calor sea menor\n" +
								"    \u25CB Establezca y siga horarios de trabajo/descansos (cantidad de tiempo de trabajo y descanso a cada hora de trabajo)\n" +
								"    \u25CB Disminuya el ritmo para las tareas de trabajo pesado\n" +
								"    \u25CB Supervise a los trabajadores para detectar signos de enfermedad a causa del calor\n" +
								" \u2022 Use protector solar y ropa de colores claros.\n";				
				title4 = "Capacitación:";
				list4 = "\n \u2022 Asegúrese de que todos repasen los temas sobre la enfermedad a causa del calor\n" +
								"    \u25CB ¿Cómo reconocer la enfermedad a causa del calor? \n" +
								"    \u25CB ¿Cómo prevenirla?\n" +
								"    \u25CB ¿Qué hacer si alguien se enferma?\n\n";
				title5 = "Estar pendiente de los síntomas de enfermedad a causa del calor: ";
				list5 = "\n \u2022 Los trabajadores deben estar atentos unos de otros.\n" +
								" \u2022 Prepare un sistema de apoyo entre pares de compañeros.\n";
            }
        	
        	LabelField header1 = new LabelField(title1, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.PEACHPUFF);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header1.setMargin(15,0,10,0);
        	
        	BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("img8.png"));
        	
        	LabelField header2 = new LabelField(title2, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.PEACHPUFF);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header2.setMargin(15,0,10,0);
        	
        	BitmapField image2 = new BitmapField(Bitmap.getBitmapResource("img9.png"));
        	
        	LabelField header3 = new LabelField(title3, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.PEACHPUFF);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header3.setMargin(15,0,10,0);
        	
        	BitmapField image3 = new BitmapField(Bitmap.getBitmapResource("img5.png"));
        	        	
        	BitmapField image4 = new BitmapField(Bitmap.getBitmapResource("img2.png"));
        	
        	LabelField header4 = new LabelField(title4, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.PEACHPUFF);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header4.setMargin(15,0,10,0);
        	
        	BitmapField image5 = new BitmapField(Bitmap.getBitmapResource("img7.png"));
        	
        	LabelField header5 = new LabelField(title5, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.PEACHPUFF);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header5.setMargin(15,0,10,0);
        	
        	BitmapField image6 = new BitmapField(Bitmap.getBitmapResource("img3.png"));
        	
        	risk_result.setText(myRiskLevel);
        	vfm.add(new LabelField(intro,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(header1);
        	vfm.add(new LabelField(list1,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image1);
        	vfm.add(header2);
        	vfm.add(new LabelField(list2,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image2);
        	vfm.add(header3);
        	vfm.add(new LabelField(list3a,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image3);
        	vfm.add(new LabelField(list3b,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image4);
        	vfm.add(header4);
        	vfm.add(new LabelField(list4,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image5);
        	vfm.add(header5);
        	vfm.add(new LabelField(list5,LabelField.FOCUSABLE) {
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image6);
        	add(vfm);
        	
        } else { // LOWER (CAUTION)
        	Background bg= BackgroundFactory.createSolidBackground(Color.YELLOW); 
        	risk_result.setBackground(Field.VISUAL_STATE_NORMAL,bg);
        	
        	String intro = "";
			String title1 = "";
			String list1 = "";
			String title2 = "";
			String list2 = "";
			String title3 = "";
			String list3 = "";
			String title4 = "";
			String list4 = "";
            if(MyValues.getMyLanguage() == "English") {
            	intro = "\nMost people can work safely. These good practice reminders can help:\n\n";
				title1 = "Water and Shade";
				list1 = "\n \u2022 Drinking water must be on site.\n" +
								" \u2022 Drink plenty of water, even if you're not thirsty.\n\n";
				title2 = "Emergency planning and response";
				list2 = " \u2022 Make sure medical help (clinic, hospital, emergency services) is nearby - if " +
								"not, first aid must be on site.\n" +
								" \u2022 Know where you are in case you need to call 911.\n\n";
				title3 = "Work and rest";
				list3 = " \u2022 Gradually build up workload and allow more breaks over the first week for new or " +
								"returning workers doing heavy work (acclimatize workers).\n" +
								" \u2022 Use more precautions for layers of protective clothing and work in direct sun.\n" +
								" \u2022 Use sunscreen and wear light-colored clothing.\n\n";
				title4 = "Training";
				list4 = " \u2022 Train on the signs and symptoms of heat illness and what to do in an emergency." +
								" \u2022 Plan ahead for hotter weather.\n\n";
            } else if(MyValues.getMyLanguage() == "Spanish") {
            	myRiskLevel = "Nivel de Riesgo: MÁS BAJO (PRECAUCIÓN)";
            	intro = "\nLa mayoría de las personas pueden trabajar de forma segura.  Avisos de buenas prácticas que pueden ser útiles:\n\n";
				title1 = "Agua y sombra:";
				list1 = "\n \u2022 Debe haber agua potable en el lugar de trabajo.\n" +
								" \u2022 Beba agua en cantidades abundantes, aunque no tenga sed.\n\n";
				title2 = "Planes para responder a emergencias:";
				list2 = "\n \u2022 Asegúrese de que asistencia médica (clínica, hospital, servicios de emergencia) esté cerca, si no es así, se debe contar con primeros auxilios en el lugar de trabajo.\n" +
								" \u2022 Sepa dónde está ubicado en caso de que necesite llamar al 911.\n\n";
				title3 = "Trabajo y descansos:";
				list3 = "\n \u2022 Aumente gradualmente la carga de trabajo y permita más descansos durante la primera semana a los trabajadores nuevos o aquellos que regresan a un trabajo pesado (aclimatíze a los trabajadores).\n" +
								" \u2022 Tome más precauciones si está usando prendas protectoras en capas o trabajando bajo el sol directo.\n" +
								" \u2022 Use protector solar y ropa de colores claros.\n\n";
				title4 = "Capacitación:";
				list4 = "\n \u2022 Proporcione capacitación sobre los signos y síntomas de la enfermedad a causa del calor y qué hacer en caso de una emergencia.\n" +
								" \u2022 Planifique con anticipación cuando haya climas más cálidos.\n\n";
            } 
        	        	  	
        	//String header1 = "Water and Shade";
        	LabelField header1 = new LabelField(title1, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.YELLOW);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header1.setMargin(15,0,10,0);
        	        	
            BitmapField image1 = new BitmapField(Bitmap.getBitmapResource("img1.png"));
        	
        	//String header2 = "Emergency planning and response";
           	LabelField header2 = new LabelField(title2, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.YELLOW);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header2.setMargin(15,0,10,0);
        	        	
        	BitmapField image2 = new BitmapField(Bitmap.getBitmapResource("img4.png"));
        	
        	//String header3 = "Work and rest";
           	LabelField header3 = new LabelField(title3, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.YELLOW);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header3.setMargin(15,0,10,0);
        	        	
        	BitmapField image3 = new BitmapField(Bitmap.getBitmapResource("img2.png"));
        	
        	//String header4 = "Training";
           	LabelField header4 = new LabelField(title4, LabelField.USE_ALL_WIDTH | LabelField.VCENTER | DrawStyle.VCENTER){
        		public void paint(Graphics g){ 
            		g.setBackgroundColor(Color.YELLOW);
            		g.clear();
            		super.paint(g); 
            	}
            	protected void layout(int width, int height) {
                    super.layout(width, height);
                    this.setExtent(this.getWidth(), 25);
                }
        	};
        	header4.setMargin(15,0,10,0);
        	
        	BitmapField image4 = new BitmapField(Bitmap.getBitmapResource("img7.png"));
        	
        	risk_result.setText(myRiskLevel);
        	vfm.add(new LabelField(intro,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(header1);
        	vfm.add(new LabelField(list1,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image1);
        	vfm.add(header2);
        	vfm.add(new LabelField(list2,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image2);
        	vfm.add(header3);
        	vfm.add(new LabelField(list3,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image3);
        	vfm.add(header4);
        	vfm.add(new LabelField(list4,LabelField.FOCUSABLE){
        		protected void drawFocus(Graphics g, boolean on) {
        			XYRect rect = new XYRect();
        			getFocusRect(rect);
        			drawHighlightRegion(g, HIGHLIGHT_FOCUS, false, rect.x, rect.y, rect.width, rect.height);
        		}
        	});
        	vfm.add(image4);
        	add(vfm);
        }
        
                   
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
	