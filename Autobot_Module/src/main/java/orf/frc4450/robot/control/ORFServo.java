package orf.frc4450.robot.control;

import edu.wpi.first.wpilibj.Servo;

/**
 * Wrapper for classes implementing Servo
 *
 * @author ShortCircuit908
 */
public class ORFServo<T extends Servo>{
	private final T servo;
	private final String name;

	/**
	 * @param servo The Servo object
	 * @param name  The name of this object
	 */
	public ORFServo(T servo, String name){
		this.servo = servo;
		this.name = name;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The Servo associated with this object
	 */
	public T getServo(){
		return servo;
	}
}
