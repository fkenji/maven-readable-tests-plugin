package br.com.seibzhen.specification;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestClassFinderTest {

	private TestClassFinder testClassFinder;
	private File directory;
	
	private String[] expectedClasses = new String[] 
          { "br.com.seibzhen.specification.TestExtractMethodName", 
			"br.com.seibzhen.specification.TestNameTranslator" ,
			"br.com.seibzhen.specification.example.TestedClassTest",
          };
	
	@Before
	public void setUp(){
		testClassFinder = new TestClassFinder();
		directory = new File("src/test/etc/test-classes");
	}
	
	@Test
	public void shouldFindAllTestClasses(){
		String[] allClasses = testClassFinder.findFrom(directory);
		Assert.assertArrayEquals(expectedClasses, allClasses);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldSignalErrorWhenGivenDirectoryDoesNotExist(){
		testClassFinder.findFrom(new File("src/some/file"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldSignalErrorWhenGivenDirectoryIsAFile(){
		testClassFinder.findFrom(new File("src/test/resources/test-classes/br/com/seibzhen/specification/TestExtractMethodName.class"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldSignalErrorWhenGivenNullDirectory(){
		testClassFinder.findFrom(null);
	}
}