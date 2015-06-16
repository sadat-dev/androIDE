package dev.sadat.androide.views.blocks;

import org.eclipse.jdt.core.dom.PackageDeclaration;
import dev.sadat.androide.datatypes.BlockContent;
import dev.sadat.androide.views.EditorView;

public class PackageBlock extends Block{

	private static final String PACKAGE_DECLARATION = "package";

	public PackageBlock(EditorView context, float x, float y, int type) {
		super(context, x, y, type);
		// TODO Auto-generated constructor stub
	}

	PackageDeclaration packageDec;
	BlockContent content;

	@Override
	public void changeContent(String... packageName) {
		packageDec = super.parent.getParser().createPackage(packageName[0]);
		content = new BlockContent(packageDec);
		content.setHeader(PACKAGE_DECLARATION);
		content.setBody(packageDec.getName().getFullyQualifiedName());
	}
	
}
