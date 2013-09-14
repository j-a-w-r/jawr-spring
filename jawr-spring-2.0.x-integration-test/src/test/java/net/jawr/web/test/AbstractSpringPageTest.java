/**
 * 
 */
package net.jawr.web.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;

import net.jawr.web.resource.bundle.factory.util.PathNormalizer;

import org.apache.commons.io.IOUtils;

/**
 * @author ibrahim Chaehoi
 *
 */
public abstract class AbstractSpringPageTest extends AbstractPageTest {

	/* (non-Javadoc)
	 * @see net.jawr.web.test.AbstractPageTest#initConfigFile()
	 */
	protected void initConfigFile(){
		
		try {
			Enumeration<URL> urls = getClass().getClassLoader().getResources("META-INF/spring.schemas");
			while(urls.hasMoreElements()){
				System.out.println(urls.nextElement());
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JawrSpringTestConfigFiles annotationConfig = (JawrSpringTestConfigFiles) getClass()
				.getAnnotation(JawrSpringTestConfigFiles.class);
		try{
			String currentJawrConfigPath = annotationConfig.jawrConfig();
			String webappRootDir = JawrIntegrationServer.getInstance().getWebAppRootDir();
			
			OutputStream outFile = new FileOutputStream(new File(webappRootDir, "/WEB-INF/classes/jawr.properties"));
			IOUtils.copy(getClass().getClassLoader().getResourceAsStream(
					currentJawrConfigPath), outFile);
			IOUtils.closeQuietly(outFile);
			
			String currentWebXmlPath = annotationConfig.webXml();
			outFile = new FileOutputStream(new File(webappRootDir, "/WEB-INF/web.xml"));
			IOUtils.copy(getClass().getClassLoader().getResourceAsStream(
					currentWebXmlPath), outFile);
			IOUtils.closeQuietly(outFile);	
			
			String currentDispatcherXmlPath = annotationConfig.dispatcherServletConfig();
			String name = PathNormalizer.getPathName(currentDispatcherXmlPath);
			outFile = new FileOutputStream(new File(webappRootDir, "/WEB-INF/"+name));
			IOUtils.copy(getClass().getClassLoader().getResourceAsStream(
					currentDispatcherXmlPath), outFile);
			IOUtils.closeQuietly(outFile);	
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

}
