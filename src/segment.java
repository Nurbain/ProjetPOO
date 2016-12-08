/**
 * Class segment permettant de créer des segments pour la vectorisation
 *
 * @author Matthieu LEON et Nathan URBAIN
 */
public class segment {
    public point p1;
    public point p2;

    /**
     * Constructeur segment
     */
    public segment() {}

    /**
     * Constructeur segment
     *
     * @param p
     *          Point p1
     * @param q
     *          Point p2
     */
    public segment (point p , point q)
    {
        this.p1=p;
        this.p2=q;
    }
}
