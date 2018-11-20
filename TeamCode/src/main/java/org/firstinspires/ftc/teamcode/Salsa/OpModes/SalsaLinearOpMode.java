package org.firstinspires.ftc.teamcode.Salsa.OpModes;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Salsa.Constants;
import org.firstinspires.ftc.teamcode.Salsa.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Salsa.Vision.SamplingDetector;

/**
 * Created by adityamavalankar on 11/19/18.
 */

public abstract class SalsaLinearOpMode extends LinearOpMode {

    /**
     * This is a modified version of the {LinearOpMode} class, with all of the functions
     * meant for autonomous, without having extra work to make them, and the autonomous LinearOpMode
     * to both work
     */

    public Robot robot = new Robot();
    public Constants constants;
    private ElapsedTime runtime = new ElapsedTime();

    /**
     *
     * @param leftTarget
     * @param rightTarget
     */

    public void setTargetPosition(int leftTarget, int rightTarget) {
        robot.leftFront.setTargetPosition(robot.leftFront.getCurrentPosition() + leftTarget);
        robot.rightFront.setTargetPosition(robot.rightFront.getCurrentPosition() + rightTarget);
        robot.leftBack.setTargetPosition(robot.leftBack.getCurrentPosition() + leftTarget);
        robot.rightBack.setTargetPosition(robot.rightBack.getCurrentPosition() + rightTarget);
    }

    public void setPower(double power) {
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);

    }

    /**
     * We first convert the inputted distance (in cm) to encoder ticks with the constants.TICKS_PER_CM constant, while
     * setting motors to the runMode RUN_TO_POSITION.
     * Then, we see the expected time the motors should run at using the { timeSec() } function
     * Then, we set the target position, set motors to move at desired speed, until we hit the correct position
     * Then, we set speed to zero
     * @param left_cm
     * @param speed
     */

    public void encoderDriveCM(double left_cm, double right_cm, double speed) {

        int left_distanceEnc = (int)(constants.TICKS_PER_CM * left_cm);
        int right_distanceEnc = (int)(constants.TICKS_PER_CM * right_cm);

        setTargetPosition(left_distanceEnc, right_distanceEnc);
        setMotorRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(Math.abs(speed));
        runtime.reset();

        while(this.opModeIsActive() && robot.leftFront.isBusy() && robot.leftBack.isBusy() && robot.rightFront.isBusy() && robot.rightBack.isBusy()) {
            telemetry.addLine("Robot in Encoder Drive");
            telemetry.addData("Target Distance (cm)", left_cm);
            telemetry.update();
        }

        setPower(0);

        setMotorRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void encoderDriveIN(double left_in, double right_in, double speed) {

        int left_distanceEnc = (int)(constants.TICKS_PER_IN * left_in);
        int right_distanceEnc = (int)(constants.TICKS_PER_IN * right_in);

        setTargetPosition(left_distanceEnc, right_distanceEnc);
        setMotorRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(Math.abs(speed));
        runtime.reset();

        while(this.opModeIsActive() && robot.leftFront.isBusy() && robot.leftBack.isBusy() && robot.rightFront.isBusy() && robot.rightBack.isBusy()) {
            telemetry.addLine("Robot in Encoder Drive");
            telemetry.addData("Target Distance (in)", left_in);
            telemetry.update();
            //just one more test...
        }

        setPower(0);

        setMotorRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void setMotorRunMode(DcMotor.RunMode runmode) {
        robot.leftFront.setMode(runmode);
        robot.rightFront.setMode(runmode);
        robot.leftBack.setMode(runmode);
        robot.rightBack.setMode(runmode);
    }

    /**
     * The expected drive time is calculated with some math. We multiply speed by RPM of motor, and multiply that
     * by wheel circumference. Then, we divide the distance to be traveled by the total speed per minute.
     * Then, we will convert from minutes to seconds
     * @param cm
     * @param speed
     * @return The total distance that movement should take, multiplied by 1.5 for padding
     */

    public int calculateDriveTimeCM(double cm, double speed) {
        double abs_speed = Math.abs(speed * constants.NEVEREST_40_RPM);
        double abs_distCM = Math.abs(cm);

        double circ = constants.WHEEL_CIRCUMFERENCE_CM;
        double dist_perMin = (abs_speed * circ);
        double timeMin = (abs_distCM/dist_perMin);
        double timeSec = (timeMin*60);

        return (int)(timeSec*constants.ENC_DRIVE_TIME_MULTIPLIER);
    }


}
