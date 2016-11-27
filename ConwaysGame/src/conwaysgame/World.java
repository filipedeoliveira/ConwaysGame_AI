package conwaysgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
 
public class World
{
    private static int width = 50;
    private static int height = 50;
     
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
        random = new Random();
         
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
     
    private void placeBombs()
    {
        for(int i = 0;i < AMOUNT_OF_BOMBS;i++)
        {
            placeBomb();
        }
    }
     
    private void placeBomb()
    {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
         
        if(!tiles[x] [y].isBomb()) tiles[x] [y].setBomb(true);
        else placeBomb();
    }
     
    private void setNumbers()
    {
        for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                int mx = x - 1;
                int gx = x + 1;
                int my = y - 1;
                int gy = y + 1;
                 
                int amountOfBombs = 0;
                if(mx >= 0&&my >= 0&&tiles[mx] [my].isBomb()) amountOfBombs++;
                if(mx >= 0&&tiles[mx] [y].isBomb()) amountOfBombs++;
                if(mx >= 0&&gy < height&&tiles[mx] [gy].isBomb()) amountOfBombs++;
                 
                if(my >= 0&&tiles[x] [my].isBomb()) amountOfBombs++;
                if(gy < height&&tiles[x] [gy].isBomb()) amountOfBombs++;
                 
                if(gx < width&&my >= 0&&tiles[gx] [my].isBomb()) amountOfBombs++;
                if(gx < width&&tiles[gx] [y].isBomb()) amountOfBombs++;
                if(gx < width&&gy < height&&tiles[gx] [gy].isBomb()) amountOfBombs++;
                 
                tiles[x] [y].setAmountOfNearBombs(amountOfBombs);
            }
        }
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
                        open(tileX, tileY);
                    }
                }
                 
                checkFinish();
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
             
            checkFinish();
        }
    }
    
    public TreeInt clicked(int x, int y,int i) {
        
        int tileX = x/Tile.getWidth();
        int tileY = y/Tile.getHeight();
        int new_state=tiles[tileX] [tileY].changeStatus(i);
        return new TreeInt(tileX,tileY,new_state);
    }
     
    private void open(int x, int y)
    {
        tiles[x] [y].setOpened(true);
        if(tiles[x] [y].getAmountOfNearBombs() == 0)
        {
            int mx = x - 1;
            int gx = x + 1;
            int my = y - 1;
            int gy = y + 1;
             
 
            if(mx >= 0&&my >= 0&&tiles[mx] [my].canOpen()) open(mx, my);
            if(mx >= 0&&tiles[mx] [y].canOpen()) open(mx, y);
            if(mx >= 0&&gy < height&&tiles[mx] [gy].canOpen()) open(mx, gy);
             
            if(my >= 0&&tiles[x] [my].canOpen()) open(x, my);
            if(gy < height&&tiles[x] [gy].canOpen()) open(x, gy);
             
            if(gx < width&&my >= 0&&tiles[gx] [my].canOpen()) open(gx, my);
            if(gx < width&&tiles[gx] [y].canOpen()) open(gx, y);
            if(gx < width&&gy < height&&tiles[gx] [gy].canOpen()) open(gx, gy);
             
//          if(mx >= 0&&tiles[mx] [y].canOpen()) open(mx, y);
//          if(gx < width&&tiles[gx] [y].canOpen()) open(gx, y);
//          if(my >= 0&&tiles[x] [my].canOpen()) open(x, my);
//          if(gy < height&&tiles[x] [gy].canOpen()) open(x, gy);
        }
    }
     
    private void checkFinish()
    {
        finish = true;
        outer : for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                if(!(tiles[x] [y].isOpened()||(tiles[x] [y].isBomb()&&tiles[x] [y].isFlag())))
                {
                    finish = false;
                    break outer;
                }
            }
        }
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
         
        placeBombs();
        setNumbers();
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

    
}