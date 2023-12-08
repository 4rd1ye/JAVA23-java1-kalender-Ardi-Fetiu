import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeeklyCalendarPanel extends JPanel {
    private final JTextArea[] dayTextArea;
    private final JLabel[] currentDayLabel;
    private final JTextField[] inputField;
    private final JButton[] addButton;
    private final JButton[] deleteButton;
    private final JPanel[] buttonPanel; //Ny array panel för buttons

    public WeeklyCalendarPanel () {
        int DaysInAWeek = 7; //Skapar variabel veckans dagar till 7

        Calendar calendar = Calendar.getInstance(new Locale("en", "SE"));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MM/yyyy"); //displa

        // Skapa arrayer för olika komponenter
        dayTextArea = new JTextArea[DaysInAWeek];
        currentDayLabel = new JLabel[DaysInAWeek];
        inputField = new JTextField[DaysInAWeek];
        buttonPanel = new JPanel[DaysInAWeek];
        addButton = new JButton[DaysInAWeek];
        deleteButton = new JButton[DaysInAWeek];

        //for loop för panel
        for (int i = 0; i < 7; i++) {

            // Aktuellt datum på current DayLabel
            currentDayLabel[i] = new JLabel(dateFormat.format(calendar.getTime()));
            currentDayLabel[i].setHorizontalTextPosition(SwingConstants.LEFT);


            dayPanelMethod(i); //Anropar dayPanel metoden

            if (i == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) {
                dayTextArea[i].setBackground(new Color(229, 246, 240, 186)); //Ny bakrundsfärg
                currentDayLabel[i].setForeground(new Color(28, 82, 44)); //Grön färg på dagens datum
                currentDayLabel[i].setFont(new Font("Arial", Font.BOLD, 14)); //Bold font på dagens datum
            } else if (i == 5 || i == 6) {
                currentDayLabel[i].setForeground(new Color(155, 4, 4)); //Röd färg på datum när det är helg
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

    private class AddButtonListener implements ActionListener {
        private final int dayIndex;

        public AddButtonListener(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Hämta aktivitet från inmatningsrutan och lägg till i textområdet
            String activity = inputField[dayIndex].getText();
            dayTextArea[dayIndex].append( "-" + activity + "\n"); //Skapar ny rad för varje input
            inputField[dayIndex].setText("");
        }
    }

    private class DeleteButtonListener implements ActionListener {
        private final int dayIndex;

        public DeleteButtonListener(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Rensa textområdet för den valda dagen
            dayTextArea[dayIndex].setText("");
        }
    }

    private void dayPanelMethod (int i) { //Skapade ny metod för panel som håller alla komponenter

        // Skapa en panel för varje dag
        JPanel dayPanel = new JPanel();
        currentDayLabel[i].setHorizontalTextPosition(SwingConstants.LEFT);

        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));

        currentDayLabel[i].setFont(new Font("Arial", Font.PLAIN, 14));
       // currentDayLabel[i].setHorizontalAlignment(SwingConstants.LEFT);//
       // currentDayLabel[i].setHorizontalTextPosition(SwingConstants.LEFT);

        // Skapa ett textområde för varje dag
        dayTextArea[i] = new JTextArea(20, 15);
        dayTextArea[i].setLineWrap(true);
        dayTextArea[i].setWrapStyleWord(true);
        dayTextArea[i].setEditable(false);
        dayTextArea[i].setFont(new Font("Arial", Font.PLAIN, 14));

        // Lägg till scroll runt textområdet
        JScrollPane scrollPane = new JScrollPane(dayTextArea[i]);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Skapa en input ruta för varje dag
        inputField[i] = new JTextField(20);
        inputField[i].setEditable(true);

        // Skapa knappar för att lägga till och radera aktiviteter
        addButton[i] = new JButton("Add activity");
        addButton[i].setMaximumSize(getMaximumSize()); //
        addButton[i].addActionListener(new AddButtonListener(i));

        deleteButton[i] = new JButton("Clear Activities");
        deleteButton[i].setMaximumSize(getMaximumSize());
        deleteButton[i].addActionListener(new DeleteButtonListener(i));

        //Skapar ny panel för att hålla buttons (för att fixa problemet med ocentrerade knappar) + ny flowlayout
        buttonPanel[i] = new JPanel();
        buttonPanel[i].setLayout(new FlowLayout());
        buttonPanel[i].add(addButton[i]);
        buttonPanel[i].add(deleteButton[i]);

        // Lägg till komponenter i panelen
        dayPanel.add(currentDayLabel[i]);
        dayPanel.add(scrollPane);
        dayPanel.add(inputField[i]);
        dayPanel.add(buttonPanel[i]);
        add(dayPanel);

    }

    static void design() {
        //Lägger till UI look and feel design för finare styles på färg, knappar, scrollpane etc)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        UIManager.put("nimbusBlueGrey", new Color(159, 185, 157, 255)); //changing color of buttons
        UIManager.put("control", new Color(239, 249, 253, 255)); //changing color of background
    }
}
