package br.com.seibzhen.specification;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MethodNamesExtractor {

	private static final String BLANK_SPACE = "";
	private static final String JUNIT_TEST_PREFFIX = "test";
	
	/**
	 * Extracts a list of test method names from the supplied test class.
	 * Two criteria are used for determining method names:
	 * <br>
	 * - if there's a 'test' prefix to the test method name it is considered a junit3 method. This is 
	 * case-sensitive, so Test or tEst won't be considered as test methods.
	 * <br> 
     * - if a org.junit.Test annotation is present it is considered a junit4 one.
	 * <br/>
	 * Junit3 methods are stripped of their 'test' prefix.
	 * @param clazz the test class
	 * @return
	 */
	public List<String> extract(Class<?> clazz){
		
		if(clazz == null){
			throw new IllegalArgumentException("Class cannot be null!");
		}
		
		Method[] methods = clazz.getMethods();
		List<String> allMethodNames = new ArrayList<String>();
		
		for(Method method : methods){
			String currentMethodName = method.getName();
			
			if(isAJunit3Method(method)){
				currentMethodName = replaceJunit3TestPrefix(method.getName());
				currentMethodName = firstLetterToUpperCase(currentMethodName);
				allMethodNames.add(currentMethodName);
			}else if(isAJunit4Method(method)){
				currentMethodName = method.getName();
				currentMethodName = firstLetterToUpperCase(currentMethodName);
				allMethodNames.add(currentMethodName);
			}
		}
		
		return allMethodNames;
	}
	
	private Boolean isAJunit3Method(Method method){
		return method.getName().startsWith(JUNIT_TEST_PREFFIX);
	}
	
	private Boolean isAJunit4Method(Method method){
		return method.isAnnotationPresent(Test.class);
	}
	
	private String replaceJunit3TestPrefix(String methodName){
		if(JUNIT_TEST_PREFFIX.equals(methodName)){
			return methodName;
		}
		
		return methodName.replaceFirst(JUNIT_TEST_PREFFIX, BLANK_SPACE);
	}
	
	private String firstLetterToUpperCase(String phrase){
		String firstLetter = String.valueOf(phrase.charAt(0));
		return phrase.replaceFirst(firstLetter, firstLetter.toUpperCase());
	}
	
}
