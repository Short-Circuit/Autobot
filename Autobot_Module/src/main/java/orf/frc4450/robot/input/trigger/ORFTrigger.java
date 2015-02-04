package orf.frc4450.robot.input.trigger;

import edu.wpi.first.wpilibj.buttons.Trigger;
import orf.frc4450.robot.input.StandardDebouncer;

/**
 * Wrapper for classes implementing Trigger
 *
 * @author ShortCircuit908
 */
public class ORFTrigger<T extends Trigger> implements Runnable{
	private final T trigger;
	private final String name;
	private boolean released = false;
	private final StandardDebouncer debouncer = new StandardDebouncer();

	/**
	 * @param trigger The Trigger object
	 * @param name    The name of this object
	 */
	public ORFTrigger(T trigger, String name){
		this.name = name;
		this.trigger = trigger;
	}

	/**
	 * @return The Trigger object associated with the object
	 */
	public T getTrigger(){
		return trigger;
	}

	/**
	 * @return The name of this object
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The raw value of the Trigger object
	 */
	public boolean get(){
		return trigger.get();
	}

	/**
	 * @return Debounced value of the Trigger object
	 */
	public boolean getDebounced(){
		return debouncer.get();
	}

	public void run(){
		while(!released){
			debouncer.update(get());
		}
	}

	public void release(){
		released = true;
	}
}
