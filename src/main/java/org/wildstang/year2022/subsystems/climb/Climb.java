
 package org.wildstang.year2022.subsystems.climb;

 import com.ctre.phoenix.motion.MotionProfileStatus;
 import com.kauailabs.navx.frc.AHRS;
 
 import org.wildstang.framework.core.Core;
 import com.revrobotics.CANSparkMax;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.Input;
 import org.wildstang.framework.logger.Log;
 import org.wildstang.framework.pid.PIDConstants;
 import org.wildstang.framework.subsystems.drive.Path;
 import org.wildstang.framework.subsystems.drive.PathFollowingDrive;
 import org.wildstang.framework.subsystems.drive.TankPath;
 import org.wildstang.hardware.roborio.inputs.WsAnalogInput;
 import org.wildstang.hardware.roborio.inputs.WsDigitalInput;
 import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
 import org.wildstang.hardware.roborio.outputs.WsPhoenix;
 import org.wildstang.hardware.roborio.outputs.WsSparkMax;
 import org.wildstang.hardware.roborio.outputs.WsSolenoid;
 import org.wildstang.hardware.roborio.outputs.WsDoubleSolenoidState;
 import org.wildstang.hardware.roborio.outputs.WsDoubleSolenoid;
 import org.wildstang.year2022.robot.WSInputs;
 import org.wildstang.year2022.robot.WSOutputs;
 import org.wildstang.framework.subsystems.Subsystem;

 
 import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;

public class Climb implements Subsystem{
    
    private WsSparkMax motorRaise, motorRotate;
    private AnalogInput raise, rotate;
    private double raiseValue, rotateValue;

    private final double RAISE_SPEED = -1.0;
    private final double ROTATE_SPEED = -0.5;

    @Override
    public void init(){

        motorRaise = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_LIFT);
        motorRotate = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_ROTATE);

        motorRaise.setCurrentLimit(50, 50, 0);
        motorRotate.setCurrentLimit(50, 50, 0);

        raise = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_JOYSTICK_Y);
        raise.addInputListener(this);
        rotate = (AnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_JOYSTICK_X);
        rotate.addInputListener(this);

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

        if(Math.abs(rotate.getValue()) > 0.2){

            rotateValue = ROTATE_SPEED * rotate.getValue();

        }
        else{

            rotateValue = 0;

        }
        

    }

    @Override
    public void update(){

        motorRaise.setSpeed(raiseValue);
        motorRotate.setSpeed(rotateValue);

        SmartDashboard.putNumber("Climb raise enc", motorRaise.getPosition());
        SmartDashboard.putNumber("Climb rotate enc", motorRotate.getPosition());

    }

@Override
public void selfTest(){

}

@Override
public void resetState() {

    raiseValue = 0;
    rotateValue = 0;

}

@Override
public String getName(){
    return "Climb";
}

}
