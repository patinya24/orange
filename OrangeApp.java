import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrangeApp extends JFrame {

    private final CardLayout card = new CardLayout();
    private final JPanel pages = new JPanel(card); // dashboard, product, sort, wash, squeeze, filter, pasteurize, pack, ship

    public OrangeApp() {
        super("SOM-D — ภาพรวมกระบวนการผลิตน้ำส้ม");
        applyThaiUI();
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

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
        JButton btProduct = mintButton("🧃 จัดการสินค้า");
        btProduct.addActionListener(e -> card.show(pages, "product"));
        stepsBar.add(btProduct);
        header.add(stepsBar);

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
        JButton btBranch = navButton("🏬 จัดการสาขา");
        JButton btOrder  = navButton("🛒 ออเดอร์ / การสั่งซื้อ");
        JButton btAlert  = navButton("🔔 แจ้งเตือน");
        JButton btChat   = navButton("💬 แชทลูกค้า");
        for (JButton b : new JButton[]{btBranch, btOrder, btAlert, btChat}) navButtons.add(b);
        btBranch.addActionListener(e -> JOptionPane.showMessageDialog(this,"จัดการสาขา — Coming Soon"));
        btOrder.addActionListener(e -> JOptionPane.showMessageDialog(this,"ออเดอร์ — Coming Soon"));
        btAlert.addActionListener(e -> JOptionPane.showMessageDialog(this,"แจ้งเตือน — Coming Soon"));
        btChat.addActionListener(e -> JOptionPane.showMessageDialog(this,"แชทลูกค้า — Coming Soon"));
        page.add(navButtons, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15,8));
        filterPanel.setBackground(new Color(255,250,235));
        filterPanel.setBorder(BorderFactory.createTitledBorder("ตัวกรองหมวดสินค้า"));
        JComboBox<String> c1 = new JComboBox<>(new String[]{"น้ำส้มสด","สมูทตี้","คอมโบ"});
        JComboBox<String> c2 = new JComboBox<>(new String[]{"คั้นสด","พาสเจอร์ไรซ์"});
        JComboBox<String> c3 = new JComboBox<>(new String[]{"ไซส์เล็ก","ไซส์กลาง","ไซส์ใหญ่"});
        JButton add = new JButton("➕ เพิ่มสินค้า");
        JButton del = new JButton("🗑️ ลบสินค้า");
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
