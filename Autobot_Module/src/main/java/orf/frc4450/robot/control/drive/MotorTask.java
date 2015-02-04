package orf.frc4450.robot.control.drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Task to asynchronously drive a motor for a given distance
 *
 * @author ShortCircuit908
 */
public class MotorTask<T extends Encoder> implements Runnable{
	private final SpeedController motor;
	private final T encoder;
	private final double speed;
	private final double distance;

	public MotorTask(SpeedController motor, T encoder, double speed, double distance){
		this.motor = motor;
		this.encoder = encoder;
		this.speed = speed;
		this.distance = Math.abs(distance);
	}

	public void run(){
		motor.set(speed);
		while(Math.abs(encoder.getDistance()) <= distance){

		}
		motor.set(0);
	}
}
