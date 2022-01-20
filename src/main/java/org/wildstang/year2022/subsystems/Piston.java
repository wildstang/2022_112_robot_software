package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsSolenoid;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;

/**
 * Turns the intake motor on/off based on joystick button states
 * @author foxler2010
 */
public class Piston implements Subsystem {
    
    //the solenoid that is being controlled
    WsSolenoid solenoid;
    //b button
    WsJoystickButton trigger;
    

    @Override
    public void init() {
        //solenoid being controlled
        solenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.PISTON_SOLENOID);
        //b button
        trigger = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_RIGHT);
    }

    @Override
    public void update() {

    }

    @Override
    public void inputUpdate(Input source) {
        //if the b button was pressed
        if (source == trigger) {
            //set value to the opposite of what it was
            solenoid.setValue(!solenoid.getValue());
            //tell the solenoid to mirror what its value is
            solenoid.sendDataToOutput();
        }
    }

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {

    }

    @Override
    public String getName() {
        return "Piston";
    }
}