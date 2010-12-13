package br.com.seibzhen.specification.mojo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;

public class ClassLoaderBuilder {

	private List<Artifact> allArtifacts;
	private ClassLoader parentClassLoader;
	private List<File> allFiles;
	
	public ClassLoaderBuilder(ClassLoader parentClassLoader){
		this.allArtifacts = new ArrayList<Artifact>();
		this.allFiles = new ArrayList<File>(); 
		this.parentClassLoader = parentClassLoader;
	}

	public void add(Artifact artifact) {
		allArtifacts.add(artifact);
	}

	public ClassLoader get() throws MalformedURLException {
		URL[] urls = new URL[allArtifacts.size() + allFiles.size()];
		
		int i = 0;
		for(Artifact artifact: allArtifacts){
			urls[i++] = artifact.getFile().toURI().toURL();
		}
		
		for(File file: allFiles){
			urls[i++] = file.toURI().toURL();
		}
		
		URLClassLoader urlClassLoader = new URLClassLoader(urls, parentClassLoader);
		return urlClassLoader;
	}

	public void addAll(List<Artifact> artifactList) {
		allArtifacts.addAll(artifactList);
	}

	public void add(File file) {
		allFiles.add(file);
	}

	
}
