import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static javax.swing.BoxLayout.LINE_AXIS;

public class WeeklyCalendarPanel extends JPanel {
    private JTextArea[] dayTextArea;
    private JLabel[] currentDayLabel;
    private JTextField[] inputField;
    private JButton[] addButton;
    private JButton[] deleteButton;

        public WeeklyCalendarPanel () { //create a class with a weekly calendar gui with all weekdays
        setLayout(new GridBagLayout()); //set layout for the panel

        int DaysInAWeek = 7; //initiate days in a week 7

        // Skapa en instans av kalender och ställs in på måndag
        Calendar calendar = Calendar.getInstance(new Locale("en", "SE"));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MM/yyyy"); //displaying current date

        // Skapa arrayer för olika komponenter //arrays with components for each day of the week
        dayTextArea = new JTextArea[DaysInAWeek];
        currentDayLabel = new JLabel[DaysInAWeek];
        inputField = new JTextField[DaysInAWeek];
        addButton = new JButton[DaysInAWeek];
        deleteButton = new JButton[DaysInAWeek];

        //for loop to create panels for each weekday with gridbagconstraint layout
        for (int i = 0; i < 7; i++) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;

            // Skapa en panel för varje dag
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));

            // Visa aktuellt datum
            currentDayLabel[i] = new JLabel(dateFormat.format(calendar.getTime()));
            currentDayLabel[i].setFont(new Font("Arial", Font.PLAIN, 14));

            // Skapa ett textområde för varje dag
            dayTextArea[i] = new JTextArea(20, 15);
            dayTextArea[i].setLineWrap(true);
            dayTextArea[i].setWrapStyleWord(true);
            //dayTextArea[i].setBorder(BorderFactory.createLineBorder(Color.BLACK)); //remove black border
            dayTextArea[i].setEditable(false);
            dayTextArea[i].setFont(new Font("Arial", Font.PLAIN, 14));

            // Lägg till scroll runt textområdet
           JScrollPane scrollPane = new JScrollPane(dayTextArea[i]);
           scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Skapa en input ruta för varje dag
            inputField[i] = new JTextField(20);
            inputField[i].setEditable(true);

            // Skapa knappar för att lägga till och radera aktiviteter
           //Dimension dimension = new Dimension(1000,1000); // not needed


            //alternativ
            // skapa panel för knapparna för att få korrect location
            addButton[i] = new JButton("Add activity");
            addButton[i].setMaximumSize(getMaximumSize()); //
            addButton[i].addActionListener(new AddButtonListener(i));
            addButton[i].setLayout(new BoxLayout(addButton[i], LINE_AXIS));

            deleteButton[i] = new JButton("Clear Activities");
            deleteButton[i].setMaximumSize(getMaximumSize());
            deleteButton[i].addActionListener(new DeleteButtonListener(i));

            // Lägg till komponenter i panelen
            add(dayPanel, constraints);
            dayPanel.add(currentDayLabel[i]);
            dayPanel.add(scrollPane);
            dayPanel.add(inputField[i]);
            //dayPanel.add() add panel with all buttons 
            dayPanel.add(addButton[i]);
            dayPanel.add(deleteButton[i]);


            // Sätter färger på dagens datum samt Lör & Sön
                if (i == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY) {
                    dayTextArea[i].setBackground(new Color(229, 246, 240, 186));
                    currentDayLabel[i].setForeground(new Color(28, 82, 44)); //changing color of todays date
                    currentDayLabel[i].setFont(new Font("Arial", Font.BOLD, 14)); //changing text to bold for todays date

                } else if (i == 5 || i == 6) {
                    currentDayLabel[i].setForeground(new Color(155, 4, 4)); //changing weekend colors to red text instead
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
            dayTextArea[dayIndex].append( "-" + activity + "\n"); //creates new line for each user input
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

        public static void design() {
            //adding UI manager "Look and Feel design just for fun
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

    //code that needs improving
//buttons are not centerd, check layout managers