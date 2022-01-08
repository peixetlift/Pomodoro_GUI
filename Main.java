import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        timer.setRunning(true);
        try {
            while(timer.isTimeLeft() && timer.isRunning())
            {
                timer.decreaseTimerByOneSecond();
                Thread.sleep(1000);
                label.setText(timer.toString());
            }
            timer.setRunning(false);
            label.setText("Timer over!");
        } catch (Exception e) {}
    }
}
