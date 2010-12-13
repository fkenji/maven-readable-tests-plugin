package br.com.seibzhen.specification;

import java.util.List;

public class ReadableNamesReporter {

	private ClassLoader classloader;
	
	private MethodNamesExtractor extractor = new MethodNamesExtractor();
	private Translator translator = new Translator();
	
	public ReadableNamesReporter(ClassLoader classloader){
		this.classloader = classloader;
	}
	
	public String report(String className) throws ClassNotFoundException{
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("Test " + className + ":\n");
		stringBuffer.append("---\n");
		
	  	List<String> methodNames = extractor.extract(classloader.loadClass(className));
	  	
    	for(String method: methodNames){
    		stringBuffer.append(translator.translate(method));
    		stringBuffer.append("\n");
    	}
    	
    	stringBuffer.append("\n");
		
		return stringBuffer.toString();
	}
}
