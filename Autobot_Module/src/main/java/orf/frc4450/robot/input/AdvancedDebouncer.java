package orf.frc4450.robot.input;

/**
 * This debouncer should only be used with primitive types
 * If, however, there happens to be an implementation where a debouncer for non-primitive types is
 * required, this should work just fine.
 *
 * @author ShortCircuit908
 */
public class AdvancedDebouncer<T>{
	private final T neutral_value;
	private T value = null;
	private long last_changed;

	public AdvancedDebouncer(T neutral_value){
		this.neutral_value = neutral_value;
		last_changed = System.currentTimeMillis();
	}

	public void update(T new_value){
		long current_time = System.currentTimeMillis();
		if(new_value != neutral_value){
			value = new_value;
			last_changed = current_time;
		}
		else if(current_time - 10 >= last_changed){
			value = neutral_value;
		}
	}

	public T get(){
		return value;
	}
}
