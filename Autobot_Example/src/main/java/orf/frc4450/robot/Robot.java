package orf.frc4450.robot;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import orf.frc4450.robot.input.AdvancedDebouncer;
import orf.frc4450.robot.input.hid.HIDHandler;
import orf.frc4450.robot.input.hid.ORFHID;
import orf.frc4450.robot.input.sensor.ORFSensor;
import orf.frc4450.robot.input.sensor.SensorHandler;
import orf.frc4450.robot.monitor.BatteryMonitor;
import orf.frc4450.robot.monitor.UltrasonicResponse;
import orf.frc4450.robot.movement.RobotDriveController;

/**
 * @author ShortCircuit908
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Robot extends Autobot{
	private final String camera_ip = "10.44.50.11";
	private RobotDriveController controller;
	private SensorHandler<ORFSensor<Ultrasonic>> ultrasonic_handler;
	private HIDHandler<ORFHID<Joystick>> joystick_handler;
	private CameraServer camera_server;
	private AxisCamera camera;

	@Override
	public void assemble(){
		ultrasonic_handler = new SensorHandler<>();
		joystick_handler = new HIDHandler();
		controller = new RobotDriveController(this, 0, 1);
		camera_server = CameraServer.getInstance();
		camera_server.startAutomaticCapture("camera0");
		camera_server.setQuality(50);
		camera = new AxisCamera(camera_ip);
		//registerSensors();
		registerInput();
		controller.initJoysticks();
		controller.setTrim(1.0, 1.0);
		new Thread(new BatteryMonitor(getDriverStation())).start();
	}

	@Override
	public boolean rollOut() throws Exception{
		boolean success = true;
		success &= controller.isCANInitialized();
		success &= controller.speedControllersAllocated();
		return success;
	}

	@Override
	public void autonomous(){
		new Thread(ultrasonic_handler).start();
		controller.setEnabled(true);
	}

	@Override
	public void manual(){
		new Thread(controller).start();
		AdvancedDebouncer<Integer> f = new AdvancedDebouncer<>(null);
		controller.setEnabled(true);
	}

	@Override
	public String getVersion(){
		return "CM7-01.22.15-01";
	}

	@Override
	public int getTeamNumber(){
		return 4450;
	}

	private void registerSensors(){
		ORFSensor<Ultrasonic> ultrasonic_00 = new ORFSensor<>(new Ultrasonic(5, 7),
				"ultrasonic_00").setAction(new UltrasonicResponse());
		ultrasonic_00.setUpdateFrequency(25);
		ultrasonic_handler.registerSensor(ultrasonic_00);
	}

	private void registerInput(){
		ORFHID<Joystick> joystick_left = new ORFHID<>(new Joystick(1), "joystick_left");
		ORFHID<Joystick> joystick_right = new ORFHID<>(new Joystick(2), "joystick_right");
		joystick_handler.registerDevices(joystick_left, joystick_right);
	}

	public HIDHandler getDeviceHandler(Class<? extends GenericHID> device_class){
		switch(device_class.getSimpleName()){
			case "Joystick":
				return joystick_handler;
			default:
				return null;
		}
	}

	public SensorHandler getSensorHandler(Class<? extends SensorBase> sensor_class){
		switch(sensor_class.getSimpleName()){
			case "Ultrasonic":
				return ultrasonic_handler;
			default:
				return null;
		}
	}

	public AxisCamera getCamera(){
		return camera;
	}
}
