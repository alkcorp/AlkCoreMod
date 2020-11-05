package alkcoremod.objects.log;

import org.apache.logging.log4j.LogManager;

public class Logger {

	public Logger(String string) {

	}

	// Logging Functions
	public static final org.apache.logging.log4j.Logger modLogger = Logger.makeLogger();

	// Generate Logger
	public static org.apache.logging.log4j.Logger makeLogger() {
		final org.apache.logging.log4j.Logger logger = LogManager.getLogger("AlkCoreMod");
		return logger;
	}


	public static final org.apache.logging.log4j.Logger getLogger(){
		return modLogger;
	}

	// Non-Dev Comments
	public static void INFO(final String s) {
		modLogger.info(s);
	}

	// Developer Comments
	public static void WARNING(final String s) {
		modLogger.warn(s);
	}

	// Errors
	public static void ERROR(final String s) {
		modLogger.fatal(s);
	}

	// Reflection
	public static void REFLECTION(String s) {
		modLogger.info("[Reflection] "+s);		
	}

}
