
package com.eyougo.common.web.resolver;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author mei
 *
 */
public class OutputStackSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	/** The default name of the exception stack trace string attribute: "exceptionStack". */
	public static final String DEFAULT_EXCEPTION_STACK_ATTRIBUTE = "exceptionStack";

	private String exceptionStackAttribute = DEFAULT_EXCEPTION_STACK_ATTRIBUTE;

	public void setExceptionStackAttribute(String exceptionStackAttribute) {
		this.exceptionStackAttribute = exceptionStackAttribute;
	}

	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex) {
		ModelAndView mv = super.getModelAndView(viewName, ex);
		StringBuilder sb = new StringBuilder();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		sb.append(stringWriter.getBuffer());
		printWriter.close();
		mv.addObject(this.exceptionStackAttribute, sb.toString());
		return mv;
	}
}
