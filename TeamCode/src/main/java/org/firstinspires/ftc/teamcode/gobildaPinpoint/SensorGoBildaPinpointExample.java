package org.firstinspires.ftc.teamcode.gobildaPinpoint;

/* SELF-CONTAINED PROGRAM TO TEST Gobilda Pinpoint Odometry pod with pinpoint computer
*/

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import java.util.List;
import com.qualcomm.hardware.lynx.LynxModule;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Hardware.HardwareRobot;
import org.firstinspires.ftc.teamcode.pedroPathing.util.DashboardPoseTracker;

import java.util.Locale;

/*   MIT License
 *   Copyright (c) [2024] [Base 10 Assets, LLC]
 */
/*
This opmode shows how to use the goBILDA® Pinpoint Odometry Computer.
The goBILDA Odometry Computer is a device designed to solve the Pose Exponential calculation
commonly associated with Dead Wheel Odometry systems. It reads two encoders, and an integrated
system of senors to determine the robot's current heading, X position, and Y position.

it uses an ESP32-S3 as a main cpu, with an STM LSM6DSV16X IMU.
It is validated with goBILDA "Dead Wheel" Odometry pods, but should be compatible with any
quadrature rotary encoder. The ESP32 PCNT peripheral is speced to decode quadrature encoder signals
at a maximum of 40mhz per channel. Though the maximum in-application tested number is 130khz.

The device expects two perpendicularly mounted Dead Wheel pods. The encoder pulses are translated
into mm and their readings are transformed by an "offset", this offset describes how far away
the pods are from the "tracking point", usually the center of rotation of the robot.

Dead Wheel pods should both increase in count when moved forwards and to the left.
The gyro will report an increase in heading when rotated counterclockwise.

The Pose Exponential algorithm used is described on pg 181 of this book:
https://github.com/calcmogul/controls-engineering-in-frc

For support, contact tech@gobilda.com

-Ethan Doak
 */


@Config    //need this to allow appearance in FtcDashboard Configuration to make adjust of variables
@TeleOp(name="goBILDA® PinPoint Example 1.1", group="TEST")
//@Disabled

public class SensorGoBildaPinpointExample extends LinearOpMode {

    GoBildaPinpointDriver gobildaPinpointOdo; // Declare OpMode member for the Odometry Computer
    double oldTime = 0;

    private DashboardPoseTracker dashboardPoseTracker;
    private Telemetry telemetry;
    HardwareRobot robot = new HardwareRobot();
    private ElapsedTime runtime = new ElapsedTime();
    //Declare variables for standard driving--not using PedroPath follower method (using Learn JAVA for FTC book)
    double y, x, rx, powerShift;
    double botHeading;
    String drivingOrientation = "robotOriented";                //TODO: as default for Eduardo, but will also reset in init as well.


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);   //note hardwareMap is default and part of FTC Robot Controller HardwareMap class
        robot.imu.resetYaw();      //reset the IMU/Gyro angle with each match.
        runtime.reset();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        //Important Step 2: Get access to a list of Expansion Hub Modules to enable changing caching methods.
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        gobildaPinpointOdo = hardwareMap.get(GoBildaPinpointDriver.class,"pinpointOdo");

        /*
        Set the odometry pod positions relative to the point that the odometry computer tracks around.
        The X pod offset refers to how far sideways from the tracking point the
        X (forward) odometry pod is. Left of the center is a positive number,
        right of center is a negative number. the Y pod offset refers to how far forwards from
        the tracking point the Y (strafe) odometry pod is. forward of center is a positive number,
        backwards is a negative number.
         */
        gobildaPinpointOdo.setOffsets(-84.0, -168.0); //these are tuned for 3110-0002-0001 Product Insight #1

        /*
        Set the kind of pods used by your robot. If you're using goBILDA odometry pods, select either
        the goBILDA_SWINGARM_POD, or the goBILDA_4_BAR_POD.
        If you're using another kind of odometry pod, uncomment setEncoderResolution and input the
        number of ticks per mm of your odometry pod.
         */
        gobildaPinpointOdo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //odo.setEncoderResolution(13.26291192);


        /*
        Set the direction that each of the two odometry pods count. The X (forward) pod should
        increase when you move the robot forward. And the Y (strafe) pod should increase when
        you move the robot to the left.
         */
        gobildaPinpointOdo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);


        /*
        Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
        The IMU will automatically calibrate when first powered on, but recalibrating before running
        the robot is a good idea to ensure that the calibration is "good".
        resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
        This is recommended before you run your autonomous, as a bad initial calibration can cause
        an incorrect starting value for x, y, and heading.
         */
        //odo.recalibrateIMU();
        gobildaPinpointOdo.resetPosAndIMU();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", gobildaPinpointOdo.getXOffset());
        telemetry.addData("Y offset", gobildaPinpointOdo.getYOffset());
        telemetry.addData("Device Version Number:", gobildaPinpointOdo.getDeviceVersion());
        telemetry.addData("Device Scalar", gobildaPinpointOdo.getYawScalar());
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        resetRuntime();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//Drivetrain Movement:  MANUAL DRIVE for Mecanum wheel drive.
            drivingOrientation = "robotOriented";
            y = -gamepad1.left_stick_y;           // Remember,joystick value is reversed!
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;

            //Cancel angle movement of gamepad left stick, make move move either up/down or right/left
            if (Math.abs(y) >= Math.abs(x)) {
                y = y;
                x = 0;
            } else {
                y = 0;
                x = x;
            }
            //DRIVETRAIN
            //baseline:  reduce motor speed to 50% max
            double motorPowerDefault = 0.5;
            double powerChange;

//SLOW DOWN with RIGHT LOWER TRIGGER (lower of the top side button) press with the other gamepad stick.
            if ((Math.abs(gamepad1.left_stick_y) > 0.1 && gamepad1.right_trigger > 0.1) || (Math.abs(gamepad1.left_stick_x) > 0.1 && gamepad1.right_trigger > 0.1) || (Math.abs(gamepad1.right_stick_x) > 0.1 && gamepad1.right_trigger > 0.1)) {
                powerChange = -0.3;
                //SPEED UP with RIGHT UPPER BUMPER (up of the top side button) press with the other gamepad stick.
            } else if ((Math.abs(gamepad1.left_stick_y) > 0.1 && gamepad1.right_bumper) || (Math.abs(gamepad1.left_stick_x) > 0.1 && gamepad1.right_bumper) || (Math.abs(gamepad1.right_stick_x) > 0.1 && gamepad1.right_bumper)) {
                powerChange = 0.4;
            } else {
                powerChange = 0;
            }
            powerShift = motorPowerDefault + powerChange;
            //robot.Intake.IntakeIN();
            robot.drive(y, x, rx, powerShift, botHeading, drivingOrientation);






            /*
            Request an update from the Pinpoint odometry computer. This checks almost all outputs
            from the device in a single I2C read.
             */
            gobildaPinpointOdo.update();

            /*
            Optionally, you can update only the heading of the device. This takes less time to read, but will not
            pull any other data. Only the heading (which you can pull with getHeading() or in getPosition().
             */
            //odo.update(GoBildaPinpointDriver.readData.ONLY_UPDATE_HEADING);


            if (gamepad1.a){
                gobildaPinpointOdo.resetPosAndIMU(); //resets the position to 0 and recalibrates the IMU
            }

            if (gamepad1.b){
                gobildaPinpointOdo.recalibrateIMU(); //recalibrates the IMU without resetting position
            }

            /*
            This code prints the loop frequency of the REV Control Hub. This frequency is effected
            by I²C reads/writes. So it's good to keep an eye on. This code calculates the amount
            of time each cycle takes and finds the frequency (number of updates per second) from
            that cycle time.
             */
            double newTime = getRuntime();
            double loopTime = newTime-oldTime;
            double frequency = 1/loopTime;
            oldTime = newTime;


            /*
            gets the current Position (x & y in mm, and heading in degrees) of the robot, and prints it.
             */
            Pose2D pos = gobildaPinpointOdo.getPosition();
            String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Position", data);

            /*
            gets the current Velocity (x & y in mm/sec and heading in degrees/sec) and prints it.
             */
            Pose2D vel = gobildaPinpointOdo.getVelocity();
            String velocity = String.format(Locale.US,"{XVel: %.3f, YVel: %.3f, HVel: %.3f}", vel.getX(DistanceUnit.MM), vel.getY(DistanceUnit.MM), vel.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Velocity", velocity);


            /*
            Gets the Pinpoint device status. Pinpoint can reflect a few states. But we'll primarily see
            READY: the device is working as normal
            CALIBRATING: the device is calibrating and outputs are put on hold
            NOT_READY: the device is resetting from scratch. This should only happen after a power-cycle
            FAULT_NO_PODS_DETECTED - the device does not detect any pods plugged in
            FAULT_X_POD_NOT_DETECTED - The device does not detect an X pod plugged in
            FAULT_Y_POD_NOT_DETECTED - The device does not detect a Y pod plugged in
            */
            telemetry.addData("Status", gobildaPinpointOdo.getDeviceStatus());
            telemetry.addData("Pinpoint Frequency", gobildaPinpointOdo.getFrequency()); //prints/gets the current refresh rate of the Pinpoint
            telemetry.addData("REV Hub Frequency: ", frequency); //prints the control system refresh rate
            telemetry.update();

        }
    }
}