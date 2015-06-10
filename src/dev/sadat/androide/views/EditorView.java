package dev.sadat.androide.views;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import dev.sadat.androide.listeners.EditorTouchCallback;
import dev.sadat.androide.listeners.EditorViewTouchListener;
import dev.sadat.androide.views.blocks.Block;

public class EditorView extends ViewGroup implements EditorTouchCallback {

	private Context context;

	private boolean isInit = false;

	private float gridWidth = 1;
	private final int GRID_GAP = 20;
	private int numHLines;
	private int numVLines;

	private Rect background;
	private Paint backgroundPaint;
	private Paint gridLinePaint;

	private float[] delta;
	
	private View currentFocus = null;

	private ArrayList<View> debug;

	private EditorViewTouchListener listener;

	public EditorView(Context context) {
		this(context, null, 0);
	}

	public EditorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		listener = new EditorViewTouchListener();
		listener.setTouchCallback(this);
		this.setOnTouchListener(listener);
		delta = new float[] { 0, 0 };
		this.setWillNotDraw(false);
	}

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
		for (View deb : debug)
			deb.draw(canvas);
		canvas.restore();
	}

	private void drawGridLines(Canvas canvas) {
		for (int i = 0; i <= numHLines; i++)
			canvas.drawLine(0, GRID_GAP * i, super.getWidth(), GRID_GAP * i, gridLinePaint);
		for (int i = 0; i < numVLines; i++)
			canvas.drawLine(GRID_GAP * i, 0, GRID_GAP * i, super.getHeight(), gridLinePaint);
	}

	private void initialize(Canvas canvas) {
		background = new Rect(0, 0, super.getWidth(), super.getHeight());
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.DKGRAY);
		backgroundPaint.setStyle(Paint.Style.FILL);
		setGridLineConfig();
		isInit = true;

		debug = new ArrayList<View>();

		for (int i = 0; i < 2; i++) {
			Block block = new Block(context, 100 * i, 50 * (i % 5));
			debug.add(block);
			this.addView(block);
		}
	}

	private void setGridLineConfig() {
		numHLines = super.getHeight() / GRID_GAP;
		numVLines = super.getWidth() / GRID_GAP;
		gridLinePaint = new Paint();
		gridLinePaint.setColor(Color.GRAY);
		gridLinePaint.setStyle(Paint.Style.STROKE);
		gridLinePaint.setStrokeWidth(gridWidth);
	}

	@Override
	public boolean motionEvent(int type, float deltaX, float deltaY) {
		if (type == EditorTouchCallback.SCROLL) {
			delta[0] += deltaX;
			delta[1] += deltaY;
			invalidate();
			return true;
		}
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.w("EditorView.onInterceptTouchEvent", "Called Intercept");
		boolean res = false;
		for (View v : debug) {
			if (v.getClipBounds().contains((int)ev.getX(), (int)ev.getY())) {
				currentFocus = v;
				v.dispatchTouchEvent(ev);
				return false;
			}
		}
		currentFocus = null;
		return true;
	}

	@SuppressLint("WrongCall")
	@Override
	protected void dispatchDraw(Canvas canvas) {
		this.onDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			this.layout(l, t, r, b);
			this.invalidate();
		}
	}

	@Override
	public View getCurrentFocus() {
		return currentFocus;
	}

}