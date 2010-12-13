package br.com.seibzhen.specification.mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import br.com.seibzhen.specification.ReadableNamesReporter;
import br.com.seibzhen.specification.TestClassFinder;


/**
 * Readable tests
 * @goal spec
 * @phase test
 */

public class SpecificationMojo extends AbstractMojo {
	
	/** @parameter default-value="${project.build.testOutputDirectory}" */
    private String testDirectory;
    
    /** @parameter default-value="${project.build.outputDirectory}" */
    private String outputDirectory;
    
    /** @parameter default-value="specification.txt" */
    private String reportName;
    
    /** @parameter default-value="${project.runtimeArtifacts}" */
    private List<Artifact> runtimeArtifacts;
    
    /** @parameter default-value="${project.testArtifacts}" */
    private List<Artifact> testArtifacts;
    
    private TestClassFinder testClassFinder = new TestClassFinder();
    
    private ReadableNamesReporter readableNamesReporter;
    
    private ClassLoaderBuilder classLoaderBuilder = new ClassLoaderBuilder(Thread.currentThread().getContextClassLoader());
    
    public void execute() throws MojoExecutionException, MojoFailureException {
    	FileWriter fw = null;
        try {
        	String[] allClasses = testClassFinder.findFrom(new File(testDirectory));
        	
        	classLoaderBuilder.addAll(runtimeArtifacts);
        	classLoaderBuilder.addAll(testArtifacts);
        	classLoaderBuilder.add(new File(outputDirectory));
        	classLoaderBuilder.add(new File(testDirectory));
        	
        	ClassLoader classloader = classLoaderBuilder.get();
        	readableNamesReporter = new ReadableNamesReporter(classloader);
        	
        	String output = outputDirectory + File.separator + reportName;
        	getLog().info("Writing output to:" + output);
        	
        	File fileOutputDirectory = new File(outputDirectory);
        	fileOutputDirectory.mkdirs();
        	
        	
        	File file = new File(output);
        	file.createNewFile();
        	
        	fw = new FileWriter(file);
            for(String clazz:allClasses){
            	String report = readableNamesReporter.report(clazz);
            	getLog().info(report);
            	fw.write(report);
            }
            
        } catch (ClassNotFoundException e) {
			getLog().error(e);
		} catch (MalformedURLException e) {
			getLog().error(e);
		} catch (IOException e) {
			getLog().error(e);
		}finally{
			if(fw != null){
				try {
					fw.flush();
				} catch (IOException e) {
					getLog().error(e);
				}finally{
					try {
						fw.close();
					} catch (IOException e) {
						getLog().error(e);
					}
				}
			}
		}
    }
    
    public void setTestDirectory(String testDirectory) {
		this.testDirectory = testDirectory;
	}
}