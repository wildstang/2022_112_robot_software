
 package org.wildstang.year2022.subsystems.climb;

 import com.ctre.phoenix.motion.MotionProfileStatus;
 import com.kauailabs.navx.frc.AHRS;
 
 import org.wildstang.framework.core.Core;
 import com.revrobotics.CANSparkMax;
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
 import edu.wpi.first.wpilibj.I2C;

public class Climb implements Subsystem{
    
    private WsSparkMax motorRaise, motorRotate;
    private WsAnalogInput raise, rotate;
    private double raiseValue, rotateValue;

    @Override
    public void init(){

        motorRaise = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_LIFT);
        motorRotate = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_ROTATE);

        raise = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_LEFT_JOYSTICK_Y);
        raise.addInputListener(this);
        rotate = (WsAnalogInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_RIGHT_JOYSTICK_X);
        rotate.addInputListener(this);

        resetState();

    }

    @Override
    public void inputUpdate(Input source){

        if(Math.abs(raise.getValue()) > 0.05){

            raiseValue = raise.getValue();

        } 
        else{

            raiseValue = 0;

        }

        if(Math.abs(rotate.getValue()) > 0.05){

            rotateValue = rotate.getValue();

        }
        else{

            rotateValue = 0;

        }
        

    }

    @Override
    public void update(){

        motorRaise.setSpeed(raiseValue);
        motorRotate.setSpeed(rotateValue);

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
