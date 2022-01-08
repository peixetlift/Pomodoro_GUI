import javax.swing.*;
import java.awt.event.*;
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
        Timer timer = new Timer(25,0);


        timerButton.setBounds(200,300,200,100);
        timerLabel.setBounds(200,500,200,100);
        frame.setTitle("Pomodoro");
        frame.setSize(400,600);
        frame.setLayout(null);
        frame.add(timerLabel);
        frame.add(timerButton);
        frame.setVisible(true);

        timerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runTimerOnLabel(timerLabel, timer);
            }
        });
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
                        Thread.sleep(1000);
                        publish(timer);
                    }
                timer.setRunning(false);
                } catch (Exception e) {}
                return null;
            }

            @Override
            protected void process(List<Timer> timerList) {
                label.setText(timerList.get(0).toString());
            }
        };
            worker.execute();
    }
}
