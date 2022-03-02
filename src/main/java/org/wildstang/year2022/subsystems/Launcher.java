package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
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
    WsSolenoid cargoHatchSolenoid;
    boolean solenoidActive;
    
    //Trigger
    AnalogInput trigger;

    @Override
    public void init() {
        launcherMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.LAUNCHER_MOTOR);
        kickerMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.KICKER_MOTOR);
        cargoHatchSolenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.CARGO_HATCH_SOLENOID);
        trigger = (AnalogInput) Core.getInputManager().getInput(WSInputs.DRIVER_RIGHT_TRIGGER);
        trigger.addInputListener(this);
    }

    @Override
    public void update() {
        
        //update outputs based on variable values
        cargoHatchSolenoid.setValue(solenoidActive);
        kickerMotor.setSpeed(kickerSpeed);
        launcherMotor.setSpeed(launcherSpeed);

    }
    
    @Override
    public void inputUpdate(Input source) {

        if (trigger.getValue() > 0.3) { //start this motor spinning before the ball is loaded

            launcherSpeed = 1;
        }
        if (trigger.getValue() > 0.5) {
    
            solenoidActive = true;

            kickerSpeed = 1;
    
        } else {
            
            solenoidActive = false;

            kickerSpeed = 0;

            launcherSpeed = 0;

        }
    }

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {

        kickerSpeed = 0;

        solenoidActive = false;

        launcherSpeed = 0;

    }

    @Override
    public String getName() {
        return "Launcher";
    }
}