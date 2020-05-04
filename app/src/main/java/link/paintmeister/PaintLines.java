package link.paintmeister;

import android.graphics.Path;
import java.util.ArrayList;

/**
 * An object to hold paths along with their respective colors and radius
 *
 * @author Dillon Ramsey
 * @author Zach Garner
 */
public class PaintLines {

    /**Path object**/
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
     * Default constructor for paintLines
     */
    PaintLines(){
        this.line = null;
        this.color = 0;
        this.radius = 0f;
        x_values = new ArrayList<>();
        y_values = new ArrayList<>();
    }

    /**
     * Constructor for PaintLines
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
        if (line == null){
            genPath();
        }
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

    /**
     * Sets the color of the line
     * @param color - The color to set the line as an int
     */
    public void setColor(int color){
        this.color = color;
    }

    /**
     * Sets the radius of the line
     * @param radius - The radius to set as a float
     */
    public void setRadius(float radius){
        this.radius = radius;
    }

    /**
     * Sets the list of x values
     * @param x_values - An arrayList of x values
     */
    public void setX(ArrayList<Float> x_values){
        this.x_values = x_values;
    }

    /**
     * Sets the list of y values
     * @param y_values - An ArrayList of y values
     */
    public void setY(ArrayList<Float> y_values){
        this.y_values = y_values;
    }

    /**
     * Sets the Path of the line using the x and y values
     * @param x_values - x values of the line
     * @param y_values - y values of the line
     */
    public void setLine(ArrayList<Float> x_values, ArrayList<Float> y_values){
        setX(x_values);
        setY(y_values);
        line = new Path();
        genPath();
    }

    /**
     * Creates a Path for the line using the x and y values
     */
    public void genPath(){
        line.moveTo(x_values.get(0), y_values.get(0));
        for (int i = 1; i < x_values.size(); i++){
            line.lineTo(x_values.get(i), y_values.get(i));
        }
    }

    /**
     * Converts the color, radius, and x and y values into XML and returns the
     * generated XML string
     */
    public String toXML(){
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t<lines>\n");
        builder.append("\t\t\t<color>"+color+"</color>\n");
        builder.append("\t\t\t<radius>"+radius+"</radius>\n");
        for (float x : x_values){
            builder.append("\t\t\t<x>"+x+"</x>\n");
        }
        for (float y : y_values){
            builder.append("\t\t\t<y>"+y+"</y>\n");
        }
        builder.append("\t\t</lines>\n");
        return builder.toString();
    }

}
