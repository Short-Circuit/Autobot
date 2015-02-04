package orf.frc4450.robot.control;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.Encoder;
import orf.frc4450.robot.control.drive.MotorTask;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * Handler class to manage motors
 *
 * @author ShortCircuit908
 */
public class MotorController<T extends ORFMotor>{
	private final ORFLogger logger = new ORFLogger(this);
	private Set<T> motors = new HashSet<>();

	/**
	 * Register an ORFMotor
	 * <p>
	 * If the ORFMotor conflicts with an existing ORFMotor with the same name, the ORFMotor
	 * will not be added
	 *
	 * @param motor The ORFMotor to register
	 */
	public void registerMotor(T motor){
		T check_exists = getMotor(motor.getName());
		if(check_exists != null){
			logger.warning("A motor with the name \"" + motor.getName() + "\" is already registered");
			return;
		}
		motors.add(motor);
		logger.info("Registered motor " + motor.getName() + " of type "
				+ motor.getMotor().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFMotors
	 * <p>
	 * If any of the ORFMotors conflicts with an existing ORFMotor with the same name, the ORFMotor
	 * will not be added
	 *
	 * @param motors The ORFMotors to register
	 */
	public void registerMotors(T... motors){
		for(T motor : motors){
			registerMotor(motor);
		}
	}

	/**
	 * Unregister an ORFMotor
	 *
	 * @param name The name of the ORFMotor to unregister
	 */
	public void unregisterMotor(String name){
		T to_remove = getMotor(name);
		if(to_remove != null){
			motors.remove(to_remove);
			logger.info("Unregistered motor " + name);
		}
		else{
			logger.warning("Could not unregister motor " + name);
		}
	}

	/**
	 * Get a registered ORFMotor
	 *
	 * @param name The name of the ORFMotor
	 * @return The ORFMotor if it exists, null if otherwise
	 */
	public T getMotor(String name){
		for(T motor : motors){
			if(motor.getName().equals(name)){
				return motor;
			}
		}
		return null;
	}

	/**
	 * Drive a motor with the given name and speed
	 * @param name The name of the motor to drive
	 * @param speed The speed at which to drive the motor
	 */
	public void drive(String name, double speed){
		T motor = getMotor(name);
		if(motor != null){
			motor.getMotor().set(speed);
		}
		else{
			logger.warning("Cannot drive non-existent motor " + name);
		}
	}

	/**
	 * Drive a motor with the given name and speed, for a given distance
	 * <p>
	 * Using this method requires the motor being driven to be matched with an Encoder
	 * The motor will drive asynchronously until it has reached the given distance
	 * Trying to drive the motor while it is already being driven may cause undesirable results
	 * @param name The name of the motor to drive
	 * @param speed The speed at which to drive the motor
	 * @param distance The target distance for the motor
	 */
	public void drive(String name, double speed, double distance){
		T motor = getMotor(name);
		if(motor != null){
			if(motor.usingEncoder()){
				MotorTask<Encoder> motor_task = new MotorTask(motor.getMotor(), motor.getEncoder(), speed, distance);
				Thread execute = new Thread(motor_task);
				execute.start();
			}
			else{
				logger.warning("Cannot drive motor " + name + " using distance");
			}
		}
		else{
			logger.warning("Cannot drive non-existent motor " + name);
		}
	}


}
