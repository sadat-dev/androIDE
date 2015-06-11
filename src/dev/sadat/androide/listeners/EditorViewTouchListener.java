package dev.sadat.androide.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class EditorViewTouchListener implements OnTouchListener {

	private float prevX;
	private float prevY;
	private float currX;
	private float currY;
	
	private float prevX1;
	private float prevY1;
	private float currX1;
	private float currY1;

	private EditorTouchCallback callback;

	public void setTouchCallback(EditorTouchCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean onTouch(View view, MotionEvent evt) {
		view.performClick();
		int numPointers = evt.getPointerCount();
		if (numPointers < 2) {
			return handleSinglePointer(evt);
		} else {
			 return handleMultiPointers(evt);
		}
	}

	private boolean handleSinglePointer(MotionEvent evt) {
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
			prevX = currX;
			prevY = currY;
			break;
		case MotionEvent.ACTION_MOVE:
			type = EditorTouchCallback.SCROLL;
			break;
		}

		float deltaX = currX - prevX;
		float deltaY = currY - prevY;

		prevX = currX;
		prevY = currY;
		
		return callback.motionEvent(type, deltaX, deltaY, new float[]{currX, currY});
	}

	private boolean handleMultiPointers(MotionEvent evt){
		currX = evt.getX(0);
		currY = evt.getY(0);
		currX1 = evt.getX(1);
		currY1 = evt.getY(1);
		int type = EditorTouchCallback.NO_ACTION;
		
		float deltaX = 0;
		
		if (evt.getPointerCount() == 2){
			
			switch (evt.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				type = EditorTouchCallback.TOUCH;
				break;
			case MotionEvent.ACTION_UP:
				type = EditorTouchCallback.UNTOUCH;
				deltaX = 0;
				break;
			case MotionEvent.ACTION_MOVE:
				type = EditorTouchCallback.ZOOM;
				deltaX = (currX - currX1)+(currY - currY1);
				deltaX *= deltaX;
				deltaX = (float) Math.sqrt(deltaX);
				break;
			}
		}
		
		prevX = currX;
		prevY = currY;
		prevX1 = currX1;
		prevY1 = currY1;
		return callback.motionEvent(type, deltaX, 0, new float[]{0,0});
	}
}
