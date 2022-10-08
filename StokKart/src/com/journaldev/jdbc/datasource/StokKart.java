package com.journaldev.jdbc.datasource;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;
import javax.sql.DataSource;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.parser.ContentModel;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class StokKart {

    private JFrame frame;
    static JTextField t1, t2, t3;
    static JComboBox<Integer> c1;
    static JComboBox<String> c2;
    static JComboBox<Double> c3;
    static JTextArea ta1;
    static JFormattedTextField ftf1;
    static JButton b1, b2, b3, b4, b5, b6;
    static DefaultListModel<String> model;
    static DefaultTableModel modelim;
    static Object[] kolonlar = { "stok_kodu", "stok_adi", "stok_tipi", "birimi", "barkodu", "kdv_tipi", "aciklama",
	    "olusturma_tarihi" };
    static Object[] satirlar = new Object[8];
    static JTable list1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {

		    StokKart window = new StokKart();
		    window.frame.setVisible(true);

		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     * 
     * t1 stok kodu t2 stok adı t3 barkodu
     * 
     * c1 stok tipi c2 birimi c3 kdv tipi
     * 
     * ta1 açıklama
     * 
     * ftf1 tarih
     * 
     * @throws SQLException
     */
    public StokKart() throws SQLException {
	initialize();
	c1.setEditable(true);
	c2.setEditable(true);
	ArrayList deneme = new ArrayList();

	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);

	    ResultSet myRs = ((java.sql.Statement) myStat).executeQuery("select  * from tblStoklar");
	    while (myRs.next()) {
		if (!deneme.contains(myRs.getInt("stok_tipi"))) {
		    c1.addItem(myRs.getInt("stok_tipi"));
		}
		if (!deneme.contains(myRs.getString("birimi"))) {
		    c2.addItem(myRs.getString("birimi"));
		}
		deneme.add(myRs.getInt("stok_tipi"));
		deneme.add(myRs.getString("birimi"));
	    }
	    con.close();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	c1.setSelectedItem(null);
	c2.setSelectedItem(null);
	c3.setSelectedItem(null);
	
	

    }

    public static void ara() throws SQLException {
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement();
	    String arama = t1.getText();
	    ResultSet myRs = ((java.sql.Statement) myStat)
		    .executeQuery("select * from tblStoklar where stok_kodu=" + arama);
	    while (myRs.next()) {

		t1.setText(myRs.getString("stok_kodu"));
		t2.setText(myRs.getString("stok_adi"));
		t3.setText(myRs.getString("barkodu"));
		c1.setSelectedItem(myRs.getInt("stok_tipi"));
		c2.setSelectedItem(myRs.getString("birimi"));
		c3.setSelectedItem(myRs.getDouble("kdv_tipi"));
		ta1.setText(myRs.getString("aciklama"));
		ftf1.setText(myRs.getDate("olusturma_tarihi").toString());

	    }
	    con.close();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void ekle() throws SQLException {
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);

	    ResultSet myRs = ((java.sql.Statement) myStat).executeQuery("select * from tblStoklar");
	    myRs.last();
	    myRs.moveToInsertRow();
	    myRs.updateString("stok_kodu", t1.getText());
	    myRs.updateString("stok_adi", t2.getText());
	    myRs.updateInt("stok_tipi", Integer.valueOf(c1.getSelectedItem().toString()));
	    myRs.updateString("birimi", (String) c2.getSelectedItem().toString());
	    myRs.updateString("barkodu", t3.getText());
	    myRs.updateDouble("kdv_tipi", Double.valueOf(c3.getSelectedItem().toString()));
	    myRs.updateString("aciklama", ta1.getText());
	    myRs.updateDate("olusturma_tarihi", Date.valueOf(ftf1.getText()));
	    myRs.insertRow();
	    myRs.beforeFirst();
	    JOptionPane.showMessageDialog(list1, "Yeni stok eklendi.");
	    con.close();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	StokKart.listele();
    }

    public static void sil() throws SQLException {
	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    ResultSet myRs = ((java.sql.Statement) myStat).executeQuery("select * from tblStoklar");
	    myRs.absolute(Integer.parseInt(t1.getText()));
	    myRs.deleteRow();
	    con.close();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	JOptionPane.showMessageDialog(null, "Seçilen satır silindi.");
	StokKart.listele();
	t1.setText(null);
	t2.setText(null);
	t3.setText(null);
	c1.setSelectedItem(null);
	c2.setSelectedItem(null);
	c3.setSelectedItem(null);
	ta1.setText(null);
	ftf1.setText(null);
    }

    public static void listele() throws SQLException {

	try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);

	    ResultSet myRs = ((java.sql.Statement) myStat).executeQuery("select * from tblStoklar");
	    modelim.setRowCount(0);
	    modelim.setColumnCount(0);
	    modelim.setColumnIdentifiers(kolonlar);
	    while (myRs.next()) {
		satirlar[0] = myRs.getString("stok_kodu");
		satirlar[1] = myRs.getString("stok_adi");
		satirlar[2] = myRs.getString("stok_tipi");
		satirlar[3] = myRs.getString("birimi");
		satirlar[4] = myRs.getString("barkodu");
		satirlar[5] = myRs.getString("kdv_tipi");
		satirlar[6] = myRs.getString("aciklama");
		satirlar[7] = myRs.getString("olusturma_tarihi");
		modelim.addRow(satirlar);
	    }

	    con.close();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// t1.setText(null);
	// t2.setText(null);
	// t3.setText(null);
	// c1.removeAllItems();
	// c2.removeAllItems();
	// c3.removeAllItems();
	// ta1.setText(null);
	// ftf1.setText(null);
    }

    static String sql_sorgu;

    public static void güncelle() throws SQLException {

	/*
	 * try { Class.forName("com.mysql.cj.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root",
	 * "31415813"); String query =
	 * "update tblStoklar set stok_kodu = ?, stok_adi= ?, barkodu= ?, aciklama = ?";
	 * PreparedStatement preparedStmt = con.prepareStatement(query);
	 * preparedStmt.setString(1, t1.getText()); preparedStmt.setString(2,
	 * t2.getText()); preparedStmt.setString(3, t3.getText());
	 * preparedStmt.setString(4, ta1.getText()); StokKart.sil();
	 * preparedStmt.executeUpdate(); con.close(); } catch (ClassNotFoundException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 */

	try {

	    String stokkodu, stokadi, brkd, acklm, birim1;
	    Integer tip;
	    Double kdv;
	    Date tarih;
	    stokkodu = t1.getText();
	    stokadi = t2.getText();
	    brkd = t3.getText();
	    acklm = ta1.getText();
	    birim1 = c2.getSelectedItem().toString();
	    tarih = Date.valueOf(ftf1.getText());
	    tip = Integer.valueOf(c1.getSelectedItem().toString());
	    kdv = Double.valueOf(c3.getSelectedItem().toString());
	    sql_sorgu = "UPDATE tblStoklar SET stok_adi='" + stokadi + "', barkodu='" + brkd + "', birimi='" + birim1
		    + "',aciklama='" + acklm + "',stok_tipi=" + tip + ",kdv_tipi=" + kdv + ",olusturma_tarihi='" + tarih
		    + "' where stok_kodu=" + stokkodu;
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stok_karti", "root", "31415813");
	    Statement myStat = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		    ResultSet.CONCUR_UPDATABLE);
	    myStat.executeUpdate(sql_sorgu);
	    JOptionPane.showMessageDialog(null, "Güncelleme yapıldı.");

	    con.close();

	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	StokKart.listele();

    }
    static String str;
    public static void kopyala() 
    {
	str= t1.getText()+" "+t2.getText()+" "+c1.getSelectedItem().toString()+" "+c2.getSelectedItem()+" "+t3.getText()+
		" "+c3.getSelectedItem()+" "+ta1.getText()+" "+ftf1.getText();
	StringSelection selection = new StringSelection(str);
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	clipboard.setContents(selection, selection);
	
    }

    /**
     * Initialize the contents of the frame.
     */

    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 1112, 537);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("Stok Kartı");

	t1 = new JTextField("");
	t1.setBounds(70, 0, 200, 30);
	t2 = new JTextField("");
	t2.setBounds(70, 50, 200, 30);
	t3 = new JTextField("");
	t3.setBounds(70, 200, 200, 30);

	JLabel l1, l2, l3, l4, l5, l6, l7, l8;
	l1 = new JLabel("Stok Kodu:");
	l1.setBounds(0, 0, 70, 30);
	l2 = new JLabel("Stok Adı:");
	l2.setBounds(0, 50, 70, 30);
	l3 = new JLabel("Stok Tipi:");
	l3.setBounds(0, 100, 70, 30);
	l4 = new JLabel("Birimi:");
	l4.setBounds(0, 150, 70, 30);
	l5 = new JLabel("Barkodu:");
	l5.setBounds(0, 200, 70, 30);
	l6 = new JLabel("KDV Tipi:");
	l6.setBounds(0, 250, 70, 30);
	l7 = new JLabel("Açıklama:");
	l7.setBounds(0, 300, 70, 30);
	l8 = new JLabel("OluşturmaTarihi:");
	l8.setBounds(0, 380, 70, 30);
	
	JLabel lblNewLabel_1 = new JLabel("Stok Kodu");
	lblNewLabel_1.setBounds(300, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_1);
	
	JLabel lblNewLabel_2 = new JLabel("Stok Adı");
	lblNewLabel_2.setBounds(398, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_2);
	
	JLabel lblNewLabel_3 = new JLabel("Stok Tipi");
	lblNewLabel_3.setBounds(496, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_3);
	
	JLabel lblNewLabel_4 = new JLabel("Birimi");
	lblNewLabel_4.setBounds(594, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_4);
	
	JLabel lblNewLabel_5 = new JLabel("Barkodu");
	lblNewLabel_5.setBounds(692, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_5);
	
	JLabel lblNewLabel_6 = new JLabel("Kdv Tipi");
	lblNewLabel_6.setBounds(790, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_6);
	
	JLabel lblNewLabel_7 = new JLabel("Açıklama");
	lblNewLabel_7.setBounds(888, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_7);
	
	JLabel lblNewLabel_8 = new JLabel("Tarih");
	lblNewLabel_8.setBounds(986, 8, 86, 14);
	frame.getContentPane().add(lblNewLabel_8);
	

	c1 = new JComboBox<Integer>();
	c1.setEditable(true);
	c1.setBounds(70, 100, 200, 30);
	c1.setEnabled(true);

	c2 = new JComboBox<String>();
	c2.setEditable(true);
	c2.setBounds(70, 150, 200, 30);
	c2.setEnabled(true);
	c3 = new JComboBox<Double>();
	c3.setModel(new DefaultComboBoxModel(new String[] { "0.01", "0.08", "0.18" }));
	c3.setBounds(70, 250, 200, 30);
	c3.setEnabled(true);

	ta1 = new JTextArea();
	ta1.setBounds(70, 300, 200, 60);

	String date = new SimpleDateFormat("MM/dd/yy  HH:mm").format(new Date(0));

	ftf1 = new JFormattedTextField();
	ftf1.setBounds(70, 380, 200, 30);

	// list1 = new JList<String>();

	modelim = new DefaultTableModel();
	list1 = new JTable();
	modelim.setColumnIdentifiers(kolonlar);
	list1.setModel(modelim);
	list1.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {

		t1.setText((String) modelim.getValueAt(list1.getSelectedRow(), 0));
		t2.setText((String) modelim.getValueAt(list1.getSelectedRow(), 1));
		t3.setText((String) modelim.getValueAt(list1.getSelectedRow(), 4));
		c1.setSelectedItem(modelim.getValueAt(list1.getSelectedRow(), 2));
		c2.setSelectedItem(modelim.getValueAt(list1.getSelectedRow(), 3));
		c3.setSelectedItem(modelim.getValueAt(list1.getSelectedRow(), 5));
		ta1.setText((String) modelim.getValueAt(list1.getSelectedRow(), 6));
		ftf1.setText((String) modelim.getValueAt(list1.getSelectedRow(), 7));

	    }
	});
	list1.setBounds(300, 31, 786, 466);

	b1 = new JButton("Ara");
	b1.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    StokKart.ara();
		} catch (SQLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});
	b1.setBounds(10, 420, 90, 30);

	b2 = new JButton("Listele");
	b2.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {

		    StokKart.listele();
		} catch (SQLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});
	b2.setBounds(10, 460, 90, 30);
	b3 = new JButton("Ekle");
	b3.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    StokKart.ekle();
		} catch (SQLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});
	b3.setBounds(100, 420, 90, 30);
	b4 = new JButton("Sil");
	b4.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    StokKart.sil();
		} catch (SQLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});
	b4.setBounds(100, 460, 90, 30);
	b5 = new JButton("Güncelle");
	b5.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    StokKart.güncelle();
		} catch (SQLException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

	    }
	});
	b5.setBounds(190, 420, 90, 30);
	b6 = new JButton("Kopyala");
	b6.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		StokKart.kopyala();
	    }
	});
	b6.setBounds(190, 460, 90, 30);

	frame.getContentPane().add(t1);
	frame.getContentPane().add(t2);
	frame.getContentPane().add(t3);
	frame.getContentPane().add(l1);
	frame.getContentPane().add(l2);
	frame.getContentPane().add(l3);
	frame.getContentPane().add(l4);
	frame.getContentPane().add(l5);
	frame.getContentPane().add(l6);
	frame.getContentPane().add(l7);
	frame.getContentPane().add(l8);
	frame.getContentPane().add(c1);
	frame.getContentPane().add(c2);
	frame.getContentPane().add(c3);
	frame.getContentPane().add(ta1);
	frame.getContentPane().add(ftf1);
	frame.getContentPane().add(list1);
	frame.getContentPane().add(b1);
	frame.getContentPane().add(b2);
	frame.getContentPane().add(b3);
	frame.getContentPane().add(b4);
	frame.getContentPane().add(b5);
	frame.getContentPane().add(b6);
	frame.getContentPane().setLayout(null);
	frame.setVisible(true);

    }
}
