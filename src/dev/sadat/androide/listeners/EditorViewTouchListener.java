package dev.sadat.androide.listeners;

import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.View.OnTouchListener;

public class EditorViewTouchListener implements OnTouchListener {

	private float prevX;
	private float prevY;
	private float currX;
	private float currY;

	private static final float DELTA_THRESHOLD = 2;

	private EditorTouchCallback callback;

	@Override
	public boolean onTouch(View view, MotionEvent evt) {
		int numPointers = evt.getPointerCount();
		if (numPointers < 2) {
			return handleSinglePointers(evt);
		} else {
			return false;
			//return handleMultiPointers(evt);
		}
	}

	private boolean handleSinglePointers(MotionEvent evt) {
		currX = evt.getX(0);
		currY = evt.getY(0);

		int type = EditorTouchCallback.NO_ACTION;

		switch (evt.getAction()) {
		case MotionEvent.ACTION_DOWN:
			type = EditorTouchCallback.TOUCH;
			break;
		case MotionEvent.ACTION_UP:
			type = EditorTouchCallback.UNTOUCH;
			break;
		}

		if (MotionEvent.ACTION_DOWN == evt.getAction()){
			prevX = currX;
			prevY = currY;
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

	/*private boolean handleMultiPointers(MotionEvent evt) {
		currX = evt.getX(0);
		currY = evt.getY(0);
		// TODO other handling

		prevX = currX;
		prevY = currY;

		return callback.motionEvent(type, delta);
	}*/

}
