import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class OrangeApp extends JFrame {

    private final CardLayout card = new CardLayout();
    private final JPanel pages = new JPanel(card); // dashboard, product, sort, wash, squeeze, filter, pasteurize, pack, ship

    public OrangeApp() {
        super("SOM-D — ภาพรวมกระบวนการผลิตน้ำส้ม");
        applyThaiUI();
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
 // ===== บังคับให้แสดงผลภาษาไทยใน Swing =====
System.setProperty("file.encoding", "UTF-8");
System.setProperty("awt.useSystemAAFontSettings", "on");
System.setProperty("swing.aatext", "true");

// เลือกฟอนต์ไทยที่มีเกือบทุกเครื่อง Windows
final Font THAI_FONT = new Font("Tahoma", Font.PLAIN, 18);

// บังคับ UI ทั้งระบบใช้ฟอนต์นี้
UIManager.put("Button.font", THAI_FONT);
UIManager.put("Label.font", THAI_FONT);
UIManager.put("TextField.font", THAI_FONT);
UIManager.put("TextArea.font", THAI_FONT);
UIManager.put("Table.font", THAI_FONT);
UIManager.put("TableHeader.font", THAI_FONT);
UIManager.put("List.font", THAI_FONT);
UIManager.put("ComboBox.font", THAI_FONT);
UIManager.put("OptionPane.messageFont", THAI_FONT);
UIManager.put("OptionPane.buttonFont", THAI_FONT);
UIManager.put("TitledBorder.font", THAI_FONT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 750);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        setContentPane(root);

        /* ---------- Header ---------- */
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        root.add(header, BorderLayout.NORTH);

        JPanel mint = new JPanel();
        mint.setBackground(new Color(67, 226, 206));
        mint.setPreferredSize(new Dimension(0, 34));
        header.add(mint);

        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(241, 145, 53));
        banner.setBorder(new EmptyBorder(8, 0, 8, 0));
        JLabel brand = new JLabel("SOM-D", SwingConstants.CENTER);
        brand.setForeground(Color.WHITE);
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 42f));
        banner.add(brand, BorderLayout.CENTER);
        header.add(banner);

        // แถวปุ่มเมนู (กดได้ทุกปุ่ม)
        JPanel stepsBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        stepsBar.setBackground(new Color(253, 246, 227));
        String[] steps = {"หน้าหลัก","คัดแยกผลส้ม","ล้าง","คั้นน้ำส้ม","กรองน้ำส้ม","พาสเจอร์ไรซ์","บรรจุ","ขนส่ง"};
        for (String s : steps) {
            JButton btn = stepButton(s);
            btn.addActionListener(e -> showPageForStep(s));
            stepsBar.add(btn);
        }
        JButton btProduct = mintButton("จัดการสินค้า");
        btProduct.addActionListener(e -> card.show(pages, "product"));
        stepsBar.add(btProduct);
        header.add(stepsBar);

        // ปุ่มวิเคราะห์ยอดขาย
        JButton btAnalytics = mintButton("📈 วิเคราะห์ยอดขาย");
        stepsBar.add(btAnalytics);
        btAnalytics.addActionListener(e -> card.show(pages, "analytics"));


        /* ---------- Pages ---------- */
        pages.add(buildDashboardPage(), "dashboard");
        pages.add(buildProductPage(), "product");
        pages.add(buildSortPage(), "sort");
        pages.add(buildWashPage(), "wash");
        pages.add(buildSqueezePage(), "squeeze");
        pages.add(buildFilterPage(), "filter");
        pages.add(buildPasteurizePage(), "pasteurize");
        pages.add(buildPackPage(), "pack");
        pages.add(buildShipPage(), "ship");
        pages.add(buildBranchPage(), "branch");
        pages.add(buildOrdersPage(), "orders");
        pages.add(buildAlertsPage(), "alerts");
        pages.add(buildChatPage(),   "chat");
        pages.add(buildAnalyticsPage(), "analytics");



        root.add(pages, BorderLayout.CENTER);

        card.show(pages, "dashboard");
    }

    private void showPageForStep(String step){
        switch (step){
            case "หน้าหลัก" -> card.show(pages, "dashboard");
            case "คัดแยกผลส้ม" -> card.show(pages, "sort");
            case "ล้าง" -> card.show(pages, "wash");
            case "คั้นน้ำส้ม" -> card.show(pages, "squeeze");
            case "กรองน้ำส้ม" -> card.show(pages, "filter");
            case "พาสเจอร์ไรซ์" -> card.show(pages, "pasteurize");
            case "บรรจุ" -> card.show(pages, "pack");
            case "ขนส่ง" -> card.show(pages, "ship");
        }
    }

    /* ================= Dashboard ================= */
    private JComponent buildDashboardPage() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBorder(new EmptyBorder(8, 12, 12, 12));
        page.setBackground(new Color(253, 246, 227));

        JLabel title = new JLabel("ภาพรวมกระบวนการผลิตน้ำส้ม");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setForeground(new Color(245, 130, 31));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        page.add(title);
        page.add(Box.createVerticalStrut(8));

        JPanel kpiGrid = new JPanel(new GridLayout(2, 3, 12, 8));
        kpiGrid.setOpaque(false);
        kpiGrid.add(kpiCard("ผลผลิตที่คัดผ่าน", "รวม: 1000 ลูก"));
        kpiGrid.add(kpiCard("ล้างสะอาดแล้ว", "950 ลูก"));
        kpiGrid.add(kpiCard("น้ำส้มที่คั้นได้", "รวม: 420 ลิตร"));
        kpiGrid.add(kpiCard("พาสเจอร์ไรซ์", "75°C / 15 นาที"));
        kpiGrid.add(kpiCard("บรรจุขวด", "พร้อมส่ง: 370 ลิตร"));
        kpiGrid.add(kpiCard("กล่องพร้อมส่ง", "80 กล่อง"));
        page.add(kpiGrid);
        page.add(Box.createVerticalStrut(10));

        JLabel head = new JLabel("รายงานล็อตล่าสุด");
        head.setOpaque(true);
        head.setBackground(new Color(234, 237, 103));
        head.setBorder(new EmptyBorder(8, 12, 8, 12));
        page.add(head);

        JTable table = new JTable(buildBatchModel());
        table.setRowHeight(24);
        JScrollPane sp = new JScrollPane(table);
        page.add(sp);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
    private DefaultTableModel buildBatchModel() {
        String[] cols = {"ล็อต","สายพันธุ์","จำนวน","สภาพ","วันที่"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        Object[][] rows = {
                {"G-001","ส้มเขียวหวาน",1000,"ผ่าน","2025-07-30"},
                {"G-002","ส้มโชกุน",850,"ผ่าน","2025-07-31"},
                {"G-003","ส้มสายน้ำผึ้ง",920,"บางส่วนเสีย","2025-08-01"},
                {"G-004","ส้มเช้ง",780,"ผ่าน","2025-08-02"}
        };
        for (Object[] r : rows) m.addRow(r);
        return m;
    }

    /* ================= Product Page ================= */
    private JComponent buildProductPage() {
        JPanel page = new JPanel(new BorderLayout(10,10));
        page.setBackground(new Color(250, 250, 245));
        page.setBorder(new EmptyBorder(20,20,20,20));

        JLabel title = new JLabel("🧃 จัดการสินค้า");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        title.setForeground(new Color(245,130,31));
        page.add(title, BorderLayout.NORTH);

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        navButtons.setBackground(new Color(255, 250, 230));
        JButton btBranch = navButton(" จัดการสาขา");
        JButton btOrder  = navButton(" ออเดอร์ / การสั่งซื้อ");
        JButton btAlert  = navButton(" แจ้งเตือน");
        JButton btChat   = navButton(" แชทลูกค้า");
        for (JButton b : new JButton[]{btBranch, btOrder, btAlert, btChat}) navButtons.add(b);
        btBranch.addActionListener(e -> card.show(pages, "branch"));
        btOrder .addActionListener(e -> card.show(pages, "orders"));
        btAlert .addActionListener(e -> card.show(pages, "alerts"));
        btChat  .addActionListener(e -> card.show(pages, "chat"));
        page.add(navButtons, BorderLayout.NORTH);

        

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,8));
        filterPanel.setBackground(new Color(255,250,235));
        filterPanel.setBorder(BorderFactory.createTitledBorder("ตัวกรองหมวดสินค้า"));
        JComboBox<String> c1 = new JComboBox<>(new String[]{"น้ำส้มสด","สมูทตี้","คอมโบ"});
        JComboBox<String> c2 = new JComboBox<>(new String[]{"คั้นสด","พาสเจอร์ไรซ์"});
        JComboBox<String> c3 = new JComboBox<>(new String[]{"ไซส์เล็ก","ไซส์กลาง","ไซส์ใหญ่"});
        JButton add = new JButton("+ เพิ่มสินค้า");
        JButton del = new JButton("- ลบสินค้า");
        styleButton(add, new Color(67, 226, 206));
        styleButton(del, new Color(255, 120, 120));
        filterPanel.add(new JLabel("หมวดหลัก:")); filterPanel.add(c1);
        filterPanel.add(new JLabel("หมวดย่อย:")); filterPanel.add(c2);
        filterPanel.add(new JLabel("ชนิดสินค้า:")); filterPanel.add(c3);
        filterPanel.add(add); filterPanel.add(del);

        String[] cols = {"รหัส","ชื่อสินค้า","หมวดใหญ่","หมวดย่อย","สายพันธุ์","ราคา","สต็อก","สถานะ"};
        DefaultTableModel model = new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){ return false; } };
        JTable tb = new JTable(model); tb.setRowHeight(28);
        tb.setGridColor(new Color(230,230,230));
        tb.setSelectionBackground(new Color(255,220,170));
        tb.setFont(tb.getFont().deriveFont(16f));
        JScrollPane sp = new JScrollPane(tb);
        sp.setBorder(BorderFactory.createTitledBorder("รายการสินค้า"));

        JPanel center = new JPanel(new BorderLayout(10,10));
        center.add(filterPanel, BorderLayout.NORTH);
        center.add(sp, BorderLayout.CENTER);
        page.add(center, BorderLayout.CENTER);

        model.addRow(new Object[]{"P001","น้ำส้มคั้นสด 250ml","น้ำส้มสด","คั้นสด","ส้มเขียวหวาน",35,50,"จำหน่าย"});
        model.addRow(new Object[]{"P002","น้ำส้มพาสเจอร์ไรซ์ 350ml","น้ำส้มสด","พาสเจอร์ไรซ์","ส้มซันคิสต์",45,60,"จำหน่าย"});

        add.addActionListener(e -> {
            JTextField code=new JTextField(), name=new JTextField(), price=new JTextField(), stock=new JTextField();
            Object[] msg={"รหัส:",code,"ชื่อ:",name,"ราคา:",price,"สต็อก:",stock};
            if (JOptionPane.showConfirmDialog(page,msg,"เพิ่มสินค้าใหม่",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
                model.addRow(new Object[]{code.getText(),name.getText(),c1.getSelectedItem(),c2.getSelectedItem(),"ส้มเขียวหวาน",price.getText(),stock.getText(),"จำหน่าย"});
        });
        del.addActionListener(e -> { int r=tb.getSelectedRow(); if(r>=0) model.removeRow(r); });

        return page;
    }

    /* ================= Sort Page (คัดแยกผลส้ม) ================= */
    private JComponent buildSortPage(){
        JPanel page = new JPanel(new BorderLayout(10,10));
        page.setBackground(new Color(253,246,227));
        page.setBorder(new EmptyBorder(16,16,16,16));

        JLabel head = new JLabel("1. คัดแยกผลส้ม");
        head.setForeground(new Color(245,130,31));
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        page.add(head, BorderLayout.NORTH);

        JPanel card = roundedCard();
        page.add(card, BorderLayout.CENTER);

        // ฟอร์ม
        JPanel form = new JPanel(new GridBagLayout()); form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets=new Insets(6,6,6,6); gc.anchor=GridBagConstraints.WEST; gc.fill=GridBagConstraints.HORIZONTAL; gc.weightx=1;
        int r=0;

        JComboBox<String> cbVariety = new JComboBox<>(new String[]{"-เลือกสายพันธุ์-","ส้มเขียวหวาน","ส้มโชกุน","ส้มสายน้ำผึ้ง","ส้มเช้ง","Valencia"});
        JTextField tfLot   = new JTextField("G-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
        JTextField tfDate  = new JTextField(LocalDate.now().toString());
        JTextField tfTotal = new JTextField();
        JTextField tfGood  = new JTextField();
        JTextField tfBad   = new JTextField();

        addRow(form, gc, r++, "เลือกสายพันธุ์ส้ม:", cbVariety);
        addRow(form, gc, r++, "รหัสล็อต (Lot id):", tfLot);
        addRow(form, gc, r++, "วันที่คัดแยก:", tfDate);
        addRow(form, gc, r++, "จำนวนผลส้มทั้งหมด:", tfTotal);
        addRow(form, gc, r++, "จำนวนผลดี:", tfGood);
        addRow(form, gc, r++, "จำนวนผลเสีย:", tfBad);

        JButton btnSave = new JButton("บันทึกข้อมูล"); styleButton(btnSave,new Color(245,145,29));
        JButton btnDelete = new JButton("ลบรายการ");  styleButton(btnDelete,new Color(255,120,120));

        JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
        formWrap.add(form, BorderLayout.CENTER);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
        right.add(btnSave); right.add(btnDelete);
        formWrap.add(right, BorderLayout.EAST);
        card.add(formWrap, BorderLayout.NORTH);

        // ตาราง
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ล็อต","สายพันธุ์","จำนวน","ดี","เสีย","คัดทิ้ง(%)","วันที่"},0){
            @Override public boolean isCellEditable(int row, int col){ return false; }
        };
        JTable table = new JTable(model); table.setRowHeight(26);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("รายการที่บันทึก"));
        card.add(sp, BorderLayout.CENTER);

        // บันทึก
        btnSave.addActionListener(e -> {
            String v = (String) cbVariety.getSelectedItem();
            if (v == null || v.startsWith("-")) { msg("กรุณาเลือกสายพันธุ์"); return; }
            try {
                int total = Integer.parseInt(tfTotal.getText().trim());
                int good  = Integer.parseInt(tfGood.getText().trim());
                int bad   = Integer.parseInt(tfBad.getText().trim());
                double pct = total==0 ? 0 : bad*100.0/total;
                model.addRow(new Object[]{tfLot.getText().trim(), v, total, good, bad, String.format("%.1f", pct), tfDate.getText().trim()});
                tfTotal.setText(""); tfGood.setText(""); tfBad.setText("");
            } catch (NumberFormatException ex){ msg("จำนวนต้องเป็นตัวเลข"); }
        });

        // ลบ
        Runnable deleteRun = () -> {
            int[] rows = table.getSelectedRows();
            if (rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
            if (JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
            for (int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
        };
        btnDelete.addActionListener(e -> deleteRun.run());
        table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
        table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
        JPopupMenu pop = new JPopupMenu(); JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
        mi.addActionListener(e -> deleteRun.run()); pop.add(mi); table.setComponentPopupMenu(pop);

        return page;
    }

    /* ================= Wash Page (ล้าง) ================= */
    private JComponent buildWashPage(){
        JPanel page = new JPanel(new BorderLayout(10,10));
        page.setBackground(new Color(253,246,227));
        page.setBorder(new EmptyBorder(16,16,16,16));

        JLabel head = new JLabel("2. ล้าง");
        head.setForeground(new Color(245,130,31));
        head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
        page.add(head, BorderLayout.NORTH);

        JPanel card = roundedCard();
        page.add(card, BorderLayout.CENTER);

        // ฟอร์ม
        JPanel form = new JPanel(new GridBagLayout()); form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets=new Insets(6,6,6,6); gc.anchor=GridBagConstraints.WEST; gc.fill=GridBagConstraints.HORIZONTAL; gc.weightx=1;
        int r=0;

        JTextField tfMethod = new JTextField(); tfMethod.setToolTipText("เช่น ล้างน้ำไหล/แช่-ฉีดแรงดัน/โอโซน");
        JTextField tfQty    = new JTextField();
        JTextField tfDate   = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        JTextField tfLot    = new JTextField("W-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");

        addRow(form, gc, r++, "เลือกวิธีการล้าง:", tfMethod);
        addRow(form, gc, r++, "จำนวนผลส้มที่ล้าง:", tfQty);
        addRow(form, gc, r++, "วันที่ล้าง:", tfDate);
        addRow(form, gc, r++, "รหัสล็อตที่ล้าง:", tfLot);

        JButton btnSave = new JButton("บันทึกข้อมูล"); styleButton(btnSave,new Color(245,145,29));
        JButton btnDelete = new JButton("ลบรายการ");  styleButton(btnDelete,new Color(255,120,120));

        JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
        formWrap.add(form, BorderLayout.CENTER);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
        right.add(btnSave); right.add(btnDelete);
        formWrap.add(right, BorderLayout.EAST);
        card.add(formWrap, BorderLayout.NORTH);

        // ตาราง
        DefaultTableModel model = new DefaultTableModel(new String[]{"ล็อต","วิธีการล้าง","จำนวนที่ล้าง","วันที่"},0){
            @Override public boolean isCellEditable(int row, int col){ return false; }
        };
        JTable table = new JTable(model); table.setRowHeight(26);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("รายการล้าง"));
        card.add(sp, BorderLayout.CENTER);

        // บันทึก
        btnSave.addActionListener(e -> {
            String method=tfMethod.getText().trim(), qtyStr=tfQty.getText().trim(), date=tfDate.getText().trim(), lot=tfLot.getText().trim();
            if(method.isEmpty()||qtyStr.isEmpty()||date.isEmpty()||lot.isEmpty()){ msg("กรอกข้อมูลให้ครบทุกช่อง"); return; }
            try{
                int qty=Integer.parseInt(qtyStr);
                model.addRow(new Object[]{lot,method,qty,date});
                tfQty.setText(""); tfMethod.requestFocus();
            }catch(NumberFormatException ex){ msg("จำนวนต้องเป็นตัวเลข"); }
        });

        // ลบ
        Runnable deleteRun = () -> {
            int[] rows = table.getSelectedRows();
            if (rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
            if (JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
            for (int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
        };
        btnDelete.addActionListener(e -> deleteRun.run());
        table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
        table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
        JPopupMenu pop = new JPopupMenu(); JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
        mi.addActionListener(e -> deleteRun.run()); pop.add(mi); table.setComponentPopupMenu(pop);

        return page;
    }
    /* ================= Squeeze Page (คั้นน้ำส้ม) ================= */
private JComponent buildSqueezePage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(253,246,227));
    page.setBorder(new EmptyBorder(16,16,16,16));

    JLabel head = new JLabel("3. คั้นน้ำส้ม");
    head.setForeground(new Color(245,130,31));
    head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
    page.add(head, BorderLayout.NORTH);

    JPanel card = roundedCard();
    page.add(card, BorderLayout.CENTER);

    // ฟอร์ม
    JPanel form = new JPanel(new GridBagLayout()); form.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets=new Insets(6,6,6,6); gc.anchor=GridBagConstraints.WEST; gc.fill=GridBagConstraints.HORIZONTAL; gc.weightx=1;
    int r=0;

    JTextField tfLot   = new JTextField("S-" + java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
    JTextField tfFruits= new JTextField(); // จำนวนผลส้มที่ใช้
    JTextField tfLiter = new JTextField(); // ปริมาณน้ำส้ม (ลิตร)
    JTextField tfDate  = new JTextField(java.time.LocalDate.now().toString());

    addRow(form, gc, r++, "รหัสล็อตที่คั้น:", tfLot);
    addRow(form, gc, r++, "จำนวนผลส้มที่ใช้:", tfFruits);
    addRow(form, gc, r++, "ปริมาณน้ำส้มที่ได้ (ลิตร):", tfLiter);
    addRow(form, gc, r++, "วันที่คั้น:", tfDate);

    JButton btnSave   = new JButton("บันทึกข้อมูล"); styleButton(btnSave,new Color(245,145,29));
    JButton btnDelete = new JButton("ลบรายการ");    styleButton(btnDelete,new Color(255,120,120));

    JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
    right.add(btnSave); right.add(btnDelete);
    formWrap.add(right, BorderLayout.EAST);
    card.add(formWrap, BorderLayout.NORTH);

    // ตาราง
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ล็อต","จำนวนผลที่ใช้","ปริมาณน้ำส้ม (ลิตร)","วันที่"},0){
        @Override public boolean isCellEditable(int row,int col){ return false; }
    };
    JTable table = new JTable(model);
    table.setRowHeight(26);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(table);
    sp.setBorder(BorderFactory.createTitledBorder("รายการคั้นน้ำส้ม"));
    card.add(sp, BorderLayout.CENTER);

    // บันทึก
    btnSave.addActionListener(e -> {
        String lot=tfLot.getText().trim(), fStr=tfFruits.getText().trim(), lStr=tfLiter.getText().trim(), date=tfDate.getText().trim();
        if(lot.isEmpty()||fStr.isEmpty()||lStr.isEmpty()||date.isEmpty()){ msg("กรอกข้อมูลให้ครบทุกช่อง"); return; }
        try{
            int fruits = Integer.parseInt(fStr);
            double liters = Double.parseDouble(lStr);
            model.addRow(new Object[]{lot, fruits, liters, date});
            tfFruits.setText(""); tfLiter.setText(""); tfFruits.requestFocus();
        }catch(NumberFormatException ex){ msg("จำนวนผล/ลิตร ต้องเป็นตัวเลข"); }
    });

    // ลบ (รองรับหลายแถว, ปุ่ม Delete, เมนูคลิกขวา)
    Runnable deleteRun = () -> {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
    };
    btnDelete.addActionListener(e -> deleteRun.run());
    table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
    table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e -> deleteRun.run());
    pop.add(mi);
    table.setComponentPopupMenu(pop);

    return page;
}
/* ================= Filter Page (กรองน้ำส้ม) ================= */
private JComponent buildFilterPage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(253,246,227));
    page.setBorder(new EmptyBorder(16,16,16,16));

    JLabel head = new JLabel("4. กรองน้ำส้ม");
    head.setForeground(new Color(245,130,31));
    head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
    page.add(head, BorderLayout.NORTH);

    JPanel card = roundedCard();
    page.add(card, BorderLayout.CENTER);

    // ----- ฟอร์ม -----
    JPanel form = new JPanel(new GridBagLayout()); form.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(6,6,6,6);
    gc.anchor = GridBagConstraints.WEST;
    gc.fill   = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;
    int r = 0;

    JTextField tfLot   = new JTextField("F-" + java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
    JTextField tfDate  = new JTextField(java.time.LocalDate.now().toString());
    JTextField tfBefore= new JTextField(); // ปริมาณก่อนกรอง (ลิตร)
    JTextField tfAfter = new JTextField(); // ปริมาณหลังกรอง (ลิตร)
    JTextField tfType  = new JTextField(); // ประเภทการกรอง

    addRow(form, gc, r++, "รหัสล็อตที่กรอง:", tfLot);
    addRow(form, gc, r++, "วันทำกรอง:", tfDate);
    addRow(form, gc, r++, "ปริมาณน้ำส้มก่อนกรอง (ลิตร):", tfBefore);
    addRow(form, gc, r++, "ปริมาณน้ำส้มหลังกรอง (ลิตร):", tfAfter);
    addRow(form, gc, r++, "ประเภทการกรอง:", tfType);

    JButton btnSave   = new JButton("บันทึกข้อมูล"); styleButton(btnSave, new Color(245,145,29));
    JButton btnDelete = new JButton("ลบรายการ");    styleButton(btnDelete, new Color(255,120,120));

    JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
    right.add(btnSave); right.add(btnDelete);
    formWrap.add(right, BorderLayout.EAST);
    card.add(formWrap, BorderLayout.NORTH);

    // ----- ตาราง -----
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ล็อต","ก่อนกรอง(ลิตร)","หลังกรอง(ลิตร)","ประเภทการกรอง","วันที่"}, 0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    JTable table = new JTable(model);
    table.setRowHeight(26);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(table);
    sp.setBorder(BorderFactory.createTitledBorder("รายการกรองน้ำส้ม"));
    card.add(sp, BorderLayout.CENTER);

    // บันทึก
    btnSave.addActionListener(e -> {
        String lot=tfLot.getText().trim(), date=tfDate.getText().trim(),
               type=tfType.getText().trim(), bStr=tfBefore.getText().trim(), aStr=tfAfter.getText().trim();
        if(lot.isEmpty()||date.isEmpty()||type.isEmpty()||bStr.isEmpty()||aStr.isEmpty()){
            msg("กรอกข้อมูลให้ครบทุกช่อง"); return;
        }
        try{
            double before = Double.parseDouble(bStr);
            double after  = Double.parseDouble(aStr);
            model.addRow(new Object[]{lot, before, after, type, date});
            tfBefore.setText(""); tfAfter.setText(""); tfType.requestFocus();
        }catch(NumberFormatException ex){ msg("ปริมาณต้องเป็นตัวเลข"); }
    });

    // ลบ (หลายแถว + ปุ่ม Delete + เมนูคลิกขวา)
    Runnable deleteRun = () -> {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
    };
    btnDelete.addActionListener(e -> deleteRun.run());
    table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
    table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e -> deleteRun.run());
    pop.add(mi);
    table.setComponentPopupMenu(pop);

    return page;
}
/* ================= Pasteurize Page (พาสเจอร์ไรซ์) ================= */
private JComponent buildPasteurizePage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(253,246,227));
    page.setBorder(new EmptyBorder(16,16,16,16));

    JLabel head = new JLabel("5. พาสเจอร์ไรซ์");
    head.setForeground(new Color(245,130,31));
    head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
    page.add(head, BorderLayout.NORTH);

    JPanel card = roundedCard();
    page.add(card, BorderLayout.CENTER);

    // ---------- ฟอร์ม ----------
    JPanel form = new JPanel(new GridBagLayout());
    form.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(6,6,6,6);
    gc.anchor = GridBagConstraints.WEST;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;
    int r = 0;

    JTextField tfLot   = new JTextField("P-" + java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
    JTextField tfTemp  = new JTextField(); // อุณหภูมิ
    JTextField tfTime  = new JTextField(); // เวลา (วินาที)
    JTextField tfDate  = new JTextField(java.time.LocalDate.now().toString());
    JTextField tfBefore= new JTextField(); // ปริมาณก่อนพาสเจอร์ไรซ์
    JTextField tfAfter = new JTextField(); // ปริมาณหลังพาสเจอร์ไรซ์

    addRow(form, gc, r++, "รหัสล็อตที่พาสเจอร์ไรซ์:", tfLot);
    addRow(form, gc, r++, "อุณหภูมิ (°C):", tfTemp);
    addRow(form, gc, r++, "ระยะเวลาให้ความร้อน (วินาที):", tfTime);
    addRow(form, gc, r++, "วันที่พาสเจอร์ไรซ์:", tfDate);
    addRow(form, gc, r++, "ปริมาณก่อนพาสเจอร์ไรซ์ (ลิตร):", tfBefore);
    addRow(form, gc, r++, "ปริมาณหลังพาสเจอร์ไรซ์ (ลิตร):", tfAfter);

    JButton btnSave   = new JButton("บันทึกข้อมูล"); styleButton(btnSave,new Color(245,145,29));
    JButton btnDelete = new JButton("ลบรายการ");    styleButton(btnDelete,new Color(255,120,120));

    JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
    right.add(btnSave); right.add(btnDelete);
    formWrap.add(right, BorderLayout.EAST);
    card.add(formWrap, BorderLayout.NORTH);

    // ---------- ตาราง ----------
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ล็อต","ก่อน(ลิตร)","อุณหภูมิ(°C)","เวลา(วินาที)","หลัง(ลิตร)","วันที่"},0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    JTable table = new JTable(model);
    table.setRowHeight(26);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(table);
    sp.setBorder(BorderFactory.createTitledBorder("รายการพาสเจอร์ไรซ์"));
    card.add(sp, BorderLayout.CENTER);

    // ---------- บันทึก ----------
    btnSave.addActionListener(e -> {
        String lot=tfLot.getText().trim(), temp=tfTemp.getText().trim(), time=tfTime.getText().trim(),
               date=tfDate.getText().trim(), bStr=tfBefore.getText().trim(), aStr=tfAfter.getText().trim();
        if(lot.isEmpty()||temp.isEmpty()||time.isEmpty()||date.isEmpty()||bStr.isEmpty()||aStr.isEmpty()){
            msg("กรอกข้อมูลให้ครบทุกช่อง"); return;
        }
        try{
            double before = Double.parseDouble(bStr);
            double after  = Double.parseDouble(aStr);
            double t      = Double.parseDouble(temp);
            int    sec    = Integer.parseInt(time);
            model.addRow(new Object[]{lot,before,t,sec,after,date});
            tfBefore.setText(""); tfAfter.setText(""); tfTemp.setText(""); tfTime.setText("");
            tfBefore.requestFocus();
        }catch(NumberFormatException ex){ msg("ค่าตัวเลขไม่ถูกต้อง"); }
    });

    // ---------- ลบ ----------
    Runnable deleteRun = () -> {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",
                JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
    };
    btnDelete.addActionListener(e -> deleteRun.run());
    table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
    table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e -> deleteRun.run());
    pop.add(mi);
    table.setComponentPopupMenu(pop);

    return page;
}
/* ================= Pack Page (บรรจุ) ================= */
private JComponent buildPackPage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(253,246,227));
    page.setBorder(new EmptyBorder(16,16,16,16));

    JLabel head = new JLabel("6. บรรจุ");
    head.setForeground(new Color(245,130,31));
    head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
    page.add(head, BorderLayout.NORTH);

    JPanel card = roundedCard();
    page.add(card, BorderLayout.CENTER);

    // ---------- ฟอร์ม ----------
    JPanel form = new JPanel(new GridBagLayout());
    form.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(6,6,6,6);
    gc.anchor = GridBagConstraints.WEST;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;
    int r = 0;

    JTextField tfLot   = new JTextField("PK-" + java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
    JTextField tfQty   = new JTextField(); // จำนวนบรรจุ (หน่วย)
    JTextField tfDate  = new JTextField(java.time.LocalDate.now().toString());

    JComboBox<String> cbType = new JComboBox<>(new String[]{
            "ขวด 250 ml","ขวด 350 ml","ขวด 500 ml","ขวด 1 L","กล่อง 12 ขวด"
    });
    JComboBox<String> cbSeal = new JComboBox<>(new String[]{"ปิดสนิท","ไม่สนิท"});

    addRow(form, gc, r++, "รหัสล็อตที่บรรจุ:", tfLot);
    addRow(form, gc, r++, "จำนวนบรรจุ (หน่วย):", tfQty);
    addRow(form, gc, r++, "วันที่บรรจุ:", tfDate);
    addRow(form, gc, r++, "ประเภทบรรจุ:", cbType);
    addRow(form, gc, r++, "ปิดฝาสนิทหรือไม่:", cbSeal);

    JButton btnSave   = new JButton("บันทึกข้อมูล"); styleButton(btnSave, new Color(245,145,29));
    JButton btnDelete = new JButton("ลบรายการ");    styleButton(btnDelete, new Color(255,120,120));

    JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
    right.add(btnSave); right.add(btnDelete);
    formWrap.add(right, BorderLayout.EAST);
    card.add(formWrap, BorderLayout.NORTH);

    // ---------- ตาราง ----------
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ล็อต","ประเภทบรรจุ","จำนวน","ปิดฝาสนิท","วันที่"}, 0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    JTable table = new JTable(model);
    table.setRowHeight(26);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(table);
    sp.setBorder(BorderFactory.createTitledBorder("รายการบรรจุ"));
    card.add(sp, BorderLayout.CENTER);

    // ---------- บันทึก ----------
    btnSave.addActionListener(e -> {
        String lot=tfLot.getText().trim(), qtyStr=tfQty.getText().trim(), date=tfDate.getText().trim();
        if(lot.isEmpty()||qtyStr.isEmpty()||date.isEmpty()){
            msg("กรอกข้อมูลให้ครบทุกช่อง"); return;
        }
        try{
            int qty = Integer.parseInt(qtyStr);
            model.addRow(new Object[]{lot, cbType.getSelectedItem(), qty, cbSeal.getSelectedItem(), date});
            tfQty.setText(""); tfQty.requestFocus();
        }catch(NumberFormatException ex){ msg("จำนวนบรรจุต้องเป็นตัวเลข"); }
    });

    // ---------- ลบ (หลายแถว + Delete + เมนูคลิกขวา) ----------
    Runnable deleteRun = () -> {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",
                JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
    };
    btnDelete.addActionListener(e -> deleteRun.run());
    table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
    table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e -> deleteRun.run());
    pop.add(mi);
    table.setComponentPopupMenu(pop);

    return page;
}
/* ================= Ship Page (ขนส่ง) ================= */
private JComponent buildShipPage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(253,246,227));
    page.setBorder(new EmptyBorder(16,16,16,16));

    JLabel head = new JLabel("7. ขนส่ง");
    head.setForeground(new Color(245,130,31));
    head.setFont(head.getFont().deriveFont(Font.BOLD, 18f));
    page.add(head, BorderLayout.NORTH);

    JPanel card = roundedCard();
    page.add(card, BorderLayout.CENTER);

    // ---------- ฟอร์ม ----------
    JPanel form = new JPanel(new GridBagLayout());
    form.setOpaque(false);
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(6,6,6,6);
    gc.anchor = GridBagConstraints.WEST;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;
    int r = 0;

    JTextField tfLot   = new JTextField("SH-" + java.time.LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")) + "-A");
    JTextField tfDriver= new JTextField();            // ชื่อพนักงานขับรถ
    JTextField tfDate  = new JTextField(java.time.LocalDate.now().toString());
    JTextField tfPlate = new JTextField();            // ป้ายทะเบียน
    JTextField tfDest  = new JTextField();            // ปลายทาง

    addRow(form, gc, r++, "รหัสล็อตที่ขนส่ง:", tfLot);
    addRow(form, gc, r++, "ชื่อพนักงานขับรถ:", tfDriver);
    addRow(form, gc, r++, "วันที่ออกขนส่ง:", tfDate);
    addRow(form, gc, r++, "ป้ายทะเบียนรถ:", tfPlate);
    addRow(form, gc, r++, "ปลายทาง:", tfDest);

    JButton btnSave   = new JButton("บันทึกข้อมูล"); styleButton(btnSave, new Color(245,145,29));
    JButton btnDelete = new JButton("ลบรายการ");    styleButton(btnDelete, new Color(255,120,120));

    JPanel formWrap = new JPanel(new BorderLayout()); formWrap.setOpaque(false);
    formWrap.add(form, BorderLayout.CENTER);
    JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT,10,6)); right.setOpaque(false);
    right.add(btnSave); right.add(btnDelete);
    formWrap.add(right, BorderLayout.EAST);
    card.add(formWrap, BorderLayout.NORTH);

    // ---------- ตาราง ----------
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ล็อต","ป้ายทะเบียน","พนักงานขับรถ","ปลายทาง","วันที่"}, 0){
        @Override public boolean isCellEditable(int r,int c){ return false; }
    };
    JTable table = new JTable(model);
    table.setRowHeight(26);
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(table);
    sp.setBorder(BorderFactory.createTitledBorder("รายการขนส่ง"));
    card.add(sp, BorderLayout.CENTER);

    // ---------- บันทึก ----------
    btnSave.addActionListener(e -> {
        String lot=tfLot.getText().trim(), plate=tfPlate.getText().trim(),
               driver=tfDriver.getText().trim(), dest=tfDest.getText().trim(),
               date=tfDate.getText().trim();
        if(lot.isEmpty()||plate.isEmpty()||driver.isEmpty()||dest.isEmpty()||date.isEmpty()){
            msg("กรอกข้อมูลให้ครบทุกช่อง"); return;
        }
        model.addRow(new Object[]{lot, plate, driver, dest, date});
        tfPlate.setText(""); tfDest.setText(""); tfDriver.setText("");
        tfDriver.requestFocus();
    });

    // ---------- ลบ (หลายแถว + Delete + คลิกขวา) ----------
    Runnable deleteRun = () -> {
        int[] rows = table.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",
                JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(rows[i]);
    };
    btnDelete.addActionListener(e -> deleteRun.run());
    table.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "delRow");
    table.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e -> deleteRun.run());
    pop.add(mi);
    table.setComponentPopupMenu(pop);

    return page;
}
/* ================= Analytics Page (สวย ไม่ทับซ้อน) ================= */
private JComponent buildAnalyticsPage() {
    JPanel page = new JPanel(new BorderLayout(12,12));
    page.setBorder(new EmptyBorder(16,16,16,16));
    page.setBackground(new Color(253,246,227));

    // Header
    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);
    JLabel title = new JLabel("📈 วิเคราะห์ยอดขาย");
    title.setFont(title.getFont().deriveFont(Font.BOLD, 26f));
    title.setForeground(new Color(245,130,31));
    JButton btBack = mintButton("← กลับหน้าหลัก");
    btBack.addActionListener(e -> card.show(pages, "dashboard"));
    header.add(title, BorderLayout.WEST);
    header.add(btBack, BorderLayout.EAST);
    page.add(header, BorderLayout.NORTH);

    // Controls (อยู่บนสุดใต้หัวข้อ)
    JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    controls.setBackground(new Color(255,250,235));
    controls.setBorder(BorderFactory.createTitledBorder("ตัวเลือกการเปรียบเทียบ"));

    String[] modes = {"ยอดขาย 10 ปีย้อนหลัง (รวมทั้งหมด)","ยอดขายตามฤดูกาล (รวมทุกปี)"};
    JComboBox<String> cbMode = new JComboBox<>(modes);
    JButton btRefresh   = mintButton("รีเฟรช");
    JButton btExportCSV = mintButton("ส่งออก CSV");
    JButton btExportPNG = mintButton("บันทึก PNG");
    controls.add(new JLabel("โหมด:"));
    controls.add(cbMode);
    controls.add(btRefresh);
    controls.add(btExportCSV);
    controls.add(btExportPNG);

    page.add(controls, BorderLayout.BEFORE_FIRST_LINE);

    // Chart + wrap ให้มี padding ด้านใน
    JPanel chartWrap = new JPanel(new BorderLayout());
    chartWrap.setBackground(Color.WHITE);
    chartWrap.setBorder(new EmptyBorder(12,12,12,12));
    SalesAnalyticsPanel chart = new SalesAnalyticsPanel();
    chart.setPreferredSize(new Dimension(980, 380));
    chartWrap.add(chart, BorderLayout.CENTER);
    page.add(chartWrap, BorderLayout.CENTER);

    // Summary table (สูงคงที่)
    String[] cols = {"มิติ","ช่วง","ยอดขาย(บาท)"};
    DefaultTableModel summaryModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r,int c){return false;} };
    JTable summaryTable = new JTable(summaryModel);
    summaryTable.setRowHeight(26);
    summaryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    summaryTable.getColumnModel().getColumn(0).setPreferredWidth(120);
    summaryTable.getColumnModel().getColumn(1).setPreferredWidth(220);
    summaryTable.getColumnModel().getColumn(2).setPreferredWidth(180);
    JScrollPane summarySP = new JScrollPane(summaryTable);
    summarySP.setPreferredSize(new Dimension(100, 220));
    summarySP.setBorder(BorderFactory.createTitledBorder("ตารางสรุป"));
    page.add(summarySP, BorderLayout.SOUTH);

    // Render summary by mode
    Runnable render = () -> {
        String mode = (String) cbMode.getSelectedItem();
        chart.setMode(mode);
        chart.repaint();

        summaryModel.setRowCount(0);
        if (chart.isSeasonMode()) {
            java.util.Map<String, Double> season = chart.getSeasonTotals();
            summaryModel.addRow(new Object[]{"ฤดูกาล","ร้อน (มี.ค.–พ.ค.)",  String.format("%,.0f", season.get("ร้อน"))});
            summaryModel.addRow(new Object[]{"ฤดูกาล","ฝน (มิ.ย.–ต.ค.)",   String.format("%,.0f", season.get("ฝน"))});
            summaryModel.addRow(new Object[]{"ฤดูกาล","หนาว (พ.ย.–ก.พ.)", String.format("%,.0f", season.get("หนาว"))});
        } else {
            java.util.Map<Integer, Double> years = chart.getYearly();
            java.util.List<Integer> keys = new java.util.ArrayList<>(years.keySet());
            java.util.Collections.sort(keys);
            for (Integer y : keys) {
                summaryModel.addRow(new Object[]{"ปี", y.toString(), String.format("%,.0f", years.get(y))});
            }
        }
    };
    render.run();

    btRefresh.addActionListener(e -> render.run());
    cbMode.addActionListener(e -> render.run());

    btExportCSV.addActionListener(e -> {
        try {
            java.io.File f = new java.io.File("analytics_export.csv");
            try (java.io.PrintWriter pw = new java.io.PrintWriter(f, java.nio.charset.StandardCharsets.UTF_8)) {
                pw.println("dimension,period,sales");
                for (int i=0;i<summaryModel.getRowCount();i++){
                    pw.println(summaryModel.getValueAt(i,0)+","+summaryModel.getValueAt(i,1)+","+summaryModel.getValueAt(i,2));
                }
            }
            msg("บันทึกไฟล์: " + f.getAbsolutePath());
        } catch (Exception ex) {
            msg("บันทึก CSV ไม่สำเร็จ: " + ex.getMessage());
        }
    });

    btExportPNG.addActionListener(e -> {
        try {
            java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(chart.getWidth(), chart.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2 = img.createGraphics();
            chart.printAll(g2);
            g2.dispose();
            java.io.File f = new java.io.File("analytics_chart.png");
            javax.imageio.ImageIO.write(img, "png", f);
            msg("บันทึกภาพ: " + f.getAbsolutePath());
        } catch (Exception ex) {
            msg("บันทึก PNG ไม่สำเร็จ: " + ex.getMessage());
        }
    });

    return page;
}

/* ===== กราฟวิเคราะห์ยอดขาย (Java2D) — ป้ายไม่ชน, เส้นอ่านง่าย ===== */
private static class SalesAnalyticsPanel extends JPanel {
    private final java.util.Map<Integer, Double> salesByYear = new java.util.LinkedHashMap<>();
    private final java.util.Map<String, Double> salesBySeason = new java.util.LinkedHashMap<>();
    private boolean seasonMode = false; // false = 10 ปี (Line), true = ฤดูกาล (Bar)

    SalesAnalyticsPanel() {
        setBackground(Color.WHITE);
        generateDemoData();
        computeSeasons();
    }

    void setMode(String modeText){ this.seasonMode = (modeText != null && modeText.contains("ฤดูกาล")); }
    boolean isSeasonMode(){ return seasonMode; }
    java.util.Map<Integer, Double> getYearly(){ return salesByYear; }
    java.util.Map<String, Double> getSeasonTotals(){ return salesBySeason; }

    // Mock 10 ปี
    private void generateDemoData(){
        int year = java.time.LocalDate.now().getYear();
        java.util.Random rnd = new java.util.Random(42);
        double base = 1_200_000;
        for (int y = year-9; y <= year; y++){
            double trend = (y - (year-9)) * 40_000;
            double noise = (rnd.nextDouble() - 0.5) * 120_000;
            salesByYear.put(y, Math.max(200_000, base + trend + noise));
        }
    }
    private void computeSeasons(){
        double hot=0, rain=0, cold=0;
        java.util.Random rnd = new java.util.Random(99);
        for (int y: salesByYear.keySet()){
            double yearTotal = salesByYear.get(y);
            double[] monthWeight = new double[12];
            for (int m=0;m<12;m++){
                double w = 1.0 + (m==10||m==11||m==0? 0.15: 0) + (rnd.nextDouble()-0.5)*0.08;
                monthWeight[m] = Math.max(0.6, w);
            }
            double sumW = java.util.Arrays.stream(monthWeight).sum();
            for (int m=0;m<12;m++){
                double mn = yearTotal * (monthWeight[m]/sumW);
                int mo = m+1;
                if (mo>=3 && mo<=5) hot += mn;       // มี.ค.–พ.ค.
                else if (mo>=6 && mo<=10) rain += mn;// มิ.ย.–ต.ค.
                else cold += mn;                      // พ.ย.–ก.พ.
            }
        }
        salesBySeason.put("ร้อน", hot);
        salesBySeason.put("ฝน", rain);
        salesBySeason.put("หนาว", cold);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // พื้นหลังกรอบ
        g2.setColor(new Color(250,250,248));
        g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);

        // Layout พื้นที่กราฟ
        int left = 90, right = 40, top = 60, bottom = 70; // กว้างขึ้น กันซ้อน
        int x = left, y = top, w = getWidth()-left-right, h = getHeight()-top-bottom;

        // Title
        g2.setColor(new Color(245,130,31));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
        g2.drawString(seasonMode? "เปรียบเทียบยอดขายตามฤดูกาล (บาท) — รวมทุกปี"
                                : "แนวโน้มยอดขาย 10 ปีย้อนหลัง (บาท)", x, y-26);

        // grid
        g2.setColor(new Color(232,232,232));
        for (int i=0;i<=5;i++){
            int yy = y + (int)(h * i/5.0);
            g2.drawLine(x, yy, x+w, yy);
        }

        if (seasonMode) drawBarSeasons(g2, x, y, w, h);
        else drawLine10Years(g2, x, y, w, h);

        // แกน X เส้นฐาน
        g2.setColor(new Color(180,180,180));
        g2.drawLine(x, y+h, x+w, y+h);

        g2.dispose();
    }

    private void drawLine10Years(Graphics2D g2, int x, int y, int w, int h){
        double max = salesByYear.values().stream().mapToDouble(Double::doubleValue).max().orElse(1d);
        java.util.List<Integer> years = new java.util.ArrayList<>(salesByYear.keySet());
        java.util.Collections.sort(years);
        int n = years.size();

        // Y labels
        g2.setColor(new Color(120,120,120));
        g2.setFont(g2.getFont().deriveFont(12f));
        for (int k=0;k<=5;k++){
            double val = max*(1.0 - k/5.0);
            String s = String.format("%,.0f", val);
            drawRight(g2, s, x-10, y+(int)(h*k/5.0), 80, 16);
        }

        // line + points
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(67,226,206));
        int prevX=-1, prevY=-1;
        for (int i=0;i<n;i++){
            int yr = years.get(i);
            double v = salesByYear.get(yr);
            int px = x + (int)(w * (i/(double)(n-1)));
            int py = y + (int)(h * (1.0 - (v/max)));
            if (prevX!=-1) g2.drawLine(prevX, prevY, px, py);
            g2.fillOval(px-4, py-4, 8, 8);
            prevX=px; prevY=py;
        }

        // X labels (วางใต้กราฟ +間隔)
        int step = (n > 8 ? 2 : 1); // ข้ามปีถ้าแน่น
        g2.setColor(new Color(90,90,90));
        for (int i=0;i<n;i+=step){
            int yr = years.get(i);
            int px = x + (int)(w * (i/(double)(n-1)));
            g2.drawLine(px, y+h, px, y+h+4);
            drawCenter(g2, String.valueOf(yr), px-20, y+h+8, 40, 18);
        }
    }

    private void drawBarSeasons(Graphics2D g2, int x, int y, int w, int h){
        double max = salesBySeason.values().stream().mapToDouble(Double::doubleValue).max().orElse(1d);
        String[] cats = {"ร้อน","ฝน","หนาว"};

        // Y labels
        g2.setColor(new Color(120,120,120));
        g2.setFont(g2.getFont().deriveFont(12f));
        for (int k=0;k<=5;k++){
            double val = max*(1.0 - k/5.0);
            drawRight(g2, String.format("%,.0f", val), x-10, y+(int)(h*k/5.0), 80, 16);
        }

        int bars = cats.length;
        int bw = Math.max(50, (int)(w*0.18));
        int gap = (w - bars*bw) / (bars+1);
        int cx = x+gap;

        for (String c : cats){
            double v = salesBySeason.getOrDefault(c, 0d);
            int bh = (int)(h * (v/max));
            int by = y + h - bh;

            g2.setColor(new Color(255, 200, 120));
            g2.fillRoundRect(cx, by, bw, bh, 10, 10);
            g2.setColor(new Color(210,140,80));
            g2.drawRoundRect(cx, by, bw, bh, 10, 10);

            g2.setColor(new Color(50,50,50));
            drawCenter(g2, c, cx, y+h+8, bw, 18);
            drawCenter(g2, String.format("%,.0f", v), cx, by-20, bw, 18);

            cx += bw + gap;
        }
    }

    // helpers (วาดข้อความจัดตำแหน่ง)
    private void drawCenter(Graphics2D g2, String s, int x, int y, int w, int h){
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (w - fm.stringWidth(s))/2;
        int ty = y + ((h - fm.getHeight())/2) + fm.getAscent();
        g2.drawString(s, tx, ty);
    }
    private void drawRight(Graphics2D g2, String s, int x, int y, int w, int h){
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + w - fm.stringWidth(s);
        int ty = y + ((h - fm.getHeight())/2) + fm.getAscent();
        g2.drawString(s, tx, ty);
    }
}


/* ================= Branch Page (จัดการสาขา) — มีปุ่ม ← กลับ ================= */
private JComponent buildBranchPage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(250,250,245));
    page.setBorder(new EmptyBorder(20,20,20,20));

    // ===== Header: ชื่อหน้า + ลูกศรย้อนกลับไป "product"
    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);

    JLabel title = new JLabel("🏬 จัดการสาขา");
    title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
    title.setForeground(new Color(245,130,31));

    JButton btBack = mintButton("← กลับ");
    btBack.addActionListener(e -> card.show(pages, "product")); // << กลับไปหน้าจัดการสินค้า

    header.add(title, BorderLayout.WEST);
    header.add(btBack, BorderLayout.EAST);
    page.add(header, BorderLayout.NORTH);

    // ===== ฟิลเตอร์ค้นหา
    JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    filter.setBackground(new Color(255,250,235));
    filter.setBorder(BorderFactory.createTitledBorder("ค้นหาสาขา"));

    JComboBox<String> cbRegion = new JComboBox<>(new String[]{"ทั้งหมด","เหนือ","อีสาน","กลาง","ใต้"});
    JTextField tfSearch = new JTextField(18); tfSearch.setToolTipText("ชื่อ/รหัสสาขา/จังหวัด");
    JButton btSearch = new JButton("ค้นหา"); styleButton(btSearch, new Color(67,226,206));
    JButton btClear  = new JButton("ล้าง");  styleButton(btClear,  new Color(255,180,120));

    filter.add(new JLabel("ภาค:")); filter.add(cbRegion);
    filter.add(new JLabel("ค้นหา:")); filter.add(tfSearch);
    filter.add(btSearch); filter.add(btClear);

    page.add(filter, BorderLayout.BEFORE_FIRST_LINE);

    // ===== ตาราง
    String[] cols = {"รหัสสาขา","ชื่อสาขา","ภาค","จังหวัด","อำเภอ","พนักงาน","สถานะ"};
    DefaultTableModel model = new DefaultTableModel(cols,0){ public boolean isCellEditable(int r,int c){return false;} };
    JTable tb = new JTable(model);
    tb.setRowHeight(26);
    tb.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    JScrollPane sp = new JScrollPane(tb);
    sp.setBorder(BorderFactory.createTitledBorder("รายการสาขา"));

    // ===== ปุ่ม เพิ่ม/แก้ไข/ลบ
    JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    actions.setBackground(new Color(255,250,240));
    JButton btAdd = new JButton("➕ เพิ่มสาขา");
    JButton btEdit = new JButton("✏️ แก้ไข");
    JButton btDel = new JButton("🗑️ ลบ");
    for (JButton b : new JButton[]{btAdd,btEdit,btDel}) styleButton(b,new Color(200,245,236));
    actions.add(btAdd); actions.add(btEdit); actions.add(btDel);

    JPanel center = new JPanel(new BorderLayout(10,10));
    center.add(sp, BorderLayout.CENTER);
    center.add(actions, BorderLayout.SOUTH);
    page.add(center, BorderLayout.CENTER);

    // ===== ตัวอย่างข้อมูลเริ่มต้น
    model.addRow(new Object[]{"B001","สาขาเชียงใหม่","เหนือ","เชียงใหม่","เมือง","คุณเกียรติ","เปิดขาย"});
    model.addRow(new Object[]{"B002","สาขานครราชสีมา","อีสาน","นครราชสีมา","เมือง","คุณพร","เปิดขาย"});
    model.addRow(new Object[]{"B003","สาขาภูเก็ต","ใต้","ภูเก็ต","กะทู้","คุณศร","ปิดปรับปรุง"});

    // ===== ค้นหา (TableRowSorter)
    final javax.swing.table.TableRowSorter<DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(model);
    tb.setRowSorter(sorter);
    Runnable applyFilter = () -> {
        java.util.List<RowFilter<Object,Object>> fs = new java.util.ArrayList<>();
        String reg = (String) cbRegion.getSelectedItem();
        if (reg != null && !"ทั้งหมด".equals(reg)) fs.add(RowFilter.regexFilter(reg, 2));
        String q = tfSearch.getText().trim();
        if (!q.isEmpty()) fs.add(RowFilter.regexFilter("(?i)"+q, 0,1,3,4)); // รหัส/ชื่อ/จังหวัด/อำเภอ
        sorter.setRowFilter(fs.isEmpty()? null : RowFilter.andFilter(fs));
    };
    btSearch.addActionListener(e -> applyFilter.run());
    btClear.addActionListener(e -> { cbRegion.setSelectedIndex(0); tfSearch.setText(""); sorter.setRowFilter(null); });
    tfSearch.addActionListener(e -> applyFilter.run()); // กด Enter ก็ค้นหา

    // ===== เพิ่ม
    btAdd.addActionListener(e -> {
        JTextField id=new JTextField(), name=new JTextField(), region=new JTextField(),
                   prov=new JTextField(), dist=new JTextField(), emp=new JTextField();
        JComboBox<String> status = new JComboBox<>(new String[]{"เปิดขาย","ปิดปรับปรุง"});
        Object[] msg={"รหัส:",id,"ชื่อ:",name,"ภาค:",region,"จังหวัด:",prov,"อำเภอ:",dist,"พนักงาน:",emp,"สถานะ:",status};
        if(JOptionPane.showConfirmDialog(page,msg,"เพิ่มสาขา",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
            model.addRow(new Object[]{id.getText(),name.getText(),region.getText(),prov.getText(),dist.getText(),emp.getText(),status.getSelectedItem()});
    });

    // ===== แก้ไข (แถวเดียว)
    btEdit.addActionListener(e -> {
        int view = tb.getSelectedRow();
        if (view < 0) { msg("กรุณาเลือกแถวที่จะแก้ไข"); return; }
        int r = tb.convertRowIndexToModel(view);
        JTextField id=new JTextField(model.getValueAt(r,0).toString());
        JTextField name=new JTextField(model.getValueAt(r,1).toString());
        JTextField region=new JTextField(model.getValueAt(r,2).toString());
        JTextField prov=new JTextField(model.getValueAt(r,3).toString());
        JTextField dist=new JTextField(model.getValueAt(r,4).toString());
        JTextField emp=new JTextField(model.getValueAt(r,5).toString());
        JComboBox<String> status=new JComboBox<>(new String[]{"เปิดขาย","ปิดปรับปรุง"});
        status.setSelectedItem(model.getValueAt(r,6).toString());
        Object[] msg={"รหัส:",id,"ชื่อ:",name,"ภาค:",region,"จังหวัด:",prov,"อำเภอ:",dist,"พนักงาน:",emp,"สถานะ:",status};
        if(JOptionPane.showConfirmDialog(page,msg,"แก้ไขสาขา",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            model.setValueAt(id.getText(), r, 0);
            model.setValueAt(name.getText(), r, 1);
            model.setValueAt(region.getText(), r, 2);
            model.setValueAt(prov.getText(), r, 3);
            model.setValueAt(dist.getText(), r, 4);
            model.setValueAt(emp.getText(), r, 5);
            model.setValueAt(status.getSelectedItem(), r, 6);
        }
    });

    // ===== ลบ (หลายแถว + ปุ่ม Delete + คลิกขวา)
    Runnable deleteRun=()->{
        int[] rows=tb.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) model.removeRow(tb.convertRowIndexToModel(rows[i]));
    };
    btDel.addActionListener(e->deleteRun.run());
    tb.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"),"delRow");
    tb.getActionMap().put("delRow",new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ deleteRun.run(); }});
    JPopupMenu pop = new JPopupMenu();
    JMenuItem mi = new JMenuItem("ลบรายการที่เลือก");
    mi.addActionListener(e->deleteRun.run());
    pop.add(mi);
    tb.setComponentPopupMenu(pop);

    return page;
}

/* ================= Orders Page ================= */
private JComponent buildOrdersPage(){
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(250,250,245));
    page.setBorder(new EmptyBorder(20,20,20,20));

    JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false);
    JLabel h = new JLabel("🛒 ออเดอร์ / การสั่งซื้อ");
    h.setFont(h.getFont().deriveFont(Font.BOLD, 26f));
    h.setForeground(new Color(245,130,31));
    JButton btBack = mintButton("← กลับ");
    btBack.addActionListener(e -> card.show(pages, "product"));
    header.add(h, BorderLayout.WEST); header.add(btBack, BorderLayout.EAST);
    page.add(header, BorderLayout.NORTH);

    // ฟิลเตอร์
    JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    filters.setBackground(new Color(255,250,235));
    JComboBox<String> cbStatus = new JComboBox<>(new String[]{"ทั้งหมด","ใหม่","รอดำเนินการ","กำลังเตรียม","จัดส่งแล้ว","สำเร็จ","ยกเลิก"});
    JTextField tfSearch = new JTextField(18); tfSearch.setToolTipText("เลขที่/ลูกค้า/สาขา");
    JButton btSearch = new JButton("ค้นหา"); styleButton(btSearch,new Color(200,245,236));
    JButton btClear  = new JButton("ล้าง");  styleButton(btClear, new Color(255,180,120));
    filters.add(new JLabel("สถานะ:")); filters.add(cbStatus);
    filters.add(new JLabel("ค้นหา:")); filters.add(tfSearch);
    filters.add(btSearch); filters.add(btClear);
    page.add(filters, BorderLayout.BEFORE_FIRST_LINE);

    String[] cols={"เลขที่ออเดอร์","วันที่","ลูกค้า/สาขา","ยอดรวม(บาท)","สถานะ"};
    DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
    JTable tb=new JTable(m); tb.setRowHeight(26);
    JScrollPane sp=new JScrollPane(tb);
    sp.setBorder(BorderFactory.createTitledBorder("รายการออเดอร์"));
    page.add(sp,BorderLayout.CENTER);

    // ตัวอย่าง
    m.addRow(new Object[]{"SO-001","2025-08-01","สาขาเชียงใหม่",1250,"สำเร็จ"});
    m.addRow(new Object[]{"SO-002","2025-08-02","คุณอรดี",450,"รอดำเนินการ"});

    // ค้นหา
    final javax.swing.table.TableRowSorter<DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(m);
    tb.setRowSorter(sorter);
    Runnable doFilter = () -> {
        java.util.List<RowFilter<Object,Object>> fs = new java.util.ArrayList<>();
        String s = (String) cbStatus.getSelectedItem();
        if (s!=null && !"ทั้งหมด".equals(s)) fs.add(RowFilter.regexFilter(s, 4));
        String q = tfSearch.getText().trim();
        if (!q.isEmpty()) fs.add(RowFilter.regexFilter("(?i)"+q, 0,2));
        sorter.setRowFilter(fs.isEmpty()? null : RowFilter.andFilter(fs));
    };
    btSearch.addActionListener(e -> doFilter.run());
    btClear.addActionListener(e -> { cbStatus.setSelectedIndex(0); tfSearch.setText(""); sorter.setRowFilter(null); });
    tfSearch.addActionListener(e -> doFilter.run());

    // ปุ่มล่าง
    JPanel act=new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    act.setBackground(new Color(255,250,240));
    JButton bAdd=new JButton("➕ เพิ่ม"), bEdit=new JButton("✏️ แก้ไข"), bDel=new JButton("🗑️ ลบ");
    for(JButton b:new JButton[]{bAdd,bEdit,bDel}) styleButton(b,new Color(200,245,236));
    act.add(bAdd); act.add(bEdit); act.add(bDel);
    page.add(act,BorderLayout.SOUTH);

    bAdd.addActionListener(e->{
        JTextField id=new JTextField(),cus=new JTextField(),total=new JTextField();
        JComboBox<String> st=new JComboBox<>(new String[]{"ใหม่","รอดำเนินการ","กำลังเตรียม","จัดส่งแล้ว","สำเร็จ","ยกเลิก"});
        Object[] msg={"เลขที่ออเดอร์:",id,"ลูกค้า/สาขา:",cus,"ยอดรวม:",total,"สถานะ:",st};
        if(JOptionPane.showConfirmDialog(page,msg,"เพิ่มออเดอร์",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)
            m.addRow(new Object[]{id.getText(),LocalDate.now().toString(),cus.getText(),total.getText(),st.getSelectedItem()});
    });

    bEdit.addActionListener(e->{
        int v = tb.getSelectedRow(); if(v<0){ msg("กรุณาเลือกแถวที่จะแก้ไข"); return; }
        int r = tb.convertRowIndexToModel(v);
        JTextField id=new JTextField(m.getValueAt(r,0).toString());
        JTextField date=new JTextField(m.getValueAt(r,1).toString());
        JTextField cus=new JTextField(m.getValueAt(r,2).toString());
        JTextField total=new JTextField(m.getValueAt(r,3).toString());
        JComboBox<String> st=new JComboBox<>(new String[]{"ใหม่","รอดำเนินการ","กำลังเตรียม","จัดส่งแล้ว","สำเร็จ","ยกเลิก"});
        st.setSelectedItem(m.getValueAt(r,4).toString());
        Object[] msg={"เลขที่ออเดอร์:",id,"วันที่:",date,"ลูกค้า/สาขา:",cus,"ยอดรวม:",total,"สถานะ:",st};
        if(JOptionPane.showConfirmDialog(page,msg,"แก้ไขออเดอร์",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            m.setValueAt(id.getText(), r, 0);
            m.setValueAt(date.getText(), r, 1);
            m.setValueAt(cus.getText(), r, 2);
            m.setValueAt(total.getText(), r, 3);
            m.setValueAt(st.getSelectedItem(), r, 4);
        }
    });

    Runnable delRun = () -> {
        int[] rows = tb.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) m.removeRow(tb.convertRowIndexToModel(rows[i]));
    };
    bDel.addActionListener(e->delRun.run());
    tb.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"),"delRow");
    tb.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ delRun.run(); }});

    return page;
}


/* ================= Alerts Page ================= */
private JComponent buildAlertsPage(){
    JPanel page=new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(250,250,245));
    page.setBorder(new EmptyBorder(20,20,20,20));

    JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false);
    JLabel h=new JLabel("🔔 แจ้งเตือน");
    h.setFont(h.getFont().deriveFont(Font.BOLD,26f));
    h.setForeground(new Color(245,130,31));
    JButton btBack = mintButton("← กลับ");
    btBack.addActionListener(e -> card.show(pages, "product"));
    header.add(h, BorderLayout.WEST); header.add(btBack, BorderLayout.EAST);
    page.add(header,BorderLayout.NORTH);

    // ฟิลเตอร์
    JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    filters.setBackground(new Color(255,250,235));
    JTextField tfSearch = new JTextField(18); tfSearch.setToolTipText("คำแจ้งเตือน/ประเภท");
    JButton btSearch = new JButton("ค้นหา"); styleButton(btSearch,new Color(200,245,236));
    JButton btClear  = new JButton("ล้าง");  styleButton(btClear, new Color(255,180,120));
    filters.add(new JLabel("ค้นหา:")); filters.add(tfSearch);
    filters.add(btSearch); filters.add(btClear);
    page.add(filters, BorderLayout.BEFORE_FIRST_LINE);

    DefaultTableModel m=new DefaultTableModel(new String[]{"เวลา","ประเภท","ข้อความ","สถานะ"},0){public boolean isCellEditable(int r,int c){return false;}};
    JTable tb=new JTable(m); tb.setRowHeight(26);
    JScrollPane sp=new JScrollPane(tb);
    sp.setBorder(BorderFactory.createTitledBorder("รายการแจ้งเตือน"));
    page.add(sp,BorderLayout.CENTER);

    m.addRow(new Object[]{"09:32","สต็อก","น้ำส้มเหลือน้อย","ใหม่"});
    m.addRow(new Object[]{"10:18","ออเดอร์","ลูกค้าใหม่สั่งซื้อ","ใหม่"});

    JPanel a=new JPanel(new FlowLayout(FlowLayout.LEFT,10,8));
    a.setBackground(new Color(255,250,240));
    JButton bRead=new JButton("✔️ ทำเป็นอ่านแล้ว");
    JButton bDel =new JButton("🗑️ ลบ");
    for(JButton b:new JButton[]{bRead,bDel}) styleButton(b,new Color(200,245,236));
    a.add(bRead); a.add(bDel);
    page.add(a,BorderLayout.SOUTH);

    // ค้นหา
    final javax.swing.table.TableRowSorter<DefaultTableModel> sorter = new javax.swing.table.TableRowSorter<>(m);
    tb.setRowSorter(sorter);
    Runnable doFilter = () -> {
        String q = tfSearch.getText().trim();
        sorter.setRowFilter(q.isEmpty()? null : RowFilter.regexFilter("(?i)"+q, 1,2));
    };
    btSearch.addActionListener(e->doFilter.run());
    btClear.addActionListener(e->{ tfSearch.setText(""); sorter.setRowFilter(null); });
    tfSearch.addActionListener(e->doFilter.run());

    bRead.addActionListener(e -> {
        int[] sel = tb.getSelectedRows();
        if (sel.length==0){ msg("เลือกแถวก่อน"); return; }
        for (int v : sel) m.setValueAt("อ่านแล้ว", tb.convertRowIndexToModel(v), 3);
    });

    Runnable delRun = () -> {
        int[] rows = tb.getSelectedRows();
        if(rows.length==0){ msg("กรุณาเลือกแถวที่จะลบ"); return; }
        if(JOptionPane.showConfirmDialog(page,"ยืนยันการลบ "+rows.length+" แถว?","ยืนยัน",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        for(int i=rows.length-1;i>=0;i--) m.removeRow(tb.convertRowIndexToModel(rows[i]));
    };
    bDel.addActionListener(e->delRun.run());
    tb.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"),"delRow");
    tb.getActionMap().put("delRow", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ delRun.run(); }});

    return page;
}
// ===== ฟอนต์ไทยที่มีในเครื่อง (เลือกตัวแรกที่พบ) =====
private static String firstAvailableFont(String... names) {
    String[] av = java.awt.GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getAvailableFontFamilyNames();
    java.util.Set<String> set = new java.util.HashSet<>();
    for (String f : av) set.add(f);
    for (String n : names) if (set.contains(n)) return n;
    return "Dialog"; // fallback เผื่อไม่มีสักตัว
}

// ใช้กับหน้าแชททั้งหมด
private static final String THAI_FONT =
        firstAvailableFont("Tahoma", "Sarabun", "Kanit", "Noto Sans Thai", "Segoe UI", "Prompt", "Dialog");


/* ================= Chat Page (แชทลูกค้า — สไตล์เฟซ, ไม่มี Auto-Reply) ================= */
private JComponent buildChatPage() {
    JPanel page = new JPanel(new BorderLayout(10,10));
    page.setBackground(new Color(250,250,245));
    page.setBorder(new EmptyBorder(20,20,20,20));

    
    // ===== Header =====
    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);
    JLabel title = new JLabel("💬 แชทลูกค้า");
    title.setFont(new Font("Tahoma", Font.BOLD, 26));
    title.setForeground(new Color(245,130,31));
    JButton btBack = mintButton("← กลับ");
    btBack.addActionListener(e -> card.show(pages, "product"));
    header.add(title, BorderLayout.WEST);
    header.add(btBack, BorderLayout.EAST);
    page.add(header, BorderLayout.NORTH);

    // ===== รายชื่อลูกค้า =====
    DefaultListModel<String> listModel = new DefaultListModel<>();
    listModel.addElement("🟢 คุณอรดี (ยังไม่อ่าน)");
    listModel.addElement("⚪ คุณมานพ (อ่านแล้ว)");
    listModel.addElement("🟢 สาขาเชียงใหม่ (ยังไม่อ่าน)");

    JList<String> customerList = new JList<>(listModel);
    customerList.setFont(new Font("Tahoma", Font.PLAIN, 18));
    customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane listScroll = new JScrollPane(customerList);
    listScroll.setBorder(BorderFactory.createTitledBorder("รายชื่อลูกค้า"));
    customerList.setFont(new Font(THAI_FONT, Font.PLAIN, 18));



    // ===== พื้นที่แชท: ใช้ JTextPane + StyledDocument ทำบับเบิลซ้าย/ขวา =====
    JTextPane chatPane = new JTextPane();
    chatPane.setEditable(false);
    chatPane.setFont(new Font("Tahoma", Font.PLAIN, 17));
    JScrollPane chatScroll = new JScrollPane(chatPane);
    chatScroll.setBorder(BorderFactory.createTitledBorder("สนทนา"));
    chatPane.setFont(new Font(THAI_FONT, Font.PLAIN, 17));

    // กล่องพิมพ์ + ปุ่มส่ง
    JTextField tfMessage = new JTextField();
    JButton btSend = new JButton("ส่ง");
    styleButton(btSend, new Color(67,226,206));
    btSend.setFont(new Font("Tahoma", Font.BOLD, 18));
    JPanel inputPanel = new JPanel(new BorderLayout(6,6));
    inputPanel.add(tfMessage, BorderLayout.CENTER);
    inputPanel.add(btSend, BorderLayout.EAST);
    tfMessage.setFont(new Font(THAI_FONT, Font.PLAIN, 18));

    JPanel chatPanel = new JPanel(new BorderLayout(10,10));
    chatPanel.add(chatScroll, BorderLayout.CENTER);
    chatPanel.add(inputPanel, BorderLayout.SOUTH);

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, chatPanel);
    split.setDividerLocation(240);
    split.setResizeWeight(0.30);
    page.add(split, BorderLayout.CENTER);

    // ===== เก็บประวัติแชท (ข้อความทีละรายการ) =====
    Map<String, java.util.List<ChatMsg>> history = new java.util.HashMap<>();
    history.put("คุณอรดี", new java.util.ArrayList<>(java.util.Arrays.asList(
            new ChatMsg("ลูกค้า: สวัสดีค่ะ อยากสอบถามน้ำส้มแบบไม่ใส่น้ำตาลมีไหมคะ?", false),
            new ChatMsg("ลูกค้า: มีส่งต่างจังหวัดไหมคะ?", false)
    )));
    history.put("คุณมานพ", new java.util.ArrayList<>(java.util.Arrays.asList(
            new ChatMsg("ลูกค้า: ได้รับของแล้วครับ สดมากครับ", false),
            new ChatMsg("ร้าน: ขอบคุณมากครับ", true)
    )));
    history.put("สาขาเชียงใหม่", new java.util.ArrayList<>(java.util.Arrays.asList(
            new ChatMsg("พนักงาน: วันนี้ของหมดสต็อกแล้วครับ จะรับรอบพรุ่งนี้ไหมครับ?", false)
    )));

    // ===== ฟังก์ชันวาดบับเบิลลงใน JTextPane =====
    Runnable renderSelected = () -> {
        String sel = customerList.getSelectedValue();
        if (sel == null) return;
        String name = sel.replace("🟢 ","").replace("⚪ ","")
                .replace(" (ยังไม่อ่าน)","").replace(" (อ่านแล้ว)","");
        java.util.List<ChatMsg> msgs = history.getOrDefault(name, new java.util.ArrayList<>());
        StyledDocument doc = chatPane.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (Exception ignored) {}

        for (ChatMsg m : msgs) appendBubble(chatPane, m.text, m.me, m.time);
        chatPane.setCaretPosition(doc.getLength());

        // เปลี่ยนเป็นอ่านแล้ว
        int idx = customerList.getSelectedIndex();
        if (idx >= 0 && listModel.get(idx).contains("ยังไม่อ่าน")) {
            listModel.set(idx, "⚪ " + name + " (อ่านแล้ว)");
        }
    };
    
    customerList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) renderSelected.run(); });

    // ===== ปุ่มส่ง (ร้านพิมพ์เอง – ไม่มี Auto Reply) =====
ActionListener doSend = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent ev) {
        String sel = (String) customerList.getSelectedValue();
        if (sel == null) { 
            msg("กรุณาเลือกลูกค้าก่อน"); 
            return; 
        }

        // ตัดไอคอน/สถานะออกให้เหลือเฉพาะชื่อ
        String name = sel
                .replace("🟢 ", "")
                .replace("⚪ ", "")
                .replace(" (ยังไม่อ่าน)", "")
                .replace(" (อ่านแล้ว)", "");

        String text = tfMessage.getText().trim();
        if (text.isEmpty()) return;

        // ร้านส่งข้อความ
        ChatMsg me = new ChatMsg("ร้าน: " + text, true);
        history.computeIfAbsent(name, k -> new java.util.ArrayList<>()).add(me);

        // แสดงเป็น bubble ทางขวา
        appendBubble(chatPane, me.text, true, me.time);

        tfMessage.setText("");
        chatPane.setCaretPosition(chatPane.getDocument().getLength());
    }
};

btSend.addActionListener(doSend);
tfMessage.addActionListener(doSend);



    // เลือกห้องแรกให้พร้อมใช้งาน
    if (!listModel.isEmpty()) customerList.setSelectedIndex(0);

    return page;
}

/* ===== บับเบิลสไตล์เฟซบุ๊ก (ซ้ายลูกค้า / ขวาร้าน) ===== */
private void appendBubble(JTextPane pane, String text, boolean isMe, long timeMillis) {
    StyledDocument doc = pane.getStyledDocument();
    
    // สี/สไตล์
    Color meBg = new Color(200, 245, 236);   // มิ้นต์
    Color otherBg = new Color(245, 245, 245);// เทาอ่อน
    Color meFg = Color.BLACK;
    Color otherFg = Color.DARK_GRAY;

    // ย่อหน้าแยกบับเบิล
    try { doc.insertString(doc.getLength(), "\n", null); } catch (Exception ignored) {}

    // กรอบบับเบิล = ใส่ Space padding + ข้อความ + เวลา
    String time = "  " + new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(timeMillis));
    String bubbleText = text + time;

    // สร้าง Attribute ของย่อหน้า (จัดชิดซ้าย/ขวา)
    SimpleAttributeSet pAttr = new SimpleAttributeSet();
    StyleConstants.setAlignment(pAttr, isMe ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
    pane.setParagraphAttributes(pAttr, true);

    // สร้าง Attribute ของตัวอักษร + พื้นหลัง
    SimpleAttributeSet a = new SimpleAttributeSet();
    StyleConstants.setForeground(a, isMe ? meFg : otherFg);
    StyleConstants.setBackground(a, isMe ? meBg : otherBg);
    StyleConstants.setLeftIndent(a, 8);
    StyleConstants.setRightIndent(a, 8);
    StyleConstants.setSpaceAbove(a, 6);
    StyleConstants.setSpaceBelow(a, 2);
    StyleConstants.setFontSize(a, 17);
    StyleConstants.setFirstLineIndent(a, 10);

    StyleConstants.setFontFamily(a, THAI_FONT);

    try { doc.insertString(doc.getLength(), "  " + bubbleText + "  ", a); } catch (Exception ignored) {}

    // ลงบรรทัดใหม่ให้บับเบิลถัดไป
    try { doc.insertString(doc.getLength(), "\n", null); } catch (Exception ignored) {}
    

}

/* ===== โครงข้อความแชท (1 ข้อความต่อครั้ง) ===== */
private static class ChatMsg {
    final String text;     // ข้อความ (prefix ระบุฝ่ายแล้ว เช่น "ร้าน: ...", "ลูกค้า: ...")
    final boolean me;      // true = ร้าน (ขวา), false = ลูกค้า (ซ้าย)
    final long time;       // เวลา (ms)
    ChatMsg(String t, boolean m){ this.text=t; this.me=m; this.time=System.currentTimeMillis(); }
}



    /* ================= Placeholders ================= */
    private JComponent buildPlaceholderPage(String title, String subtitle, Color bg){
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(bg);
        page.setBorder(new EmptyBorder(18,18,18,18));

        JLabel head = new JLabel(title);
        head.setFont(head.getFont().deriveFont(Font.BOLD, 24f));
        head.setForeground(new Color(70,70,70));
        page.add(head, BorderLayout.NORTH);

        JTextArea info = new JTextArea(
                "หน้าระบบ: " + title + "\n" +
                "รายละเอียด: " + subtitle + "\n\n" +
                "หมายเหตุ: หน้านี้เป็น Placeholder — สามารถต่อยอดใส่ฟอร์ม/ตารางได้ทันที.");
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setBorder(new EmptyBorder(12,12,12,12));
        page.add(new JScrollPane(info), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JButton b1 = new JButton("เพิ่มข้อมูล");
        JButton b2 = new JButton("บันทึก");
        JButton b3 = new JButton("ส่งออก CSV");
        for (JButton b : new JButton[]{b1,b2,b3}) styleButton(b, new Color(200,245,236));
        actions.add(b1); actions.add(b2); actions.add(b3);
        page.add(actions, BorderLayout.SOUTH);

        b1.addActionListener(e -> msg("เพิ่มข้อมูล ("+title+")"));
        b2.addActionListener(e -> msg("บันทึก ("+title+")"));
        b3.addActionListener(e -> msg("ส่งออก CSV ("+title+")"));

        return page;
    }

    /* ================= Helpers ================= */
    private JPanel roundedCard(){
        return new JPanel(new BorderLayout(10,10)){
            @Override protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2=(Graphics2D)g;
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
                g2.setColor(new Color(230,230,230));
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,14,14);
            }
        };
    }
    private void msg(String m){ JOptionPane.showMessageDialog(this, m); }

    private void addRow(JPanel p, GridBagConstraints gc, int row, String label, JComponent field){
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0; gc.fill = GridBagConstraints.NONE;
        JLabel l = new JLabel(label);
        p.add(l, gc);
        gc.gridx = 1; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        field.setPreferredSize(new Dimension(260, 34));
        p.add(field, gc);
    }

    private JButton navButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(245, 230, 180));
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,150,80)),
                new EmptyBorder(10,20,10,20)
        ));
        return b;
    }
    private JButton stepButton(String text){
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(255,213,164));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210,125,60)),
                new EmptyBorder(6,18,6,18)
        ));
        return b;
    }
    private JButton mintButton(String text){
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(200,245,236));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,137,170)),
                new EmptyBorder(6,14,6,14)
        ));
        return b;
    }
    private JPanel kpiCard(String title, String sub){
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(255,145,29));
        p.setBorder(new EmptyBorder(10,14,10,14));
        JLabel t = new JLabel(title); t.setForeground(Color.WHITE);
        t.setFont(t.getFont().deriveFont(Font.BOLD, 16f));
        JLabel s = new JLabel(sub); s.setForeground(new Color(255,250,240));
        p.add(t, BorderLayout.NORTH); p.add(s, BorderLayout.CENTER);
        return p;
    }
    private void styleButton(JButton b, Color c){
        b.setBackground(c);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
        b.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
    }

    /* ================= Thai Font ================= */
    private static void applyThaiUI(){
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
        String font = pickFont(new String[]{"Sarabun","Kanit","Prompt","Tahoma","Dialog"});
        Font ui = new Font(font, Font.PLAIN, 18);
        UIManager.put("Label.font", ui);
        UIManager.put("Button.font", ui);
        UIManager.put("Table.font", ui);
        UIManager.put("TableHeader.font", ui.deriveFont(Font.BOLD, 18f));
        UIManager.put("TextField.font", ui);
        UIManager.put("OptionPane.messageFont", ui);
        UIManager.put("OptionPane.buttonFont", ui);
    }
    private static String pickFont(String[] prefs){
        String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String p: prefs) for (String n: names) if (n.equalsIgnoreCase(p)) return n;
        return "Dialog";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrangeApp().setVisible(true));
    }
}
