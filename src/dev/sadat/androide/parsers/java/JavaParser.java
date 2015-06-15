package dev.sadat.androide.parsers.java;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaParser {

	private AST ast;
	private CompilationUnit classUnit;

	public JavaParser() {
		ast = AST.newAST(AST.JLS3);
		classUnit = ast.newCompilationUnit();
	}

	public void createPackage(String packageName) {
		PackageDeclaration pack = ast.newPackageDeclaration();
		pack.setName(ast.newSimpleName(packageName));
		classUnit.setPackage(pack);
	}

	public void createImportStatement(String packageName) {
		ImportDeclaration importDec = ast.newImportDeclaration();
		importDec.setName(ast.newSimpleName(packageName));
		importDec.setOnDemand(true);
		classUnit.imports().add(importDec);
	}
	
	public void createClassStatement(String className, String superClassName, String... interfacenames){
		TypeDeclaration typeDec = ast.newTypeDeclaration();
		typeDec.modifiers().add(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
		typeDec.setName(ast.newSimpleName(className));
		// Add superclass and interfaces
	}

}
