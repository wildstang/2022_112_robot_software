package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import org.wildstang.year2022.robot.WSSubsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.wildstang.hardware.roborio.outputs.WsSolenoid;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;

/**
 * Loads cargo and shoots it when the right trigger is pressed
 * @author foxler2010
 */
public class Launcher implements Subsystem {

    //Launcher Motor
    WsSparkMax launcherMotor;
    double launcherSpeed;

    //Kicker Motor
    WsSparkMax kickerMotor;
    double kickerSpeed;

    //Cargo Hatch Solenoid
    //WsSolenoid cargoHatchSolenoid;
    boolean solenoidActive;
    private boolean aiming;

    private double preset;
    
    //Trigger
    AnalogInput trigger, readyTrigger;
    private DigitalInput aButton, leftBumper, rightBumper;

    //private SimpleWidget modifier;
    //private ShuffleboardTab tab;

    private final double REG_A = 0.000580;
    private final double REG_B = -0.0008214 + 0.08/16;
    private final double REG_C = 0.386 - 0.02;

    private AimHelper limelight;


    @Override
    public void init() {
        launcherMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.LAUNCHER_MOTOR);
        launcherMotor.setCurrentLimit(50, 50, 0);
        kickerMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.KICKER_MOTOR);
        kickerMotor.setCurrentLimit(25, 25, 0);
        //cargoHatchSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.CARGO_HATCH_SOLENOID);
        trigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_TRIGGER);
        trigger.addInputListener(this);
        readyTrigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_TRIGGER);
        readyTrigger.addInputListener(this);
        aButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);
        leftBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_SHOULDER);
        leftBumper.addInputListener(this);
        rightBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_SHOULDER);
        rightBumper.addInputListener(this);
        limelight = (AimHelper) Core.getSubsystemManager().getSubsystem(WSSubsystems.LIMELIGHT);

        //tab = Shuffleboard.getTab("Tab");

        //modifier = tab.add("Flywheel Power", 0.4);
    }

    @Override
    public void update() {
        
        //update outputs based on variable values
        //cargoHatchSolenoid.setValue(solenoidActive);
        if (aiming){

            double dist = limelight.getDistance();
            launcherSpeed = dist*dist*REG_A + dist*REG_B + REG_C;
            kickerSpeed = 1;

        } 
        kickerMotor.setSpeed(kickerSpeed);
        launcherMotor.setSpeed(-launcherSpeed);

        SmartDashboard.putNumber("flywheel preset", preset);
        SmartDashboard.putNumber("flywheel speed", launcherSpeed);

    }
    
    @Override
    public void inputUpdate(Input source) {

        if (leftBumper.getValue() && rightBumper.getValue()){
            preset = 0.45;
        } else if (source == leftBumper && leftBumper.getValue() && !rightBumper.getValue()){
            preset = 0.4;
        } else if (source == rightBumper && rightBumper.getValue() && !leftBumper.getValue()){
            preset = 0.55;
        }

        if (Math.abs(readyTrigger.getValue()) > 0.3) { //start this motor spinning before the ball is loaded

            //launcherSpeed = modifier.getEntry().getDouble(0);
            launcherSpeed = preset;

            kickerSpeed = 1;

        } else {

            launcherSpeed = 0;

            kickerSpeed = 0;

        }
        aiming = aButton.getValue();
        // if (Math.abs(trigger.getValue()) > 0.5) {
    
        //     solenoidActive = true;
    
        // } else {
            
        //     solenoidActive = false;

        // }
    }

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {

        kickerSpeed = 0;

        //solenoidActive = false;

        launcherSpeed = 0;

        preset = 0.4;

    }

    public void launcherOn(){

        launcherSpeed = 0.4;
        
    }

    @Override
    public String getName() {
        return "Launcher";
    }
    public void setAiming(boolean isAiming){
        aiming = isAiming;
    }
}