
package bejeweled;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Brandon Barber <brandonsbarber@gmail.com>
 */
class DisplayComp extends JComponent{
    
    private int tileDim = 20;
    private int[][] board;
    public DisplayComp(Coord c,int[][] b)
    {
        board = b;
        selected = null;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        System.out.println("Painting");
        for(int y = 0; y < board.length; y++)
        {
            for(int x = 0; x < board[y].length; x++)
            {
                int val = board[y][x];
                g.setColor(pickColor(val));
                g.fillRect(x*tileDim,y*tileDim,tileDim,tileDim);
                
            }
        }
    }
    
    Color pickColor(int i)
    {
        switch(i)
        {
            case 1: return Color.YELLOW;
            case 2: return Color.BLUE;
            case 3: return Color.RED;
            case 5: return Color.GRAY;
            default: return Color.BLACK;
        }
    }

    Coord send(int y, int x)
    {
        System.out.println(x/tileDim+" " + y/tileDim);
        if(selected == null)
        {
            selected = new Coord(x/tileDim, y/tileDim);
        }
        return new Coord(x/tileDim,y/tileDim);
    }
    
    private Coord selected;
    
    public Coord getSelected()
    {
        return selected;
    }
    
    public void clearSelected()
    {
        selected = null;
    }
}
