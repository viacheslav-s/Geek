package ru.vyacheslav.javacore.lesson4;

// @author Vyacheslav Suslov

import javax.swing.*;
import java.awt.event.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Chat extends JDialog {
    private JPanel contentPane;
    private JButton buttonSend;
    private JFormattedTextField chatTextField;
    private JTextArea chatArea;
    private JPanel textSender;
    private JPanel chatView;
    private JLabel errorsLabel;

    public Chat() {
        setTitle("Уютный чатик");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSend);

        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });

        chatTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        onSend();
                        break;
                }
            }
        });
    }
        private void onSend () {
            int maxChar = 40;
            if (chatTextField.getText().equals("")) {
                errorsLabel.setText("Поле пустое, введите сообщение!");
            }
            if (chatTextField.getText().length() > maxChar) {
                errorsLabel.setText("Максмальное колчество знаков " + maxChar);
            }
            if (chatTextField.getText().length() > 0 && chatTextField.getText().length() <= maxChar) {
                errorsLabel.setText("Сообщение отправлено: " + dateNow());
                chatArea.append("[Вы:] " + chatTextField.getText() + "\n");
                chatTextField.setText("");
            }
        }

        private String dateNow () {
            String today = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));
            return today;

        }
}