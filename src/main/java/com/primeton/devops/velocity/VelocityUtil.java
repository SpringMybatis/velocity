package com.primeton.devops.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityUtil {
	
	public static String parse(InputStream template, Map<String, Object> context, String logTag) {
		if (null == template) {
			return null;
		}
		logTag = null == logTag ? "" : logTag;
		Velocity.init();
		VelocityContext vcontext = new VelocityContext();
		if (null != context && !context.isEmpty()) {
			for (String key : context.keySet()) {
				vcontext.put(key, context.get(key));
			}
		}
		StringWriter writer = null;
		InputStreamReader reader = null;
		try {
			writer = new StringWriter();
			reader = new InputStreamReader(template);
			Velocity.evaluate(vcontext, writer, logTag, reader);
			writer.flush();
			String content = writer.toString();
			return content;
		} catch (Throwable t) {
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	/**
	 * if parse error return template(source). <br>
	 * 
	 * @param template
	 * @param context
	 * @param logTag
	 * @return
	 */
	public static String parse(String template, Map<String, Object> context, String logTag) {
		if (StringUtils.isBlank(template)) {
			return template;
		}
		logTag = null == logTag ? "" : logTag;
		Velocity.init();
		VelocityContext vcontext = new VelocityContext();
		if (null != context && !context.isEmpty()) {
			for (String key : context.keySet()) {
				vcontext.put(key, context.get(key));
			}
		}
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			Velocity.evaluate(vcontext, writer, logTag, template);
			writer.flush();
			String content = writer.toString();
			return content;
		} catch (Throwable t) {
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
		return template;
	}

}
