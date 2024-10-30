import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SatelliteLifetime extends JFrame {

    private JTextField inputLambda;
    private JTextField inputYearsAlive;
    private JTextField inputYearsBetween;
    private JLabel resultAlive;
    private JLabel resultBetween;

    public SatelliteLifetime() {
        setTitle("Satellite Lifetime Probability");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label and input for Lambda
        JLabel labelLambda = new JLabel("Lambda (λ):");
        labelLambda.setBounds(20, 20, 150, 20);
        add(labelLambda);

        inputLambda = new JTextField();
        inputLambda.setBounds(170, 20, 100, 20);
        add(inputLambda);

        // Label and input for "alive after X years"
        JLabel labelAlive = new JLabel("Years (Alive after):");
        labelAlive.setBounds(20, 60, 150, 20);
        add(labelAlive);

        inputYearsAlive = new JTextField();
        inputYearsAlive.setBounds(170, 60, 100, 20);
        add(inputYearsAlive);

        // Label and input for "between X and Y years"
        JLabel labelBetween = new JLabel("Years (Between X-Y):");
        labelBetween.setBounds(20, 100, 150, 20);
        add(labelBetween);

        inputYearsBetween = new JTextField();
        inputYearsBetween.setBounds(170, 100, 100, 20);
        add(inputYearsBetween);

        // Button to calculate
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(20, 140, 150, 30);
        add(calculateButton);

        // Result labels
        resultAlive = new JLabel("Probability alive: ");
        resultAlive.setBounds(20, 190, 350, 20);
        add(resultAlive);

        resultBetween = new JLabel("Probability between: ");
        resultBetween.setBounds(20, 220, 350, 20);
        add(resultBetween);

        // Add action listener to button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateProbabilities();
            }
        });
    }

    private void calculateProbabilities() {
        try {
            // Check if Lambda is provided
            if (inputLambda.getText().isEmpty()) {
                throw new IllegalArgumentException("Lambda (λ) value is required.");
            }
            double lambda = Double.parseDouble(inputLambda.getText());

            // Lambda suggestions for large values
            if (lambda > 5) {
                JOptionPane.showMessageDialog(this,
                        "The value of λ seems high. Typical values for λ:\n" +
                                "- Moderate lifespan: 0.3 - 0.5\n" +
                                "- Short lifespan: 1 - 2\n" +
                                "Consider using lower values for more realistic results.",
                        "Suggestion",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if (lambda <= 0) {
                throw new IllegalArgumentException("Lambda (λ) must be positive.");
            }

            // Check if yearsAlive input is provided
            if (inputYearsAlive.getText().isEmpty()) {
                throw new IllegalArgumentException("Years (Alive after) value is required.");
            }
            double yearsAlive = Double.parseDouble(inputYearsAlive.getText());

            // Check if yearsBetween input is provided and correctly formatted
            if (inputYearsBetween.getText().isEmpty()) {
                throw new IllegalArgumentException("Years (Between X-Y) value is required.");
            }
            String yearsText = inputYearsBetween.getText();
            if (!yearsText.contains("-")) {
                throw new IllegalArgumentException("Years (Between X-Y) must be in the format 'X-Y'.");
            }

            String[] years = yearsText.split("-");
            if (years.length != 2) {
                throw new IllegalArgumentException("Invalid format for Years (Between X-Y). Use 'X-Y'.");
            }

            double year1 = Double.parseDouble(years[0].trim());
            double year2 = Double.parseDouble(years[1].trim());

            if (year1 >= year2) {
                throw new IllegalArgumentException("First year must be less than second year in 'X-Y'.");
            }

            // Calculate probability that satellite is alive after the given years
            double probAlive = Math.exp(-lambda * yearsAlive);

            // Calculate probability that satellite dies between the two given years
            double probBetween = Math.exp(-lambda * year1) - Math.exp(-lambda * year2);

            // Display results
            resultAlive.setText(String.format("Probability alive after %.2f years: %.5f", yearsAlive, probAlive));
            resultBetween.setText(String.format("Probability dies between %.2f and %.2f years: %.5f", year1, year2, probBetween));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SatelliteLifetime app = new SatelliteLifetime();
        app.setVisible(true);
    }
}
