package link.paintmeister;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;

/**
 * An object to hold paths along with their respective colors and radii
 *
 * @author Dillon Ramsey
 */
public class PaintLines {

    /**ArrayList of Path objects**/
    private Path line;

    /**The colors for the Paths**/
    private Integer color;

    /**The radius to draw each Path**/
    private Float radius;

    /**The x values for the line**/
    private ArrayList<Float> x_values;

    /**The y values for the line**/
    private ArrayList<Float> y_values;

    /**
     * Default constructor
     */
    PaintLines(Path line, int color, float radius, ArrayList<Float> x_values, ArrayList<Float> y_values){
        this.line = line;
        this.color = color;
        this.radius = radius;
        this.x_values = x_values;
        this.y_values = y_values;
    }

    /**
     * Gets the Path at the specified index
     */
    public Path getPath(){
        return line;
    }

    /**
     * Gets the color at the specified index
     */
    public int getColor(){
        return color;
    }

    /**
     * Gets the radius at the specified index
     */
    public float getRadius(){
        return radius;
    }

    public String toXML(){
        StringBuilder builder = new StringBuilder();
        builder.append("<lines>\n");
        builder.append("\t<color>"+color+"</color>\n");
        builder.append("\t<color>"+radius+"</color>\n");
        for (float x : x_values){
            builder.append("\t<x>"+x+"</x>\n");
        }
        for (float y : y_values){
            builder.append("\t<y>"+y+"</y>\n");
        }
        builder.append("</lines>\n");
        return builder.toString();
    }
}
