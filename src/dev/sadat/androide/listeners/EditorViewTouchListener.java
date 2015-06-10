package dev.sadat.androide.listeners;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import dev.sadat.androide.views.EditorView;

public class EditorViewTouchListener implements OnTouchListener {

	private float prevX;
	private float prevY;
	private float currX;
	private float currY;

	private EditorTouchCallback callback;

	public void setTouchCallback(EditorTouchCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean onTouch(View view, MotionEvent evt) {
		Log.w("EditorViewTouchListener.onTouch", "Called Touch");
		if (!(view instanceof EditorView)){
			view.onTouchEvent(evt);
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
		if (callback.getCurrentFocus() != null){
			return callback.getCurrentFocus().callOnClick();
		}
		
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
			type = EditorTouchCallback.SCROLL;
			break;
		}

		float deltaX = currX - prevX;
		float deltaY = currY - prevY;

		prevX = currX;
		prevY = currY;
		
		return callback.motionEvent(type, deltaX, deltaY);
	}

}
