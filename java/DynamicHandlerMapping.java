/**
 * Requires Tomcat\lib\
 *               asm-*.jar
 *               antlr-*.jar
 *               groovy-*.jar
 */
public class DynamicHandlerMapping extends DefaultAnnotationHandlerMapping
{
	@Autowired
	ApplicationContext applicationContext;
	
	@Override
	protected Object getHandlerInternal(HttpServletRequest request) throws Exception
	{
		Object handler = super.getHandlerInternal(request);
		if (handler == null && debugEnabled) {
			TilesConfigurer tilesConfigurer = (TilesConfigurer) applicationContext.getBean("TilesConfigurer");
			tilesConfigurer.setCheckRefresh(true);
			handler = overrideDynamicHotCoding(handler, request.getSession().getServletContext());
		}
		return handler;
	}

	private Object overrideDynamicHotCoding(Object handler, ServletContext servletContext) throws Exception {
		if (hasGroovy()) {
			GroovyScriptFactory gsf = new GroovyScriptFactory(".");
			gsf.setBeanClassLoader(applicationContext.getClassLoader());
			ServletContextResource resource = new ServletContextResource(servletContext, "/WEB-INF/dynamicControllers/DynamicController.java");
			ResourceScriptSource rss = new ResourceScriptSource(resource);
			handler = gsf.getScriptedObject(rss, null);
			handler.getClass().getDeclaredMethod("setApplicationContext", ApplicationContext.class).invoke(handler, applicationContext);
			String[] urls = determineUrlsForHandlerMethods(handler.getClass(), true);
			System.out.println(urls);
		}
		return handler;
	}

	private boolean hasGroovy() {
		boolean hasGroovy = false;
		try {
			hasGroovy = Class.forName("groovy.lang.GroovyObjectSupport") != null;
		}
		catch (ClassNotFoundException e) {
			hasGroovy = false;
		}
		return hasGroovy;
	}
}