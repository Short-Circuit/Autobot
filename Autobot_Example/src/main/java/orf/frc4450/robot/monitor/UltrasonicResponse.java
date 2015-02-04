package orf.frc4450.robot.monitor;

import edu.wpi.first.wpilibj.Ultrasonic;
import orf.frc4450.robot.input.sensor.SensorResponse;

/**
 * @author ShortCircuit908
 */
public class UltrasonicResponse extends SensorResponse<Ultrasonic>{

	public UltrasonicResponse(){
		super();
	}

	@Override
	public void onSensorRefresh(Ultrasonic sensor){
		sensor.ping();
		double inches = sensor.getRangeInches();
		int feet = (int)(inches / 12);
		inches %= 12;
		logger.info("Range: " + feet + "'" + inches + "\"");
	}
}
