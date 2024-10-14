package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.HardwareDrivetrain;
import org.firstinspires.ftc.teamcode.Hardware.HardwareIntake;
import org.firstinspires.ftc.teamcode.Hardware.HardwareRobot;

import java.util.ArrayList;

// THIS CLASS SET UP THE HARDWAREMAP AND VARIOUS HARDWARE TEST CHOICES.  THIS IS THEN PULL INTO CLASS TestWIRING_OpMODE



public class TestProgrammingBoard extends HardwareRobot {
    HardwareMap hardwareMap = null;
    private double leftFront_ticksPerRotation;
    private double leftRear_ticksPerRotation;
    private double rightFront_ticksPerRotation;
    private double rightRear_ticksPerRotation;
    private AngleUnit angleUnit;

    public void init(HardwareMap hardwareMap) {
        leftFront_ticksPerRotation = HardwareDrivetrain.leftFront.getMotorType().getTicksPerRev();
        leftRear_ticksPerRotation = HardwareDrivetrain.leftRear.getMotorType().getTicksPerRev();
        rightFront_ticksPerRotation = HardwareDrivetrain.rightFront.getMotorType().getTicksPerRev();
        rightRear_ticksPerRotation = HardwareDrivetrain.rightRear.getMotorType().getTicksPerRev();

    }

    //********* METHODS ****
    public void leftFront_setMotorSpeed(double speed) {HardwareDrivetrain.leftFront.setPower(speed);}
    public double leftFront_getMotorRotations() {return HardwareDrivetrain.leftFront.getCurrentPosition() / leftFront_ticksPerRotation;}
    public void rightFront_setMotorSpeed(double speed) {HardwareDrivetrain.rightFront.setPower(speed);}
    public double rightFront_getMotorRotations() {return HardwareDrivetrain.rightFront.getCurrentPosition() / rightFront_ticksPerRotation;}

    public void leftRear_setMotorSpeed(double speed) {HardwareDrivetrain.leftRear.setPower(speed);}
    public double leftRear_getMotorRotations() {return HardwareDrivetrain.leftRear.getCurrentPosition() / leftRear_ticksPerRotation;}
    public void rightRear_setMotorSpeed(double speed) {HardwareDrivetrain.rightRear.setPower(speed);}
    public double rightRear_getMotorRotations() {return HardwareDrivetrain.rightRear.getCurrentPosition() / rightRear_ticksPerRotation;}

    public void intake_setServoPosition(double position) {//HardwareIntake.(position);
    }

    public double getHeading(AngleUnit angleUnit) { return imu.getRobotYawPitchRollAngles().getPitch(angleUnit);}

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
        tests.add(new TestingMotor("rightFront-positive power", 0.01, HardwareDrivetrain.rightFront));
        tests.add(new TestingMotor("leftFront-positive power", 0.01, HardwareDrivetrain.leftFront));
        tests.add(new TestingMotor("rightRear-positive power", 0.01, HardwareDrivetrain.rightRear));
        tests.add(new TestingMotor("leftRear-positive power", 0.01, HardwareDrivetrain.leftRear));

        //tests.add(new TestServo("Test_Servo", Test_Servo, 0.0, 1.0));
        //tests.add(new TestServo("Airplane_Servo", Airplane_Servo, 0.19, 0.38));      //lock plane=0.19, launch plane=0.38

        //tests.add(new TestDigitalChannel_14_1("PB Touch", touchSensor));

        //tests.add(new TestIMU("IMU heading in degrees = ", getHeading(AngleUnit.DEGREES)));

        return tests;
    }
}
