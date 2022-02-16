package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import org.wildstang.hardware.roborio.outputs.WsSolenoid;

/**
 * Sample Subsystem that controls a motor with a joystick.
 * @author Liam
 */
public class Everybot implements Subsystem {

    WsSparkMax roller;
    WsJoystickButton aButton;
    WsJoystickButton yButton;
    WsJoystickButton bButton;
    //These will work, but using DigitalInput would be preferred
    WsSolenoid solenoid;

    @Override
    public void init() {
        roller = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.ROLLER_MOTOR);
        solenoid = (WsSolenoid) Core.getOutputManager().getOutput(WSOutputs.EVERYBOT_SOLENOID);
        aButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);
        yButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_RIGHT);
        //pretty sure you meant DRIVER_FACE_UP. Be careful, these are the mistakes that are very
        //annoying to fix later if you don't catch them (spoken from experience)
        yButton.addInputListener(this);
        bButton = (WsJoystickButton) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_RIGHT);
        bButton.addInputListener(this);
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void inputUpdate(Input source) {

        if (source == aButton && aButton.getValue()) {

            roller.setSpeed(1);

            solenoid.setValue(true);

        } else if (source == aButton && aButton.getValue() == false || source == yButton && yButton.getValue() == false) {

            roller.setSpeed(0);

        } else if (source == yButton && yButton.getValue()) {

            roller.setSpeed(-1);

        } else if (source == bButton && bButton.getValue()) {

            solenoid.setValue(!solenoid.getValue());

        }

    }

    
    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {
        //I would've called roller.setSpeed(0) here to make sure that the roller resets to 0
        //if the robot resets, as well as put the solenoid to true
        //Great job! Go ahead and take a crack at coding one of the robot subsystems now, the 
        //hood currently doesn't have anyone working on it to my knowledge
        //https://github.com/wildstang/2022_112_robot_software/issues/6
    }

    @Override
    public String getName() {
        return "Everybot";
    }
}