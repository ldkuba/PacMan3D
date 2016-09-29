/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nawro
 */
public class Level 
{
    Spatial wallModel;
    Spatial floorModel;
    
    Spatial[][] model;
    
    boolean[][] layout;
    
    int x, y, id;
    
    Node localNode;
    
    public Level(int id, int x, int y, Node rootNode, AssetManager assetManager)
    {
        this.id = id;
        
        localNode = new Node("LevelNode");
        
        wallModel = assetManager.loadModel("Models/wall.j3o");
        Material wallMat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture wallTex = assetManager.loadTexture("Textures/Tiles/wallTex.png");
        wallMat.setTexture("DiffuseMap", wallTex);
        wallModel.setMaterial(wallMat);
        
        floorModel = assetManager.loadModel("Models/floor.j3o");
        Material floorMat = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture floorTex = assetManager.loadTexture("Textures/Tiles/floorTex.png");
        floorMat.setTexture("DiffuseMap", floorTex);
        floorModel.setMaterial(floorMat);
        
        model = new Spatial[x][y];
        
        layout = new boolean[x][y];
        readMap(id);
        
        for(int j = 0; j < y; j++)
        {
            for(int i = 0; i < x; i++)
            {
                Spatial tile;
                
                if(layout[i][j] == false)
                {
                    tile = wallModel.clone();
                }else
                {
                    tile = floorModel.clone();
                }
                
                tile.setLocalTranslation(i, 0.0f, j);
                
                localNode.attachChild(tile);
            }
        }
        
        rootNode.attachChild(localNode);
    }

    public boolean[][] getLayout() {
        return layout;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void readMap(int id)
    {
        int x1 = 0;
        int y1 = 0;
        
        int sizeX = 0;
        int sizeY = 0;
        
        BufferedReader br = null;

	try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("assets/Models/Layouts/level" + id + ".txt"));

            while ((sCurrentLine = br.readLine()) != null) 
            {
                sizeX = sCurrentLine.length();
                for(int i = 0; i < sizeX; i++)
                {
                    if(sCurrentLine.charAt(i) == '0') this.layout[i][y1] = true;
                    if(sCurrentLine.charAt(i) == '1') this.layout[i][y1] = false;
                }
                
                y1++;
            }
            
            sizeY = y1;

        } catch (IOException e) 
        {
            e.printStackTrace();
        } 
        
        this.x = sizeX;
        this.y = sizeY;
        
    }
            
}
