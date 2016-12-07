/**
 * Class segment permettant de créer des segments pour la vectorisation
 *
 * @author Matthieu LEON et Nathan URBAIN
 */
public class segment {
    public int x1;
    public int y1;

    public int x2;
    public int y2;

    /**
     * Constructeur segment
     */
    public segment() {}

    /**
     * Constructeur segment
     *
     * @param x
     *          Position X
     * @param y
     *             Positon Y
     * @param i
     *  param i
     * @param j
     *  param j
     */
    public segment (int x , int y , int i , int j)
    {
        this.x1=x;
        this.y1=y;
        this.x2=i;
        this.y2=j;
    }
}
