package orf.frc4450.robot.control.drive;

/**
 * @author ShortCircuit908
 *
 * This is what the jokers on the FRC programming team SHOULD have used. Instead, they used a
 * static class. With static variables for each element.
 */
public enum ORFMotorType{
	FRONT_LEFT(0),
	FRONT_RIGHT(1),
	REAR_LEFT(2),
	REAR_RIGHT(3);

	private final int value;
	private ORFMotorType(int value){
		this.value = value;
	}
}
