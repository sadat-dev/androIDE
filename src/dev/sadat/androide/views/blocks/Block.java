package dev.sadat.androide.views.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import dev.sadat.androide.listeners.EditorTouchCallback;
import dev.sadat.androide.views.CodeBlockPaints;
import dev.sadat.androide.views.EditorView;

public class Block {

	private EditorView context;

	private boolean isInit = false;

	private static int PADDING = 16;

	private float[] position = { 0, 0 };
	private Rect backBlock;
	private Rect headBlock;
	private Rect typeIconBlock;

	private String header = "default";
	private String body = "default";
	private int blockType = CodeBlockPaints.BOOLEAN;

	public Block(EditorView context, float x, float y,int type) {
		this.position = new float[] { x, y };
		this.context = context;
		if(type != 0)
			this.blockType = type;
	}
	
	public Block(EditorView context, float x, float y) {
		this(context,0,0,0);
	}

	public Block(EditorView context) {
		this(context, 0, 0,0);
	}

	public void draw(Canvas canvas) {
		if (isInit == false)
			initialize(canvas);
		canvas.save();
		canvas.translate(position[0], position[1]);
		canvas.drawRect(backBlock, context.getPaints().getBackground());
		canvas.drawRect(headBlock, context.getPaints().getHeaderBack(blockType));
		canvas.drawRect(typeIconBlock, context.getPaints().getHeaderText());
		canvas.drawText(header, 0, context.getPaints().getHeaderText()
				.getTextSize(), context.getPaints().getHeaderText());
		canvas.drawText(body, 0, headBlock.height()
				+ context.getPaints().getBodyText().getTextSize(), context
				.getPaints().getBodyText());
		canvas.restore();
	}

	// TODO Move these initializations to a global layer so that number of
	// instances becomes
	// constant instead of n
	protected void initialize(Canvas canvas) {
		int textRight = (int) Math.max(context.getPaints().getHeaderText()
				.measureText(header), context.getPaints().getBodyText()
				.measureText(body));
		textRight += PADDING * 2;
		headBlock = new Rect(0, 0, textRight, (int) context.getPaints()
				.getHeaderText().getTextSize());
		headBlock.inset(-PADDING, -PADDING);
		backBlock = new Rect(0, 0, textRight, (int) context.getPaints()
				.getBodyText().getTextSize() * 4);
		backBlock.inset(-PADDING, -PADDING);
		typeIconBlock = new Rect(headBlock);
		typeIconBlock.inset(PADDING, PADDING);
		typeIconBlock.left = 100;
	}

	public boolean onBlockTouched(int type, float deltaX, float deltaY) {
		if (type == EditorTouchCallback.SCROLL) {
			position[0] += deltaX;
			position[1] += deltaY;
			return true;
		}
		return true;
	}

	public Rect getBounds() {
		Rect bound = new Rect((int) position[0], (int) position[1],
				(int) (backBlock.right + position[0]),
				(int) (backBlock.bottom + position[1]));
		return bound;
	}
}