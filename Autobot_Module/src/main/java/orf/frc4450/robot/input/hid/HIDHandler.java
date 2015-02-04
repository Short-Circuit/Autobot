package orf.frc4450.robot.input.hid;

import java.util.HashSet;
import java.util.Set;

import orf.frc4450.robot.logging.ORFLogger;

/**
 * Handler class to manage GenericHID devices
 *
 * @author ShortCircuit908
 */
public class HIDHandler<T extends ORFHID>{
	private final ORFLogger logger = new ORFLogger(this);
	private final Set<T> devices = new HashSet<>();

	/**
	 * Register an ORFHID
	 * <p>
	 * If the ORFHID conflicts with an existing ORFHID with the same name, the ORFHID will not
	 * be added
	 *
	 * @param device The ORFHID to register
	 */
	public void registerDevice(T device){
		T check_exists = getDevice(device.getName());
		if(check_exists != null){
			logger.warning("An input device with the name \"" + device.getName() + "\" is already registered");
			return;
		}
		devices.add(device);
		logger.info("Registered input device " + device.getName() + " of type "
				+ device.getDevice().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFHIDs
	 * <p>
	 * If any of the ORFHIDs conflicts with an existing ORFHID with the same name, the ORFHID will
	 * not be added
	 *
	 * @param devices The ORFHIDs to register
	 */
	public void registerDevices(T... devices){
		for(T device : devices){
			registerDevice(device);
		}
	}

	/**
	 * Unregister an ORFHID
	 *
	 * @param name The name of the ORFHID to unregister
	 */
	public void unregisterDevice(String name){
		T to_remove = getDevice(name);
		if(to_remove != null){
			devices.remove(to_remove);
			logger.info("Unregistered input device " + name);
		}
		else{
			logger.warning("Could not unregister input device " + name);
		}
	}

	/**
	 * Get a registered ORFHID
	 *
	 * @param name The name of the ORFHID
	 * @return The ORFHID if it exists, null if otherwise
	 */
	public T getDevice(String name){
		for(T device : devices){
			if(device.getName().equals(name)){
				return device;
			}
		}
		return null;
	}
}
