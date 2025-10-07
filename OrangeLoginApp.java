import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.io.File;

public class OrangeLoginApp extends JFrame {

    public OrangeLoginApp() {
        super("SOM-D — Login");
        applyThaiUI();

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 560);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(245,140,52)); // พื้นหลังส้ม
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };

        // แถบหัวสีมิ้นต์
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(0,70));
        top.setBackground(new Color(67,226,206));
        root.add(top, BorderLayout.NORTH);

        // โลโก้ + ฟอร์ม
        JPanel center = new JPanel(new GridBagLayout()); center.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints(); gc.gridx=0; gc.insets=new Insets(8,8,8,8);

        JPanel logo = new OvalLogo("SOM-D","orange.png"); logo.setPreferredSize(new Dimension(420,120));
        JPanel form = new JPanel(); form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); form.setBorder(new EmptyBorder(10,10,10,10));

        JLabel l1 = new JLabel("ชื่อผู้ใช้"), l2 = new JLabel("รหัสผ่าน");
        JTextField tfUser = new JTextField(); tfUser.setPreferredSize(new Dimension(420,44));
        JPasswordField tfPass = new JPasswordField(); tfPass.setPreferredSize(new Dimension(420,44));
        JButton login = new JButton("เข้าสู่ระบบ"); login.setPreferredSize(new Dimension(420,46));
        styleMintButton(login);

        login.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String p = new String(tfPass.getPassword());
            if (u.equals("cheer") && p.equals("1234")) {
                dispose();
                SwingUtilities.invokeLater(() -> new OrangeApp().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", "ผิดพลาด", JOptionPane.ERROR_MESSAGE);
            }
        });

        form.add(l1); form.add(tfUser); form.add(Box.createVerticalStrut(10));
        form.add(l2); form.add(tfPass); form.add(Box.createVerticalStrut(15)); form.add(login);

        gc.gridy=0; center.add(logo,gc);
        gc.gridy=1; center.add(form,gc);

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);
    }

    // โลโก้วงรี
    static class OvalLogo extends JPanel {
        private final String text; private final Image img;
        OvalLogo(String t, String path){ text=t; Image temp=null;
            File f=new File(path);
            if (f.exists()) temp=new ImageIcon(path).getImage().getScaledInstance(86,86,Image.SCALE_SMOOTH);
            img=temp;
        }
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2=(Graphics2D)g;
            g2.setColor(new Color(255,248,232));
            g2.fill(new Ellipse2D.Double(getWidth()*0.08, getHeight()*0.10, getWidth()*0.72, getHeight()*0.80));
            g2.setColor(Color.BLACK);
            g2.setFont(getFont().deriveFont(Font.BOLD, 44f));
            g2.drawString(text, (int)(getWidth()*0.25), getHeight()/2+10);
            if (img!=null) g2.drawImage(img, getWidth()-130, 5, null);
        }
    }

    private static void styleMintButton(JButton b){
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setForeground(new Color(22,75,90));
        b.setFont(b.getFont().deriveFont(Font.BOLD,18f));
        b.setOpaque(false);
        b.setBorder(new EmptyBorder(0,0,0,0));
        b.setUI(new javax.swing.plaf.basic.BasicButtonUI(){
            protected void paintButtonPressed(Graphics g, AbstractButton b) {}
            public void paint(Graphics g, JComponent c){
                Graphics2D g2=(Graphics2D)g.create();
                g2.setPaint(new GradientPaint(0,0,new Color(68,227,205),c.getWidth(),c.getHeight(),new Color(40,210,190)));
                g2.fill(new RoundRectangle2D.Double(0,0,c.getWidth(),c.getHeight(),26,26));
                g2.setColor(new Color(0,137,170));
                g2.draw(new RoundRectangle2D.Double(0,0,c.getWidth()-1,c.getHeight()-1,26,26));
                g2.dispose(); super.paint(g,c);
            }
        });
    }

    private static void applyThaiUI(){
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        String font = pickFont(new String[]{"Sarabun","Kanit","Prompt","Tahoma","Dialog"});
        Font ui = new Font(font, Font.PLAIN, 18);
        UIManager.put("Label.font", ui); UIManager.put("Button.font", ui);
        UIManager.put("TextField.font", ui); UIManager.put("PasswordField.font", ui);
        UIManager.put("OptionPane.messageFont", ui); UIManager.put("OptionPane.buttonFont", ui);
    }
    private static String pickFont(String[] prefs){
        String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String p: prefs) for (String n: names) if (n.equalsIgnoreCase(p)) return n; return "Dialog";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrangeLoginApp().setVisible(true));
    }
}
