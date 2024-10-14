package org.firstinspires.ftc.teamcode.Hardware;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.w8wjb.ftc.AdafruitNeoDriver;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import android.graphics.Color;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareLED {
    AdafruitNeoDriver neopixels;
    public HardwareLED() {

    }
    public void init(HardwareMap hardwareMap) {
        int NUM_PIXELS = 30;

        neopixels = hardwareMap.get(AdafruitNeoDriver.class,"neopixels");
        neopixels.setNumberOfPixels(NUM_PIXELS);
    }

    public void LEDinitReady() {
        neopixels.fill(Color.rgb(0, 255, 0));
    }
    public void LEDinitError() {
        neopixels.fill(Color.rgb(255, 0, 0));
    }
}