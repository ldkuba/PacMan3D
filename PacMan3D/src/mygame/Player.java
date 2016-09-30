/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import Input.InputHandler;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
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
    
    final float camHeight = 2.0f;
    final float corridorThreshold = 0.15f;
    final float camBumps = 0.0f;
    
    boolean keyLeftReleased = true;
    boolean keyDownReleased = true;
    boolean keyRightReleased = true;
    
    int turnFrames = 0; //-5 = left 5 = right
    
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
        Main.mainCamera.lookAt(localRootNode.getWorldTranslation().add(0, 2.0f, 0), Vector3f.UNIT_Y);        
    }
    
    public void update(float tpf)
    {
        if(!Main.inputHandler.getInputState(InputHandler.Mappings.KeyLeft))
        {
            this.keyLeftReleased = true;
        }
        
        if(!Main.inputHandler.getInputState(InputHandler.Mappings.KeyDown))
        {
            this.keyDownReleased = true;
        }
        
        if(!Main.inputHandler.getInputState(InputHandler.Mappings.KeyRight))
        {
            this.keyRightReleased = true;
        }
        
        if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyLeft) && this.keyLeftReleased)
        {
            boolean allowTurn = false;
            
            if(dir == 0)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getZ()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getZ() - closestPoint);
                
                System.out.println(difference);
                
                if(difference < corridorThreshold)
                {
                    System.out.println(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.025f)+1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)]);
                    System.out.println((int)(localRootNode.getWorldTranslation().getZ()+0.25f));
                    System.out.println((localRootNode.getWorldTranslation().getX()+0.025f));
                    
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)+1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)])
                    {
                        localRootNode.move(0, 0, -(localRootNode.getWorldTranslation().getZ() - closestPoint));
                        allowTurn = true;
                    }
                }
            }else if(dir == 1)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getX()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getX() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)-1]) 
                    {
                        localRootNode.move(-(localRootNode.getWorldTranslation().getX() - closestPoint), 0, 0);
                        allowTurn = true;
                    }
                }
            }else if(dir == 2)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getZ()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getZ() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)-1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)])
                    {
                        localRootNode.move(0, 0, -(localRootNode.getWorldTranslation().getZ() - closestPoint));
                        allowTurn = true;
                    }
                }
            }else if(dir == 3)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getX()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getX() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)+1]) 
                    {
                        localRootNode.move(-(localRootNode.getWorldTranslation().getX() - closestPoint), 0, 0);
                        allowTurn = true;
                    }
                }
            }
            
            if(allowTurn)
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
                
                this.keyLeftReleased = false;
                this.turnFrames = -150;
            }
        }else if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyDown) && this.keyDownReleased)
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
            
            this.keyDownReleased = false;
            
            this.turnFrames = 0;
            
            
        }else if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyRight) && this.keyRightReleased)
        {
            boolean allowTurn = false;
            
            if(dir == 0)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getZ()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getZ() - closestPoint);
                
                System.out.println(difference);
                
                if(difference < corridorThreshold)
                {
                    //System.out.println(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.025f)+1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)]);
                    //System.out.println((int)(localRootNode.getWorldTranslation().getZ()+0.25f));
                    //System.out.println((localRootNode.getWorldTranslation().getX()+0.025f));
                    
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)-1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)])
                    {
                        localRootNode.move(0, 0, -(localRootNode.getWorldTranslation().getZ() - closestPoint));
                        allowTurn = true;
                    }
                }
            }else if(dir == 1)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getX()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getX() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)+1]) 
                    {
                        localRootNode.move(-(localRootNode.getWorldTranslation().getX() - closestPoint), 0, 0);
                        allowTurn = true;
                    }
                }
            }else if(dir == 2)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getZ()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getZ() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)+1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)])
                    {
                        localRootNode.move(0, 0, -(localRootNode.getWorldTranslation().getZ() - closestPoint));
                        allowTurn = true;
                    }
                }
            }else if(dir == 3)
            {
                float closestPoint = (float) (Math.abs((int)(localRootNode.getWorldTranslation().getX()+0.51f)));
                float difference = Math.abs(localRootNode.getWorldTranslation().getX() - closestPoint);
                
                if(difference < corridorThreshold)
                {
                    if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)-1]) 
                    {
                        localRootNode.move(-(localRootNode.getWorldTranslation().getX() - closestPoint), 0, 0);
                        allowTurn = true;
                    }
                }
            }
            
            if(allowTurn)
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
                
                this.keyRightReleased = false;
                this.turnFrames = 150;
            }
        }
        
        if(dir == 0)
        {
            if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.025f)+1]) 
            {
                localRootNode.move(0, 0, speed*tpf);
                Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(-(this.turnFrames/100.0f)*(1.0f+camBumps), camHeight, -1.5f + (Math.abs(this.turnFrames)/100.0f)*(0.5f-camBumps)));
            }
            
            //System.out.println(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()+0.025f)+1]);
           // System.out.println((int)(localRootNode.getWorldTranslation().getX()+0.25f) + "    " + (int)((localRootNode.getWorldTranslation().getZ()+0.025f)+1));
           // System.out.println((localRootNode.getWorldTranslation().getX()+0.25f) + "   " + (localRootNode.getWorldTranslation().getZ()+0.025f));
        }else if(dir == 1)
        {
            if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.025f)+1][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)]) 
            {
                localRootNode.move(speed*tpf, 0, 0);
                Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(-1.5f + (Math.abs(this.turnFrames)/100.0f)*(0.5f-camBumps), camHeight, (this.turnFrames/100.0f)*(1.0f+camBumps)));
            }
        }else if(dir == 2)
        {
            if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()+0.25f)][(int)(localRootNode.getWorldTranslation().getZ()-0.025f)]) 
            {
                localRootNode.move(0, 0, -speed*tpf);
                Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add((this.turnFrames/100.0f)*(1.0f+camBumps), camHeight, 1.5f - (Math.abs(this.turnFrames)/100.0f)*(0.5f-camBumps)));
            }
        }else if(dir == 3)
        {
            if(Main.activeLevel.getLayout()[(int)(localRootNode.getWorldTranslation().getX()-0.025f)][(int)(localRootNode.getWorldTranslation().getZ()+0.25f)]) 
            {
                localRootNode.move(-speed*tpf, 0, 0);
                Main.mainCamera.setLocation(localRootNode.getWorldTranslation().add(1.5f - (Math.abs(this.turnFrames)/100.0f)*(0.5f-camBumps), camHeight, -(this.turnFrames/100.0f)*(1.0f+camBumps)));
            }
        }
        
        Main.mainCamera.lookAt(localRootNode.getWorldTranslation().add(0, 1.2f, 0), Vector3f.UNIT_Y); 
        
        
        if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyS))
        {
            this.changedDirection = false;
        }
        
        if(Main.inputHandler.getInputState(InputHandler.Mappings.KeyK))
        {
            if(this.speed != 0)
            {
                speed = 0;
            }else
            {
                speed = 2.0f;
            }
        }
        
        if(this.turnFrames > 0)
        {
            this.turnFrames--;
        }
        
        if(this.turnFrames < 0)
        {
            this.turnFrames++;
        }
    }
}
