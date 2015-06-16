package dev.sadat.androide.views.blocks;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import dev.sadat.androide.datatypes.BlockContent;
import dev.sadat.androide.views.EditorView;

public class ClassBlock extends Block{

	public ClassBlock(EditorView context, float x, float y, int type) {
		super(context, x, y, type);
		// TODO Auto-generated constructor stub
	}

	TypeDeclaration typeDec;
	BlockContent content;

	@Override
	public void changeContent(String... packageName) {
		typeDec = super.parent.getParser().createClassStatement(packageName[0], packageName[1], packageName[2]);
		content = new BlockContent(typeDec);
		content.setHeader(typeDec.getName().getFullyQualifiedName());
		content.setBody(typeDec.getModifiersProperty().toString());
		super.setContent(content);
	}
	
}
