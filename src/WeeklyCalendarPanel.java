import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

    public class WeeklyCalendarPanel extends JPanel {
    private JTextArea[] dayTextArea;
    private JLabel[] currentDayLabel;
    private JTextField[] inputField;
    private JButton[] addButton;
    private JButton[] deleteButton;

    public WeeklyCalendarPanel () {
        setLayout(new GridBagLayout());

        int DaysInAWeek = 7;

        // Skapa en instans av kalender och ställs in på måndag
        Calendar calendar = Calendar.getInstance(new Locale("en", "SE"));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MM/yyyy");

        // Skapa arrayer för olika komponenter
        dayTextArea = new JTextArea[DaysInAWeek];
        currentDayLabel = new JLabel[DaysInAWeek];
        inputField = new JTextField[DaysInAWeek];
        addButton = new JButton[DaysInAWeek];
        deleteButton = new JButton[DaysInAWeek];

        for (int i = 0; i < 7; i++) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;

            // Skapa en panel för varje dag
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));

            // Visa aktuellt datum
            currentDayLabel[i] = new JLabel(dateFormat.format(calendar.getTime()));

            // Skapa ett textområde för varje dag
            dayTextArea[i] = new JTextArea(20, 15);
            dayTextArea[i].setLineWrap(true);
            dayTextArea[i].setWrapStyleWord(true);
            dayTextArea[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dayTextArea[i].setEditable(false);
            dayTextArea[i].setFont(new Font("Arial", Font.PLAIN, 18));

            // Lägg till scroll runt textområdet
            JScrollPane scrollPane = new JScrollPane(dayTextArea[i]);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Skapa en input ruta för varje dag
            inputField[i] = new JTextField(20);
            inputField[i].setEditable(true);

            // Skapa knappar för att lägga till och radera aktiviteter
            Dimension dimension = new Dimension(1000,1000);

            addButton[i] = new JButton("Add activity");
            addButton[i].setMaximumSize(dimension);
            addButton[i].addActionListener(new AddButtonListener(i));

            deleteButton[i] = new JButton("Clear Activities");
            deleteButton[i].setMaximumSize(dimension);
            deleteButton[i].addActionListener(new DeleteButtonListener(i));

            // Lägg till komponenter i panelen
            add(dayPanel,constraints);
            dayPanel.add(currentDayLabel[i]);
            dayPanel.add(scrollPane);
            dayPanel.add(inputField[i]);
            dayPanel.add(addButton[i]);
            dayPanel.add(deleteButton[i]);

            // Sätter färger på dagens datum samt Lör & Sön
            if (i == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) {
                dayTextArea[i].setBackground(Color.GREEN);
            }else if (i == 5 || i == 6) {
                dayTextArea[i].setBackground(Color.RED);
            }
            calendar.add(Calendar.DATE, 1);
        }
    }


    private class AddButtonListener implements ActionListener {
        private int dayIndex;

        public AddButtonListener(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Hämta aktivitet från inmatningsrutan och lägg till i textområdet
            String activity = inputField[dayIndex].getText();
            dayTextArea[dayIndex].append( "-" + activity + "\n");
            inputField[dayIndex].setText("");
        }
    }

    private class DeleteButtonListener implements ActionListener {
        private int dayIndex;

        public DeleteButtonListener(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Rensa textområdet för den valda dagen
            dayTextArea[dayIndex].setText("");
        }
    }
}