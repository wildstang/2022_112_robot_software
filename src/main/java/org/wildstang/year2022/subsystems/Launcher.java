package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

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
    
    //Trigger
    AnalogInput trigger, readyTrigger;

    private SimpleWidget modifier;
    private ShuffleboardTab tab;


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

        tab = Shuffleboard.getTab("Tab");

        modifier = tab.add("Flywheel Power", 0.4);
    }

    @Override
    public void update() {
        
        //update outputs based on variable values
        //cargoHatchSolenoid.setValue(solenoidActive);
        kickerMotor.setSpeed(kickerSpeed);
        launcherMotor.setSpeed(-launcherSpeed);

    }
    
    @Override
    public void inputUpdate(Input source) {

        if (Math.abs(readyTrigger.getValue()) > 0.3) { //start this motor spinning before the ball is loaded

            launcherSpeed = modifier.getEntry().getDouble(0);

            kickerSpeed = 1;

        } else {

            launcherSpeed = 0;

            kickerSpeed = 0;

        }
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

    }

    @Override
    public String getName() {
        return "Launcher";
    }
}