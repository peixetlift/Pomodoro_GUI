import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
        JPanel topPanel = new JPanel();
        JPanel botPanel = new JPanel();
        Timer timer = new Timer(25,0);

        //botPanel.setLayout(new BorderLayout());
        //topPanel.setLayout(new BorderLayout());
        //timerButton.setBounds(100,100,200,100);
        //timerLabel.setBounds(150,300,500,100);
        frame.setTitle("Pomodoro");
        //frame.setLayout(new BorderLayout());
        //botPanel.add(timerLabel, BorderLayout.CENTER);
        //topPanel.add(timerButton, BorderLayout.CENTER);
        //frame.add(topPanel, BorderLayout.NORTH);
        //frame.add(botPanel, BorderLayout.SOUTH);
        //topPanel.setPreferredSize(new Dimension(100,300));
        //botPanel.setPreferredSize(new Dimension(100,300));
        frame.add(timerButton);
        frame.add(timerLabel);
        frame.setSize(400,600);
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
