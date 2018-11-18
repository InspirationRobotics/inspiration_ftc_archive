package org.firstinspires.ftc.teamcode.Salsa.Hardware;

import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Salsa.Constants;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.Salsa.Vision.CameraCropAngle;
import org.firstinspires.ftc.teamcode.Salsa.Vision.SamplingDetector;

/**
 * Created by adityamavalankar on 11/4/18.
 */

public class Robot {


    /** The Robot class is a class meant to combine all of the major hardware and HardwareMap() of all of the functions
     *  in one file. Hence, later we will not have to modify any of the hardware, as we have made all of the hardware
     *  for the pre-configured hardware. Please refer to the Google Docs file at {https://goo.gl/yfN7XZ} to see al of the
     *  hardware and its configuration on the robot.
     */

    HardwareMap hwmap = null;

    public Constants constants = null;

    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public BNO055IMU imu = null;
    public ColorSensor leftLine = null;
    public ColorSensor rightLine = null;
    public WebcamName webcamFront = null;
    public Orientation angles;
    public Servo leftMineral = null;
    public Servo rightMineral = null;
    public CRServo mineralFeeder = null;
    public Servo depositerRotate = null;
    public Servo depositerDump = null;
    public DistanceSensor groundDistance = null;

    public DcMotor mineralShooter = null;
    public DcMotor craterSlides = null;
    public DcMotor intakeMotor = null;
    public DcMotor liftSlides = null;

    public Servo intakeLifter = null;
    public Servo markerDepositer = null;

    public SamplingDetector samplingDetector = new SamplingDetector();


    /**
     * Each of these init functions for the hardware is meant to be able to modularize the initiation of the hardware
     * This is so that if we want just a drivetrain, we get just a drivetrain
     * @param ahwmap
     *
     * The ahwmap object is so that we can have an OpMode-specific function without it being an OpMode
     */

    public void initDrivetrain(HardwareMap ahwmap) {

        hwmap = ahwmap;

        //Drivetrain
        leftFront = hwmap.dcMotor.get(constants.LEFT_FRONT_NAME);
        leftBack = hwmap.dcMotor.get(constants.LEFT_BACK_NAME);
        rightFront = hwmap.dcMotor.get(constants.RIGHT_FRONT_NAME);
        rightBack = hwmap.dcMotor.get(constants.RIGHT_BACK_NAME);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void initMotors(HardwareMap ahwmap) {

        hwmap = ahwmap;

        mineralShooter = hwmap.dcMotor.get(constants.MINERAL_SHOOTER_NAME);
        craterSlides = hwmap.dcMotor.get(constants.CRATER_SLIDES_NAME);
        intakeMotor = hwmap.dcMotor.get(constants.INTAKE_MOTOR_NAME);
        liftSlides = hwmap.dcMotor.get(constants.LIFT_SLIDES_NAME);

    }

    public void initSensors(HardwareMap ahwmap) {

        hwmap = ahwmap;

        //Sensors
        imu = hwmap.get(BNO055IMU.class, constants.GYRO_NAME);
        leftLine = hwmap.get(ColorSensor.class, constants.LEFT_COLOR_NAME);
        rightLine = hwmap.get(ColorSensor.class, constants.RIGHT_COLOR_NAME);
        groundDistance = hwmap.get(DistanceSensor.class, constants.GROUND_DISTANCE_SENSOR_NAME);

        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public void initWebcam(HardwareMap ahwmap) {

        hwmap = ahwmap;

        //Webcam
        webcamFront = hwmap.get(WebcamName.class, constants.WEBCAM_FRONT_NAME);

    }

    public void initServos(HardwareMap ahwmap) {

        hwmap = ahwmap;

        //Servos
        intakeLifter = hwmap.servo.get(constants.INTAKE_LIFTER_NAME);
        markerDepositer = hwmap.servo.get(constants.MARKER_DEPOSITER_NAME);
        leftMineral = hwmap.servo.get(constants.LEFT_MINERAL_NAME);
        rightMineral = hwmap.servo.get(constants.RIGHT_MINERAL_NAME);
        mineralFeeder = hwmap.crservo.get(constants.MINERAL_FEEDER_NAME);
        depositerRotate = hwmap.servo.get(constants.DEPOSITER_ROTATE_NAME);
        depositerDump = hwmap.servo.get(constants.DEPOSITER_DUMP_NAME);

    }

    /**
     * The next six functions are meant for the Computer Vision sampling of the three objects
     * First, we init, then enable, then take the input, then disable
     * @param ahwmap
     */

    public void initSampling(HardwareMap ahwmap) {

        samplingDetector.initVision(ahwmap, constants.CAMERA_AIM_DIRECTION);

    }

    /**
     * By default, we will be looking for just the right two, but in case we want to be able select
     * which crop view we want, we can use this function.
     * @param ahwmap
     * @param cropAngle
     */

    public void initSampling(HardwareMap ahwmap, CameraCropAngle cropAngle) {

        samplingDetector.initVision(ahwmap, cropAngle);

    }

    /**
     * Here, we enable the vision, which means we actively begin to track and capture data
     */
    public void enableVision() {
        samplingDetector.samplingDetector.enable();
    }

    public SamplingOrderDetector.GoldLocation getSamplingOrder() {

        SamplingOrderDetector.GoldLocation order;
        order = samplingDetector.samplingDetector.getCurrentOrder();

        return order;
    }

    public SamplingOrderDetector.GoldLocation getLastOrder() {

        SamplingOrderDetector.GoldLocation lastOrder;
        lastOrder = samplingDetector.samplingDetector.getLastOrder();

        return lastOrder;
    }

    public void disableVision() {
        samplingDetector.samplingDetector.disable();
    }


    public void initAll(HardwareMap ahwmap) {

        initDrivetrain(ahwmap);
        initSensors(ahwmap);
        initWebcam(ahwmap);
        initServos(ahwmap);
        initMotors(ahwmap);
        initSampling(ahwmap);
    }

    public void sleep(int ms) {
        int startTime = (int)System.currentTimeMillis();
        while (startTime + ms > (int)System.currentTimeMillis()) {
            //do nothing, as we are in break :)
        }
    }
}
