package orf.frc4450.robot.control.drive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * Class to handle main robot drive control
 *
 * @author ShortCircuit908
 */
public abstract class DriveController extends RobotDrive implements Runnable{
	private final ORFLogger logger = new ORFLogger(this);
	private boolean enabled = false;

	public DriveController(int left_motor, int right_motor){
		super(left_motor, right_motor);
	}

	public DriveController(int front_left_motor, int rear_left_motor, int front_right_motor, int rear_right_motor){
		super(front_left_motor, rear_left_motor, front_right_motor, rear_right_motor);
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		setSafetyEnabled(enabled);
		logger.info("Motor drive " + (enabled ? "enabled" : "disabled"));
	}

	public boolean getEnabled(){
		return enabled;
	}

	public void run(){
		while(true){
			if(enabled){
				movement();
			}
		}
	}

	public boolean isCANInitialized(){
		return m_isCANInitialized;
	}

	public boolean speedControllersAllocated(){
		return m_allocatedSpeedControllers;
	}

	public SpeedController getMotor(ORFMotorType type){
		switch(type){
			case FRONT_LEFT:
				return m_frontLeftMotor;
			case FRONT_RIGHT:
				return m_frontRightMotor;
			case REAR_LEFT:
				return m_rearLeftMotor;
			case REAR_RIGHT:
				return m_rearRightMotor;
			default:
				return null;
		}
	}

	/**
	 * Method containing main drive code
	 */
	public abstract void movement();
}
