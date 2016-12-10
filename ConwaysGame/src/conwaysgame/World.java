package conwaysgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
 
public class World
{
    private static int width = 5;
    private static int height = 5;
     
    private final int AMOUNT_OF_BOMBS = 40;
     
    private boolean finish;
    private boolean dead;
     
    private Random random;
     
    private Tile[] [] tiles;
     
    private BufferedImage normal = ImageLoader.scale(ImageLoader.loadImage("gfx/normal.png"), Tile.getWidth(), Tile.getHeight());
    private BufferedImage caotico = ImageLoader.scale(ImageLoader.loadImage("gfx/caotico.png"), Tile.getWidth(), Tile.getHeight());
    private BufferedImage cidade = ImageLoader.scale(ImageLoader.loadImage("gfx/cidade.png"), Tile.getWidth(), Tile.getHeight());
    private BufferedImage movel = ImageLoader.scale(ImageLoader.loadImage("gfx/movel.png"), Tile.getWidth(), Tile.getHeight());
    private BufferedImage mentiroso = ImageLoader.scale(ImageLoader.loadImage("gfx/mentiroso.png"), Tile.getWidth(), Tile.getHeight());
    private BufferedImage ativado = ImageLoader.scale(ImageLoader.loadImage("gfx/ativado.png"), Tile.getWidth(), Tile.getHeight());
     
    public World()
    {
        
         
        tiles = new Tile[width] [height];
         
        for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                tiles[x] [y] = new Tile(x, y, normal, movel, cidade, caotico,mentiroso,ativado);
            }
        }
         
        reset();
    }
     
    public void clickedLeft(int x, int y)
    {
        if(!dead&&!finish)
        {
            int tileX = x/Tile.getWidth();
            int tileY = y/Tile.getHeight();
             
            if(!tiles[tileX] [tileY].isFlag())
            {
                tiles[tileX] [tileY].setOpened(true);
                 
                if(tiles[tileX] [tileY].isBomb()) dead = true;
                else
                {
                    if(tiles[tileX] [tileY].getAmountOfNearBombs() == 0) 
                    {
                        //open(tileX, tileY);
                    }
                }
                 
                //checkFinish();
            }
        }
    }
     
    public void clickedRight(int x, int y)
    {
        if(!dead&&!finish)
        {
            int tileX = x/Tile.getWidth();
            int tileY = y/Tile.getHeight();
            tiles[tileX] [tileY].placeFlag();
             
            //checkFinish();
        }
    }
    
    public TreeInt clicked(int x, int y,int i) {
        
        int tileX = x/Tile.getWidth();
        int tileY = y/Tile.getHeight();
        int new_state=tiles[tileX] [tileY].changeStatus(i);
        return new TreeInt(tileX,tileY,new_state);
    }
     
    
    public void reset()
    {
        for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                tiles[x] [y].reset();
            }
        }
         
        dead = false;
        finish = false;
         
        
    }
     
    public void draw(Graphics g)
    {
        for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                tiles[x] [y].draw(g);
            }
        }
         
        if(dead)
        {
            g.setColor(Color.RED);
            g.drawString("You're dead!", 10, 30);
        }
        else if(finish)
        {
            g.setColor(Color.RED);
            g.drawString("You won!", 10, 30);
        }
    }
     
    public static int getWidth()
    {
        return width;
    }
     
    public static int getHeight()
    {
        return height;
    }

    public void next_gen(ArrayList<TreeInt> states) {
        for(TreeInt t:states){
            tiles[t.getX()][t.getY()].update_status(t.getState());
        }
    }

    
}