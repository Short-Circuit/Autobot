package orf.frc4450.robot.input.sensor;

import edu.wpi.first.wpilibj.SensorBase;

/**
 * Wrapper for classes implementing SensorBase
 *
 * @author ShortCircuit908
 */
public class ORFSensor<T extends SensorBase>{
	private final T sensor;
	private final String name;
	private SensorResponse action;
	private long last_update;
	private long update_frequency;

	/**
	 * @param sensor           The SensorBase object
	 * @param name             The name of this object
	 * @param update_frequency The delay, in milliseconds, between sensor refreshes
	 */
	public ORFSensor(T sensor, String name, int update_frequency){
		this.sensor = sensor;
		this.name = name;
		this.update_frequency = update_frequency;
		last_update = System.currentTimeMillis();
	}

	/**
	 * @param sensor The SensorBase object
	 * @param name   The name of this object
	 */
	public ORFSensor(T sensor, String name){
		this.sensor = sensor;
		this.name = name;
		update_frequency = 0;
		last_update = System.currentTimeMillis();
	}

	/**
	 * @param action The class containing methods to respond to the sensor input
	 * @return The ORFSensor object
	 */
	public ORFSensor<T> setAction(SensorResponse<T> action){
		this.action = action;
		return this;
	}

	/**
	 * @return The SensorBase object associated with the object
	 */
	public T getSensor(){
		return sensor;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The SensorResponse associated with this object
	 */
	public SensorResponse getAction(){
		return action;
	}

	/**
	 * @return The time, in milliseconds, between sensor refreshes
	 */
	public long getUpdateFrequency(){
		return update_frequency;
	}

	/**
	 * @return The time, in milliseconds, of the last sensor refresh
	 */
	public long getLastUpdate(){
		return last_update;
	}

	/**
	 * @param last_update Internal method to keep track of refresh delays
	 */
	protected void setLastUpdate(long last_update){
		this.last_update = last_update;
	}

	/**
	 * @param update_frequency The time, in milliseconds, between sensor refreshes
	 */
	public void setUpdateFrequency(long update_frequency){
		this.update_frequency = update_frequency;
	}
}
