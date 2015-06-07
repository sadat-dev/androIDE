package dev.sadat.androide.listeners;

import android.text.method.MovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class EditorViewTouchListener implements OnTouchListener {

	private float prevX;
	private float prevY;
	private float currX;
	private float currY;

	private static final float DELTA_THRESHOLD = 0.2f;

	private EditorTouchCallback callback;

	public void setTouchCallback(EditorTouchCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean onTouch(View view, MotionEvent evt) {
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
		
		return callback.motionEvent(type, deltaX, deltaY);
	}

}