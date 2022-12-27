package org.wildstang.year2022.auto.steps;

import org.wildstang.framework.auto.AutoStep;
import org.wildstang.framework.core.Core;
import org.wildstang.year2022.robot.WSSubsystems;
import org.wildstang.year2022.subsystems.drive.Drive;

import edu.wpi.first.wpilibj.Timer;

public class AutoRotateStep extends AutoStep {

    private Drive drive;
    private double time;
    private Timer timer;
    private boolean direction;

    public AutoRotateStep(double timeInSec, boolean directionOfRot){
        this.time = timeInSec;
        direction = directionOfRot;
    }
    public void initialize(){
        timer = new Timer();
        timer.start();
        drive = (Drive) Core.getSubsystemManager().getSubsystem(WSSubsystems.DRIVE);
    }
    public void update(){
        if (direction) drive.setAutoRotate(0.1);
        else drive.setAutoRotate(-0.1);
        if (timer.hasElapsed(time)){
            drive.setAutoDrive(0);
            setFinished();
        }
    }
    public String toString(){
        return "Auto Drive Step";
    }
    
}
