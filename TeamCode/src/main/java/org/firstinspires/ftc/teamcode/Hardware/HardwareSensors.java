package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareSensors {
//    private DcMotor Intake_Motor = null;
ColorSensor colorIntake1;
    /*Constructor*/
    public HardwareSensors() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map
        colorIntake1 = hardwareMap.get(ColorSensor.class,"ColorTest");

//example:  map and setup mode of Intake motor
//        Intake_Motor = hardwareMap.get(DcMotor.class, "Intake_Motor");
//        Intake_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        Intake_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Intake_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Intake_Motor.setDirection(DcMotor.Direction.FORWARD);
//        Intake_Motor.setPower(0);


    }


    public void start(){

    }


    public void stop () {

    }

    public int getRed() {
        return colorIntake1.red();
    }
    public int getGreen() {
        return colorIntake1.red();
    }
    public int getBlue() {
        return colorIntake1.red();
    }

//example:  for Intake motor
//public method (function) for Intake motor power--to be accessible from anywhere
//    public void setPower(double i) {
//        Intake_Motor.setPower(i);
//    }
}