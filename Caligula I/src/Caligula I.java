import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

class CaligulaI {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                result.append(alphabet.charAt((index + shift) % alphabet.length()));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, alphabet.length() - shift);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Caligula I");
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Open");

        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile())) {
                    String encryptedText = encrypt(textArea.getText(), 7); // Шифруем текст сдвигом 7
                    writer.print(encryptedText);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try (Scanner scanner = new Scanner(fileChooser.getSelectedFile())) {
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine());
                    }
                    String decryptedText = decrypt(sb.toString(), 7); // Дешифруем текст обратно
                    textArea.setText(decryptedText);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(openItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
