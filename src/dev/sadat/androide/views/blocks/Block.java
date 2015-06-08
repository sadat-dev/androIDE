package dev.sadat.androide.views.blocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import dev.sadat.androide.listeners.BlockTouchCallback;
import dev.sadat.androide.listeners.BlockViewTouchListener;
import dev.sadat.androide.listeners.EditorTouchCallback;

public class Block extends View implements BlockTouchCallback{

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
		this(context, null, 0, x, y);
	}

	public Block(Context context) {
		this(context, null, 0, 0, 0);
	}

	public Block(Context context, AttributeSet attrs) {
		this(context, attrs, 0, 0, 0);
	}

	public Block(Context context, AttributeSet attrs, int defStyleAttr, float x, float y) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		this.position = new float[] { x, y };
		BlockViewTouchListener listener = new BlockViewTouchListener();
		listener.setTouchCallback(this);
		this.setOnTouchListener(listener);
	}

	@Override
	protected void onDraw(Canvas canvas) {
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

	@Override
	public boolean onBlockTouched(int type, float deltaX, float deltaY) {
		Log.w("Block.onBlockTouched", deltaX + ", " + deltaY);
		if (type == EditorTouchCallback.SCROLL){
			position[0] += deltaX;
			position[1] += deltaY;
			invalidate();
		}
		return true;
	}

}
