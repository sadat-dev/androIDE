package dev.sadat.androide.datatypes;

import org.eclipse.jdt.core.dom.ASTNode;

public class BlockContent {

	private String header;
	private String body;
	
	private ASTNode content;
	
	public BlockContent(ASTNode content){
		this.content = content;
	}
	
	public int getType(){
		return content.getNodeType();
	}
	
	public void setHeader(String header){
		this.header = header;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	public String getHeader(){
		return header;
	}
	
	public String getBody(){
		return body;
	}
	
}
