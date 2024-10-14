package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
public class TestingMotor extends TestItem {
    private final double speed;
    private final DcMotor motor;
    public TestingMotor(String description, double speed, DcMotor motor) {
        super(description);
        this.speed = speed;
        this.motor = motor;
    }

    @Override
    public void run(boolean A_on, boolean Y_on, Telemetry telemetry) {
        if (A_on || Y_on) {
            motor.setPower(speed);
        } else {
            motor.setPower(0.0);
        }

        telemetry.addData("Motor Encoder = ", motor.getCurrentPosition());
    }
}