package dev.sadat.androide.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import dev.sadat.androide.listeners.EditorTouchCallback;
import dev.sadat.androide.listeners.EditorViewTouchListener;
import dev.sadat.androide.views.blocks.Block;

public class EditorView extends View implements EditorTouchCallback{

	private static final float SPEED = 1;

	private Context context;
	
	private boolean isInit = false;
	
	private float gridWidth = 1;
	private final int GRID_GAP = 20;
	private int numHLines;
	private int numVLines;
	
	private Rect background;
	private Paint backgroundPaint;
	private Paint gridLinePaint;
	
	private float [] delta;
	
	private Block[] debug;
	
	public EditorView(Context context) {
		this(context, null, 0);
	}

	public EditorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		EditorViewTouchListener listener = new EditorViewTouchListener();
		listener.setTouchCallback(this);
		this.setOnTouchListener(listener);
		delta = new float[]{0,0};
	}
	
	@SuppressLint("WrongCall")
	@Override
	protected void onDraw(Canvas canvas) {
		// Call Initialization only once
		if (isInit == false)
			initialize(canvas);
		canvas.drawRect(background, backgroundPaint);
		drawGridLines(canvas);
		canvas.save();
		canvas.translate(delta[0], delta[1]);
		// TODO Code Block Drawing
		for (Block deb:debug)
			deb.draw(canvas);
		canvas.restore();
	}
	
	private void drawGridLines(Canvas canvas){
		for (int i=0; i<=numHLines;i++)
			canvas.drawLine(0, GRID_GAP*i, super.getWidth(), GRID_GAP*i, gridLinePaint);
		for (int i=0; i<numVLines;i++)
			canvas.drawLine(GRID_GAP*i, 0, GRID_GAP*i, super.getHeight(), gridLinePaint);
	}
	
	private void initialize(Canvas canvas){
		background = new Rect(0, 0, super.getWidth(), super.getHeight());
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.DKGRAY);
		backgroundPaint.setStyle(Paint.Style.FILL);
		setGridLineConfig();
		isInit = true;
		
		debug = new Block[10];
		
		for (int i=0; i< 10; i++){
			debug[i] = new Block(context, 20*i, 20);
		}
	}
	
	private void setGridLineConfig(){
		numHLines = super.getHeight()/GRID_GAP;
		numVLines = super.getWidth()/GRID_GAP;
		gridLinePaint = new Paint();
		gridLinePaint.setColor(Color.GRAY);
		gridLinePaint.setStyle(Paint.Style.STROKE);
		gridLinePaint.setStrokeWidth(gridWidth);
	}

	@Override
	public boolean motionEvent(int type, float deltaX, float deltaY) {
		if (type == EditorTouchCallback.SCROLL){
			delta[0] += deltaX*SPEED;
			delta[1] += deltaY*SPEED;
			invalidate();
		}
		return true;
	}

}