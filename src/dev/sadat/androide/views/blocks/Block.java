package dev.sadat.androide.views.blocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import dev.sadat.androide.listeners.EditorTouchCallback;

public class Block{

	private Context context;
	
	private boolean isInit = false;

	private static int PADDING = 16;

	private float[] position = { 0, 0 };
	private Rect backBlock;
	private Rect headBlock;
	private Paint headPaint;
	private Paint backPaint;
	private Paint textPaint;

	private String header = "default";
	private String body = "default";

	private int textSize = 32;

	public Block(Context context, float x, float y) {
		this.position = new float[] { x, y };
		this.context = context;
	}

	public Block(Context context) {
		this(context, 0, 0);
	}


	public void draw(Canvas canvas) {
		if (isInit == false)
			initialize(canvas);
		canvas.save();
		canvas.translate(position[0], position[1]);
		canvas.drawRect(backBlock, backPaint);
		canvas.drawRect(headBlock, headPaint);
		canvas.drawText(header, PADDING, PADDING + textSize, textPaint);
		canvas.drawText(body, PADDING, headBlock.height()+PADDING + textSize, textPaint);
		canvas.restore();
	}

	// TODO Move these initializations to a global layer so that number of instances becomes 
	// constant instead of n
	protected void initialize(Canvas canvas) {
		backPaint = new Paint();
		headPaint = new Paint();
		textPaint = new Paint();
		backPaint.setColor(Color.BLACK);
		headPaint.setColor(Color.RED);
		textPaint.setColor(Color.WHITE);
		// TODO Make it adjustable from shared settings
		textPaint.setTextSize(textSize);
		int textRight = (int) Math.max(textPaint.measureText(header), textPaint.measureText(body));
		textRight += PADDING * 2;
		headBlock = new Rect(0, 0, textRight + PADDING * 2, textSize + PADDING * 2);
		backBlock = new Rect(0, 0, textRight + PADDING * 2, textSize * 4 + PADDING * 2);
	}

	public boolean onBlockTouched(int type, float deltaX, float deltaY) {
		if (type == EditorTouchCallback.SCROLL){
			position[0] += deltaX;
			position[1] += deltaY;
			return true;
		}
		return true;
	}
	
	public Rect getBounds() {
		Rect bound = new Rect((int)position[0], (int)position[1], (int)(backBlock.right+position[0]), (int)(backBlock.bottom+position[1]));
		return bound;
	}
}