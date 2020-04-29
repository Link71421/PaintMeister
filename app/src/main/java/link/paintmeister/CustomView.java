package link.paintmeister;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

//#######################################################
/**
 * A custom view to capture onClickEvents and draw circles
 * where clicked.
 * @author Andrew
 *
 */
//#######################################################
public class CustomView extends View  implements OnTouchListener {

	private Paint paint1;
	private ArrayList<ShapeDrawable> mDrawable;

	private float radius;
	private int color;

	/**Store the X coordinates**/
	private ArrayList<Float> xs = new ArrayList<>();
	/**Store the Y coordinates**/
	private ArrayList<Float> xy = new ArrayList<>();
	/**Store the Radius**/
	private ArrayList<Float> rad = new ArrayList<>();
	/**Store the Color**/
	private ArrayList<Integer> col = new ArrayList<>();
	//===================================================
	/**
	 * Called when the custom view is initialized
	 * @param context
	 */
	//===================================================
	public CustomView(Context context, AttributeSet attrs) {
		super(context,attrs);

		mDrawable = new ArrayList<ShapeDrawable>();
		this.setOnTouchListener(this);

		paint1 = new Paint();
		paint1.setColor(Color.GREEN);
		color = Color.GREEN;
		paint1.setStrokeWidth(4);
		radius = 10;
	}//===================================================

	//=====================================================
	/**
	 * Draw the canvas items
	 */
	//=====================================================
	protected void onDraw(Canvas canvas) {

		//Loop though each x and y coordinate and draw a circle
		int y = 0;
		for(Float x : xs){
			paint1.setColor(col.get(y));
			canvas.drawCircle(x, xy.get(y),rad.get(y), paint1);
			y++;
		}//end for

	}//end onDraw========================================


	//===================================================
	/**
	 * When the view is touched.
	 */
	//===================================================
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		Log.v("TouchDemo","Touch");

		//Define drawable attibutes.
		float x = event.getX();
		float y = event.getY();

		xs.add(new Float(x));
		xy.add(new Float(y));
		rad.add(radius);
		col.add(color);


		float width = 4f;
		float height = 4f;

		this.invalidate();
		return true;
	}//===================================================

	/**
	 * Sets the radius of the brush
	 * @param rad - The radius to set the brush
	 */
	public void setRadius(float rad){
		this.radius = rad;
	}

	/**
	 * Sets the color of the brush
	 * @param col - The color to set the brush
	 */
	public void setColor(int col){
		this.color = col;
	}
}//#######################################################
