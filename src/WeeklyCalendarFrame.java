import javax.swing.*;
import java.awt.*;

public class WeeklyCalendarFrame extends JFrame {
    ImageIcon image = new ImageIcon("cooldog.png");  //create image icon to use in frame

    public WeeklyCalendarFrame() {

        WeeklyCalendarPanel.design(); //adding design method

        setTitle("Weekly Calendar");
        setIconImage(image.getImage()); //place logo in frame (switches out java logo)
        WeeklyCalendarPanel weekPanel = new WeeklyCalendarPanel();
        add(weekPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}