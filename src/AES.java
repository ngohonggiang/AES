import java.awt.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class AES extends JFrame {

    private final JTextField keyField;
    private final JTextArea message;
    private final JTextArea encryptText;
    private final JTextArea decryptText;
    private static final String AES_MODE = "AES/ECB/PKCS5PADDING";

    // launch
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AES frame = new AES();
                frame.setVisible(true);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void encrypt() {
        try {
            String originalText = message.getText();
            String key = keyField.getText();

            SecretKeySpec skSpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, skSpec);
            byte[] byteEncrypted = cipher.doFinal(originalText.getBytes());
            String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
            encryptText.setText(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void decrypt() {
        try {
            String encryptText = this.encryptText.getText();
            String key = keyField.getText();

            SecretKeySpec skSpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, skSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            decryptText.setText(new String(decrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // display
    public AES() {
        setTitle("AES Encryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1190, 720);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(SystemColor.control);
        contentPanel.setForeground(Color.WHITE);
        setContentPane(contentPanel);
        contentPanel.setLayout(null);

        JLabel Key = new JLabel("Key: ");
        Key.setFont(new Font("Serif", Font.BOLD, 20));
        Key.setBounds(20, 15, 50, 30);
        contentPanel.add(Key);

        this.keyField = new JTextField();
        keyField.setFont(new Font("Serif", Font.PLAIN, 18));
        this.keyField.setBounds(80, 15, 300, 30);
        contentPanel.add(this.keyField);

        JLabel lblOrigin = new JLabel("Original text: ");
        lblOrigin.setFont(new Font("Serif", Font.BOLD, 20));
        lblOrigin.setBounds(20, 65, 150, 30);
        contentPanel.add(lblOrigin);

        JLabel lblEncrypt = new JLabel("Encrypted text: ");
        lblEncrypt.setFont(new Font("Serif", Font.BOLD, 20));
        lblEncrypt.setBounds(435, 65, 150, 30);
        contentPanel.add(lblEncrypt);

        JLabel lblDecrypt = new JLabel("Decrypted text: ");
        lblDecrypt.setFont(new Font("Serif", Font.BOLD, 20));
        lblDecrypt.setBounds(850, 65, 150, 30);
        contentPanel.add(lblDecrypt);

        this.message = new JTextArea();
        this.message.setLineWrap(true);
        message.setFont(new Font("Serif", Font.PLAIN, 18));
        this.message.setBounds(20, 100, 300, 550);
        contentPanel.add(this.message);

        this.encryptText = new JTextArea();
        this.encryptText.setLineWrap(true);
        encryptText.setFont(new Font("Serif", Font.PLAIN, 18));
        this.encryptText.setBounds(435, 100, 300, 550);
        contentPanel.add(this.encryptText);

        this.decryptText = new JTextArea();
        this.decryptText.setLineWrap(true);
        decryptText.setFont(new Font("Serif", Font.PLAIN, 18));
        this.decryptText.setBounds(850, 100, 300, 550);
        contentPanel.add(this.decryptText);

        JButton buttonEncrypt = new JButton("Encrypt");
        buttonEncrypt.setFont(new Font("Serif", Font.ITALIC, 18));
        buttonEncrypt.addActionListener(e -> encrypt());
        buttonEncrypt.setBounds(330, 200, 95, 30);
        contentPanel.add(buttonEncrypt);

        JButton buttonDecrypt = new JButton("Decrypt");
        buttonDecrypt.setFont(new Font("Serif", Font.ITALIC, 18));
        buttonDecrypt.addActionListener(e -> decrypt());
        buttonDecrypt.setBounds(745, 200, 95, 30);
        contentPanel.add(buttonDecrypt);
    }
}
