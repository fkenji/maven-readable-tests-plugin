package br.com.seibzhen.specification;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadableNamesReporterTest {

	private ReadableNamesReporter readableNamesReporter;
	
	private ClassLoader classloader;
	
	@Before
	public void setUp() throws MalformedURLException{
		classloader = new URLClassLoader(new URL[]{new File("src/test/etc/test-classes").toURI().toURL()});
		readableNamesReporter = new ReadableNamesReporter(classloader);
	}

	@Test
	public void shouldGenerateReportOnClass() throws ClassNotFoundException{
		String expectedReport =
			"Test br.com.seibzhen.specification.example.TestedClassTest:\n" +
			"---\n"+
			"Junit 3 test\n" +
			"Junit 4 test\n\n";
		Assert.assertEquals(expectedReport,readableNamesReporter.report("br.com.seibzhen.specification.example.TestedClassTest"));
	}
	
}
