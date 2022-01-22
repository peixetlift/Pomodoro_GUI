import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.*;

public class Main {
    public static void main (String[] args)
    {
        runGui();
    }

    public static void runGui()
    {
        JFrame frame = new JFrame();
        JButton timerButton = new JButton("Start/Stop");
        JLabel timerLabel = new JLabel("Timer stopped"); 
        JPanel panel = new JPanel();
        Timer timer = new Timer(25,0);

        frame.setTitle("Pomodoro");
        frame.setLayout(new FlowLayout());

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        timerButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(timerLabel);
        panel.add(timerButton);

        frame.add(Box.createHorizontalGlue());
        frame.add(panel);
        frame.add(Box.createHorizontalGlue());
        frame.pack();
        frame.setVisible(true);

        timerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning())
                    runTimerOnLabel(timerLabel, timer);
                else
                    timer.setRunning(false);

                if(!timer.isTimeLeft())
                    startNextTimer(timer, 25, 5);
            }
        });

    }

    public static void startNextTimer(Timer timer, int longTimerMinutes, int shortTimerMinutes)
        {
            if(timer.isShortTimer())
            {
                timer.setMinutes(longTimerMinutes);
                timer.unsetShortTimer();
            }
            else
            {
                timer.setMinutes(shortTimerMinutes);
                timer.setShortTimer();
            }
        }

    public static void runTimerOnLabel(JLabel label, Timer timer) 
    {
        SwingWorker<Void,Timer> worker = new SwingWorker<Void,Timer>() {

            @Override
            protected Void doInBackground() {
                timer.setRunning(true);
                try {
                    while(timer.isTimeLeft() && timer.isRunning())
                    {
                        timer.decreaseTimerByOneSecond();
                        publish(timer);
                        Thread.sleep(5);
                    } 
                    timer.setRunning(false);
                } catch (Exception e) {}
                return null;
            }

            @Override
            protected void process(List<Timer> timerList) {
                label.setText(timerList.get(0).toString());
                if(!timer.isTimeLeft())
                    changeTextToTimerOver(label, timer);
            }
        };
            worker.execute();
    }

    public static void changeTextToTimerOver(JLabel label, Timer timer)
    {
        if(!timer.isShortTimer())
            label.setText("Working Timer Over, start the break timer!");
        else
            label.setText("Pause Timer Over, start the working timer!");
    }
}
