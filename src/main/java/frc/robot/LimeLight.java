package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Wrapper around the Limelight's network tables
 */
public class LimeLight {
    private static LimeLight sInstance = new LimeLight();
    private NetworkTable mTable;

    public LimeLight() {
        mTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public static LimeLight getInstance() {
        return sInstance;
    }

    /**
     * @return Whether the limelight has any valid targets (0 or 1)
     */
    public boolean isTargetFound() {
        return mTable.getEntry("tv").getDouble(0.0) != 0.0;
    }

    /**
     * @return Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
     */
    public double getYawToTarget() {
        return mTable.getEntry("tx").getDouble(0.0);
    }

    /**
     * @return Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
     */
    public double getPitchToTarget() {
        NetworkTableEntry ty = mTable.getEntry("ty");
        return ty.getDouble(0.0);
    }

    /**
     * @return Target Area (0% of image to 100% of image)
     */
    public double getTargetArea() {
        return mTable.getEntry("ta").getDouble(0.0);
    }

    /**
     * @return Skew or rotation (-90 degrees to 0 degrees)
     */
    public double getSkew() {
        return mTable.getEntry("ts").getDouble(0.0);
    }

    /**
     * @return The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
     */
    public double getPipelineLatency() {
        return mTable.getEntry("tl").getDouble(0.0);
    }

    /**
     * @param camMode {@link LimelightControlMode.CamMode#VISION} Run vision processing, decrease
     *                exposure, only shows targets {@link LimelightControlMode.CamMode#DRIVER} Clear
     *                video for streaming to drivers
     */
    public void setCamMode(LimelightControlMode.CamMode camMode) {
        mTable.getEntry("camMode").setValue(camMode.getValue());
    }



    /**
     * @return the distance in inches to the target, -1 if the target is not found
     */
    public double getDistanceToTarget() {
        if (!isTargetFound()) {
            return -1;
        }

        double verticalAngleToTarget = getPitchToTarget();
        double targetHeight = 16;
        double limeLightHeight = 20.5;
        return (targetHeight - limeLightHeight) / Math.tan(Math.toRadians(7.1 + verticalAngleToTarget));
    }

    public void setLEDMode(LimelightControlMode.LedMode ledMode) {
        mTable.getEntry("ledMode").setValue(ledMode.getValue());
    }

    public void setPipeline(int pipeline) {
        if (pipeline < 0) {
            throw new IllegalArgumentException("Pipeline can not be less than zero");
        } else if (pipeline > 9) {
            throw new IllegalArgumentException("Pipeline can not be greater than nine");
        }
        mTable.getEntry("pipeline").setValue(pipeline);
    }
}