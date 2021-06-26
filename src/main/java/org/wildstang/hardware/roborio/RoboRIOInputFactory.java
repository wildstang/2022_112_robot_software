package org.wildstang.hardware.roborio;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.wildstang.framework.core.Inputs;
import org.wildstang.framework.io.inputs.Input;
import org.wildstang.hardware.roborio.inputs.WSInputType;
import org.wildstang.hardware.roborio.inputs.WsAbsoluteEncoder;
import org.wildstang.hardware.roborio.inputs.WsAnalogGyro;
import org.wildstang.hardware.roborio.inputs.WsAnalogInput;
import org.wildstang.hardware.roborio.inputs.WsDPadButton;
import org.wildstang.hardware.roborio.inputs.WsDigitalInput;
import org.wildstang.hardware.roborio.inputs.WsHallEffectInput;
import org.wildstang.hardware.roborio.inputs.WsI2CInput;
import org.wildstang.hardware.roborio.inputs.WsJoystickAxis;
import org.wildstang.hardware.roborio.inputs.WsJoystickButton;
import org.wildstang.hardware.roborio.inputs.WsLidarSensor;
import org.wildstang.hardware.roborio.inputs.WsMotionProfileControl;
import org.wildstang.hardware.roborio.inputs.WsRemoteAnalogInput;
import org.wildstang.hardware.roborio.inputs.WsRemoteDigitalInput;
import org.wildstang.hardware.roborio.inputs.config.WsAbsoluteEncoderConfig;
import org.wildstang.hardware.roborio.inputs.config.WsAnalogGyroConfig;
import org.wildstang.hardware.roborio.inputs.config.WsAnalogInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsDigitalInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsI2CInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsJSButtonInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsJSJoystickInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsRemoteAnalogInputConfig;
import org.wildstang.hardware.roborio.inputs.config.WsRemoteDigitalInputConfig;

/**
 * Builds inputs from WsInputs enumerations.
 */
public class RoboRIOInputFactory {

    private static Logger s_log = Logger.getLogger(RoboRIOInputFactory.class.getName());
    private static final String s_className = "RoboRIOInputFactory";
    private boolean s_initialised = false;

    /**
     * Empty constructor override WPILib InputFactory constructor.
     */
    public RoboRIOInputFactory() {}

    /**
     * Prepares logger.
     */
    public void init() {
        if (s_log.isLoggable(Level.FINER)) {
            s_log.entering(s_className, "init");
        }

        if (!s_initialised) {
            s_initialised = true;
        }

        if (s_log.isLoggable(Level.FINER)) {
            s_log.exiting(s_className, "init");
        }
    }

    /**
     * Creates an Input from an enumeration of WsInputs.
     * @param p_input An enumeration of WsInputs.
     * @return A constructed Input.
     */
    public Input createInput(Inputs p_input) {
        if (s_log.isLoggable(Level.FINER)) {
            s_log.entering(s_className, "createAnalogInput");
        }

        Input in = null;

        if (s_log.isLoggable(Level.FINE)) {
            s_log.fine("Creating analog input: Name = " + p_input.getName() + ", type = "
                    + p_input.getType());
        }

        switch ((WSInputType) p_input.getType()) {
        case POT:
            in = new WsAnalogInput(p_input.getName(),
                    ((WsAnalogInputConfig) p_input.getConfig()).getChannel());
        break;
        case SWITCH:
            in = new WsDigitalInput(p_input.getName(),
                    ((WsDigitalInputConfig) p_input.getConfig()).getChannel(),
                    ((WsDigitalInputConfig) p_input.getConfig()).getPullup());
        break;
        case ABSOLUTE_ENCODER:
            in = new WsAbsoluteEncoder(p_input.getName(),
                    ((WsAbsoluteEncoderConfig) p_input.getConfig()).getChannel(),
                    ((WsAbsoluteEncoderConfig) p_input.getConfig()).getMaxVoltage());
        break;
        case HALL_EFFECT:
            in = new WsHallEffectInput(p_input.getName(),
                    ((WsI2CInputConfig) p_input.getConfig()).getPort(),
                    ((WsI2CInputConfig) p_input.getConfig()).getAddress());
        break;
        case LIDAR:
            // Port is the address, module is the port - such as I2C.kMXP
            in = new WsLidarSensor(p_input.getName(), ((WsI2CInputConfig) p_input.getConfig()).getPort(),
                    ((WsI2CInputConfig) p_input.getConfig()).getAddress());
        break;
        case JS_BUTTON:
            in = new WsJoystickButton(p_input.getName(),
                    ((WsJSButtonInputConfig) p_input.getConfig()).getPort(),
                    ((WsJSButtonInputConfig) p_input.getConfig()).getButton());
        break;
        case JS_JOYSTICK:
            in = new WsJoystickAxis(p_input.getName(),
                    ((WsJSJoystickInputConfig) p_input.getConfig()).getPort(),
                    ((WsJSJoystickInputConfig) p_input.getConfig()).getAxis());
        break;
        case JS_DPAD_BUTTON:
            in = new WsDPadButton(p_input.getName(),
                    ((WsJSButtonInputConfig) p_input.getConfig()).getPort(),
                    ((WsJSButtonInputConfig) p_input.getConfig()).getButton());
        break;
        case REMOTE_DIGITAL:
            in = new WsRemoteDigitalInput(p_input.getName(),
                    ((WsRemoteDigitalInputConfig) p_input.getConfig()).getTableName());
        break;
        case REMOTE_ANALOG:
            in = new WsRemoteAnalogInput(p_input.getName(),
                    ((WsRemoteAnalogInputConfig) p_input.getConfig()).getTableName());
        break;
        case I2C:
            in = new WsI2CInput(p_input.getName(),
                    ((WsI2CInputConfig) p_input.getConfig()).getPort(),
                    ((WsI2CInputConfig) p_input.getConfig()).getAddress());
        break;
        case ANALOG_GYRO:
            in = new WsAnalogGyro(p_input.getName(),
                    ((WsAnalogGyroConfig) p_input.getConfig()).getChannel(),
                    ((WsAnalogGyroConfig) p_input.getConfig()).getCompensate());
        break;
        case MOTION_PROFILE_CONTROL:
            in = new WsMotionProfileControl(p_input.getName());
        break;
        case NULL:
        default:
        break;
        }

        if (s_log.isLoggable(Level.FINER)) {
            s_log.exiting(s_className, "createAnalogInput");
        }

        return in;
    }

}