package dev.sadat.androide.datatypes;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class BlockContent {

	private String header;
	private String body;
	
	private ASTNode content;
	
	public BlockContent(ASTNode content){
		this.content = content;
		initialize();
	}
	
	private void initialize(){
		// TODO
	}
	
	public int getType(){
		return content.getNodeType();
	}
	
	public String getHeader(){
		return header;
	}
	
	public String getBody(){
		return body;
	}
	
}
