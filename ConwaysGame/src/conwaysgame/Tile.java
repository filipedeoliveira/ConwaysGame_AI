package conwaysgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
 
public class Tile
{
    private BufferedImage normal;
    private BufferedImage ativado;
    private BufferedImage movel;
    private BufferedImage cidade;
    private BufferedImage caotico;
    private BufferedImage mentiroso;
    
    private int status;   
    private int x;
    private int y;
    private boolean bomb;
    private boolean opened;
    private boolean flag;
    private int amountOfNearBombs;
     
    private static int width = Frame.getScreenWidth()/World.getWidth(); 
    private static int height = Frame.getScreenHeight()/World.getHeight(); 
     
    public Tile(int x, int y, BufferedImage normal, BufferedImage movel, BufferedImage cidade, BufferedImage caotico,BufferedImage mentiroso,BufferedImage ativado)
    {
        this.x = x;
        this.y = y;
        this.status=0;
        this.normal = normal;
        this.movel = movel;
        this.cidade = cidade;
        this.caotico = caotico;
        this.mentiroso=mentiroso;
        this.ativado=ativado;
    }
     
    public int changeStatus(int i) {
        if(status==0){
            status=i;
        }else{
            status=0;
        }
        return status;
    }
    
    public void setOpenedImage(BufferedImage openedImage)
    {
        this.caotico = caotico;
    }
    
    
     
    public void setOpened(boolean opened)
    {
        this.opened = opened;
    }
     
    public boolean isOpened()
    {
        return opened;
    }
     
    public void setBomb(boolean bomb)
    {
        this.bomb = bomb;
    }
     
    public boolean isBomb()
    {
        return bomb;
    }
     
    public void setAmountOfNearBombs(int amountOfNearBombs)
    {
        this.amountOfNearBombs = amountOfNearBombs;
    }
     
    public int getAmountOfNearBombs()
    {
        return amountOfNearBombs;
    }
     
    public boolean canOpen()
    {
        return !opened&&!bomb&&amountOfNearBombs >= 0;
    }
     
    public void placeFlag()
    {
        if(flag) flag = false;
        else
        {
            if(!opened) flag = true;
        }
    }
     
    public boolean isFlag()
    {
        return flag;
    }
     
    public void reset()
    {
        flag = false;
        bomb = false;
        opened = false;
    }
 
    public void draw(Graphics g)
    {
        // ADICIONAR QUANDO OUVER MAIS ESTADOS
        switch(status){
            case 0:
                g.drawImage(normal, x * width, y * height, null);
                break;
            case 1:
                g.drawImage(ativado, x * width, y * height, null);
                break;
            case 2:
                g.drawImage(caotico, x * width, y * height, null);
                break;
               
            default:
        }
        /*if(!opened) 
        {
            if(!flag) g.drawImage(normal, x * width, y * height, null);
            else g.drawImage(normal, x * width, y * height, null);
        }
        else
        {
            if(bomb) g.drawImage(caotico, x * width, y * height, null);
            else
            {
                g.drawImage(cidade, x * width, y * height, null);
                if(amountOfNearBombs > 0)
                {
                    g.setColor(Color.WHITE);
                    g.drawString("" + amountOfNearBombs, x * width + 7, y * height + height - 4);
                }
            }
        }*/
    }
     
    public static int getWidth()
    {
        return width;
    }
     
    public static int getHeight()
    {
        return height;
    }

    public void update_status(int state) {
        status=state;
    }

    
}