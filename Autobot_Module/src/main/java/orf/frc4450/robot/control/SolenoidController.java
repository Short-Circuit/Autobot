package orf.frc4450.robot.control;

import java.util.HashSet;
import java.util.Set;

import orf.frc4450.robot.logging.ORFLogger;


/**
 * Handler class to manage SolenoidBase instances
 *
 * @author ShortCircuit908
 */
public class SolenoidController<T extends ORFSolenoid>{
	private final ORFLogger logger = new ORFLogger(this);
	private Set<T> solenoids = new HashSet<>();

	/**
	 * Register an ORFSolenoid
	 * <p>
	 * If the ORFSolenoid conflicts with an existing ORFSolenoid with the same name, the ORFSolenoid
	 * will not be added
	 *
	 * @param solenoid The ORFSolenoid to register
	 */
	public void registerSolenoid(T solenoid){
		T check_exists = getSolenoid(solenoid.getName());
		if(check_exists != null){
			logger.warning("A solenoid with the name \"" + solenoid.getName() + "\" is already registered");
			return;
		}
		solenoids.add(solenoid);
		logger.info("Registered solenoid " + solenoid.getName() + " of type "
				+ solenoid.getSolenoid().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFSolenoids
	 * <p>
	 * If any of the ORFSolenoids conflicts with an existing ORFSolenoid with the same name,
	 * the ORFSolenoid will not be added
	 *
	 * @param solenoids The ORFSolenoids to register
	 */
	public void registerSolenoids(T... solenoids){
		for(T solenoid : solenoids){
			registerSolenoid(solenoid);
		}
	}

	/**
	 * Unregister an ORFSolenoid
	 *
	 * @param name The name of the ORFSolenoid to unregister
	 */
	public void unregisterSolenoid(String name){
		T to_remove = getSolenoid(name);
		if(to_remove != null){
			solenoids.remove(to_remove);
			logger.info("Unregistered solenoid " + name);
		}
		else{
			logger.warning("Could not unregister solenoid " + name);
		}
	}

	/**
	 * Get a registered ORFSolenoid
	 *
	 * @param name The name of the ORFSolenoid
	 * @return The ORFSolenoid if it exists, null if otherwise
	 */
	public T getSolenoid(String name){
		for(T solenoid : solenoids){
			if(solenoid.getName().equals(name)){
				return solenoid;
			}
		}
		return null;
	}
}
