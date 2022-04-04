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

    public AutoDriveStep(double timeInSec){
        this.time = timeInSec;
    }
    public void initialize(){
        timer = new Timer();
        timer.start();
        drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE);
    }
    public void update(){
        drive.setAutoDrive(0.3);
        if (timer.hasElapsed(time)){
            drive.setAutoDrive(0);
            setFinished();
        }
    }
    public String toString(){
        return "Auto Drive Step";
    }
    
}
