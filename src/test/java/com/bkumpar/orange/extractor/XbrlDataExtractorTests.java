package com.bkumpar.orange.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bkumpar.orange.entities.*;
import com.bkumpar.orange.formatters.*;

@SpringBootTest
public class XbrlDataExtractorTests {

	private static String getEntityName(Entity entity) {
		return entity.getClass().getSimpleName();
	}

	public static String formatterName(String formatName) {
		String className = formatName.substring(0, 1).toUpperCase() + formatName.substring(1).toLowerCase();
		String fullClassName = IFormatter.class.getPackage().getName() + "." + className + "Formatter";
		return fullClassName;
	}

	public static IFormatter getInstance(String formatName)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String className = formatterName(formatName);
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			Constructor<?> ctor;
			ctor = clazz.getConstructor();
			IFormatter formatter = (IFormatter) ctor.newInstance(new Object[] {});
			return formatter;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Formater Not found");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String[] formatParameters(Matcher matcher) {

		String[] formatParams = new String[matcher.groupCount()];
		for (int i = 1; i <= matcher.groupCount(); i++) {
			formatParams[i - 1] = matcher.group(i);
		}
		return formatParams;
	}

	private String[] getParameters(String format) throws Exception {
		/*
		 * this regex match pattern: TYPE [(SIZE UNIT) | (SIZE, PRECISION) | (SIZE) ]
		 * [-FORMAT]
		 */
		final String regex = "(.*)\\(([0-9]*)(\\s*([A-Z]*))(,?([0-9]*))\\)(-?([a-zA-Z]*))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(format);

		if (matcher.find()) {
			// String formatName = matcher.group(1);
			// IFormatter formatter = getInstance(formatName);
			String[] formatParams = formatParameters(matcher);
			return formatParams;
		} else {
			throw new Exception();
		}
	}

//	@Test
	public void regexTestNumber() throws Exception {
		String fieldFormat = "NUMBER(5,0)";
		String[] formatParams = getParameters(fieldFormat);
		assertEquals("NUMERIC", formatParams[NumberFormatter.TYPE_INDEX]);
		assertEquals("5", formatParams[1]);
		assertEquals("0", formatParams[5]);
	}

//	@Test
	public void regexNumber2() throws Exception {
		String fieldFormat = "NUMBER(5)";
		String[] formatParams = getParameters(fieldFormat);
		assertEquals("NUMERIC", formatParams[NumberFormatter.TYPE_INDEX]);
		assertEquals("5", formatParams[NumberFormatter.LENGTH_INDEX]);
		assertEquals("", formatParams[NumberFormatter.PRECISION_INDEX]);
	}

//	@Test
	public void regexVarchar2() throws Exception {
		String fieldFormat = "VARCHAR2(15 CHAR)";
		String[] formatParams = getParameters(fieldFormat);
		assertEquals("VARCHAR2", formatParams[Varchar2Formatter.TYPE_INDEX]);
		assertEquals("15", formatParams[Varchar2Formatter.LENGTH_INDEX]);
		assertEquals("CHAR", formatParams[Varchar2Formatter.UNIT_INDEX]);
		assertEquals("", formatParams[Varchar2Formatter.FORMAT_INDEX]);
	}

//	@Test
	public void formatterTest1() throws Exception {
		assertEquals("Note", getEntityName(new Note(null, null, null)));
	}

//	@Test
	public void formatterTest2() throws Exception {
		assertEquals("LinkBetweenSubjects", getEntityName(new LinkBetweenSubjects(null, null, null, null)));
	}

}
