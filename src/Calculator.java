import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {

    JFrame frame;
    JTextField textField;
    // array for numbers
    JButton[] numberButtons = new JButton[10];
    // for functions buttons
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton;
    JPanel panel;

    Font myFont = new Font("SansSerif", Font.BOLD, 30);

    double num1 = 0, num2 = 0, result = 0;
    char operator;
    private boolean start, notFirstOperation;
    private boolean flagminus;
    private int flag;

    public Calculator() {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        start = true;
        notFirstOperation = true;
        flagminus = true;
        flag = 0;

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();
        ActionListener delete = new DeleteAction();
        ActionListener clear = new ClearAction();
        ActionListener equal = new EqualsAction();

        // Textfield
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);

        // Functions Buttons
        addButton = addButton("+", command);
        subButton = addButton("-", command);
        mulButton = addButton("*", command);
        divButton = addButton("/", command);
        decButton = addButton(".", insert);
        equButton = addButton("=", equal);
        delButton = addButton("Delete", delete);
        clrButton = addButton("Clear", clear);

        // Number Buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = addButton(String.valueOf(i), insert);
        }

        // Set the Buttons
        delButton.setBounds(50, 430, 145, 50);
        clrButton.setBounds(205, 430, 145, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // 1 row
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        // 2 row
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        // 3 row
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        // 4 row
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);


        frame.add(panel);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(textField);
        frame.setVisible(true);
    }

    // добавление кнопки на панель
    private JButton addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(myFont);
        button.addActionListener(listener);
        button.setFocusable(false);
        return button;
    }

    // delete Action
    private class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == delButton) {
                String str = textField.getText();
                if (str.length() != 0) {
                    textField.setText(str.substring(0, str.length() - 1));
                }
                else {
                    flag = 0;
                    flagminus = true;
                }
            }
        }
    }

    // clearAction
    private class ClearAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clrButton) {
                textField.setText("");
                start = true;
                flag = 0;
                flagminus = true;
                notFirstOperation = true;
            }
        }
    }


    // действие вставка цифры или  .
    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            for (int i = 0; i < 10; i++) {
                if ((event.getSource() == numberButtons[i]) && start) {
                    textField.setText(textField.getText().concat(String.valueOf(i)));

                }
            }

            // .
            if (event.getSource() == decButton && start) {
                textField.setText(textField.getText().concat("."));
            }
            flag = 0;
            flagminus = false;
        }
    }

        // действие нажатие на кнопки действий
        private class CommandAction implements ActionListener {
            public void actionPerformed(ActionEvent event) {

                // +, -, *, /
                if(textField.getText().equals("") && !(event.getSource() == subButton)) {
                    textField.setText("");
                }
                else if((flag == 0)) {
                    switch (event.getActionCommand()) {
                        case "+":
                            addActionOnButton(event, addButton, '+');
                            break;
                        case "-":
                            addActionOnButton(event, subButton, '-');
                            break;
                        case "*":
                            addActionOnButton(event, mulButton, '*');
                            break;
                        case "/":
                            addActionOnButton(event, divButton, '/');
                            break;

                    }
                }
            }

            public void addActionOnButton(ActionEvent e, JButton buttonName, char label) {

                if (((e.getSource() == buttonName) && start) || !(notFirstOperation)) {
                    if (flagminus && (label == '-')) {
                        textField.setText("-");
                        flagminus = false;
                        flag++;
                    }
                    else {
                        num1 = Double.parseDouble(textField.getText());
                        operator = label;
                        textField.setText("");
                        start = true;
                    }
                }
            }

        }

    private class EqualsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(textField.getText().equals("")) {
                textField.setText("");
            }
        else if(e.getSource() == equButton) {
            num2 = Double.parseDouble(textField.getText());
            boolean flag = false;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    }
                    else {
                        flag = true;
                    }
                    break;
            }
            if(flag) {
                textField.setText("Dividing by zero");
            }
            else {
                textField.setText(String.valueOf(result));
                num1 = result;
            }
            start = false;
            notFirstOperation = false;
        }
        }
    }

    // ------------------Main----------------------------
        public static void main(String[] args) {
            Calculator calc = new Calculator();
        }
    }




