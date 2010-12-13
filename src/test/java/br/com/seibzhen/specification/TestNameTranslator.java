package br.com.seibzhen.specification;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.seibzhen.specification.Translator;


public class TestNameTranslator {

	private Translator translator;
	
	@Before
	public void setUp(){
		translator = new Translator();
	}
	
	@Test
	public void testShouldSeparateByCamelCase(){
		assertEquals("Should not register use if value is zero",translator.translate("ShouldNotRegisterUseIfValueIsZero"));
	}
	
	@Test
	public void testFirstLetterIsLowercase(){
		assertEquals("Hello world",translator.translate("helloWorld"));
	}
	
	@Test
	public void testFirstLetterIsUppercase(){
		assertEquals("Test should be ok",translator.translate("TestShouldBeOk"));
	}
	
	@Test
	public void testTestNameHasNumbers(){
		assertEquals("Test register use is possible in a 30 day month",translator.translate("testRegisterUseIsPossibleInA30DayMonth"));
	}
	
	@Test
	public void testTestNameStartsWithANumber(){
		assertEquals("30 avos",translator.translate("30Avos"));
	}
	
	@Test
	public void testTestNameWithNumberAndLowerCase(){
		assertEquals("30 avos",translator.translate("30avos"));
	}	

	@Test(expected=IllegalArgumentException.class)
	public void testExceptionOnNullMethodNames(){
		translator.translate(null);
	}
}
