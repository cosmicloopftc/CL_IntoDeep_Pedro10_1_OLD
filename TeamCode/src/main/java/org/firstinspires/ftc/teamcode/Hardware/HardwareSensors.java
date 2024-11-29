package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareSensors {
//    private DcMotor Intake_Motor = null;
public DistanceSensor SensorDistance;
    public ColorSensor SensorColor;

    /*Constructor*/
    public HardwareSensors() {
    }

    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {



        SensorDistance = hardwareMap.get(DistanceSensor.class,"SensorDistance");
//        SensorColor = hardwareMap.get(ColorSensor.class, "SensorColor");



        //Save reference to Hardware map
//
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

//example:  for Intake motor
//public method (function) for Intake motor power--to be accessible from anywhere
//    public void setPower(double i) {
//        Intake_Motor.setPower(i);
//    }
}