package mygame;

import Input.InputHandler;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.shadow.DirectionalLightShadowFilter;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static InputHandler inputHandler;
    
    Level level;
    
    Level[] levels;
    
    public static Level activeLevel;
    
    public static Player player;
    
    public static Camera mainCamera;    
    
    public static void main(String[] args) 
    {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() 
    {   
        initScene();
        
        mainCamera = this.getCamera();
    
        player = new Player(2, 1, assetManager, rootNode);
        
        this.getFlyByCamera().setEnabled(false);        
        //this.getFlyByCamera().setMoveSpeed(50.0f);
        inputHandler = new InputHandler(this.inputManager);
    }

    public void initScene()
    {
             
        levels = new Level[1];
        
        levels[0] = new Level(1, 12, 12, this.rootNode, this.assetManager);
        
        Main.activeLevel = levels[0];
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
            /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
            /** A white, directional light source */ 
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setDirection((new Vector3f(-0.5f, 0.5f, -0.5f)).normalizeLocal());
        sun2.setColor(ColorRGBA.White.mult(0.2f));
        rootNode.addLight(sun2); 
            /* this shadow needs a directional light */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, 1024, 2);
        dlsf.setLight(sun);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp); 

    }
    
    @Override
    public void simpleUpdate(float tpf) 
    {
        player.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
