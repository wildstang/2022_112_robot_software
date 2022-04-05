package org.wildstang.year2022.subsystems;
import org.wildstang.framework.core.Core;
import com.revrobotics.SparkMaxAnalogSensor.Mode;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.hardware.roborio.inputs.WsJoystickAxis;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.outputs.WsPhoenix;
import org.wildstang.hardware.roborio.outputs.WsSparkMax;
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import org.wildstang.year2022.robot.WSSubsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.wildstang.framework.subsystems.Subsystem;

public class hood implements Subsystem {

    WsSparkMax hood_Motor;
    WsJoystickAxis yAxis;
    DigitalInput leftBumper;
    DigitalInput rightBumper;
    DigitalInput aButton;

    private boolean isAiming;
    

    double position = 0;
    double p1 = 0.94;
    double p2 = 2.09;
    double p3 = 1.91;

    private final double REG_A = -0.0038393;
    private final double REG_B = 0.17336;
    private final double REG_C = 0.386;

    private AimHelper limelight;


    @Override
    public void init() {
        
        hood_Motor = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.HOOD_MOTOR);
        hood_Motor.initClosedLoop(1.0, 0.0, 0.0, 0.0);
        leftBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_SHOULDER); 
        leftBumper.addInputListener(this);
        rightBumper = (DigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_SHOULDER); 
        rightBumper.addInputListener(this);
        aButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);
        limelight = (AimHelper) Core.getSubsystemManager().getSubsystem(WSSubsystems.LIMELIGHT);
        resetState();
    }

    @Override
    public void update() {
        if (isAiming){
            double dist = limelight.getDistance();
            position = dist*dist*REG_A + dist*REG_B + REG_C;
        }
        setPosition(position);
        
        SmartDashboard.putNumber("hood MA3", hood_Motor.getController().getAnalog(Mode.kAbsolute).getVoltage());
        SmartDashboard.putNumber("hood target", position);
    }

    @Override
    public void inputUpdate(Input source) {
        // if  (source == yAxis){
        //     if (yAxis.getValue() >= .75){
        //         position  += position_change;
        //     }
        //     else if (yAxis.getValue() <= -0.75){
        //         position -= position_change;
        //     }
        // }
        if (source == leftBumper && leftBumper.getValue()){
            position = p1;
        }
        if (source == rightBumper && rightBumper.getValue()){
            position = p2;
        }
        if ((source == leftBumper || source == rightBumper) && (leftBumper.getValue() && rightBumper.getValue())){
            position = p3;
        }
        isAiming = aButton.getValue();
    } 

    @Override
    public void selfTest() {

    }

    @Override
    public void resetState() {
        position = getMA3();
        hood_Motor.resetEncoder();
        isAiming = false;
        
    }

    @Override
    public String getName() {
        return "hood";
    }
    private double getMA3(){
        return hood_Motor.getController().getAnalog(Mode.kAbsolute).getVoltage();
    }
    public void setPosition(double target){
        double pidSpeed = 0;
        if (target*.99 > getMA3() || target*1.01<getMA3()){
            pidSpeed = -1.4 * (target - getMA3());
            pidSpeed += Math.signum(pidSpeed) * 0.024;
        }
        if ((pidSpeed < 0 && getMA3()>2.39) || (pidSpeed > 0 && getMA3() < 0.94)) hood_Motor.setSpeed(0);
        else hood_Motor.setSpeed(pidSpeed);
    }
    public void limelightHood(boolean isOn){
        isAiming = isOn;
    }
}