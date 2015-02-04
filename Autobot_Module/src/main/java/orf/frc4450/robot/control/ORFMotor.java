package orf.frc4450.robot.control;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Wrapper for classes implementing SpeedController
 * Can be paired with an Encoder
 *
 * @author ShortCircuit908
 */
public class ORFMotor<S extends SpeedController, C extends Encoder>{
	private final S motor;
	private final C encoder;
	private final String name;

	/**
	 * @param motor   The SpeedController object
	 * @param encoder The encoder to pair with the motor (may be null)
	 * @param name    The name of this object
	 */
	public ORFMotor(S motor, C encoder, String name){
		this.motor = motor;
		this.encoder = encoder;
		this.name = name;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The SpeedController object associated with the object
	 */
	public S getMotor(){
		return motor;
	}

	/**
	 * @return The Encoder object associated with the object
	 */
	public C getEncoder(){
		return encoder;
	}

	/**
	 * @return Whether or not an encoder is paired with the SpeedController
	 */
	public boolean usingEncoder(){
		return encoder != null;
	}
}
