/*
 * IconToolbarComponent.java
 *
 * Copyright © 1998-2010 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package heatIndex;

import java.util.*;
import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.accessibility.*;

/**
 * IconToolbarComponent is a customized accessible UI field implementation.
 * The field contains icons laid out in a horizontal configuration.  The icons
 * are themselves accessible and are represented by the inner class Icon. 
 */
public final class IconToolbarComponent extends Field implements AccessibleContext
{
    private Vector _icons;   
    private int[] _iconX;    
    private int _currentSelection;    
    private int _iconHeight;
    private int _state;
    
    // Constructor
    public IconToolbarComponent() 
    {
        super(Field.FOCUSABLE);
        
        _icons = new Vector();
        _currentSelection = -1;
        
        // The component can recieve focus
        _state = AccessibleState.FOCUSABLE;
    }
    
    /**
     * Adds an accessible icon to the icons vector
     * @param image The icon image
     * @param label The icon label
     */
    void addIcon(Bitmap image, String label) 
    {
        Icon icon = new Icon(label, image);
        _icons.addElement( icon );
        
        int iconHeight = image.getHeight();
        if(_iconHeight < iconHeight)
        {
            _iconHeight = iconHeight;
        }
        
        // The first icon will be selected by default
        if(_currentSelection == -1)
        {
            _currentSelection = 0;
            icon._state |= AccessibleState.FOCUSED;
        }
           
        // Causes the component to be repainted   
        invalidate();
    }
    
    /**
     * @see Field#layout(int, int)     
     */
    protected void layout(int width, int height)
    {        
        int iconCount = _icons.size();        
        
        // Layout icons horizontally
        if(iconCount != 0)
        {
            int xStep = width / iconCount;
            int x = 0;
            
            _iconX = new int[iconCount];
            for(int i = 0; i < iconCount; i++) 
            {
                _iconX[i] = x;
                x += xStep;
            }
                        
            setExtent(width, Math.min(_iconHeight, height));
        }
    }
    
    /**
     * @see Field#navigationMovement(int, int, int, int) 
     */
    protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
        // Move to next or prev icon
        if(dx > 0) 
        {
            if(_currentSelection < _icons.size() - 1)
            {
                // Update accessible state and notify screen reader
                Icon currentIcon = (Icon) _icons.elementAt(_currentSelection);
                currentIcon.focusChanged(false);
                
                // Update current selected icon in the toolbar
                _currentSelection ++;
                
                // Update accessible state and notify screen reader
                currentIcon = (Icon) _icons.elementAt(_currentSelection);
                currentIcon.focusChanged(true);
            }
            
            // Force a repaint of the component
            invalidate();
                        
            return true;
        } 
        else if(dx < 0) 
        {
            if(_currentSelection > 0)
            {
                // Update accessible state and notify screen reader
                Icon currentIcon = (Icon) _icons.elementAt(_currentSelection);
                currentIcon.focusChanged(false);
                
                _currentSelection --;

                // Update accessible state and notify screen reader
                currentIcon = (Icon) _icons.elementAt(_currentSelection);
                currentIcon.focusChanged(true);
            }
            
            // Force a repaint of the component
            invalidate();
            
            return true;
        }        
        return super.navigationMovement( dx, dy, status, time );
    }


    /**
     * @see Field#paint(Graphics)     
     */
    protected void paint( Graphics graphics )
    {
        // Paint all the icons at their locations.
        for( int i = 0; i < _icons.size(); i++ ) 
        {
            Icon icon = (Icon) _icons.elementAt( i );
            icon.paint( graphics, _iconX[i], 0);
        }        
    }
    
    /**
     * @see Field#drawFocus(Graphics, boolean)     
     */
    protected void drawFocus(Graphics graphics, boolean on)
    {        
        drawHighlightRegion(graphics, Field.HIGHLIGHT_FOCUS, on, _iconX[_currentSelection], 0, 48, _iconHeight);
    } 
    
    /**
     * @see Field#onFocus(int)
     */
    protected void onFocus( int direction )
    {
        super.onFocus( direction );
        
        // Update accessible state and notify screen reader
        int oldState = _state;
        _state = _state | AccessibleState.FOCUSED;
        
        // Notify screen reader if the panel is visible
        if(isVisible())
        {
            AccessibleEventDispatcher.dispatchAccessibleEvent( AccessibleContext.ACCESSIBLE_STATE_CHANGED, new Integer(oldState), new Integer(_state), this );
        }
    }
    
    /**
     * @see Field#onUnFocus()
     */
    protected void onUnfocus()
    {
        super.onUnfocus();
        
        // Update accessible state and notify screen reader
        int oldState = _state;
        _state = _state & ~AccessibleState.FOCUSED;
        
        // Notify screen reader if the panel is visible
        if(isVisible())
        {
            AccessibleEventDispatcher.dispatchAccessibleEvent( AccessibleContext.ACCESSIBLE_STATE_CHANGED, new Integer(oldState), new Integer(_state), this );
        }
    }
    
    /**
     * @see Field#getAccesssibleContext()     
     */
    public AccessibleContext getAccessibleContext()
    {
        return this;
    }
    
    
    // *********** AccessibleContext implementation ****************************
    
    /**
     * @see AccessibleContext#getAccessibleChildAt(int)     
     */
    public AccessibleContext getAccessibleChildAt(int index)
    {
        // Return icon child at the requested index
        return (Icon) _icons.elementAt( index );
    }
    
    /**
     * @see AccessibleContext#getAccessibleChildCount()
     */
    public int getAccessibleChildCount()
    {
        // Return total number of children
        return _icons.size();
    }

    /**
     * @see AccessibleContext#getAccessibleName()
     */
    public String getAccessibleName()
    {
        return " My Icon Toolbar ";
    }

    /**
     * @see AccessibleContext#getAccessibleParent()
     */
    public AccessibleContext getAccessibleParent()
    {
        // Return manager where the toolbar component is added
        Manager manager = getManager();
        return (manager != null) ? manager.getAccessibleContext() : null;
    }

    /**
     * @see AccessibleContext#getAccessibleRole()
     */
    public int getAccessibleRole()
    {
        // This is a panel with icons
        return AccessibleRole.PANEL;
    }

    /**
     * @see AccessibleContext#getAccessibleSelectionCount()
     */
    public int getAccessibleSelectionCount()
    {
        // Only one icon selected at a time
        return 1;
    }
    
    /**
     * @see AccessibleContext#getAccessibleSelectionAt(int)
     */
    public AccessibleContext getAccessibleSelectionAt(int index)
    {
        if(index == 0) 
        {
            return (Icon) _icons.elementAt(_currentSelection);
        }
        
        // Wrong selection index
        return null;
    }

    /**
     * @see AccessibleContext#getAccessibleStateSet()
     */
    public int getAccessibleStateSet()
    {      
        return _state;
    }
    
    /**
     * @see AccessibleContext#isAccessibleStateSet(int)
     */
    public boolean isAccessibleStateSet(int state)
    {
        return (getAccessibleStateSet() & state) != 0;
    }

    /**
     * @see AccessibleContext#getAccessibleChildSelected(int)
     */
    public boolean isAccessibleChildSelected(int index)
    {
        // Check whether child icon at the specified index is selected
        return _currentSelection == index;
    }
    
    /**
     * @see AccessibleContext#getAccessibleTable()
     */
    public AccessibleTable getAccessibleTable()
    {
        // This is a panel, not a table
        return null;
    }

    /**
     * @see AccessibleContext#getAccessibleText()
     */
    public AccessibleText getAccessibleText()
    {
        // This is a panel, not text
        return null;
    }

    /**
     * @see AccessibleContext#getAccessibleValue()
     */
    public AccessibleValue getAccessibleValue()
    {
        // This is a panel, not a numerical value
        return null;
    }
    
    
    // ********************** IconToolbarComponent child ***********************
    
    /**
     * The Icon class, serves as a child to the icon toolbar component
     */
    public class Icon implements AccessibleContext 
    {
        private String _label;
        private Bitmap _image;
        private int _state;
        
        // Constructor
        public Icon(String label, Bitmap image)
        {
            _label = label;
            _image = image;
            
            // Icon can be focused
            _state = AccessibleState.FOCUSABLE;
        }
        
        /**
         * Paints this icon using the provided Graphics object
         * @param graphics Graphics object used for drawing
         * @param x Horizontal position within the IconToolbarComponent at which to draw the icon
         * @param y Vertical position within the IconToolbarComponent at which to draw the icon
         */
        public void paint(Graphics graphics, int x, int y) 
        {
            graphics.drawBitmap(x, y, _image.getWidth(), _image.getHeight(), _image, 0, 0);
        }
        
        // *********** AccessibleContext implementation ****************************
                          
        /**
         * @see AccessibleContext#getAccessibleName()         
         */
        public String getAccessibleName()
        {
            // Icon has a name and it will be read by the reader
            return _label;
        }
        
        /**
         * @see AccessibleContext#getAccessibleParent()         
         */
        public AccessibleContext getAccessibleParent()
        {
            // Icon toolbar contains the icon
            return IconToolbarComponent.this;
        }       
        
        /**
         * @see AccessibleContext#getAccessibleRole()         
         */
        public int getAccessibleRole()
        {
            return AccessibleRole.ICON;
        }
        
        /**
         * @see AccessibleContext#getAccessibleChildAt(int)         
         */
        public AccessibleContext getAccessibleChildAt(int index)
        {
            // Icon contains no children
            return null;
        }
        
        /**
         * @see AccessibleContext#getAccessibleChildCount()         
         */
        public int getAccessibleChildCount()
        {
            // Icon contains no children
            return 0;
        }
        
        /**
         * @see AccessibleContext#getAccessibleSelectionAt(int)         
         */
        public AccessibleContext getAccessibleSelectionAt(int index)
        {
            // Icon contains no children
            return null;
        }
        
        /**
         * @see AccessibleContext#getAccessibleSelectionCount()         
         */
        public int getAccessibleSelectionCount()
        {
            // Icon contains no children.
            return 0;
        }
        
        /**
         * @see AccessibleContext#getAccessibleStateSet()         
         */
        public int getAccessibleStateSet()
        {
            return _state;
        }
        
        /**
         * @see AccessibleContext#getAccessibleTable()         
         */
        public AccessibleTable getAccessibleTable()
        {
            // This is an icon, not a table
            return null;
        }
        
        /**
         * @see AccessibleContext#getAccessibleText()         
         */
        public AccessibleText getAccessibleText()
        {
            // This is an icon, not text
            return null;
        }
        
        /**
         * @see AccessibleContext#getAccessibleValue()         
         */
        public AccessibleValue getAccessibleValue()
        {
            // Icon contains no numerical values
            return null;
        }
        
        /**
         * @see AccessibleContext#isAccessibleChildSelected(int)         
         */
        public boolean isAccessibleChildSelected(int index)
        {
            // Icon contains no children
            return false;
        }
        
        /**
         * @see AccessibleContext#isAccessibleStateSet(int)
         */
        public boolean isAccessibleStateSet(int state)
        {
            return (getAccessibleStateSet() & state) != 0;
        }
        
        /**
         * Called when the icon gains or loses focus
         * @param focusGained True if focus gained, false if focus lost
         */
        private void focusChanged(boolean focusGained)
        {
            // Update state
            int initialState = _state;
            if(focusGained)
            {
                _state |= AccessibleState.FOCUSED;
            }
            else
            {
                _state &= ~AccessibleState.FOCUSED;
            }
            
            // Notify screen reader if component is visible
            int currentState = _state;
            if(initialState != currentState && isVisible())
            {
                AccessibleEventDispatcher.dispatchAccessibleEvent(AccessibleContext.ACCESSIBLE_STATE_CHANGED,
                        new Integer(initialState), new Integer(currentState), this);
            }
        }
    }
}
