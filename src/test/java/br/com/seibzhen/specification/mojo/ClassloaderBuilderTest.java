package br.com.seibzhen.specification.mojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.junit.Before;
import org.junit.Test;

public class ClassloaderBuilderTest {
	
	private ClassLoaderBuilder classloaderBuilder;
	
	private ClassLoader parentClassloader;
	
	@Before
	public void setUp() throws MalformedURLException{
		parentClassloader = new URLClassLoader(new URL[]{new File(".").toURI().toURL()});
		classloaderBuilder = new ClassLoaderBuilder(parentClassloader);
	}
	
	@Test
	public void shouldBuildClassloaderBasedOnMavenArtifact() throws MalformedURLException{
		Artifact artifact = mock(Artifact.class);
		File file = new File("/some/location");
		when(artifact.getFile()).thenReturn(file);
		
		classloaderBuilder.add(artifact);
		
		ClassLoader classloader = classloaderBuilder.get();
		assertTrue(classloader instanceof URLClassLoader);
		assertEquals(file.toURI().toURL(), ((URLClassLoader)classloader).getURLs()[0]);
	}
	
	@Test
	public void shouldBuildClassloaderBasedOnListOfMavenArtifacts() throws MalformedURLException{
		List<Artifact> randomMavenRuntimeArtifacts = new ArrayList<Artifact>();
		randomMavenRuntimeArtifacts.add(anArtifact());
		randomMavenRuntimeArtifacts.add(anArtifact());
		randomMavenRuntimeArtifacts.add(anArtifact());
		
		classloaderBuilder.addAll(randomMavenRuntimeArtifacts);
		
		URLClassLoader classloader = (URLClassLoader) classloaderBuilder.get();
		assertEquals(3, classloader.getURLs().length);
	}
	
	@Test
	public void shouldHaveAClassLoaderHierarchy() throws MalformedURLException{
		
		classloaderBuilder.add(anArtifact());
		
		URLClassLoader classloader = (URLClassLoader) classloaderBuilder.get();
		assertEquals(parentClassloader, classloader.getParent());
		assertFalse(parentClassloader.equals(classloader));
	}
	
	@Test
	public void shouldBuildClassloaderBasedOnArbitraryFileLocation() throws MalformedURLException{
		File file = new File("target/classes");
		
		classloaderBuilder.add(file);
		
		URLClassLoader classloader = (URLClassLoader) classloaderBuilder.get();
		assertEquals(file.toURI().toURL(), ((URLClassLoader)classloader).getURLs()[0]);
	}
	
	protected Artifact anArtifact(){
		Artifact artifact = mock(Artifact.class);
		File file = new File("/some/location/" + Math.random());
		when(artifact.getFile()).thenReturn(file);
		
		return artifact;
	}

}
