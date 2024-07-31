package StudentGradeTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Student {
    private String name;
    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }
}

public class GradeTracker extends JFrame {
    private ArrayList<Student> students;
    private JTextField nameField, gradeField;
    private JTextArea resultArea;

    public GradeTracker() {
        students = new ArrayList<>();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name: ");
        nameField = new JTextField();

        JLabel gradeLabel = new JLabel("Grade: ");
        gradeField = new JTextField();

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(addButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton calculateButton = new JButton("Calculate Results");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });

        JButton clearButton = new JButton("Clear Results");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });

        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addStudent() {
        String name = nameField.getText();
        String gradeText = gradeField.getText();
        
        if (!name.matches("^[a-zA-Z ]+$")) {
            showErrorDialog("Please enter a valid name.");
            return;
        }

        if (name.isEmpty() || gradeText.isEmpty()) {
            showErrorDialog("Please fill in both name and grade.");
            return;
        }

        int grade;
        try {
            grade = Integer.parseInt(gradeText);
        } catch (NumberFormatException e) {
            showErrorDialog("Please enter a valid grade.");
            return;
        }

        students.add(new Student(name, grade));
        nameField.setText("");
        gradeField.setText("");
        resultArea.append("Added student: " + name + " with grade: " + grade + "\n");
    }

    private void showErrorDialog(String message) {
        JDialog errorDialog = new JDialog(this, "Error", true);
        errorDialog.setSize(300, 150);
        errorDialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorDialog.dispose();
            }
        });

        errorDialog.add(messageLabel, BorderLayout.CENTER);
        errorDialog.add(closeButton, BorderLayout.SOUTH);

        errorDialog.setLocationRelativeTo(this);
        errorDialog.setVisible(true);
    }

    private void calculateResults() {
        if (students.isEmpty()) {
            resultArea.append("No students to calculate.\n");
            return;
        }
        double average = calculateAverage();
        int highest = findHighestGrade();
        int lowest = findLowestGrade();

        showResultsDialog(average, highest, lowest);
    }

    private void showResultsDialog(double average, int highest, int lowest) {
        JDialog resultsDialog = new JDialog(this, "Results", true);
        resultsDialog.setSize(300, 200);
        resultsDialog.setLayout(new GridLayout(4, 1));

        JLabel averageLabel = new JLabel("Average grade: " + average);
        JLabel highestLabel = new JLabel("Highest grade: " + highest);
        JLabel lowestLabel = new JLabel("Lowest grade: " + lowest);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultsDialog.dispose();
            }
        });

        resultsDialog.add(averageLabel);
        resultsDialog.add(highestLabel);
        resultsDialog.add(lowestLabel);
        resultsDialog.add(closeButton);

        resultsDialog.setLocationRelativeTo(this);
        resultsDialog.setVisible(true);
    }

    private void clearResults() {
        students.clear();
        resultArea.setText("");
    }

    public double calculateAverage() {
        if (students.isEmpty()) return 0;
        int sum = 0;
        for (Student student : students) {
            sum += student.getGrade();
        }
        return (double) sum / students.size();
    }

    public int findHighestGrade() {
        if (students.isEmpty()) return -1;
        int highest = Integer.MIN_VALUE;
        for (Student student : students) {
            if (student.getGrade() > highest) {
                highest = student.getGrade();
            }
        }
        return highest;
    }

    public int findLowestGrade() {
        if (students.isEmpty()) return -1;
        int lowest = Integer.MAX_VALUE;
        for (Student student : students) {
            if (student.getGrade() < lowest) {
                lowest = student.getGrade();
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeTracker().setVisible(true);
            }
        });
    }
}
