import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.List;

public final class ThaiUI {

    private ThaiUI() {}

    /** เรียกครั้งเดียวก่อนสร้างหน้าต่างใด ๆ */
    public static void apply() {
        // ตั้งค่า Locale ไทย
        Locale.setDefault(new Locale("th", "TH"));

        // หา "ฟอนต์ไทย" ที่ติดตั้งในระบบตามลำดับที่กำหนด
        String fontName = pickFirstAvailableFont(
                List.of("Sarabun", "Kanit", "Prompt", "Tahoma", "Dialog")
        );
        int size = 18; // ขนาดพื้นฐานอ่านสบาย

        Font ui = new Font(fontName, Font.PLAIN, size);

        // ตั้งค่าฟอนต์ให้คอมโพเนนต์มาตรฐาน
        UIManager.put("Label.font", ui);
        UIManager.put("Button.font", ui);
        UIManager.put("ToggleButton.font", ui);
        UIManager.put("RadioButton.font", ui);
        UIManager.put("CheckBox.font", ui);
        UIManager.put("ComboBox.font", ui);
        UIManager.put("List.font", ui);
        UIManager.put("Table.font", ui);
        UIManager.put("TableHeader.font", ui.deriveFont(Font.BOLD, size));
        UIManager.put("TabbedPane.font", ui);
        UIManager.put("TextField.font", ui);
        UIManager.put("PasswordField.font", ui);
        UIManager.put("TextArea.font", ui);
        UIManager.put("TextPane.font", ui);
        UIManager.put("EditorPane.font", ui);
        UIManager.put("OptionPane.messageFont", ui);
        UIManager.put("OptionPane.buttonFont", ui);
        UIManager.put("ToolTip.font", ui.deriveFont(size - 2f));
        UIManager.put("TitledBorder.font", ui.deriveFont(Font.BOLD, size - 1f));
        UIManager.put("Menu.font", ui);
        UIManager.put("MenuItem.font", ui);
        UIManager.put("PopupMenu.font", ui);

        // เปิด Anti-Aliasing สำหรับข้อความทั่วทั้งแอป
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }

    private static String pickFirstAvailableFont(List<String> prefs) {
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var names = ge.getAvailableFontFamilyNames();
        for (String want : prefs) {
            for (String have : names) {
                if (have.equalsIgnoreCase(want)) return have;
            }
        }
        return "Dialog"; // fallback
    }
}
