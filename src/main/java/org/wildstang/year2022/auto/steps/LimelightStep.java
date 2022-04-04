package org.wildstang.year2022.auto.steps;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.AimHelper;
import org.wildstang.year2022.subsystems.Launcher;
import org.wildstang.year2022.subsystems.hood;

public class LimelightStep extends AutoStep{

    private AimHelper limelight;
    private Launcher launcher;
    private hood robotHood;

    private boolean isOn;

    public LimelightStep(boolean turnOn){
        isOn = turnOn;
    }

    @Override
    public void initialize() {
        limelight = (AimHelper) Core.getSubsystemManager().getSubsystem(WSSubsystems.LIMELIGHT);
        launcher = (Launcher) Core.getSubsystemManager().getSubsystem(WSSubsystems.LAUNCHER);
        robotHood = (hood) Core.getSubsystemManager().getSubsystem(WSSubsystems.HOOD);
    }

    @Override
    public void update() {
        limelight.turnOnLED(isOn);
        launcher.setAiming(isOn);
        robotHood.limelightHood();
        setFinished();
    }

    @Override
    public String toString() {
        return "Limelight Step";
    }
    
}
