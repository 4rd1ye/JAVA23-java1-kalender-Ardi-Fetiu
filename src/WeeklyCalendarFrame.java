import javax.swing.*;
import java.awt.*;

public class WeeklyCalendarFrame extends JFrame {
    public WeeklyCalendarFrame() {
        setTitle("Weekly Calendar");
        setLayout(new BorderLayout());

        WeeklyCalendarPanel weekPanel = new WeeklyCalendarPanel();
        add(weekPanel, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(null);

        setResizable(false);
    }
}