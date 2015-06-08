package dev.sadat.androide.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import dev.sadat.androide.views.EditorView;

public class BlockViewTouchListener implements OnTouchListener {

	private BlockTouchCallback listener;
	private static final float DELTA_THRESHOLD = 0.2f;
	private float prevX;
	private float prevY;
	private float currX;
	private float currY;
	
	public BlockViewTouchListener(){
	}
	
	public void setTouchCallback(BlockTouchCallback callback) {
		this.listener = callback;
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent evt) {
		if (!(view instanceof EditorView)) {
			boolean handled = view.onTouchEvent(evt);
			if (handled)
				return true;
		}
		int numPointers = evt.getPointerCount();
		if (numPointers < 2) {
			return handleSinglePointers(evt);
		} else {
			return false;
			// return handleMultiPointers(evt);
		}
	}

	private boolean handleSinglePointers(MotionEvent evt) {
		currX = evt.getX(0);
		currY = evt.getY(0);

		int type = EditorTouchCallback.NO_ACTION;

		switch (evt.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			type = EditorTouchCallback.TOUCH;
			prevX = currX;
			prevY = currY;
			break;
		case MotionEvent.ACTION_UP:
			type = EditorTouchCallback.UNTOUCH;
			break;
		case MotionEvent.ACTION_MOVE:
			type = EditorTouchCallback.TOUCH;
			break;
		}

		float deltaX = currX - prevX;
		float deltaY = currY - prevY;

		if ((deltaX < DELTA_THRESHOLD || deltaX > DELTA_THRESHOLD) && type == EditorTouchCallback.TOUCH)
			type = EditorTouchCallback.SCROLL;
		else if ((deltaY < DELTA_THRESHOLD || deltaY > DELTA_THRESHOLD) && type == EditorTouchCallback.TOUCH)
			type = EditorTouchCallback.SCROLL;

		prevX = currX;
		prevY = currY;
		
		return listener.onBlockTouched(type, deltaX, deltaY);
	}

}
