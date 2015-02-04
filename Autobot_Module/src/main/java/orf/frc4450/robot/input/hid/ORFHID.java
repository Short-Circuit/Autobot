package orf.frc4450.robot.input.hid;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Wrapper for GenericHID instances
 *
 * @author ShortCircuit908
 */
public class ORFHID<T extends GenericHID>{
	private final T hid;
	private final String name;

	/**
	 * @param hid  The GenericHID object
	 * @param name The name of this object
	 */
	public ORFHID(T hid, String name){
		this.hid = hid;
		this.name = name;
	}

	/**
	 * @return The GenericHID associated with this object
	 */
	public T getDevice(){
		return hid;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}
}
