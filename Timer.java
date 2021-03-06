import java.lang.Thread;

public class Timer 
{

    int minutes;
    int seconds;
    boolean running;
    boolean restTimer; //rest timer == true; long timer == false

    public Timer(int minutes,int seconds) 
    {
        this.minutes = minutes;
        this.seconds = seconds;
        this.running = false;
        this.restTimer = true;
    }

    public void setMinutes(int minutes) 
    {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) 
    {
        this.seconds = seconds;
    }

    public void setRunning(boolean running) 
    {
        this.running = running;
    }

    public void setRestTimer(boolean value)
    {
        this.restTimer = value;
    }

    public int getMinutes() 
    {
        return this.minutes;
    }
    
    public int getSeconds() 
    {
        return this.seconds;
    }

    public boolean isRunning() 
    {
        return this.running;
    }

    public boolean isRestTimer()
    {
        return this.restTimer;
    }

    public boolean isTimeLeft() 
    {
        if (this.minutes != 0 || this.seconds != 0)
            return true;
        return false;
    }

    public void decreaseTimerByOneSecond() 
    {
        if(this.seconds != 0)
            this.seconds--;
        else if (this.minutes != 0) //seconds == 0 && minutes != 0
        {
            this.minutes--;
            this.seconds = 59;
        }
    }

    public String toString() 
    {
        if (this.seconds > 9)
            return this.minutes + ":" +  this.seconds;
        else
            return this.minutes + ":" + "0" + this.seconds;
    }
}
