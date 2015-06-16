package dev.sadat.androide.parsers.java;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaParser {

	private AST ast;
	private CompilationUnit classUnit;

	public JavaParser() {
		ast = AST.newAST(AST.JLS3);
		classUnit = ast.newCompilationUnit();
	}

	public PackageDeclaration createPackage(String packageName) {
		PackageDeclaration pack = ast.newPackageDeclaration();
		pack.setName(ast.newName(packageName));
		classUnit.setPackage(pack);
		return pack;
	}

	public ImportDeclaration createImportStatement(String packageName) {
		String[] packageNames = packageName.split("\\.");
		ImportDeclaration importDec = ast.newImportDeclaration();
		importDec.setName(ast.newName(packageNames));
		importDec.setOnDemand(true);
		classUnit.imports().add(importDec);
		return importDec;
	}
	
	public TypeDeclaration createClassStatement(String className, String superClassName, String... interfacenames){
		TypeDeclaration typeDec = ast.newTypeDeclaration();
		//typeDec.modifiers().add(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
		typeDec.setName(ast.newSimpleName(className));
		if (superClassName != null){
			typeDec.setSuperclassType(ast.newSimpleType(ast.newName(superClassName)));
		}
		for (int i=0;i<interfacenames.length; i++){
			typeDec.superInterfaceTypes().add(ast.newSimpleType(ast.newSimpleName(interfacenames[i])));
		}
		classUnit.types().add(typeDec);
		return typeDec;
	}
	
	public String result(){
		return classUnit.toString();
	}

}
