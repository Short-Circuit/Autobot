package orf.frc4450.robot.input;

/**
 * Your good, old-fashioned boolean debouncer
 * Great with buttons, triggers, whatever
 *
 * @author ShortCircuit908
 */
public class StandardDebouncer extends AdvancedDebouncer<Boolean>{

	public StandardDebouncer(){
		super(false);
	}
}
