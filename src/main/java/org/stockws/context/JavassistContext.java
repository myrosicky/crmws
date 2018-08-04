package org.stockws.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.annotation.PostConstruct;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class JavassistContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavassistContext.class);
	
	@PostConstruct
	 public void init() {
		LOGGER.info("begin init");
		 try {
			ClassPool pool = ClassPool.getDefault();
			CtClass restController = pool.makeClass("RController");
			File rootDir = new File("./target/classes/service/iface");
			File[] serviceClz = rootDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});
			ClassLoader classLoader = this.getClass().getClassLoader();
			for (File serviceClsFile : serviceClz) {
				Class serviceCls = classLoader.loadClass(serviceClsFile.getName().replace(".class", ""));
				String objectName = serviceCls.getSimpleName().substring(0,1).toLowerCase()+serviceCls.getSimpleName().substring(1);
				CtClass evalClass = pool.makeClass(serviceCls.getSimpleName()+"Controller",restController);
				evalClass.addField(CtField.make("private @Autowired "+serviceCls.getSimpleName()+" "+objectName+";", evalClass));
				 for (java.lang.reflect.Method method : serviceCls.getDeclaredMethods()) {
					 StringBuffer sb = new StringBuffer();
					 StringBuffer sb2 = new StringBuffer();
					 for (int i = 0; i < method.getParameterTypes().length; i++) {
						 sb.append(",")
						 .append(method.getParameterTypes()[i].getSimpleName())
						 .append(" arg").append(i)
						 ;
						 sb2.append(",").append("arg").append(i);
					}
					 sb.delete(0, 1);
					 sb2.delete(0, 1);
					 evalClass.addMethod(
					         CtNewMethod.make(
					             "@RequestMapping(\""+method.getName()+".do\") public "+method.getReturnType()+" "+method.getName()
					             +" ("+sb.toString() +") {  return "+objectName+"."+method.getName()+"("+sb2.toString()+"); }",evalClass));
				} 
			 }
		} catch ( RuntimeException
				| CannotCompileException | ClassNotFoundException  e) {
			e.printStackTrace();
		}
		 
		 LOGGER.info("end init");
	}
	
}
