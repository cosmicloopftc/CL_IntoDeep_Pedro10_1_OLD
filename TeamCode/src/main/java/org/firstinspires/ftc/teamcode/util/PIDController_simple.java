package org.firstinspires.ftc.teamcode.util;

//modified from FTC Thunderbolts (Sacramento, CA) mentor's program structure
//**PID Controller example starts at 1:01:00 time mark, JAVA code starts at 1:16:00 to 1:33:00

public class PIDController_simple {
    double Kp;
    double Ki;
    double Kd;
    double targetValue;
    double error = 0;
    double errorSum = 0;
    double errorDiff = 0;
    double errorPrevious = 0;

    /*Constructor*/
    public PIDController_simple(double targetVal, double Kp, double Ki, double Kd) {
        Kp = Kp;
        Ki = Ki;
        Kd = Kd;
        targetValue = targetVal;                                    //such as endDistance of 0
    }
    public double update(double input) {                            //such as startingDistance
        double output;                                              //such as power
        error = input - targetValue;
        errorSum +=error;
        errorDiff = error - errorPrevious;
        output = Kp*error + Ki*errorSum + Kd*errorDiff;
        errorPrevious = error;
        return output;                                              //such as power
    }
}
