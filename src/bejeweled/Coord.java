
package bejeweled;

/**
 *
 * @author Brandon Barber <brandonsbarber@gmail.com>
 */
class Coord
{
    public Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int x, y;
    
    @Override
    public String toString()
    {
        return ""+x + " "+y;
    }
}
