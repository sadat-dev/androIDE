package dev.sadat.androide.parsers;

import dev.sadat.androide.datatypes.CodeTree;

public interface CodeParser {
	public void parse();
	public void load();
	public CodeTree getTree();
	public void setTree(CodeTree tree);
}
