package org.firstinspires.ftc.teamcode.Diagnostic;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.HardwareDrivetrain;
import org.firstinspires.ftc.teamcode.Hardware.HardwareNoDriveTrainRobot;
import org.firstinspires.ftc.teamcode.Hardware.HardwareTestDevice;

import java.util.ArrayList;

// THIS CLASS SET UP THE HARDWAREMAP AND VARIOUS HARDWARE TEST CHOICES.  THIS IS THEN PULL INTO CLASS TestWIRING_OpMODE


@Disabled
public class TestProgrammingBoard  {
    HardwareMap hardwareMap = null;
    HardwareNoDriveTrainRobot hardwareNoDriveTrainRobot = new HardwareNoDriveTrainRobot();
    HardwareTestDevice testDevices = new HardwareTestDevice();
    HardwareDrivetrain hardwareDrivetrainRobot = new HardwareDrivetrain();

    private double leftFront_ticksPerRotation;
    private double leftRear_ticksPerRotation;
    private double rightFront_ticksPerRotation;
    private double rightRear_ticksPerRotation;
    private AngleUnit angleUnit;

    public void init(HardwareMap hardwareMap) {
        hardwareDrivetrainRobot .init(hardwareMap);
        hardwareNoDriveTrainRobot.init(hardwareMap);
        testDevices.init(hardwareMap);

        leftFront_ticksPerRotation = hardwareDrivetrainRobot.leftFront.getMotorType().getTicksPerRev();
        leftRear_ticksPerRotation = hardwareDrivetrainRobot.leftRear.getMotorType().getTicksPerRev();
        rightFront_ticksPerRotation = hardwareDrivetrainRobot.rightFront.getMotorType().getTicksPerRev();
        rightRear_ticksPerRotation = hardwareDrivetrainRobot.rightRear.getMotorType().getTicksPerRev();

    }

    //********* METHODS ****
    public void leftFront_setMotorSpeed(double speed) {hardwareDrivetrainRobot.leftFront.setPower(speed);}
    public double leftFront_getMotorRotations() {return hardwareDrivetrainRobot.leftFront.getCurrentPosition() / leftFront_ticksPerRotation;}
    public void rightFront_setMotorSpeed(double speed) {hardwareDrivetrainRobot.rightFront.setPower(speed);}
    public double rightFront_getMotorRotations() {return hardwareDrivetrainRobot.rightFront.getCurrentPosition() / rightFront_ticksPerRotation;}

    public void leftRear_setMotorSpeed(double speed) {hardwareDrivetrainRobot.leftRear.setPower(speed);}
    public double leftRear_getMotorRotations() {return hardwareDrivetrainRobot.leftRear.getCurrentPosition() / leftRear_ticksPerRotation;}
    public void rightRear_setMotorSpeed(double speed) {hardwareDrivetrainRobot.rightRear.setPower(speed);}
    public double rightRear_getMotorRotations() {return hardwareDrivetrainRobot.rightRear.getCurrentPosition() / rightRear_ticksPerRotation;}

//    public void rightOuttakeArm_setServoPosition(double position) {
//        HardwareOuttake.
//                rightOuttakeArm.setPosition(position);
//    }

    public double getHeading(AngleUnit angleUnit) {return hardwareNoDriveTrainRobot.imu.getRobotYawPitchRollAngles().getPitch(angleUnit);}

//    public void set_Test_Servo_Position(double position) {Test_Servo.setPosition(position);}

//    public double sensorDistance_getDistance(DistanceUnit du) {
//        return Sensor.sensorColorDistance.getDistance(du);
//    }

//    public int getAmountRed() {
//        return Sensor.sensorColor.red();
//    }

//    public boolean isTouchSensorPressed() {
//        return !touchSensor.getState();
//    }

//    public double getPotAngle() {
//        return Range.scale(pot.getVoltage(), 0, pot.getMaxVoltage(), 0, 270);
//    }



    public ArrayList<TestItem> getTests() {
        ArrayList<TestItem> tests = new ArrayList<>();
        tests.add(new Test_Motor("rightFront-positive power", 0.01, hardwareDrivetrainRobot.rightFront));
        tests.add(new Test_Motor("leftFront-positive power", 0.01, hardwareDrivetrainRobot.leftFront));
        tests.add(new Test_Motor("rightRear-positive power", 0.01, hardwareDrivetrainRobot.rightRear));
        tests.add(new Test_Motor("leftRear-positive power", 0.01, hardwareDrivetrainRobot.leftRear));

        tests.add(new Test_Servo("servoTest-A min, Y max", testDevices.servoTest, 0.0, 1.0));

        //tests.add(new TestDigitalChannel_14_1("PB Touch", touchSensor));

        //tests.add(new TestIMU("IMU heading in degrees = ", getHeading(AngleUnit.DEGREES)));

        return tests;
    }
}