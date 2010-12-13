package br.com.seibzhen.specification;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import br.com.seibzhen.specification.MethodNamesExtractor;
import br.com.seibzhen.specification.example.Junit3And4ClassTest;
import br.com.seibzhen.specification.example.Junit3TestClass;
import br.com.seibzhen.specification.example.Junit3WithPreffixOnlyAsTestName;
import br.com.seibzhen.specification.example.Junit3WithVariedCasingTestClass;
import br.com.seibzhen.specification.example.Junit4TestClass;

public class TestExtractMethodName {

	private MethodNamesExtractor methodNamesExtractor;
	
	@Before
	public void setUp(){
		methodNamesExtractor = new MethodNamesExtractor();		
	}
	
	
	@Test
	public void shouldExtractJunit3Test(){
		List<String> methodNames = methodNamesExtractor.extract(Junit3TestClass.class);
		assertTrue(methodNames.contains("Junit3Test"));
	}
	
	@Test
	public void shouldExtractJunit4Test(){
		List<String> methodNames = methodNamesExtractor.extract(Junit4TestClass.class);
		assertTrue(methodNames.contains("Junit4Test"));
	}
	
	@Test
	public void shouldExtractWhenTestNameIsComposedSolelyWithJunit3Preffix(){
		List<String> methodNames = methodNamesExtractor.extract(Junit3WithPreffixOnlyAsTestName.class);
		assertTrue(methodNames.contains("Test"));
	}
	
	@Test
	public void shouldExtractBothJunit4And3Tests(){
		List<String> methodNames = methodNamesExtractor.extract(Junit3And4ClassTest.class);
		
		assertEquals(2, methodNames.size());
		assertTrue(methodNames.contains("Junit3Test"));
		assertTrue(methodNames.contains("Junit4Test"));
	}
	
	@Test
	public void shouldNotExtractCaseVariationsOfJunit3Preffixes(){
		List<String> methodNames = methodNamesExtractor.extract(Junit3WithVariedCasingTestClass.class);
		assertEquals(0, methodNames.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullArgumentClass(){
		methodNamesExtractor.extract(null);
	}
	
}
