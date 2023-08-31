package com.bkumpar.orange.extractor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bkumpar.orange.entities.Entity;
import com.bkumpar.orange.entities.LinkBetweenSubjects;
import com.bkumpar.orange.entities.Metadata;
import com.bkumpar.orange.entities.Note;
import com.bkumpar.orange.entities.XbrlNodeLeaf;
import com.bkumpar.orange.formatters.IFormatter;

/**
 * Extracts given entity as XML
 * @author bkumpar
 *
 */
public class XbrlDataExtractor {

	/**
	 * return class name of rntity
	 * 
	 * @param entity
	 * @return
	 */
	private static String getEntityName(Entity entity) {
		return entity.getClass().getSimpleName();
	}

	/**
	 * return formatter class name based on variable format
	 * 
	 * @param formatName
	 * @return
	 */
	public static String formatterName(String formatName) {

		String className;
		switch (formatName.length()) {
		case 0:
			className = "";
			break;
		case 1:
			className = formatName.substring(0, 1).toUpperCase().toLowerCase();
			break;
		default:
			className = formatName.substring(0, 1).toUpperCase() + formatName.substring(1).toLowerCase();

		}
		String fullClassName = IFormatter.class.getPackage().getName() + "." + className + "Formatter";
		return fullClassName;
	}

	/**
	 * create array of format parameters (type, size, unit, precision, format...)
	 * some items can be null / empty but every format know positions of values of
	 * interest
	 * 
	 * @param matcher
	 * @return
	 */
	public static String[] formatParameters(Matcher matcher) {

		String[] formatParams = new String[matcher.groupCount()];
		for (int i = 1; i <= matcher.groupCount(); i++) {
			formatParams[i - 1] = matcher.group(i);
		}
		return formatParams;
	}

	/**
	 * Create instance of appropriate formatter
	 * 
	 * @param formatName
	 * @return formatter
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
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

	/**
	 * Display node in form of xml tag
	 * 
	 * @param nodeLeaf
	 * @return
	 * @throws Exception
	 */
	private static String getXmlTag(XbrlNodeLeaf nodeLeaf) throws Exception {

		Object value = nodeLeaf.getValue();
		Metadata matadata = nodeLeaf.getMetadata();
		boolean isPrimaryKey = matadata.isPrimaryKey();
		String fieldName = matadata.getName();
		String fieldFormat = matadata.getFormat();

		/*
		 * this regex match pattern: TYPE [(SIZE UNIT) | (SIZE, PRECISION) | (SIZE) ]
		 * [-FORMAT]
		 */
		final String regex = "(.*)\\(([0-9]*)(\\s*([A-Z]*))(,?([0-9]*))\\)(-?([a-zA-Z]*))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fieldFormat);

		if (matcher.find()) {
			String formatName = matcher.group(1);
			IFormatter formatter = getInstance(formatName);
			String[] formatParams = formatParameters(matcher);
			return formatter.format(value, formatParams, fieldName, isPrimaryKey);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Extract entity as xml
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static String extract(Entity entity) throws Exception {
		
		if (!entity.getProcessed()) { // prevents extraction more than once
			String result = "<xbrld>\n";
			result += String.format("<entity name=\"%s\">\n", getEntityName(entity));

			Method[] methods = entity.getClass().getMethods();
			for (Method m : methods) {
				String methodname = m.getName();
				if (methodname.startsWith("get") && (m.getReturnType() == XbrlNodeLeaf.class)) {
					XbrlNodeLeaf nodeLeaf = (XbrlNodeLeaf) m.invoke(entity);
					result += getXmlTag(nodeLeaf);
				}
			}
			result += "</entity>\n";
			result += "</xbrld>\n";

			entity.setProcessed(true);
			return result;
		} else {
			return "";
		}
	}

	/**
	 * Just for quick tests...
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			// test 1
			Note note = new Note(new XbrlNodeLeaf(42, new Metadata("PROG_NOTE", "NUMBER(5,0)", true)),
					new XbrlNodeLeaf("X1", new Metadata("TYPE_NOTE", "(2 CHARS)", false)),
					new XbrlNodeLeaf("ooooooooo", new Metadata("TEXT_NOTE", "VARCHAR2(64000 BYTES)", false)));
			System.out.println(extract(note));

			// test 2
			note = new Note(new XbrlNodeLeaf(27.d, new Metadata("PROG_NOTE", "NUMBER(5,0)", true)),
					new XbrlNodeLeaf("20230831", new Metadata("TYPE_NOTE", "VARCHAR2(8 CHARS)-YYYYMMDD", false)),
					new XbrlNodeLeaf("Note about this entity",
							new Metadata("TEXT_NOTE", "VARCHAR2(64000 BYTES)", false)));
			System.out.println(extract(note));

			// test 3
			LinkBetweenSubjects link = new LinkBetweenSubjects(
					new XbrlNodeLeaf(53, new Metadata("PROGG_SOGG_PRIM", "NUMBER(5,0)", true)),
					new XbrlNodeLeaf(125, new Metadata("PROGG_SOGG_SEC", "NUMBER(5,0)", true)),
					new XbrlNodeLeaf("PPL23", new Metadata("TYPE_LINK_PEOPLE_PEOPLE", "VARCHAR2(2 CHAR)", false)),
					new XbrlNodeLeaf("Description", new Metadata("DESCRIPTION", "VARCHAR2(50 CHAR)", false)));
			System.out.println(extract(link));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
