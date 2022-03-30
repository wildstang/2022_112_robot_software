package org.wildstang.year2022.subsystems.drive;
import com.kauailabs.navx.frc.AHRS;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.pid.PIDConstants;
import org.wildstang.framework.subsystems.drive.PathFollowingDrive;
import org.wildstang.hardware.roborio.inputs.WsJoystickAxis;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.I2C;

public class Drive extends PathFollowingDrive {

    public enum DriveState{ TELEOP, AUTO, BASELOCK;}

    private WsSparkMax left, right;
    private WsJoystickAxis throttleJoystick, headingJoystick;
    private WsJoystickButton baseLock, gyroReset;
    private AnalogInput rightTrigger, leftTrigger;
    private DriveState state;

    private double heading;
    private double throttle;
    private double backThrottle;
    private DriveSignal signal;

    private WSDriveHelper helper = new WSDriveHelper();
    private final AHRS gyro = new AHRS(I2C.Port.kOnboard);

    private final double INVERT = -1.0;

    //private SlewRateLimiter limiter = new SlewRateLimiter(3);
    //private SlewRateLimiter turnLimiter = new SlewRateLimiter(3);

    @Override
    public void init() {
        left = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.LEFT_DRIVE);
        right = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.RIGHT_DRIVE);
        motorSetUp(left);
        motorSetUp(right);
        left.getController().setOpenLoopRampRate(2);
        right.getController().setOpenLoopRampRate(2);
        throttleJoystick = (WsJoystickAxis) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_JOYSTICK_Y);
        throttleJoystick.addInputListener(this);
        headingJoystick = (WsJoystickAxis) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_JOYSTICK_X);
        headingJoystick.addInputListener(this);
        baseLock = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_SHOULDER);
        baseLock.addInputListener(this);
        gyroReset = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_SELECT);
        gyroReset.addInputListener(this);
        rightTrigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_TRIGGER);
        rightTrigger.addInputListener(this);
        leftTrigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_LEFT_TRIGGER);
        leftTrigger.addInputListener(this);
        resetState();
    }

    @Override
    public void selfTest() {

    }

    @Override
    public void update() {
        if (state == DriveState.TELEOP){
            //if (Math.abs(rightTrigger.getValue()) < 0.15 && Math.abs(leftTrigger.getValue()) < 0.15) throttle = 0;
            if (Math.abs(throttle) < Math.abs(backThrottle)){
                signal = helper.teleopDrive(0.75*backThrottle, 0.5*heading);
            } else {
                signal = helper.teleopDrive(0.75*throttle, 0.5*heading);
            }
            drive(signal);
        } else if (state == DriveState.BASELOCK){
            left.setPosition(left.getPosition());
            right.setPosition(right.getPosition());
        } 
        SmartDashboard.putString("drive state",state.toString());
        SmartDashboard.putNumber("drive throttle", throttle);
        SmartDashboard.putNumber("drive back throttle", backThrottle);
    }

    @Override
    public void resetState() {
        state = DriveState.TELEOP;
        throttle = 0.0;
        backThrottle = 0;
        heading = 0.0;
        signal = new DriveSignal(0.0, 0.0);
        setBrakeMode(false);
        //gyro.reset();
    }

    @Override
    public String getName() {
        return "Drive";
    }

    @Override
    public void inputUpdate(Input source) {
        // heading = -headingJoystick.getValue();
        heading = -headingJoystick.getValue() * Math.pow(Math.abs(headingJoystick.getValue()), headingJoystick.getValue());
        // throttle = -throttleJoystick.getValue();
       // throttle = -throttleJoystick.getValue() * Math.abs(throttleJoystick.getValue());
       //throttle = -getTriggerThrottle();
       throttle = -Math.pow(Math.abs(rightTrigger.getValue()), 2);
       backThrottle = Math.pow(Math.abs(leftTrigger.getValue()), 2);
       
        if (baseLock.getValue()){
            state = DriveState.BASELOCK;
        } else {
            state = DriveState.TELEOP;
        }
        if (source == gyroReset && gyroReset.getValue()){
            gyro.reset();
            gyro.setAngleAdjustment(0.0);
        }

    }

    @Override
    public void setBrakeMode(boolean brake) {
        if (brake) {
            left.setBrake();
            right.setBrake();
        }
        else {
            left.setCoast();
            right.setCoast();
        }
    }

    @Override
    public void resetEncoders() {
        left.resetEncoder();
        right.resetEncoder();
    }
    
    @Override
    public void startPathFollower() {
        state = DriveState.AUTO;
    }
    
    @Override
    public void stopPathFollower() {
        state = DriveState.TELEOP;
        drive(new DriveSignal(0.0, 0.0));
    }

    @Override
    public void updatePathFollower(double[] trajectoryInfo) {
        signal = helper.autoDrive(trajectoryInfo[5], trajectoryInfo[13], trajectoryInfo[8], trajectoryInfo[16], 
            (left.getPosition()+right.getPosition())/2.0, gyro.getAngle());
        drive(signal);
    }

    public void drive(DriveSignal commandSignal){
        left.setSpeed(INVERT * commandSignal.leftMotor);
        right.setSpeed(commandSignal.rightMotor);
    }

    private void motorSetUp(WsSparkMax setupMotor){
        PIDConstants constants = DrivePID.BASE_LOCK.getConstants();
        setupMotor.initClosedLoop(constants.p, constants.i, constants.d, constants.f);
        setupMotor.setCurrentLimit(80, 20, 10000);
        setupMotor.enableVoltageCompensation();
    }

    public void setGyro(double degrees){
        gyro.reset();
        gyro.setAngleAdjustment(degrees);
    }

    private double getTriggerThrottle(){
        if (Math.abs(rightTrigger.getValue()) > 0.15){
            return Math.pow(Math.abs(rightTrigger.getValue()), 2);
        } else if (Math.abs(leftTrigger.getValue()) > 0.15) {
            return -Math.pow(Math.abs(leftTrigger.getValue()), 2);
        } else {
            return 0.0;
        }
    }

}
