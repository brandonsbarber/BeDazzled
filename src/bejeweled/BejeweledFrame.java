//*****************NOTE TO SELF: X and Y are inconsistent...
package bejeweled;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Brandon Barber <brandonsbarber@gmail.com>
 */
class BejeweledFrame extends JFrame implements MouseListener,ActionListener
{
    private int[][] board = new int[8][6];//{{2,1,3},{1,2,1},{1,1,3}};
    private Coord dim = new Coord(3,3);
    private int score;
    private DisplayComp disp;
    
    private JButton but,oBut;
    private int valRange;

    public BejeweledFrame()
    {
        score = 0;
        valRange = 3;
        
        setLayout(new BorderLayout());
        but = new JButton("Repopulate");but.addActionListener(this);
        oBut = new JButton("Check Matches");oBut.addActionListener(this);
        JPanel botPane = new JPanel();
        botPane.setLayout(new GridLayout(2,0,15,15));
        botPane.add(but);
        botPane.add(oBut);
        add(botPane,BorderLayout.SOUTH);
        repopulate();
        disp = new DisplayComp(dim,board);
        disp.addMouseListener(this);
        add(disp);
        setSize(600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    void printBoard()
    {
        for(int[] row : board)
        {
            for(int c : row)
            {
                System.out.print(""+c+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }
    
    
    public void run()
    {
        checkMatches();
        setVisible(true);
        /*printBoard();
        checkMatches();
        printBoard();
        move(new Coord(0,0),new Coord(0,1));
        printBoard();*/
    }
    
    private void move(Coord one, Coord two)
    {
        int temp = board[one.x][one.y];
        board[one.x][one.y] = board[two.x][two.y];
        board[two.x][two.y] = temp;
        
        boolean moved = false;
        ArrayList<Coord> c;
        //checking first loc
        if((c = checkVertical(one)).size() > 2)
        {
            moved = true;
            System.out.println("Found vertical at 1?");
            printCoords(c);
            dissolve(c);
        }
        if((c = checkHorizontal(one)).size() > 2)
        {
            moved = true;
            System.out.println("Found horizontal at coord 1");
            printCoords(c);
            dissolve(c);
        }
        
        //checking second loc
        /*if((c = checkVertical(two)).size() > 2)
        {
            moved = true;
            System.out.println("Found vertical at 2?");
            printCoords(c);
            dissolve(c);
        }*/
        if((c = checkHorizontal(two)).size() > 2)
        {
            moved = true;
            System.out.println("Found horizontal at coord 2");
            printCoords(c);
            dissolve(c);
        }
        
        if(!moved)
        {
            //System.out.println("no match found. reverting switch");
            //swap back
            int temp2 = board[one.x][one.y];
            board[one.x][one.y] = board[two.x][two.y];
            board[two.x][two.y] = temp2;
        }
    }
    ArrayList<Coord> checkVertical(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        int val = board[c.x][c.y];
        if(val == 0)
        {
            return coords;
        }
        coords.add(c);
        checkLineLeft(new Coord(c.x-1,c.y),val,coords);
        checkLineRight(new Coord(c.x+1,c.y),val,coords);
        return coords;
    }
    ArrayList<Coord> checkHorizontal(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        int val = board[c.x][c.y];
        if(val == 0)
        {
            return coords;
        }
        coords.add(c);
        checkLineUp(new Coord(c.x,c.y-1),val,coords);
        checkLineDown(new Coord(c.x,c.y+1),val,coords);
        return coords;
    }
    
    ArrayList<Coord> checkLineUp(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        checkLineUp(c,board[c.x][c.y],coords);
        return coords;
    }
    private void checkLineUp(Coord c, int i, ArrayList<Coord> coords)
    {
        if(c.y < 0 || i != board[c.x][c.y])
        {
            return;
        }
        coords.add(c);
        checkLineUp(new Coord(c.x,c.y-1),i,coords);
    }
    
    ArrayList<Coord> checkLineDown(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        checkLineDown(c,board[c.x][c.y],coords);
        return coords;
    }
    private void checkLineDown(Coord c, int i, ArrayList<Coord> coords)
    {
        if(c.y >= board[c.x].length || i != board[c.x][c.y])
        {
            return;
        }
        coords.add(c);
        checkLineDown(new Coord(c.x,c.y+1),i,coords);
    }
    ArrayList<Coord> checkLineLeft(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        checkLineLeft(c,board[c.x][c.y],coords);
        return coords;
    }
    private void checkLineLeft(Coord c, int i, ArrayList<Coord> coords)
    {
        if(c.x < 0 || i != board[c.x][c.y])
        {
            return;
        }
        coords.add(c);
        checkLineLeft(new Coord(c.x-1,c.y),i,coords);
    }
    ArrayList<Coord> checkLineRight(Coord c)
    {
        ArrayList<Coord> coords = new ArrayList<Coord>();
        checkLineRight(c,board[c.x][c.y],coords);
        return coords;
    }
    private void checkLineRight(Coord c, int i, ArrayList<Coord> coords)
    {
        if(c.x >= board.length || i != board[c.x][c.y])
        {
            return;
        }
        coords.add(c);
        checkLineRight(new Coord(c.x+1,c.y),i,coords);
    }

    private void printCoords(ArrayList<Coord> c)
    {
            System.out.println(c);
    }

    private void dissolve(ArrayList<Coord> c)
    {
        ArrayList<Coord> lowest = new ArrayList<Coord>();
        lowest.add(c.get(0));
        for(Coord co : c)
        {
            if(co.x > lowest.get(0).x)
            {
                lowest.clear();
                lowest.add(co);
            }
            else if(co.x == lowest.get(0).x && co.y != lowest.get(0).y)
            {
                lowest.add(co);
            }
            board[co.x][co.y] = 0;
        }
        //find the lowest tile
        
        for(Coord co : lowest)
        {
            shiftDown(co);
        }
        //repopulate();
        checkMatches();
    }
    
    private void checkMatches()
    {
        System.out.println("CHECKING MATCHES");
        for(int y = 0; y < board.length; y++)
        {
            for(int x = 0; x < board[y].length; x++)
            {
                System.out.print(y + " "+x+" ");
                move(new Coord(y,x),new Coord(y,x));
            }
            System.out.println();
        }
    }
    
    private void repopulate()
    {
        System.out.println("Repopulating");
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board[x].length; y++)
            {
                if(board[x][y] == 0)
                {
                    int val = (int)(Math.random()*valRange + 1);
                    board[x][y] = val;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(disp.getSelected() != null)
        {
            Coord c = disp.send(e.getX(),e.getY());
            move(c,disp.getSelected());
            disp.clearSelected();
            repaint();
        }
        else
        {
            disp.send(e.getX(), e.getY());
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void shiftDown(Coord co)
    {
        int numMoves = 0;
        Coord temp = new Coord(co.x,co.y);
        System.out.println(board[temp.x][temp.y]);
        while(temp.x >= 0 && board[temp.x][temp.y] == 0)
        {
            numMoves++;
            board[temp.x][temp.y] = 0;
            temp = new Coord(co.x - numMoves,co.y);
        }
        System.out.println("Number of Moves: " + numMoves + " from " +co);
        temp = new Coord(co.x,co.y);
        while(temp.x - numMoves >= 0)
        {
            board[temp.x][temp.y] = board[temp.x - numMoves][temp.y];
            temp.x -= 1;
        }
        while(temp.x >= 0)
        {
            board[temp.x--][temp.y] = 0;
        }
        

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == but)
        {
            repopulate();
            repaint();  
        }
        else
        {
            checkMatches();
            repaint();
        }
    }
    
    
    
}
