package dev.sadat.androide.views.blocks;

import android.graphics.Canvas;
import android.graphics.Rect;
import dev.sadat.androide.datatypes.BlockContent;
import dev.sadat.androide.listeners.EditorTouchCallback;
import dev.sadat.androide.views.CodeBlockPaints;
import dev.sadat.androide.views.EditorView;

public abstract class Block {

	protected EditorView parent;
	private boolean isInit = false;
	private boolean drawArc = false;

	private static int PADDING = 16;
	private float[] position = { 0, 0 };
	private int[] bounds = { 0, 0 };

	private String header = "default";
	private String body = "default";
	private int blockType = CodeBlockPaints.BOOLEAN;

	public Block(EditorView context, float x, float y, int type) {
		this.position = new float[] { x, y };
		this.parent = context;
		if (type != 0)
			this.blockType = type;
	}

	public void draw(Canvas canvas) {
		if (isInit == false)
			initialize(canvas);

		setupRects();

		canvas.save();
		canvas.translate(position[0], position[1]);
		canvas.drawRect(parent.getPaints().getBack(), parent.getPaints().getBackground());
		canvas.drawRect(parent.getPaints().getHeader(), parent.getPaints().getHeaderBack(blockType));
		canvas.drawRect(parent.getPaints().getIcon(), parent.getPaints().getHeaderText());
		canvas.drawText(header, 0, parent.getPaints().getHeaderText().getTextSize(),
				parent.getPaints().getHeaderText());
		canvas.drawText(body, 0,
				parent.getPaints().getHeader().height() + parent.getPaints().getBodyText().getTextSize(),
				parent.getPaints().getBodyText());
		if (drawArc) {
			canvas.drawLine(parent.getPaints().getIcon().right, parent.getPaints().getIcon().top, 500, 500,
					parent.getPaints().getBodyText());
		}
		canvas.restore();
	}

	private void setupRects() {
		parent.getPaints().getHeader().set(0, 0, bounds[0], (int) parent.getPaints().getHeaderText().getTextSize());
		parent.getPaints().getHeader().inset(-PADDING, -PADDING);
		parent.getPaints().getBack().set(0, 0, bounds[0], (int) parent.getPaints().getBodyText().getTextSize() * 4);
		parent.getPaints().getBack().inset(-PADDING, -PADDING);
		parent.getPaints().getIcon().set(parent.getPaints().getHeader());
		parent.getPaints().getIcon().inset(PADDING, PADDING);
		parent.getPaints().getIcon().left = parent.getPaints().getIcon().right - parent.getPaints().getIcon().height();
	}

	protected void initialize(Canvas canvas) {
		int textRight = (int) Math.max(parent.getPaints().getHeaderText().measureText(header),
				parent.getPaints().getBodyText().measureText(body));
		// TODO Clamp the size to a relative maximum
		// textRight = Math.min(textRight, 500);
		textRight += PADDING * 2;
		bounds = new int[] { textRight, (int) parent.getPaints().getBodyText().getTextSize() * 4 };
		isInit = true;
	}

	public boolean onBlockTouched(int type, float deltaX, float deltaY, float[] coords) {
		if (type == EditorTouchCallback.TOUCH) {
			Rect iconRect = getBounds();
			iconRect.bottom = (int) parent.getPaints().getHeaderText().getTextSize();
			iconRect.inset(PADDING, PADDING);
			iconRect.left = iconRect.right - iconRect.height();
			if (iconRect.contains((int) coords[0], (int) coords[1])) {
				drawArc = true;
			}
		}
		if (type == EditorTouchCallback.SCROLL) {
			position[0] += deltaX;
			position[1] += deltaY;
			return true;
		}
		if (type == EditorTouchCallback.UNTOUCH) {
			position[0] += parent.GRID_GAP / 2;
			position[1] += parent.GRID_GAP / 2;
			position[0] -= position[0] % parent.GRID_GAP;
			position[1] -= position[1] % parent.GRID_GAP;
			drawArc = false;
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

	protected void setContent(BlockContent content) {
		this.header = content.getHeader();
		this.body = content.getBody();
		isInit = false;
	}

	public abstract void changeContent(String... content);

}