package demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jndi.ldap.sasl.SaslInputStream;

/*
 * ÄãºÃÊÀ½ç
 */
public class HelloWorld {
	public static void main(String[] args) {
		String str = "  000019800  ";
		System.out.println(str.replaceAll("^(\\s*)(0*)","").trim());
		 

		
	}
}
