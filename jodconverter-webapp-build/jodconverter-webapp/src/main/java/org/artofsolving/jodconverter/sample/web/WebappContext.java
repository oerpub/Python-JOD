package org.artofsolving.jodconverter.sample.web;

import java.io.File;
import java.util.logging.Logger;
import java.util.Properties;
import javax.servlet.ServletContext;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.artofsolving.jodconverter.process.SigarProcessManager;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import java.lang.reflect.Field;


public class WebappContext {

    public static final String PARAMETER_OFFICE_PORT = "office.port";
	public static final String PARAMETER_OFFICE_HOME = "office.home";
	public static final String PARAMETER_OFFICE_PROFILE = "office.profile";
	public static final String PARAMETER_FILEUPLOAD_FILE_SIZE_MAX = "fileupload.fileSizeMax";

	private final Logger logger = Logger.getLogger(getClass().getName());

	private static final String KEY = WebappContext.class.getName();

	private final ServletFileUpload fileUpload;

	private final OfficeManager officeManager;
	private final OfficeDocumentConverter documentConverter;
    
	public WebappContext(ServletContext servletContext) {
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		String fileSizeMax = servletContext.getInitParameter(PARAMETER_FILEUPLOAD_FILE_SIZE_MAX);
		fileUpload = new ServletFileUpload(fileItemFactory);
		if (fileSizeMax != null) {
			fileUpload.setFileSizeMax(Integer.parseInt(fileSizeMax));
			logger.info("max file upload size set to " + fileSizeMax);
		} else {
			logger.warning("max file upload size not set");
		}

		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		String officePortParam = servletContext.getInitParameter(PARAMETER_OFFICE_PORT);
		/* Read in options from a file */
		String oldPath = System.getProperty("java.library.path");
		String oldClassPath = System.getProperty("java.class.path");
		/*String ureLibPath = "/usr/lib/libreoffice/basis-link/ure-link/lib";*/
		configuration.setConnectionProtocol(OfficeConnectionProtocol.PIPE);
		logger.info("Set the PIPE Protocol successfully");
		logger.info("java.library.path=" + System.getProperty("java.library.path"));
		logger.info("java.class.path=" + System.getProperty("java.class.path"));
		Properties configFile = new Properties();
		String rawNumOpenOffices, rawQueueTime, rawTaskTime, rawMaxTasks;
		int numOpenOffices, queueTime, taskTime, maxTasks;
		
		try{
			/* Gets home path of user */	
			String homePath = System.getProperty("user.dir");
			/* Gets all options from config file */
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
			logger.info("Loaded");
			rawNumOpenOffices = configFile.getProperty("NUM_OPENOFFICE");
			logger.info(rawNumOpenOffices);
			rawQueueTime = configFile.getProperty("QUEUE_TIME");
			logger.info(rawQueueTime);
			rawTaskTime = configFile.getProperty("TASK_TIME");
			logger.info(rawTaskTime);
			rawMaxTasks = configFile.getProperty("MAX_TASKS");
			rawTaskTime = configFile.getProperty("TASK_TIME");
			logger.info(rawMaxTasks);
			numOpenOffices = Integer.parseInt(rawNumOpenOffices);
			queueTime = Integer.parseInt(rawQueueTime);
			taskTime = Integer.parseInt(rawTaskTime);
			maxTasks = Integer.parseInt(rawMaxTasks);
			/* Grabs the number of open office processes to create from config file and then configures JOD */
			String[] openOfficeProcessNames = new String[numOpenOffices];
			for (int i = 0; i < numOpenOffices; i++) {
				openOfficeProcessNames[i] = "office" + (i+1);
			}
			configuration.setPipeNames(openOfficeProcessNames);
			configuration.setMaxTasksPerProcess(maxTasks);
			configuration.setTaskExecutionTimeout(taskTime);
			configuration.setTaskQueueTimeout(queueTime);
			/*configuration.setMaxTasksPerProcess(200);
			configuration.setTaskExecutionTimeout(300000);
			configuration.setTaskQueueTimeout(2000000);*/
	      	configuration.setProcessManager(new SigarProcessManager());
		}
		catch(Exception e) {
			logger.info("Getting configuration failed");
			logger.info(e.toString());
			throw new RuntimeException();
		}

	
      	/* Can be used to set port numbers instead */
		/*if (officePortParam != null) {
		  	configuration.setPortNumber(Integer.parseInt(officePortParam));
      	}*/
		String officeHomeParam = servletContext.getInitParameter(PARAMETER_OFFICE_HOME);
		if (officeHomeParam != null) {
		    configuration.setOfficeHome(new File(officeHomeParam));
		}
		String officeProfileParam = servletContext.getInitParameter(PARAMETER_OFFICE_PROFILE);
		if (officeProfileParam != null) {
		    configuration.setTemplateProfileDir(new File(officeProfileParam));
		}

		officeManager = configuration.buildOfficeManager();
		documentConverter = new OfficeDocumentConverter(officeManager);
	}

	protected static void init(ServletContext servletContext) {
		WebappContext instance = new WebappContext(servletContext);
		servletContext.setAttribute(KEY, instance);
		instance.officeManager.start();
	}

	protected static void destroy(ServletContext servletContext) {
		WebappContext instance = get(servletContext);
		instance.officeManager.stop();
	}

	public static WebappContext get(ServletContext servletContext) {
		return (WebappContext) servletContext.getAttribute(KEY);
	}

	public ServletFileUpload getFileUpload() {
		return fileUpload;
	}

	public OfficeManager getOfficeManager() {
        return officeManager;
    }

	public OfficeDocumentConverter getDocumentConverter() {
        return documentConverter;
    }

}
