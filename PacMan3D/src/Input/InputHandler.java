/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;

/**
 *
 * @author Kuba
 */
public class InputHandler
{
    private InputManager inputManager;
    
    public static String[] MappingsStr =
    {
       "KeyUp", "KeyDown", "KeyLeft", "KeyRight", "MouseWheelUp", "MouseWheelDown", "KeyS", "KeyC", "MouseLeft", "KeyK"
    };
    
    public static enum Mappings
    {
        KeyUp, KeyDown, KeyLeft, KeyRight, MouseWheelUp, MouseWheelDown, KeyS, KeyC, MouseLeft, KeyK
    }
    
    public static boolean[] inputStates = new boolean[MappingsStr.length];
    
    public InputHandler(InputManager inputManager)
    {
        this.inputManager = inputManager;
        
        inputManager.addMapping(MappingsStr[0], new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(MappingsStr[1], new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(MappingsStr[2], new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(MappingsStr[3], new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(MappingsStr[4], new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping(MappingsStr[5], new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping(MappingsStr[6], new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(MappingsStr[7], new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping(MappingsStr[8], new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping(MappingsStr[9], new KeyTrigger(KeyInput.KEY_K));
        
        ActionListener actionListener = new ActionListener()
        {
            public void onAction(String name, boolean isPressed, float tpf) 
            {
                if(name.equals(MappingsStr[Mappings.KeyUp.ordinal()]))
                {
                    inputStates[Mappings.KeyUp.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyDown.ordinal()]))
                {
                    inputStates[Mappings.KeyDown.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyLeft.ordinal()]))
                {
                    inputStates[Mappings.KeyLeft.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyRight.ordinal()]))
                {
                    inputStates[Mappings.KeyRight.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyS.ordinal()]))
                {
                    inputStates[Mappings.KeyS.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyC.ordinal()]))
                {
                    inputStates[Mappings.KeyC.ordinal()] = isPressed;
                }

                if(name.equals(MappingsStr[Mappings.KeyK.ordinal()]))
                {
                    inputStates[Mappings.KeyK.ordinal()] = isPressed;
                }
                
                if(name.equals(MappingsStr[Mappings.MouseLeft.ordinal()]) && !isPressed)
                {
                    inputStates[Mappings.MouseLeft.ordinal()] = true;
                    System.out.println("LMB" + " " + name + " " + tpf);
                }
                
                //System.out.println("onAction");
                
            }
        };
        
        AnalogListener analogListener = new AnalogListener()
        {
            public void onAnalog(String name, float value, float tpf) 
            {
                if(name.equals(MappingsStr[Mappings.MouseWheelUp.ordinal()]))
                {
                    inputStates[Mappings.MouseWheelUp.ordinal()] = true;
                }

                if(name.equals(MappingsStr[Mappings.MouseWheelDown.ordinal()]))
                {
                    inputStates[Mappings.MouseWheelDown.ordinal()] = true;
                }                
            }
        };
                
        inputManager.addListener(actionListener, MappingsStr);
        inputManager.addListener(analogListener, MappingsStr);
    }
    
    
    
    public boolean getInputState(Mappings id)
    {
        return inputStates[id.ordinal()];
    }
    
    public void setInputState(Mappings id, boolean value)
    {
        inputStates[id.ordinal()] = value;
    }  
    
    public Vector2f getMousePos()
    {
        return inputManager.getCursorPosition();
    }
}
