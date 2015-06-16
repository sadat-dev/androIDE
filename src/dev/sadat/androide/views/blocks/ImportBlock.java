package dev.sadat.androide.views.blocks;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import dev.sadat.androide.datatypes.BlockContent;
import dev.sadat.androide.views.EditorView;

public class ImportBlock extends Block{

	private static final String IMPORT_DECLARATION = "import";
	private static final String IMPORT_STATIC = "static";

	public ImportBlock(EditorView context, float x, float y, int type) {
		super(context, x, y, type);
		// TODO Auto-generated constructor stub
	}

	ImportDeclaration importDec;
	BlockContent content;

	@Override
	public void changeContent(String... imports) {
		 this.importDec = super.parent.getParser().createImportStatement(imports[0]);
		content = new BlockContent(importDec);
		String header = IMPORT_DECLARATION;
		if (importDec.isStatic())
			header += " "+IMPORT_STATIC;
		content.setHeader(header);
		content.setBody(importDec.getName().getFullyQualifiedName());
		super.setContent(content);
	}
	
}
