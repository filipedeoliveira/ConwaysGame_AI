package conwaysgame;
 
import jade.gui.GuiEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Frame extends JFrame implements MouseListener, KeyListener
{
    private static int width = 500;
    private static int height = 500;
     
    private Screen screen;
    private World world;
    private Font font;
     
    private int insetLeft;
    private int insetTop;
    
    private InterfaceAgent myagent;
     
    public Frame(InterfaceAgent a)
    {
        super("MineSweeper");
        myagent=a; 
        world = new World();
         
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
         
        screen = new Screen();
        add(screen);
         
        pack();
        insetLeft = getInsets().left;
        insetTop = getInsets().top;
        setSize(width + insetLeft + getInsets().right, height + getInsets().bottom + insetTop);
        setLocationRelativeTo(null);
        setVisible(true);
         
        font = new Font("SansSerif", 0, 12);
    }
 
    @Override
    public void mouseClicked(MouseEvent e)
    {
         
    }
 
    @Override
    public void mouseEntered(MouseEvent e)
    {
         
    }
 
    @Override
    public void mouseExited(MouseEvent e)
    {
         
    }
 
    @Override
    public void mousePressed(MouseEvent e)
    {
         
    }
 
    @Override
    public void mouseReleased(MouseEvent e)
    {
        // MUDAR QUANDO OUVER MAIS AGENTES PARA ALEM DO NORMAL
        TreeInt cell=world.clicked(e.getX() - insetLeft, e.getY() - insetTop,1);
        String msg=""+cell.getX()+","+cell.getY()+","+cell.getState();
        GuiEvent ge= new GuiEvent(msg,1);
        myagent.postGuiEvent(ge);
        /*if(e.getButton() == 1) world.clickedLeft(e.getX() - insetLeft, e.getY() - insetTop);
        if(e.getButton() == 3) world.clickedRight(e.getX() - insetLeft, e.getY() - insetTop);*/
        screen.repaint();
    }
 
    @Override
    public void keyPressed(KeyEvent e)
    {
         
    }
 
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_R)
        {
            world.reset();
            screen.repaint();
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            GuiEvent ge= new GuiEvent("NewGen",2);
            myagent.postGuiEvent(ge);
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e)
    {
         
    }

    public void next_gen(ArrayList<TreeInt> states) {
        world.next_gen(states);
        screen.repaint();
    }
     
    public class Screen extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            g.setFont(font);
            world.draw(g);
        }
    }
     
    public static int getScreenWidth()
    {
        return width;
    }
     
    public static int getScreenHeight()
    {
        return width;
    }
}