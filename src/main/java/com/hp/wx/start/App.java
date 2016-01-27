package com.hp.wx.start;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 启动类
 *
 */
public class App 
{
	static final Logger logger = LoggerFactory.getLogger(App.class);

	  public static void main(String[] args) throws Exception {
	    int port = Integer.getInteger("port", 8089);
	    logger.info("Starting server at port {}", port);
	    Server server = new Server(port);
	    WebAppContext handler = new WebAppContext();
	    
	    handler.setContextPath("/");
	    handler.setBaseResource(Resource.newClassPathResource("/webapp"));
	    handler.setMaxFormContentSize(Integer.MAX_VALUE);
	    handler.setDefaultsDescriptor("/webdefault.xml");

	    server.setHandler(handler);
	    server.start();
	    logger.info("Server started at port {}", port);

	    server.join();
	  }
}
