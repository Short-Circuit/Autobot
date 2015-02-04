package orf.frc4450.robot.control;

import edu.wpi.first.wpilibj.SolenoidBase;

/**
 * Wrapper for classes implementing SolenoidBase
 *
 * @author ShortCircuit908
 */
public class ORFSolenoid<T extends SolenoidBase>{
	private final T solenoid;
	private final String name;

	/**
	 * @param solenoid The SolenoidBase object
	 * @param name     The name of this object
	 */
	public ORFSolenoid(T solenoid, String name){
		this.solenoid = solenoid;
		this.name = name;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The SolenoidBase associated with this object
	 */
	public T getSolenoid(){
		return solenoid;
	}
}
