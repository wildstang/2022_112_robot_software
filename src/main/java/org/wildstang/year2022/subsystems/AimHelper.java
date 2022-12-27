package org.wildstang.year2022.subsystems; 
// ton of imports
import org.wildstang.framework.subsystems.Subsystem;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.kauailabs.navx.frc.AHRS;

import org.wildstang.framework.core.Core;
import com.revrobotics.CANSparkMax;

import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
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
import org.wildstang.year2022.robot.WSInputs;
import org.wildstang.year2022.robot.WSOutputs;
import org.wildstang.year2022.robot.WSSubsystems;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Arrays;

public class AimHelper implements Subsystem{
    
    private NetworkTable LimeTable;
    private NetworkTableEntry ty; //y angle
    private NetworkTableEntry tx; //x angle
    private NetworkTableEntry tv;
    private NetworkTableEntry ledModeEntry;
    private NetworkTableEntry llModeEntry;
    
    public double x;
    public double y;

    private double modifier;

    public boolean TargetInView;

    private double TargetDistance;

    private DigitalInput aButton;

    private final double TARGET_HEIGHT = 104;
    private final double CAMERA_HEIGHT = 34;
    private final double CAMERA_ANGLE = 44;


    public void calcTargetCoords(){ //update target coords. 
        if(tv.getDouble(0) == 1){
            x = tx.getDouble(0);
            y = ty.getDouble(0);
            TargetInView = true;
        }
        else{
            x = 0; //no target case
            y = 0;
            TargetInView = false;
        }
    }
    public double getDistance(){
        calcTargetCoords();
        TargetDistance = (modifier*12) + 32 + (TARGET_HEIGHT-CAMERA_HEIGHT) / Math.tan(Math.toRadians(ty.getDouble(0) + CAMERA_ANGLE));
        return TargetDistance/12;
    }
    
    public double getRotPID(){
        calcTargetCoords();
        return tx.getDouble(0) * -0.015;
    }

    public void turnOnLED(boolean onState){
        if (onState) {
            ledModeEntry.setNumber(0);//turn led on
            llModeEntry.setNumber(0);//turn camera to vision tracking
        } else {
            ledModeEntry.setNumber(0);//turn led off
            llModeEntry.setNumber(1);//turn camera to normal color mode
        }
    }
    @Override
    public void inputUpdate(Input source) {
        turnOnLED(aButton.getValue());
        
    }
    @Override
    public void init() {
        x = 0;  //x and y angular offsets from limelight. Only updated when calcTargetCoords is called.
        y = 0;
        TargetInView = false; //is the target in view? only updated when calcTargetCoords is called.
        TargetDistance = 0; //distance to target in feet. Only updated when calcTargetCoords is called.


        LimeTable  = NetworkTableInstance.getDefault().getTable("limelight");

        ty = LimeTable.getEntry("ty");
        tx = LimeTable.getEntry("tx");
        tv = LimeTable.getEntry("tv");
        ledModeEntry = LimeTable.getEntry("ledMode");
        llModeEntry = LimeTable.getEntry("camMode");

        modifier = 1;

        aButton = (DigitalInput) Core.getInputManager().getInput(WSInputs.DRIVER_FACE_DOWN);
        aButton.addInputListener(this);
        resetState();
        
    }
    @Override
    public void selfTest() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void update() {
        calcTargetCoords();
        SmartDashboard.putNumber("limelight distance", getDistance());    
        SmartDashboard.putNumber("limelight tx", tx.getDouble(0));
        SmartDashboard.putNumber("limelight ty", ty.getDouble(0));  
        SmartDashboard.putBoolean("limelight target in view", tv.getDouble(0)==1);  
    }

    @Override
    public void resetState() {
        turnOnLED(false);
        
    }
    @Override
    public String getName() {
        return "Limelight";
    }

    

}