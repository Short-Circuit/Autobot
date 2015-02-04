package orf.frc4450.robot.control;

import java.util.HashSet;
import java.util.Set;

import orf.frc4450.robot.logging.ORFLogger;


/**
 * Handler class to manage Servo instances
 *
 * @author ShortCircuit908
 */
public class ServoController<T extends ORFServo>{
	private final ORFLogger logger = new ORFLogger(this);
	private Set<T> servos = new HashSet<>();

	/**
	 * Register an ORFServo
	 * <p>
	 * If the ORFServo conflicts with an existing ORFServo with the same name, the ORFServo
	 * will not be added
	 *
	 * @param servo The ORFServo to register
	 */
	public void registerServo(T servo){
		T check_exists = getServo(servo.getName());
		if(check_exists != null){
			logger.warning("A servo with the name \"" + servo.getName() + "\" is already registered");
			return;
		}
		servos.add(servo);
		logger.info("Registered servo " + servo.getName() + " of type "
				+ servo.getServo().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFServos
	 * <p>
	 * If any of the ORFServos conflicts with an existing ORFServo with the same name, the ORFServo
	 * will not be added
	 *
	 * @param servos The ORFServos to register
	 */
	public void registerServos(T... servos){
		for(T servo : servos){
			registerServo(servo);
		}
	}

	/**
	 * Unregister an ORFServo
	 *
	 * @param name The name of the ORFServo to unregister
	 */
	public void unregisterServo(String name){
		T to_remove = getServo(name);
		if(to_remove != null){
			servos.remove(to_remove);
			logger.info("Unregistered servo " + name);
		}
		else{
			logger.warning("Could not unregister servo " + name);
		}
	}

	/**
	 * Get a registered ORFServo
	 *
	 * @param name The name of the ORFServo
	 * @return The ORFServo if it exists, null if otherwise
	 */
	public T getServo(String name){
		for(T servo : servos){
			if(servo.getName().equals(name)){
				return servo;
			}
		}
		return null;
	}
}
