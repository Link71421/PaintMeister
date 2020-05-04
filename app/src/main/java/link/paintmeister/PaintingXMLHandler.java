package link.paintmeister;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Custom XML handler used to read in the saved XML file for the paintings
 *
 * @author Dillon Ramsey
 * @author Zach Garner
 */
public class PaintingXMLHandler extends DefaultHandler {

    /**The current element being read*/
    private String curElement;

    /**An array list of PaintLines objects*/
    private ArrayList<PaintLines> pLines;

    /**A PaintLines object to be placed into the pLines ArrayList*/
    private PaintLines pl;

    /**The background color*/
    private int backgroundColor;

    /**The x values for the line**/
    private ArrayList<Float> x_values;

    /**The y values for the line**/
    private ArrayList<Float> y_values;

    /**
     * Default constructor for the XML handler
     */
    public PaintingXMLHandler(){
    }

    /**
     * Gets the loaded background color
     */
    public int getBackground(){
        return backgroundColor;
    }

    /**
     * Gets the ArrayList of PaintLines that form the painting
     */
    public ArrayList<PaintLines> getPaintLines(){
        return pLines;
    }

    /**
     * Called when a start element is found
     * @param uri The xml name space uri
     * @param localName The name of the xml tag opening i.e. marker
     * @param qName The fully qualified name of the tag opening i.e. xs:marker.
     * @param attributes Any attributes that happen to be with the tag.
     * @throws SAXException  SAXException Any SaxException, possibly wrapping another exception
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)  throws SAXException {
        curElement = "";
        if (qName.equals("PaintLines")) {
            if (pLines == null) {
                pLines = new ArrayList<>();
            }
        }else if (qName.equals("x")){
            if (x_values == null){
                x_values = new ArrayList<>();
            }
        }
        else if (qName.equals("y")){
            if (y_values == null){
                y_values = new ArrayList<>();
            }
        }else if (qName.equals("lines")){
            if (pl == null) {
                pl = new PaintLines();
            }
        }
    }

    /**
     * When closing tag is reached.
     * @param uri
     * @param localName The name of the closing tag i.e. lat
     * @param qName The fully qualified name of the closing tag i.e. xs:lat
     * @throws SAXException   SAXException Any SaxException, possibly wrapping another exception
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("background_color")){
            backgroundColor = Integer.parseInt(curElement);
        }else if (qName.equals("color")){
            pl.setColor(Integer.parseInt(curElement));
        }else if (qName.equals("radius")){
            pl.setRadius(Float.parseFloat(curElement));
        }else if (qName.equals("x")){
            x_values.add(Float.parseFloat(curElement));
        }else if (qName.equals("y")){
            y_values.add(Float.parseFloat(curElement));
        }else if (qName.equals("lines")){
            pl.setLine(x_values, y_values);
            pLines.add(pl);
            pl = null;
            x_values = null;
            y_values = null;;
        }
        curElement = null;
    }

    /**
     * Read the characters in. This may be called two or three times for one element
     * @param ch  An array of characters read.
     * @param start The start position of the character array.
     * @param length The number of characters to use from the array.
     * @throws SAXException Any SaxException, possibly wrapping another exception.
     */
    @Override
    public void characters(char[] ch, int start, int length)  throws SAXException {
        if (curElement != null){
            curElement = curElement + new String(ch, start, length);
        }
    }
}
