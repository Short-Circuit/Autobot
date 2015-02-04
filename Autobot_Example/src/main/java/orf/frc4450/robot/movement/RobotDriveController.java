package orf.frc4450.robot.movement;


import edu.wpi.first.wpilibj.Joystick;
import orf.frc4450.robot.Robot;
import orf.frc4450.robot.control.drive.DriveController;
import orf.frc4450.robot.input.StandardDebouncer;
import orf.frc4450.robot.input.hid.HIDHandler;
import orf.frc4450.robot.input.hid.ORFHID;
import orf.frc4450.robot.logging.ORFLogger;


/**
 * @author ShortCircuit908
 */
@SuppressWarnings({"unused", "unchecked"})
public class RobotDriveController extends DriveController{
	private final ORFLogger logger = new ORFLogger(this);
	private final Robot robot;
	private HIDHandler<ORFHID<Joystick>> joystick_handler;
	private Joystick joystick_left;
	private Joystick joystick_right;
	private double left_trim = 1.0;
	private double right_trim = 1.0;
	private StandardDebouncer left_trigger_debouncer = new StandardDebouncer();
	private StandardDebouncer right_trigger_debouncer = new StandardDebouncer();


	public RobotDriveController(Robot robot, int left_motor, int right_motor){
		super(left_motor, right_motor);
		this.robot = robot;
	}

	public RobotDriveController(Robot robot, int front_left_motor, int rear_left_motor, int front_right_motor, int rear_right_motor){
		super(front_left_motor, rear_left_motor, front_right_motor, rear_right_motor);
		this.robot = robot;
	}

	public void initJoysticks(){
		joystick_handler = robot.getDeviceHandler(Joystick.class);
		joystick_left = joystick_handler.getDevice("joystick_left").getDevice();
		joystick_right = joystick_handler.getDevice("joystick_right").getDevice();
	}

	public void setTrim(double left_trim, double right_trim){
		this.left_trim = left_trim;
		this.right_trim = right_trim;
		ORFLogger.getLCD().printLine(1, "left_trim : " + left_trim);
		ORFLogger.getLCD().printLine(2, "right_trim: " + right_trim);
	}

	@Override
	public void movement(){
		// Update the debouncers with fresh values from the joystick triggers
		left_trigger_debouncer.update(joystick_left.getTrigger(null));
		right_trigger_debouncer.update(joystick_right.getTrigger(null));
		double left_y = joystick_left.getY();
		double right_y = joystick_right.getY();
		// Create a dead zone and apply the trim
		left_y *= (Math.abs(left_y) < 0.1 ? 0 : left_trim);
		right_y *= (Math.abs(right_y) < 0.1 ? 0 : right_trim);
		double difference = Math.abs(left_y - right_y);
		// Only match speeds when the right trigger is not pressed
		if(difference < 0.1d && !right_trigger_debouncer.get()){
			double max = Math.max(left_y, right_y);
			left_y = max;
			right_y = max;
		}
		// If the left trigger is pressed, square the input values
		left_y = limit((left_trigger_debouncer.get() ? left_y : Math.pow(left_y, 2)));
		right_y = limit((left_trigger_debouncer.get() ? right_y : Math.pow(right_y, 2)));
		ORFLogger.getLCD().printLine(3, "left_y : " + left_y);
		ORFLogger.getLCD().printLine(4, "right_y: " + right_y);
		setLeftRightMotorOutputs(left_y, right_y);
		//robot.delay(20);
	}
}
