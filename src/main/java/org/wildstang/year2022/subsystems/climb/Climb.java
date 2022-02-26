
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
    
    private WsSparkMax ArmA, ArmB;
    private WsDoubleSolenoid SolenoidA, SolenoidB;
    private WsDigitalInput Start, Select, D1, D2, D3, D4;
    private enum armStates{
        extend, retract, off;
    }
    private armStates armState;
    private boolean solenoidDeploy, climbDeploy;
    private int armSpeed;

    @Override
    public void init(){

        ArmA = (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_ARM_ONE);
        ArmB= (WsSparkMax) Core.getOutputManager().getOutput(WSOutputs.CLIMB_ARM_TWO);

        SolenoidA = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.CLIMB_SOLENOID_A);
        SolenoidB = (WsDoubleSolenoid) Core.getOutputManager().getOutput(WSOutputs.CLIMB_SOLENOID_B);
        
        Start = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_START);
        Start.addInputListener(this);
        Select = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_SELECT);
        Select.addInputListener(this);

        D1 = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_DPAD_UP);
        D1.addInputListener(this);
        D2 = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_DPAD_RIGHT);
        D2.addInputListener(this);
        D3 = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_DPAD_DOWN);
        D3.addInputListener(this);
        D4 = (WsDigitalInput) Core.getInputManager().getInput(WSInputs.MANIPULATOR_DPAD_LEFT);
        D4.addInputListener(this);
        
        resetState();

    }

    @Override
    public void inputUpdate(Input source){

        if(Start.getValue() && Select.getValue()){

            climbDeploy = true;
            armState = armStates.extend;

        }

        if(D1.getValue() && climbDeploy == true){

            armState = armStates.extend;

        }
        if(D3.getValue() && climbDeploy == true){

            armState = armStates.retract;

        }

        if(D2.getValue() && climbDeploy == true){

            solenoidDeploy = true;
 
        }
        if(D4.getValue() && climbDeploy == true){

            solenoidDeploy = false;
 
        }

        if(armState == armStates.extend){
            armSpeed = 1;
        }
        if(armState == armStates.retract){
            armSpeed = -1;
        }
        if(armState == armStates.off){
            armSpeed = 0;
        }

    }

    @Override
    public void update(){

    ArmA.setSpeed(armSpeed);
    ArmB.setSpeed(armSpeed);

    if(solenoidDeploy = true){
    SolenoidA.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
    SolenoidB.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
    }
    else{
    SolenoidA.setValue(WsDoubleSolenoidState.FORWARD.ordinal());
    SolenoidB.setValue(WsDoubleSolenoidState.REVERSE.ordinal());
    }

}

@Override
public void selfTest(){

}

@Override
public void resetState() {

    armSpeed = 0;
    armState = armStates.off;
    solenoidDeploy = false;
    climbDeploy = false;

}

@Override
public String getName(){
    return "Climb";
}

}
