package heatIndex;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;

import net.rim.device.api.ui.accessibility.AccessibilityManager;
import net.rim.device.api.ui.component.Dialog;
import com.rim.samples.device.accessibilitydemo.screenreaderdemo.ScreenReader;

/**
 * This class extends the UiApplication class, providing a graphical user interface.
 */
//public class HeatIndex extends UiApplication {
public final class HeatIndex extends UiApplication
{
	//UNCOMMENT THIS FOR SCREEN READER TESTING
    //private static ScreenReader _screenReader;
	//ScreenReader _screenReader;
    /**
     * Entry point for application.
     * @param args Command line arguments (not used)
     */
    public static void main( String[] args ) 
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
    	HeatIndex theApp = new HeatIndex();
        theApp.enterEventDispatcher();
    }
    
    /**
     * Prevent the save dialog from being displayed.
     *
     * @see net.rim.device.api.ui.container.MainScreen#onSavePrompt()
     */
    /*
   public boolean onSavePrompt()
   {
       return true;
   } */   
         
    // Constructor
    //public HeatIndex() //UNCOMMENT THIS FOR SCREEN READER TESTING
    /*{       
        try
        { 
            // Only one listener can be registered
            if(!AccessibilityManager.isAccessibleEventListenerRegistered())
            {
                _screenReader = new ScreenReader();
                AccessibilityManager.setAccessibleEventListener(_screenReader);                    
            }
            else
            {
                System.out.println("An AccessibleEventListener is already registered");
            }               
        }
        catch(net.rim.device.api.system.UnsupportedOperationException uoe)
        {
            UiApplication.getUiApplication().invokeLater(new Runnable()
            {
                public void run()
                {
                    Dialog.alert("Accessibilty not supported on this device");
                }                
            });            
        }*/

    /**
     * Creates a new HeatIndexClass object
     */
    public HeatIndex() { //COMMENT THIS LINE OUT FOR SCREEN READER TESTING
        // Push a screen onto the UI stack for rendering.
        pushScreen( new HeatIndexMainScreen() );
   
    
    }
    
   
    /**
     * @see Screen#close()
     */
    /*public void close() 
    {
        // Unregister the accessible event listener when exiting the app
        if(AccessibilityManager.isAccessibleEventListenerRegistered())
        {
            AccessibilityManager.removeAccessibleEventListener(_screenReader); 
        }
                    
        /*super.close();
    }*/       
}


