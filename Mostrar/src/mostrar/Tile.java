package mostrar;

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
            default:
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