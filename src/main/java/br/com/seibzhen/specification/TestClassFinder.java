package br.com.seibzhen.specification;

import java.io.File;
import java.util.Arrays;

import com.google.classpath.ClassPath;
import com.google.classpath.ClassPathFactory;
import com.google.classpath.RegExpResourceFilter;
import com.google.classpath.ResourceFilter;

public class TestClassFinder {

	public String[] findFrom(File directory) {
		
		if(directory == null || !directory.exists() || !directory.isDirectory()){
			throw new IllegalArgumentException("Directory :" + directory + " does not exist!");
		}
		
		ClassPathFactory factory = new ClassPathFactory();
		ClassPath classpath = factory.createFromPath(directory.toString());
		
		ResourceFilter filter = new RegExpResourceFilter(".*", ".*.class");
		String[] allClasses = classpath.findResources("", filter);
		
		for (int i = 0; i < allClasses.length; i++) {
			allClasses[i] = allClasses[i].replaceAll("/", ".").replaceAll(".class", "");
		}
		
		Arrays.sort(allClasses);
		
		return allClasses;
	}
}
