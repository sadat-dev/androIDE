package dev.sadat.androide.listeners;

public interface EditorTouchCallback {

	public static final int NO_ACTION = 0;
	public static final int ZOOM = 1;
	public static final int SCROLL = 2;
	public static final int TOUCH = 3;
	public static final int UNTOUCH = 4;
	
	public boolean motionEvent(int type, float deltaX, float deltaY, float[]coords);

}
