package org.wildstang.framework.auto.steps;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.subsystems.drive.PathFollowingDrive;

public class SetGyroStep extends AutoStep {

    private PathFollowingDrive m_drive;
    private double heading;

    /** sets the gyro value to be the given argument value
     * @param heading value you want the gyro to currently read
     * @param drive the Drive subsystem
     */
    public SetGyroStep(double heading, PathFollowingDrive drive) {
        this.heading = heading;
        m_drive = drive;
    }

    @Override
    public void initialize() {
        m_drive.setGyro(heading);
    }

    @Override
    public void update() {
        setFinished(true);
    }

    @Override
    public String toString() {
        return "Set Gyro Step";
    }

}
