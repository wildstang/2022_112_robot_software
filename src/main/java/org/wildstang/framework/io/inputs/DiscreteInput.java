package org.wildstang.framework.io.inputs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * First abstraction of Input representing "discrete" Inputs
 * such as hall effect and LIDAR sensors.
 */
public abstract class DiscreteInput extends Input {

    private static Logger s_log = Logger.getLogger(DiscreteInput.class.getName());
    private static final String s_className = "DiscreteInput";

    private int m_currentValue = 0;

    /**
     * Constructor simply passes on name.
     * @param p_name Name of the Input.
     */
    public DiscreteInput(String p_name) {
        super(p_name);
    }

    /**
     * Constructor with default value.
     * @param p_name Name of the Input.
     * @param p_default Default value of the Input.
     */
    public DiscreteInput(String p_name, int p_default) {
        super(p_name);
        m_currentValue = p_default;
    }

    /**
     * Processes raw value read from Input's hardware.
     */
    @Override
    public void readDataFromInput() {
        if (s_log.isLoggable(Level.FINER)) {
            s_log.entering(s_className, "readDataFromInput");
        }

        int newValue = readRawValue();

        setNewValue(newValue);

        if (s_log.isLoggable(Level.FINER)) {
            s_log.exiting(s_className, "readDataFromInput");
        }
    }

    /**
     * Takes a new value stores it and notifys listeners.
     * This is a public version of setNewValue() for manual value updating.
     * @param p_newValue New value read for the Input.
     */
    public void setValue(int p_newValue) {
        if (s_log.isLoggable(Level.FINER)) {
            s_log.entering(s_className, "setValue");
        }

        setNewValue(p_newValue);

        logCurrentState();

        notifyListeners();

        if (s_log.isLoggable(Level.FINER)) {
            s_log.exiting(s_className, "setValue");
        }
    }

    /**
     * Takes an ingested value, store it, and marks the value changed flag if it has.
     * @param p_newValue New value to store.
     */
    private void setNewValue(int p_newValue) {
        // Only update if the value has changed
        if (s_log.isLoggable(Level.FINEST)) {
            s_log.finest("Current value = " + m_currentValue + " : New value = " + p_newValue);
        }
        if (p_newValue != m_currentValue) {
            m_currentValue = p_newValue;
            setValueChanged(true);
        } else {
            setValueChanged(false);
        }
    }

    /**
     * Abstract function to request and return the raw value from hardware.
     * @return The latest value read from hardware.
     */
    protected abstract int readRawValue();

    /**
     * Returns the latest stored value from the Input.
     * @return Latest value store in the Input.
     */
    public int getValue() {
        return m_currentValue;
    }

    /**
     * Logs the Input's state to the StateTracker.
     */
    @Override
    protected void logCurrentStateInternal() {
        if (s_log.isLoggable(Level.FINER)) {
            s_log.entering(s_className, "logCurrentState");
        }

        getStateTracker().addState(getName(), getName(), getValue());

        if (s_log.isLoggable(Level.FINER)) {
            s_log.exiting(s_className, "logCurrentState");
        }
    }

}
