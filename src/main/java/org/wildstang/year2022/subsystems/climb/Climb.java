
 package org.wildstang.year2022.subsystems.climb;
 
 import org.wildstang.framework.core.Core;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.Input;
 import org.wildstang.hardware.roborio.outputs.WsSparkMax;
 import org.wildstang.year2022.robot.WSInputs;
 import org.wildstang.year2022.robot.WSOutputs;
 import org.wildstang.framework.subsystems.Subsystem;

 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb implements Subsystem{
    
    private WsSparkMax motorRaise;
    private AnalogInput raise;
    private double raiseValue;

    private final double RAISE_SPEED = -1.0;
    private final double ROTATE_SPEED = -0.5;

    @Override
    public void init(){

        motorRaise = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_LIFT);

        motorRaise.setCurrentLimit(50, 50, 0);

        raise = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_JOYSTICK_Y);
        raise.addInputListener(this);

        resetState();

    }

    @Override
    public void inputUpdate(Input source){

        if(Math.abs(raise.getValue()) > 0.2){

            raiseValue = RAISE_SPEED * raise.getValue();

        } 
        else{

            raiseValue = 0;

        }
        

    }

    @Override
    public void update(){

        motorRaise.setSpeed(raiseValue);

        SmartDashboard.putNumber("Climb raise enc", motorRaise.getPosition());

    }

@Override
public void selfTest(){

}

@Override
public void resetState() {

    raiseValue = 0;

}

@Override
public String getName(){
    return "Climb";
}

}
