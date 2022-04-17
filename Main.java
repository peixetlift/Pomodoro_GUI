import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.*;
import java.lang.Math;

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
        JProgressBar progressBar = new JProgressBar(0,100);
        JPanel panel = new JPanel();
        Timer timer = new Timer(0,0);
        ArrayList<JComboBox> dropDownTimes = new ArrayList<>();
        
        initJComboBoxes(dropDownTimes);

        frame.setTitle("Pomodoro");
        frame.setPreferredSize(new Dimension(550, 250));
        frame.setLayout(new FlowLayout());

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        timerButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        progressBar.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(timerLabel);
        panel.add(timerButton);
        panel.add(progressBar);

        frame.add(Box.createHorizontalGlue());
        frame.add(panel);
        frame.add(Box.createHorizontalGlue());
        for(JComboBox box : dropDownTimes)
            frame.add(box);
        frame.pack();
        frame.setVisible(true);


        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!timer.isRunning())
                    runTimerOnLabelAndProgressBar(timerLabel, progressBar, timer);
                else
                    timer.setRunning(false);

                if(!timer.isTimeLeft())
                    startNextTimer(timer, (int) dropDownTimes.get(0).getSelectedItem(), (int) dropDownTimes.get(1).getSelectedItem());
            }
        });

    }

    public static void startNextTimer(Timer timer, int workTimerMinutes, int restTimerMinutes)
        {
            if(timer.isRestTimer())
            {
                timer.setMinutes(workTimerMinutes);
                timer.setRestTimer(false);
            }
            else
            {
                timer.setMinutes(restTimerMinutes);
                timer.setRestTimer(true);
            }
        }

    public static void runTimerOnLabelAndProgressBar(JLabel timerLabel, JProgressBar progressBar, Timer timer) 
    {
        SwingWorker<Void,Timer> worker = new SwingWorker<Void,Timer>() {

            @Override
            protected Void doInBackground() {
                int initialTimerInMinutes = timer.getMinutes();
                float progress = 0;
                timer.setRunning(true);
                try {
                    while(timer.isTimeLeft() && timer.isRunning())
                    {
                        timer.decreaseTimerByOneSecond();
                        publish(timer);
                        progress = calculateProgress(timer, (initialTimerInMinutes * 60));
                        updateProgressBar(progressBar, (1 - progress));
                        Thread.sleep(1000);
                    } 
                    updateProgressBar(progressBar, 1);
                    timer.setRunning(false);
                } catch (Exception e) {}
                return null;
            }

            @Override
            protected void process(List<Timer> timerList) {
                timerLabel.setText(timerList.get(0).toString());
                if(!timer.isTimeLeft())
                    changeTextToTimerOver(timerLabel, timer);
            }
        };
            worker.execute();
    }
    
    public static float calculateProgress(Timer timer, int totalSeconds) 
    {
        int elapsedSeconds = (timer.getMinutes() * 60) + timer.getSeconds();
        return (float) elapsedSeconds / totalSeconds;
    }

    public static void changeTextToTimerOver(JLabel label, Timer timer)
    {
        if(!timer.isRestTimer())
            label.setText("Start the break timer!");
        else
            label.setText("Start the work timer!");
    }

    public static void updateProgressBar(JProgressBar bar, float value)
    {
        bar.setStringPainted(true);
        bar.setValue(Math.round(value * 100));
    }
    
    public static void initJComboBoxes(ArrayList<JComboBox> boxArray)
    {
        JComboBox workTimersBox = new JComboBox();
        JComboBox restTimersBox = new JComboBox();
        boxArray.add(workTimersBox);
        boxArray.add(restTimersBox);
        
        for(JComboBox box : boxArray) {
            for(int i = 0; i <= 60; i++) {
                box.addItem(i);
            }
        }
    }        
}
