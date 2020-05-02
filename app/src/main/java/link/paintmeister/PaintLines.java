package link.paintmeister;

import android.graphics.Path;

import java.util.ArrayList;

/**
 * An object to hold paths along with their respective colors and radii
 *
 * @author Dillon Ramsey
 */
public class PaintLines {

    /**ArrayList of Path objects**/
    private ArrayList<Path> lines;

    /**The colors for the Paths**/
    private ArrayList<Integer> color;

    /**The radius to draw each Path**/
    private ArrayList<Float> radius;

    /**
     * Default constructor
     */
    PaintLines(){
        this.lines = new ArrayList<>();
        this.color = new ArrayList<>();
        this.radius = new ArrayList<>();
    }

    /**
     * Adds a Path to the array list along with its color and radius
     * @param p - The Path to add
     * @param c - The color of the Path
     * @param r - The radius of the Path
     */
    public void addLine(Path p, int c, float r){
        lines.add(p);
        color.add(c);
        radius.add(r);
    }

    /**
     * Gets the Path at the specified index
     * @param i - The index of the Path
     */
    public Path getPath(int i){
        return lines.get(i);
    }

    /**
     * Gets the color at the specified index
     * @param i - The index of the color
     */
    public int getColor(int i){
        return color.get(i);
    }

    /**
     * Gets the radius at the specified index
     * @param i - The index of the radius
     */
    public float getRadius(int i){
        return radius.get(i);
    }

    /**
     * Gets the length of the Path ArrayList
     */
    public int getLength(){
        return lines.size();
    }

    /**
     * Clears all the ArrayLists
     */
    public void clearPainting(){
        lines.clear();
        color.clear();
        radius.clear();
    }
}
