package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import java.util.List;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure
//***This was setup for OpMode but can be it used for LinearOpMode also?
//OpMode structure: init(), init_loop(), start(), loop(), stop().  as of SDK 8.1, 1ms between call in loop()
//LinearOpMode structure: runOpMode(), waitForStart(), isStarted(), isStopRequested(), idle(), opModeIsActive(), opModeInInit()

@Config
public class HardwareRobot {
//**ADD assignment of variables here for subsequent connected device.

/* example use of enum from FTC Thunderbolts (Sacramento, CA) mentor's program structure
    enum hwMapState {
        DRIVETRAIN_MOTOR_ONLY,
        MOTOR_OUTAKE_SLIDER,
        MOTOR_OUTAKE_SLIDER_ONLY
    }
*/

    HardwareMap hwMap = null;

    //*Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    //*Important to set initial value to Motor and Servo objects, even if null.

    //voltage sensor
    public VoltageSensor batteryVoltageSensor;
    public double batteryVoltageSensorThreshold = 9.0;

    //*Setup IMU sensor object.
    public IMU imu;

    //**Create variable name for an object to be created--THIS IS WHERE new device setup is added.
    public HardwareDrivetrain Drivetrain = null;
    public HardwareIntake Intake = null;
    public HardwareOuttake Outtake = null;
    public HardwareHang Hang = null;
    public HardwareSensors Sensor = null;
    public HardwareGamePadLED GamePadLED = null;
    public HardwareLED AdafruitLED = null;



    //**ADD on subsequent connected device.
    boolean drivetrainConnected = true;
    boolean intakeConnected = false;
    boolean outtakeConnected = true;
    boolean hangConnected = false;
    boolean sensorConnected = false;
    boolean gamePadLEDConnected = false;
    boolean AdafruitLEDConnected = true;


    List<LynxModule> allHubs = null;
    LynxModule CTRLHub = null;
    LynxModule EXPHub = null;

//declare variables for the "drive" method.
    double driveTheta, r, newRight, newForward;


    /*Constructor*/
    public HardwareRobot() {

    }


    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map


        //TODO: Define and initialize IMU sensor on new Control Hub--new orientation of the control hub
        imu = hardwareMap.get(IMU.class, "imu");
        //Define how the hub is mounted on robot to get correct Yaw, Pitch and Roll values.
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.DOWN;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        //Initialize IMU with this mounting orientation
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();



        //map and setup mode of drivetrain motors
        if (drivetrainConnected){
            Drivetrain = new HardwareDrivetrain();
            Drivetrain.init(hardwareMap);
        }

        //map and setup mode of Intake motors
        if (intakeConnected) {
            Intake = new HardwareIntake();
            Intake.init(hardwareMap);
        }

        //map and setup mode of Outtake motors and servos
        if (outtakeConnected) {
            Outtake = new HardwareOuttake();
            Outtake.init(hardwareMap);
        }

        if (hangConnected) {
            Hang = new HardwareHang();
            Hang.init(hardwareMap);
        }

        if (sensorConnected) {
            Sensor = new HardwareSensors();
            Sensor.init(hardwareMap);
        }

        if (gamePadLEDConnected) {
            GamePadLED = new HardwareGamePadLED();
            GamePadLED.init(hardwareMap);
        }

        if (AdafruitLEDConnected) {
            AdafruitLED = new HardwareLED();
            AdafruitLED.init(hardwareMap);
        }


//?unknown source        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();
        //batteryVoltageSensor = hardwareMap.voltageSensor.get("Expansion Hub 2");       //GeorgeFIRST kickoff video

    }


    public void start(){
        //PUT functions here

    }

    public void loop(){

    }

    public void stop () {

    }

    //reading Control/Expansion Hub Data
    public Number[] bulkRead() {
//        int newLeftEncoder = -LBack_Motor.getCurrentPosition();       //0. direction is reverse
//        int newRightEncoder = RBack_Motor.getCurrentPosition();       //1.
//        int newCenterEncoder = -RFront_Motor.getCurrentPosition();    //2. direction is reverse
//        int sliderMotorRightPosition = Slider.Slider_Motor_Right.getCurrentPosition();    //3
//        int sliderMotorLeftPosition = Slider.Slider_Motor_Left.getCurrentPosition();      //4
//        double Slider_ServoRightPosition = Slider.Slider_Servo_Arm.getPosition();       //5
//        double Slider_ServoLeftPosition = Slider.Slider_Servo_Dropper.getPosition();         //6
//        double Intake_ServoPosition = Intake.Intake_Servo.getPosition();                  //7
//        int Intake_MotorPosition = Intake.Intake_Motor.getCurrentPosition();              //8
//        double Airplane_ServoPosition = Airplane.Airplane_Servo.getPosition();            //9
//        int Suspension_MotorPosition = Suspension.Suspension_Motor.getCurrentPosition();  //10
//        double Suspension_ServoPosition = Suspension.Suspension_Servo.getPosition();      //11
//
//        double batteryVoltage = Double.POSITIVE_INFINITY;
//        for (VoltageSensor sensor : hwMap.voltageSensor) {
//            double voltage = sensor.getVoltage();
//            if (voltage > 0) {
//                batteryVoltage= Math.min(batteryVoltage, voltage);
//            }
//        }
//
        Number[] bulkArray = new Number[20];
//        bulkArray[0] = newLeftEncoder;
//        bulkArray[1] = newRightEncoder;
//        bulkArray[2] = newCenterEncoder;
//        bulkArray[3] = sliderMotorRightPosition;
//        bulkArray[4] = sliderMotorLeftPosition;
//        bulkArray[5] = Slider_ServoRightPosition;
//        bulkArray[6] = slider_ServoDropperPosition;
//        bulkArray[7] = Intake_ServoPosition;
//        bulkArray[8] = Intake_MotorPosition;
//        bulkArray[9] = Airplane_ServoPosition;
//        bulkArray[10]= Suspension_MotorPosition;
//        bulkArray[11]= Suspension_ServoPosition;

        return bulkArray;
    }

    //simplify driving move, based on Learn Java for FTC p. 145-146, Oct 2023
    //botheading is used for fieldOriented (driver's perspective driving)
    public void drive(double forward, double right, double rotate, double power, double botHeading, String drivingOrientation) {
        if (drivingOrientation == "fieldOriented") {
            driveTheta = Math.atan2(forward, right);                         //convert to polar
            r = Math.hypot(forward, right);                                  //convert to polar
            driveTheta = AngleUnit.normalizeRadians(driveTheta - botHeading);       // rotate angle
            newForward = r * Math.sin(driveTheta);
            newRight = 1.1 * r * Math.cos(driveTheta);  //factor = 1.1; correct for strafe imperfection; convert back to cartesian
        }
        if (drivingOrientation == "robotOriented") {
            newForward = forward;
            newRight = right;
        }
        double denominator = Math.max(Math.abs(newForward) + Math.abs(newRight) + Math.abs(rotate), 1);
        double frontLeftPower =     power*(newForward + newRight + rotate)/denominator;
        double frontRightPower = power*(newForward - newRight - rotate)/denominator;
        double backLeftPower = power*(newForward - newRight + rotate)/denominator;
        double backRightPower = power*(newForward + newRight - rotate)/denominator;

        HardwareDrivetrain.setMotorPower(frontRightPower, frontLeftPower, backRightPower, backLeftPower);
    }

}