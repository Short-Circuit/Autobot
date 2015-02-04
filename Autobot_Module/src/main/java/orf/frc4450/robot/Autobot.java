package orf.frc4450.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * @author ShortCircuit908
 *
 * Main robot class
 */
public abstract class Autobot extends RobotBase{
	private final ORFLogger logger = new ORFLogger(this);
	private DriverStation driver = DriverStation.getInstance();
	private Alliance alliance;
	private boolean autonomous;

	/**
	 * Default constructor
	 */
	public Autobot(){
		SmartDashboard.putString("Program", getVersion());
		logger.info("Initializing " + getClass().getSimpleName() + " version " + getVersion());
		init();
	}

	/**
	 * Called when the competition begins
	 */
	@Override
	public final void startCompetition(){
		logger.info("Starting competition");
		logger.info("Calling main method");
		if(autonomous){
			autonomous();
		}
		else{
			manual();
		}
	}

	/**
	 * Initialize resources and perform system checks
	 */
	private final void init(){
		logger.info("Initializing resources");
		assemble();
		logger.info("Setting alliance");
		alliance = driver.getAlliance();
		logger.info("Allied with " + alliance.name() + " team");
		autonomous = driver.isAutonomous();
		logger.info("Operating in " + (autonomous ? "autonomous" : "manual") + " mode");
		logger.info("Running system checks");
		boolean checks_passed = false;
		try{
			checks_passed = rollOut();
		}
		catch(Exception e){
			logger.exception(e);
			checks_passed = false;
		}
		logger.info("System checks " + (checks_passed ? "passed" : "failed"));
		logger.info("Initialization complete");
	}

	/**
	 * Initialize resources
	 */
	public abstract void assemble();

	/**
	 * Pre-game system checks
	 *
	 * @return Whether all system checks passed
	 */
	public abstract boolean rollOut() throws Exception;

	/**
	 * Autonomous mode
	 */
	public abstract void autonomous();

	/**
	 * Manual drive mode
	 */
	public abstract void manual();

	/**
	 * Gets the robot's main logger
	 * @return Robot's logger
	 */
	public ORFLogger getlogger(){
		return logger;
	}

	/**
	 * Quietly sleep for the provided time
	 * @param millis Number of milliseconds to sleep
	 */
	public static final void delay(long millis){
		try{
			Thread.sleep(millis);
		}
		catch(InterruptedException e){
			ORFLogger.getDefaultLogger().exception(e);
		}
	}

	/**
	 * Get the version of the software
	 * @return Current software version
	 */
	public abstract String getVersion();

	/**
	 * Get the driver station
	 * @return Current DriverStation instance
	 */
	public DriverStation getDriverStation(){
		return driver;
	}

	/**
	 * Get the team number
	 * @return 4-digit team number
	 */
	public abstract int getTeamNumber();
}
