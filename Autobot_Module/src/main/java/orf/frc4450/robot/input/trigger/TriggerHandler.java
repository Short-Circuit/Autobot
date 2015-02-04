package orf.frc4450.robot.input.trigger;

import java.util.HashSet;
import java.util.Set;

import orf.frc4450.robot.logging.ORFLogger;

/**
 * Handler class to manage Trigger instances
 *
 * @author ShortCircuit908
 */
public class TriggerHandler<T extends ORFTrigger>{
	private final ORFLogger logger = new ORFLogger(this);
	private final Set<T> triggers = new HashSet<>();

	/**
	 * Register an ORFTrigger
	 * <p>
	 * If the ORFTrigger conflicts with an existing ORFTrigger with the same name, the ORFTrigger
	 * will not be added
	 *
	 * @param trigger The ORFTrigger to register
	 */
	public void registerTrigger(T trigger){
		T check_exists = getTrigger(trigger.getName());
		if(check_exists != null){
			logger.warning("A trigger with the name \"" + trigger.getName() + "\" is already registered");
			return;
		}
		triggers.add(trigger);
		new Thread(trigger).start();
		logger.info("Registered trigger " + trigger.getName() + " of type "
				+ trigger.getTrigger().getClass().getSimpleName());
	}

	/**
	 * Register multiple ORFTriggers
	 * If any of the ORFTriggers conflicts with an existing ORFTrigger with the same name, the ORFTrigger
	 * will not be added
	 *
	 * @param triggers The ORFTriggers to register
	 */
	public void registerTriggers(T... triggers){
		for(T trigger : triggers){
			registerTrigger(trigger);
		}
	}

	/**
	 * Unregister an ORFTrigger
	 *
	 * @param name The name of the ORFTrigger to unregister
	 */
	public void unregisterTrigger(String name){
		T to_remove = getTrigger(name);
		if(to_remove != null){
			to_remove.release();
			triggers.remove(to_remove);
			logger.info("Unregistered trigger " + name);
		}
		else{
			logger.warning("Could not unregister trigger " + name);
		}
	}

	/**
	 * Get a registered ORFTrigger
	 *
	 * @param name The name of the ORFTrigger
	 * @return The ORFTrigger if it exists, null if otherwise
	 */
	public T getTrigger(String name){
		for(T trigger : triggers){
			if(trigger.getName().equals(name)){
				return trigger;
			}
		}
		return null;
	}
}
