package link.paintmeister;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;


/**
 * A custom view to capture onClickEvents and draw strokes
 * where clicked.
 *
 * @author Dillon Ramsey
 */
public class CustomView extends View  implements OnTouchListener {


	/**An array list of Line objects (line is inner class)**/
	private ArrayList<PaintLines> lines;

	/**The path currently being drawn by the user**/
	private Path activePath;

	/**The pain that is currently used to draw**/
	Paint paint;

	/**The x values for the line**/
	private ArrayList<Float> x_values;

	/**The y values for the line**/
	private ArrayList<Float> y_values;

	private float radius;
	private int color;

	/**
	 * Called when the custom view is initialized
	 * @param context
	 */
	public CustomView(Context context, AttributeSet attrs) {
		super(context,attrs);

		this.setOnTouchListener(this);

		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.GREEN);
	}

	/**
	 * Draw the canvas items
	 */
	protected void onDraw(Canvas canvas) {
		if(lines != null){
			int size = lines.size();
			for(int i = 0; i < size; i++){
				paint.setColor(lines.get(i).getColor());
				paint.setStrokeWidth(lines.get(i).getRadius());
				canvas.drawPath(lines.get(i).getPath(),paint);
			}
			if (activePath != null) {
				paint.setColor(color);
				paint.setStrokeWidth(radius);
				canvas.drawPath(activePath, paint);
			}
		}
	}

	/**
	 * When the users finger is placed on the screen
	 * @param x coordinate
	 * @param y The y coordinate.
	 * @param v The view touched
	 */
	public void touchDown(float x, float y,View v){
		if (lines == null){
			lines = new ArrayList<>();
		}
		if (activePath == null){
			activePath = new Path();
		}
		if (x_values == null){
			x_values = new ArrayList<>();
		}
		if (y_values ==  null){
			y_values = new ArrayList<>();
		}
		x_values.add(x);
		y_values.add(y);
		activePath.moveTo(x,y);
	}

	/**
	 * When the users finger is dragged.
	 * @param x coordinate
	 * @param y The y coordinate.
	 * @param v The view touched
	 */
	public void touchMove(float x, float y, View v){
		x_values.add(x);
		y_values.add(y);
		activePath.lineTo(x,y);
	}

	/**
	 * When the users finger is lifted.
	 * @param x The x coordinate
	 * @param y The y coordinate.
	 * @param v The view touched
	 */

	public void touchUp(float x, float y,View v){
		x_values.add(x);
		y_values.add(y);
		lines.add(new PaintLines(activePath,color,radius,x_values,y_values));
		activePath = null;
		x_values = null;
		y_values = null;
	}

	/**
	 * When the view is touched.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		//Define drawable attibutes.
		float x = event.getX();
		float y = event.getY();


		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchDown(x, y, v);
				break;
			case MotionEvent.ACTION_MOVE:
				touchMove(x, y,v);
				break;
			case MotionEvent.ACTION_UP:
				touchUp(x,y,v);
				break;
		}
		invalidate();
		return true;
	}

	/**
	 * Sets the radius of the brush
	 * @param rad - The radius to set the brush
	 */
	public void setRadius(float rad){
		this.radius = rad;
		paint.setStrokeWidth(radius);
	}

	/**
	 * Sets the color of the brush
	 * @param col - The color to set the brush
	 */
	public void setColor(int col){
		this.color = col;
		paint.setColor(color);
	}

	/**
	 * Clears the arrays to start a new painting
	 */
	public void newPainting(){
		lines.clear();
		this.invalidate();
	}

	public String toXML(){
		StringBuilder builder = new StringBuilder();
		builder.append("<PaintLines>\n");
		for (PaintLines pl : lines){

			builder.append(pl.toXML());
		}
		builder.append("</PaintLines>\n");
		return builder.toString();
	}
}