/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import Input.InputHandler;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author nawro
 */
public class Player 
{
    int x;
    int y;
    
    int lives;
    
    boolean[] effects;
    
    int dir;
    
    float speed;
    
    Spatial playerModel;
    
    Node rootNode;
    Node localRootNode;
    
    boolean changedDirection;
    
    final float camHeight = 2.5f;
    
    public Player(int x, int y, AssetManager assetManager, Node rootNode)
    {        
        this.rootNode = rootNode;
        
        localRootNode = new Node("PlayerNode");
        
        this.x = x;
        this.y = y;
        this.lives = 3;
        this.speed = 2.0f;
        this.dir = 0;
        
        this.changedDirection = false;
        
        playerModel = assetManager.loadModel("Models/player.j3o");
        Material modelMat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        playerModel.setMaterial(modelMat);
        playerModel.rotate(0, (float) Math.PI, 0);
        
        localRootNode.setLocalTranslation(this.x, 0, this.y);
        
        localRootNode.attachChild(playerModel);
        
        rootNode.attachChild(localRootNode);
        
        Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(0.0f, camHeight, -camHeight));
        Main.mainCamera.lookAt(localRootNode.getWorldTranslation(), Vector3f.UNIT_Y);        
    }
    
    public void update(float tpf)
    {
        if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyLeft) && !this.changedDirection)
        {
            this.changedDirection = true;
            if(dir == 3)
            {
                dir = 0;
            }else
            {
                dir++;
            }
            
            this.localRootNode.rotate(0.0f, (float)(Math.PI/2), 0.0f);
            
        }else if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyDown) && !this.changedDirection)
        {
            this.changedDirection = true;
            if(dir == 2) 
            {
                dir = 0;
            }else if(dir == 3)
            {
                dir = 1;
            }else if(dir == 0)
            {
                dir = 2;
            }else if(dir == 1)
            {
                dir = 3;
            }
            
            this.localRootNode.rotate(0.0f, (float)(Math.PI), 0.0f);
            
            
        }else if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyRight) && !this.changedDirection)
        {
            this.changedDirection = true;
            if(dir == 0)
            {
                dir = 3;
            }else
            {
                dir--;
            }
            
            this.localRootNode.rotate(0.0f, -(float)(Math.PI/2), 0.0f);
            
        }
        
        if(dir == 0)
        {
            localRootNode.move(0, 0, speed*tpf);
            Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(0.0f, camHeight, -1.5f));
        }else if(dir == 1)
        {
            localRootNode.move(speed*tpf, 0, 0);
            Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(-1.5f, camHeight, 0.0f));
        }else if(dir == 2)
        {
            localRootNode.move(0, 0, -speed*tpf);
            Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(0.0f, camHeight, 1.5f));
        }else if(dir == 3)
        {
            localRootNode.move(-speed*tpf, 0, 0);
            Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(1.5f, camHeight, 0.0f));
        }
        
        Main.mainCamera.lookAt(localRootNode.getWorldTranslation(), Vector3f.UNIT_Y); 
        
        
        if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyS))
        {
            this.changedDirection = false;
        }
    }
}
