package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.graphics.Color;


//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure

public class HardwareSensors {
    public DistanceSensor sensorColorDistance;
    public ColorSensor sensorColor;


    /*Constructor*/
    public HardwareSensors() {
    }


    /* Initialize standard Hardware interface */
    public void init(HardwareMap hardwareMap)    {
        //Save reference to Hardware map

        //TODO: example in main class:         robot.Sensor.LEDGreen.setState(true);
        //if red, green = false, then state is off.        if red, green = true, then state is orange

        // get a reference to both color sensors.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");

        // get a reference to the distance sensors that share the same names.
        sensorColorDistance = hardwareMap.get(DistanceSensor.class, "sensorColor");

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