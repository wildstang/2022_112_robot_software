package org.wildstang.year2022.robot;

/**
 * CAN Constants are stored here.
 * We primarily use CAN to communicate with Talon motor controllers.
 * These constants must correlate with the IDs set in Phoenix Tuner.
 * Official documentation can be found here:
 * https://phoenix-documentation.readthedocs.io/en/latest/ch08_BringUpCAN.html
 */
public final class CANConstants {

    // Replace these examples.
    // While not independently dangerous if implemented these could have unintended effects.
    public static final int[] EXAMPLE_PAIRED_CONTROLLERS    = {1,2};
    public static final int   EXAMPLE_MOTOR_CONTROLLER      = 3;

    //tank drive constants
    public static final int LEFT_DRIVE = 11;
    public static final int RIGHT_DRIVE = 12;
    public static final int LEFT_DRIVE_FOLLOWER = 13;
    public static final int RIGHT_DRIVE_FOLLOWER = 14;
    
    public static final int INTAKE = 20;
    public static final int FEED = 30;
    public static final int KICKER = 22;
    public static final int SHOOTER = 23;
    public static final int SHOOTER_FOLLOWER = 24;
    public static final int HOOD = 25;

    public static final int CLIMBER = 28;
    public static final int CLIMBER_FOLLOWER = 29;

    public static final int BALL_GATE = 21;

    
}