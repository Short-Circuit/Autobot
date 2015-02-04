package orf.frc4450.robot.input.sensor;

import edu.wpi.first.wpilibj.SensorBase;
import orf.frc4450.robot.logging.ORFLogger;

/**
 * @author ShortCircuit908
 */
public abstract class SensorResponse<T extends SensorBase>{
	protected final ORFLogger logger = new ORFLogger(this);

	public abstract void onSensorRefresh(T sensor);
}
