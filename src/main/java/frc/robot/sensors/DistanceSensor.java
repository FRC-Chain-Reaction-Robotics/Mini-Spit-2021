package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;

public class DistanceSensor
{
    AnalogPotentiometer sensor;

    public DistanceSensor()
    {
        sensor = new AnalogPotentiometer(Constants.DISTANCE_SENSOR_ANALOG_IN_PORT, 512);
    }

    /**
     * Gets distance to wall from sensor
     * @return distance [0 to 512 cm]
     */
    public double getDistance()
    {
        return sensor.get();
    }
}
