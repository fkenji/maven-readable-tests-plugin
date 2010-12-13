package br.com.seibzhen.specification;

import static java.lang.Character.isUpperCase;
import static java.lang.Character.isDigit;

public class Translator {
	
	private static final String WHITESPACE = " ";

	/**
	 * Translate a given method name to a readable one.
	 * It is expected that the method name is written in camel case, such as in
	 * shouldRegisterUseWhenFinancialBalanceIsPositive.
	 * @param name
	 * @return
	 */
	public String translate(String name){
		
		if(name == null){
			throw new IllegalArgumentException("Name should not be null!");
		}
		
		StringBuilder readableName = new StringBuilder();
		readableName.append(Character.toUpperCase(name.charAt(0)));
		
		for(int i = 1; i < name.length(); i++){
			Character currentCharacter = name.charAt(i);
			Character previousCharacter = name.charAt(i - 1);
			
	        if(newWordStarts(currentCharacter, previousCharacter) || newNumberStarts(currentCharacter, previousCharacter)){
	        	readableName.append(WHITESPACE);
	        }
	        
	        readableName.append(Character.toLowerCase(currentCharacter));	        
		}
		
		return readableName.toString();
	}

	private boolean newWordStarts(Character currentCharacter, Character previousCharacter) {
		return isUpperCase(currentCharacter) || (isDigit(previousCharacter) && !isDigit(currentCharacter));
	}

	private boolean newNumberStarts(Character currentCharacter, Character previousCharacter){
		return isDigit(currentCharacter) && !isDigit(previousCharacter);
	}
}