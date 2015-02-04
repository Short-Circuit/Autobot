package orf.frc4450.robot.input.sensor;


import java.util.HashSet;
import java.util.Set;

import orf.frc4450.robot.logging.ORFLogger;

/**
 * Handler class to manage SensorBase instances
 *
 * @author ShortCircuit908
 */
public class SensorHandler<T extends ORFSensor> implements Runnable{
	private final Set<T> sensors = new HashSet<>();
	private final ORFLogger logger = new ORFLogger(this);

	/**
	 * Register an ORFMotor
	 * <p>
	 * If the ORFMotor conflicts with an existing ORFMotor with the same name, the ORFMotor
	 * will not be added
	 *
	 * @param sensor The ORFMotor to register
	 */
	public void registerSensor(T sensor){
		T check_exists = getSensor(sensor.getName());
		if(check_exists != null){
			logger.warning("A sensor with the name \"" + sensor.getName() + "\" is already registered");
			return;
		}
		sensors.add(sensor);
		logger.info("Registered sensor " + sensor.getName() + " of type "
				+ sensor.getSensor().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFMotors
	 * <p>
	 * If any of the ORFMotors conflicts with an existing ORFMotor with the same name, the ORFMotor
	 * will not be added
	 *
	 * @param sensors The ORFMotors to register
	 */
	public void registerSensors(T... sensors){
		for(T sensor : sensors){
			registerSensor(sensor);
		}
	}

	/**
	 * Unregister an ORFMotor
	 *
	 * @param name The name of the ORFMotor to unregister
	 */
	public void unregisterSensor(String name){
		T to_remove = getSensor(name);
		if(to_remove != null){
			to_remove.getSensor().free();
			sensors.remove(to_remove);
			logger.info("Unregistered sensor " + name);
		}
		else{
			logger.warning("Could not unregister sensor " + name);
		}
	}

	/**
	 * Get a registered ORFMotor
	 *
	 * @param name The name of the ORFMotor
	 * @return The ORFMotor if it exists, null if otherwise
	 */
	public T getSensor(String name){
		for(T sensor : sensors){
			if(sensor.getName().equals(name)){
				return sensor;
			}
		}
		return null;
	}

	public void run(){
		while(true){
			long current_time = System.currentTimeMillis();
			for(T sensor : sensors){
				if(current_time - sensor.getLastUpdate() >= sensor.getUpdateFrequency()){
					SensorResponse response = sensor.getAction();
					if(response != null){
						response.onSensorRefresh(sensor.getSensor());
						sensor.setLastUpdate(System.currentTimeMillis());
					}
					else{
						logger.warning("Sensor " + sensor.getName() + " does not have a valid SensorResponse");
						logger.warning("Skipping method invocation");
					}
				}
			}
		}
	}
}
