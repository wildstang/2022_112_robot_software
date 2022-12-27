package org.wildstang.year2022.auto.steps;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.drive.Drive;

import edu.wpi.first.wpilibj.Timer;

public class AutoDriveStep extends AutoStep {

    private Drive drive;
    private double time;
    private Timer timer;
    private boolean directionOfMotion;

    public AutoDriveStep(double timeInSec, boolean direction){
        this.time = timeInSec;
        directionOfMotion = direction;
    }
    public void initialize(){
        timer = new Timer();
        timer.start();
        drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE);
        drive.startPathFollower();
    }
    public void update(){
        if (directionOfMotion) drive.setAutoDrive(0.3);
        else drive.setAutoDrive(-0.3);
        if (timer.hasElapsed(time)){
            drive.setAutoDrive(0);
            setFinished();
            drive.resetState();
        }
    }
    public String toString(){
        return "Auto Drive Step";
    }
    
}
