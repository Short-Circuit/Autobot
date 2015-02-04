package orf.frc4450.robot.monitor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import orf.frc4450.robot.Autobot;

/**
 * @author ShortCircuit908
 */
public class BatteryMonitor implements Runnable{
	private final DriverStation driver_station;
	private boolean warning_state = false;
	public BatteryMonitor(DriverStation driver_station){
		this.driver_station = driver_station;
	}
	@Override
	public void run(){
		while(true){
			double voltage = driver_station.getBatteryVoltage();
			if(voltage < 11.7){
				warning_state = !warning_state;
				SmartDashboard.putBoolean("Low Battery", warning_state);
				Autobot.delay(1000);
			}
			else{
				warning_state = false;
				SmartDashboard.putBoolean("Low Battery", false);
				Autobot.delay(10000);
			}
		}
	}
}
