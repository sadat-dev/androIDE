package dev.sadat.androide.views;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CodeBlockPaints {

	public static final int BOOLEAN = Color.rgb(44, 191, 28);
	public static final int STRING = Color.rgb(191, 63, 0);
	
	private static int textSize = 32;
	
	private static Paint headerPaint;
	private static Paint backgroundPaint;
	private static Paint headerText;
	private static Paint bodyText;
	
	private Rect headerRect;
	private Rect backRect;
	private Rect iconRect;
	
	public CodeBlockPaints(){
		createHeader();
		createBody();
	}
	
	private void createHeader(){
		headerPaint = new Paint();
		headerPaint.setColor(Color.MAGENTA);
		headerPaint.setStyle(Paint.Style.FILL);
		headerText = new Paint();
		headerText.setColor(Color.BLACK);
		headerText.setTextSize(textSize);
		headerText.setStyle(Paint.Style.FILL_AND_STROKE);
		
		headerRect = new Rect();
		iconRect = new Rect();
	}
	
	private void createBody(){
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.BLACK);
		backgroundPaint.setStyle(Paint.Style.FILL);
		bodyText = new Paint();
		bodyText.setColor(Color.WHITE);
		bodyText.setTextSize(textSize);
		bodyText.setStyle(Paint.Style.FILL_AND_STROKE);
		
		backRect = new Rect();
	}
	
	public Paint getHeaderBack(int type){
		headerPaint.setColor(type);
		return headerPaint;
	}
	
	public Paint getBackground(){
		return backgroundPaint;
	}
	
	public Paint getHeaderText(){
		return headerText;
	}
	
	public Paint getBodyText(){
		return bodyText;
	}
	
	public Rect getHeader(){
		return headerRect;
	}
	
	public Rect getBack(){
		return backRect;
	}
	
	public Rect getIcon(){
		return iconRect;
	}
	
}
