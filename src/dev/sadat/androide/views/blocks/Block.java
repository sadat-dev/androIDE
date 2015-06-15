package dev.sadat.androide.views.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import dev.sadat.androide.datatypes.BlockContent;
import dev.sadat.androide.listeners.EditorTouchCallback;
import dev.sadat.androide.views.CodeBlockPaints;
import dev.sadat.androide.views.EditorView;

public class Block {

	private CodeBlockPaints context;
	private boolean isInit = false;

	private static int PADDING = 16;
	private float[] position = { 0, 0 };
	private int[] bounds = { 0, 0 };

	private String header = "default";
	private String body = "default";
	private int blockType = CodeBlockPaints.BOOLEAN;

	public Block(CodeBlockPaints context, float x, float y, int type) {
		this.position = new float[] { x, y };
		this.context = context;
		if (type != 0)
			this.blockType = type;
	}

	public void draw(Canvas canvas) {
		if (isInit == false)
			initialize(canvas);
		
		setupRects();

		canvas.save();
		canvas.translate(position[0], position[1]);
		canvas.drawRect(context.getBack(), context.getBackground());
		canvas.drawRect(context.getHeader(), context.getHeaderBack(blockType));
		canvas.drawRect(context.getIcon(), context.getHeaderText());
		canvas.drawText(header, 0, context.getHeaderText().getTextSize(), context.getHeaderText());
		canvas.drawText(body, 0, context.getHeader().height() + context.getBodyText().getTextSize(),
				context.getBodyText());
		canvas.restore();
	}

	private void setupRects() {
		context.getHeader().set(0, 0, bounds[0], (int) context.getHeaderText().getTextSize());
		context.getHeader().inset(-PADDING, -PADDING);
		context.getBack().set(0, 0, bounds[0], (int) context.getBodyText().getTextSize() * 4);
		context.getBack().inset(-PADDING, -PADDING);
		context.getIcon().set(context.getHeader());
		context.getIcon().inset(PADDING, PADDING);
		context.getIcon().left = context.getIcon().right - context.getIcon().height();
	}

	protected void initialize(Canvas canvas) {
		int textRight = (int) Math.max(context.getHeaderText().measureText(header),
				context.getBodyText().measureText(body));
		textRight += PADDING * 2;
		bounds = new int[] { textRight, (int) context.getBodyText().getTextSize() * 4 };
		isInit = true;
	}

	public boolean onBlockTouched(int type, float deltaX, float deltaY) {
		if (type == EditorTouchCallback.SCROLL) {
			position[0] += deltaX;
			position[1] += deltaY;
			return true;
		}
		if (type == EditorTouchCallback.ZOOM) {
			return false;
		}
		return true;
	}

	public Rect getBounds() {
		Rect bound = new Rect((int) position[0] - PADDING, (int) position[1] - PADDING,
				(int) (bounds[0] + position[0] + PADDING), (int) (bounds[1] + position[1]) + PADDING);
		return bound;
	}

	protected void setContent(BlockContent content){
		this.header = content.getHeader();
		this.body = content.getBody();
		isInit = false;
	}
	
}