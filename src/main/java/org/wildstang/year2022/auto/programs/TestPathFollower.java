package org.wildstang.year2022.auto.programs;

import org.wildstang.framework.auto.AutoProgram;
import org.wildstang.framework.auto.steps.PathFollowerStep;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.subsystems.drive.PathFollowingDrive;
import org.wildstang.sample.robot.WSSubsystems;

import frc.paths.Test;

public class TestPathFollower extends AutoProgram{
    
    public void defineSteps(){
        PathFollowingDrive mydrive = (PathFollowingDrive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE);
        addStep(new PathFollowerStep( new Test().getPath(), mydrive));
    }

    public String toString(){
        return "Test Path Follower";
    }
}
