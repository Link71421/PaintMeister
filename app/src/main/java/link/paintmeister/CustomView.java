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

	/**Store the X coordinates**/
	private ArrayList<Float> xs = new ArrayList<Float>();
	/**Store the Y coordinates**/
	private ArrayList<Float> xy = new ArrayList<Float>();
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
		paint1.setStrokeWidth(4);
		radius=10;
	}//===================================================

	/**Radius of brush**/
	private float radius;

	//=====================================================
	/**
	 * Draw the canvas items
	 */
	//=====================================================
	protected void onDraw(Canvas canvas) {

		//Loop though each x and y coordinate and draw a circle
		int y = 0;
		for(Float x : xs){
			canvas.drawCircle(x, xy.get(y),radius, paint1);
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

		float width = 4f;
		float height = 4f;

		this.invalidate();
		return true;
	}//===================================================

	public void setRadius(float rad){
		this.radius = rad;
	}

	public void setColor(String col){
		paint1.setColor(0xFFFFFF);
	}
}//#######################################################
