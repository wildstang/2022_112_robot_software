package org.wildstang.year2022.subsystems;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.framework.subsystems.Subsystem;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSOutputs;

import org.wildstang.year2022.robot.WSInputs;

public class Tester implements Subsystem {

    private DigitalInput leftBumper, rightBumper;
    private AnalogInput leftStickY, rightStickY;
    
    private WsSparkMax hoodMotor, climbRotateMotor, climbLiftMotor;
    
    private double hoodSpeed, climbRotateSpeed, climbLiftSpeed;
    
    private final double hoodMaxSpeed = 0.1;
    private final double climbMaxSpeed = 0.5;


    @Override
    public void inputUpdate(Input source) {
        if (rightBumper.getValue()) {
            hoodSpeed = hoodMaxSpeed;
        } else if (leftBumper.getValue()) {
            hoodSpeed = -hoodMaxSpeed;
        } else {
            hoodSpeed = 0;
        }

        if (Math.abs(rightStickY.getValue()) > 0.2) {
            climbRotateSpeed = climbMaxSpeed * rightStickY.getValue();
        } else {
            climbRotateSpeed = 0;
        }
        if (Math.abs(leftStickY.getValue()) > 0.2) {
            climbLiftSpeed = leftStickY.getValue();//probably want this to be able to go more than 0.2 to lift the robot
        } else {
            climbLiftSpeed = 0;
        }
    }

    @Override
    public void init() {
        leftBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_SHOULDER);
        leftBumper.addInputListener(this);
        rightBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_SHOULDER);
        rightBumper.addInputListener(this);
        leftStickY = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_JOYSTICK_Y);
        leftStickY.addInputListener(this);
        rightStickY = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_JOYSTICK_Y);
        rightStickY.addInputListener(this);

        climbLiftMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_LIFT);
        climbRotateMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_ROTATE);
        hoodMotor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.HOOD_MOTOR);
        
        hoodMotor.setCurrentLimit(15, 15, 0);

        resetState();
    }

    @Override
    public void selfTest() {
    }

    @Override
    public void update() {
        hoodMotor.setSpeed(hoodSpeed);

        //climbRotateMotor.setSpeed(climbRotateSpeed);
        //climbLiftMotor.setSpeed(climbLiftSpeed);    
    }

    @Override
    public void resetState() {
        hoodSpeed = 0;
        climbRotateSpeed = 0;
        climbLiftSpeed = 0;
    }

    @Override
    public String getName() {
        return "Tester";
    }
}