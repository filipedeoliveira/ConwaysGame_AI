package mostrar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
 
public class World
{
    private static int width = 5;
    private static int height = 5;
     
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
    }
    
    public TreeInt clicked(int x, int y,int i) {
        int tileX = x/Tile.getWidth();
        int tileY = y/Tile.getHeight();
        int new_state=tiles[tileX] [tileY].changeStatus(i);
        return new TreeInt(tileX,tileY,new_state);
    }
    
    public void next_gen_value(int x, int y,int i) {
        tiles[x] [y].changeStatus(i);
        //return new TreeInt(tileX,tileY,new_state);
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