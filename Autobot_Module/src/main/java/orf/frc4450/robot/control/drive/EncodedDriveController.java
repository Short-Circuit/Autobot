package orf.frc4450.robot.control.drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * Class to handle main robot drive control
 * Makes use of encoders to support driving with distances
 *
 * @author ShortCircuit908
 */
public abstract class EncodedDriveController extends DriveController implements Runnable{
	private final ORFLogger logger = new ORFLogger(this);
	private boolean enabled = false;
	private Encoder front_left;
	private Encoder front_right;
	private Encoder rear_left;
	private Encoder rear_right;


	public EncodedDriveController(int left_motor, int right_motor){
		super(left_motor, right_motor);
	}

	public EncodedDriveController(int front_left_motor, int rear_left_motor, int front_right_motor, int rear_right_motor){
		super(front_left_motor, rear_left_motor, front_right_motor, rear_right_motor);
	}

	/**
	 * When using two motors, only the front left and front right encoders are used
	 * The rear encoders are left null, and should not be used(
	 */
	public void initEncoders(Encoder left, Encoder right){
		front_left = left;
		front_right = right;
	}

	public void initEncoders(Encoder front_left, Encoder front_right, Encoder rear_left, Encoder rear_right){
		this.front_left = front_left;
		this.front_right = front_right;
		this.rear_left = rear_left;
		this.rear_right = rear_right;
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

	/**
	 * Gets an Encoder associated with a given motor
	 *
	 * @param designation The location of the encoder
	 * @return The Encoder associated with the motor
	 */
	public Encoder getEncoder(ORFMotorType designation){
		switch(designation){
			case FRONT_LEFT:
				return front_left;
			case FRONT_RIGHT:
				return front_right;
			case REAR_LEFT:
				return rear_left;
			case REAR_RIGHT:
				return rear_right;
			default:
				return null;
		}
	}

	/**
	 * Drive a motor with a given speed and distance
	 *
	 * @param designation The location of the motor
	 * @param speed       The speed at which to drive the motor
	 * @param distance    The target distance to drive the motor
	 */
	public void driveMotor(ORFMotorType designation, double speed, double distance){
		SpeedController motor = null;
		Encoder encoder = null;
		switch(designation){
			case FRONT_LEFT:
				motor = m_frontLeftMotor;
				encoder = front_left;
			case FRONT_RIGHT:
				motor = m_frontRightMotor;
				encoder = front_right;
			case REAR_LEFT:
				motor = m_rearLeftMotor;
				encoder = rear_left;
			case REAR_RIGHT:
				motor = m_rearRightMotor;
				encoder = rear_right;
		}
		if(motor != null && encoder != null){
			MotorTask<Encoder> motor_task = new MotorTask(motor, encoder, speed, distance);
			Thread execution = new Thread(motor_task);
			execution.start();
		}
	}

	/**
	 * Method containing main drive code
	 */
	public abstract void movement();
}
