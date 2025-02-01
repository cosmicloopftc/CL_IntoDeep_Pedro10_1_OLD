package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.w8wjb.ftc.AdafruitNeoDriver;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareLED {
    int red = Color.rgb(150, 0, 0);
    int green = Color.rgb(0, 150, 0);
    int blue = Color.rgb(0, 0, 150);
    int yellow = Color.rgb(150, 150, 0);

    public AdafruitNeoDriver neopixels;
    public DigitalChannel LEDrightGreen;
    public DigitalChannel LEDrightRed;
    public DigitalChannel LEDleftRed;
    public DigitalChannel LEDleftGreen;
    public DigitalChannel LEDright2Green;
    public DigitalChannel LEDright2Red;
    public DigitalChannel LEDleft2Red;
    public DigitalChannel LEDleft2Green;
    public int NUM_PIXELS = 30;

    public HardwareLED() {

    }
    public void init(HardwareMap hardwareMap) {

/*
        LEDleftGreen = hardwareMap.get(DigitalChannel.class, "leftgreen");               //connect to Digital port 0
        LEDleftRed = hardwareMap.get(DigitalChannel.class, "leftred");                   //connect to Digital port 1

        LEDleft2Green = hardwareMap.get(DigitalChannel.class, "left2green");               //connect to Digital port 2
        LEDleft2Red = hardwareMap.get(DigitalChannel.class, "left2red");                   //connect to Digital port 3

        LEDrightRed = hardwareMap.get(DigitalChannel.class, "rightgreen");             //connect to Digital port 4
        LEDrightGreen = hardwareMap.get(DigitalChannel.class, "rightred");             //connect to Digital port 5

        LEDright2Green = hardwareMap.get(DigitalChannel.class, "right2green");             //connect to Digital port 6
        LEDright2Red = hardwareMap.get(DigitalChannel.class, "right2red");                 //connect to Digital port 7
*/



        neopixels = hardwareMap.get(AdafruitNeoDriver.class,"neopixels");
        neopixels.setNumberOfPixels(NUM_PIXELS);
    }
    public void start() {

    }
    public void stop() {

    }

    public void LEDinitReady() {
/*
        LEDrightGreen.setMode(DigitalChannel.Mode.OUTPUT);
        LEDrightRed.setMode(DigitalChannel.Mode.INPUT);
        LEDrightGreen.setState(true);
        LEDrightRed.setState(false);

        LEDright2Green.setMode(DigitalChannel.Mode.OUTPUT);
        LEDright2Red.setMode(DigitalChannel.Mode.OUTPUT);
        LEDright2Green.setState(true);
        LEDright2Red.setState(false);

        LEDleft2Green.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleft2Red.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleft2Green.setState(true);
        LEDleft2Red.setState(false);

        LEDleftGreen.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleftGreen.setState(true);
        LEDleftRed.setState(false);


//        LEDleftGreen.setMode(DigitalChannel.Mode.INPUT);
//        LEDleftRed.setMode(DigitalChannel.Mode.INPUT);
//        LEDleftGreen.setState(false);
//        LEDleftRed.setState(false);
*/

    }
    public void LEDinitError() {
/*        LEDrightGreen.setMode(DigitalChannel.Mode.INPUT);
        LEDrightRed.setMode(DigitalChannel.Mode.OUTPUT);
        LEDrightGreen.setState(false);
        LEDrightRed.setState(true);

        LEDright2Green.setMode(DigitalChannel.Mode.OUTPUT);
        LEDright2Red.setMode(DigitalChannel.Mode.OUTPUT);
        LEDright2Green.setState(false);
        LEDright2Red.setState(true);

//        LEDleft2Green.setMode(DigitalChannel.Mode.INPUT);
//        LEDleft2Red.setMode(DigitalChannel.Mode.INPUT);
//        LEDleft2Green.setState(false);
//        LEDleft2Red.setState(false);

        LEDleft2Green.setMode(DigitalChannel.Mode.INPUT);
        LEDleft2Red.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleft2Green.setState(false);
        LEDleft2Red.setState(true);

        LEDleftGreen.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleftRed.setMode(DigitalChannel.Mode.OUTPUT);
        LEDleftGreen.setState(false);
        LEDleftRed.setState(true);*/
    }

    public void setYellow() {
        neopixels.fill(yellow);
        neopixels.show();
    }

    public void setRed() {
        neopixels.fill(red);
        neopixels.show();
    }
    public void setBlue() {
        neopixels.fill(blue);
        neopixels.show();
    }

    public void setNothing() {
        neopixels.fill(0);
        neopixels.show();
    }

}