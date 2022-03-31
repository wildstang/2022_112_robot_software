package org.wildstang.year2022.robot;

// expand this and edit if trouble with Ws
import org.wildstang.framework.core.Outputs;
import org.wildstang.framework.hardware.OutputConfig;
import org.wildstang.hardware.roborio.outputs.config.WsServoConfig;
import org.wildstang.hardware.roborio.outputs.config.WsPhoenixConfig;
import org.wildstang.hardware.roborio.outputs.config.WsI2COutputConfig;
import org.wildstang.hardware.roborio.outputs.config.WsMotorControllers;
import org.wildstang.hardware.roborio.outputs.config.WsDigitalOutputConfig;
import org.wildstang.hardware.roborio.outputs.config.WsSparkMaxConfig;
import org.wildstang.hardware.roborio.outputs.config.WsSparkMaxFollowerConfig;
import org.wildstang.hardware.roborio.outputs.config.WsSolenoidConfig;
import org.wildstang.hardware.roborio.outputs.config.WsDoubleSolenoidConfig;
import org.wildstang.hardware.roborio.outputs.WsDoubleSolenoidState;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * Output mappings are stored here.
 * Below each Motor, PWM, Digital Output, Solenoid, and Relay is enumerated with their appropriated IDs.
 * The enumeration includes a name, output type, and output config object.
 */
public enum WSOutputs implements Outputs {

    // ********************************
    // PWM Outputs
    // ********************************
    // ---------------------------------
    // Motors
    // ---------------------------------
    LEFT_DRIVE("Left Drive Motor", new WsSparkMaxConfig(CANConstants.LEFT_DRIVE, true)),
    RIGHT_DRIVE("Right Drive Motor", new WsSparkMaxConfig(CANConstants.RIGHT_DRIVE, true)),
    LEFT_DRIVE_FOLLOWER("Left Drive Follower", new WsSparkMaxFollowerConfig("Left Drive Motor", CANConstants.LEFT_DRIVE_FOLLOWER, true)),
    RIGHT_DRIVE_FOLLOWER("Right Drive Follower", new WsSparkMaxFollowerConfig("Right Drive Motor", CANConstants.RIGHT_DRIVE_FOLLOWER, true)),

    TEST_MOTOR("Test Motor", new WsPhoenixConfig(CANConstants.EXAMPLE_MOTOR_CONTROLLER, WsMotorControllers.VICTOR_SPX)),

    
    ARM_WHEEL("Intake Wheel Motor", new WsSparkMaxConfig(CANConstants.INTAKE, true)),
    FEED("Feed Motor", new WsSparkMaxConfig(CANConstants.FEED, true)),

    BALL_GATE("Ball Gate", new WsSparkMaxConfig(CANConstants.BALL_GATE, true)),
   
    LAUNCHER_MOTOR("Launcher Motor", new WsSparkMaxConfig(CANConstants.SHOOTER, true)),
    LAUNCHER_MOTOR_FOLLOWER("Launcher Motor Follower", new WsSparkMaxFollowerConfig("Launcher Motor", CANConstants.SHOOTER_FOLLOWER, true, true)),

    KICKER_MOTOR("Kicker Motor", new WsSparkMaxConfig(CANConstants.KICKER, true)),

    HOOD_MOTOR("Hood Motor", new WsSparkMaxConfig(CANConstants.HOOD, true)),


    CLIMB_LIFT("Climb Lift Motor", new WsSparkMaxConfig(CANConstants.CLIMBER, true)),
    CLIMB_LIFT_FOLLOWER("Climb Lift Follower", new WsSparkMaxFollowerConfig("Climb Lift Motor", CANConstants.CLIMBER_FOLLOWER, true, true)),
    CLIMB_ROTATE("Climb Rotate Motor", new WsSparkMaxConfig(CANConstants.CLIMBER_ROTATE, true)),
    CLIMB_ROTATE_FOLLOWER("Climb Rotate Follower", new WsSparkMaxFollowerConfig("Climb Rotate Motor", CANConstants.CLIMBER_ROTATE_FOLLOWER, true, true)),

    
    // ---------------------------------
    // Servos
    // ---------------------------------
    
    // ********************************
    // DIO Outputs
    // ********************************
    
    // ********************************
    // Solenoids
    // ********************************


    TEST_SOLENOID("Test Solenoid", new WsSolenoidConfig(PneumaticsModuleType.REVPH, 0, false)),


    INTAKE("Intake Arm Solenoid", new WsSolenoidConfig(PneumaticsModuleType.REVPH, 6, false)),
    INTAKE_FOLLOWER("Intake Arm Solenoid Follower", new WsSolenoidConfig(PneumaticsModuleType.REVPH, 7, false)),
    //CARGO_HATCH_SOLENOID("Cargo Hatch Solenoid", new WsSolenoidConfig(PneumaticsModuleType.REVPH, 1, false)),

    
    // ********************************
    // Relays
    // ********************************

    // ********************************
    // Others ...
    // ********************************

    ; // end of enum

    /**
     * Do not modify below code, provides template for enumerations.
     * We would like to have a super class for this structure, however,
     * Java does not support enums extending classes.
     */

    private String m_name;
    private OutputConfig m_config;

    /**
     * Initialize a new Output.
     * @param p_name Name, must match that in class to prevent errors.
     * @param p_config Corresponding configuration for OutputType.
     */
    WSOutputs(String p_name, OutputConfig p_config) {
        m_name = p_name;
        m_config = p_config;
    }

    /**
     * Returns the name mapped to the Output.
     * @return Name mapped to the Output.
     */
    public String getName() {
        return m_name;
    }

    /**
     * Returns the config of Output for the enumeration.
     * @return OutputConfig of enumeration.
     */
    public OutputConfig getConfig() {
        return m_config;
    }

}