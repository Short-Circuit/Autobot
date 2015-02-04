package orf.frc4450.robot.logging;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author ShortCircuit908
 */
public class ORFLogger extends Logger{
	private final String name;
	private static final ORFLogger global_logger = new ORFLogger();
	private static final LCD lcd = new LCD();

	/**
	 * Create a logger with the provided name
	 *
	 * @param context The object that created the logger
	 * @param name    The prefix to use when logging
	 */
	public ORFLogger(Object context, String name){
		super(context.getClass().getCanonicalName(), (String)null);
		this.name = name != null ? "[" + name + "] " : "[" + context.getClass().getSimpleName() + "] ";
		this.setParent(global_logger);
		this.setLevel(Level.ALL);
	}

	/**
	 * Create a logger
	 * The logger's prefix will be set to the name of the object that created it
	 *
	 * @param context The object that created the logger
	 */
	public ORFLogger(Object context){
		super(context.getClass().getCanonicalName(), (String)null);
		this.name = "[" + context.getClass().getSimpleName() + "] ";
		setParent(global_logger);
		setLevel(Level.ALL);
	}

	/**
	 * Private constructor to create the default logger
	 */
	private ORFLogger(){
		super(ORFLogger.class.getCanonicalName(), (String)null);
		this.name = "[ORFLogger] ";
		setParent(Logger.getGlobal());
		setLevel(Level.ALL);
	}

	/**
	 * Override the default log method to add the prefix
	 *
	 * @param log_record
	 */
	public void log(LogRecord log_record){
		SmartDashboard.putString(name, log_record.getMessage());
		log_record.setMessage(name + log_record.getMessage());
		super.log(log_record);
	}

	/**
	 * Log an exception to the console
	 *
	 * @param e
	 */
	public void exception(Exception e){
		for(StackTraceElement element : e.getStackTrace()){
			severe(element.toString());
		}
	}

	/**
	 * Log an error to the console
	 *
	 * @param e
	 */
	public void error(Error e){
		for(StackTraceElement element : e.getStackTrace()){
			warning(element.toString());
		}
	}

	/**
	 * Get the default logger
	 *
	 * @return default ORFLogger instance
	 */
	public static ORFLogger getDefaultLogger(){
		return global_logger;
	}

	/**
	 * Just some SmartDashboard wrapping
	 */
	public void smartDash(String key, Object value){
		if(value instanceof String){
			SmartDashboard.putString(key, (String)value);
		}
		else if(value instanceof Number){
			SmartDashboard.putNumber(key, (Double)value);
		}
		else if(value instanceof Boolean){
			SmartDashboard.putBoolean(key, (Boolean)value);
		}
		else if(value instanceof NamedSendable){
			SmartDashboard.putData((NamedSendable)value);
		}
		else if(value instanceof Sendable){
			SmartDashboard.putData(key, (Sendable)value);
		}
		else{
			SmartDashboard.putString(key, value.toString());
		}
	}

	public static LCD getLCD(){
		return lcd;
	}

	public static final class LCD{
		private final int line_width = 42;
		private final String empty_line = StringUtils.repeat(' ', line_width);

		public LCD(){
			clearAll();
		}

		public void print(int line, int column, Object message){
			String line_name = "LCD_Line_" + line;
			String text = StringUtils.rightPad(StringUtils.repeat(' ', column - 1) + message, line_width, " ");
			text = StringUtils.abbreviate(text, line_width);
			SmartDashboard.putString(line_name, text);
		}

		public void printLine(int line, Object message){
			print(line, 1, message);
		}

		public void clearLine(int line){
			print(line, 1, empty_line);
		}

		public void clearAll(){
			for(int i = 1; i < 11; i++){
				print(i, 1, empty_line);
			}
		}
	}
}
