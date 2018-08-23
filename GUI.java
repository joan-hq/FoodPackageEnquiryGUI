import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorListener;


import javax.swing.event.AncestorEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField textPriceSearch;
	private JTextField textDesc;
	private JTextField textPrice;
	private JTextField textCountry;
	private JLabel Country;
	private JLabel Price;
	private JLabel Description;
	private JToggleButton PriceSearch;
	private JComboBox cmbType;
	private JList foodlist;
	private ArrayList<FoodPackageSystem> list;
	private ArrayList<FoodPackageSystem> filterlist;
	private ArrayList<FoodPackageSystem> filterPricelist;
	private HashSet<String> setCountry;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		initializeData();
	}

	private void initializeData() {
		// TODO Auto-generated method stub
		list = new ArrayList<FoodPackageSystem>();
		setCountry = new HashSet<String>();
		populatelistFrom();
		foodlist.setListData(list.toArray());/*data from items.toArray*/
		updateComoBox ();
		
	}

	private void populatelistFrom() {
		InputStream i = this.getClass().getResourceAsStream("/FoodPackage.csv");
		if(i == null){
			JOptionPane.showMessageDialog(this.frame,"No Data File","System Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		//once file exists, start to read,use reader must use try and catch
		InputStreamReader csv = new InputStreamReader(i);
		BufferedReader br;
		String line = "";
		String delimiter = ",";
		try {
			br = new BufferedReader(csv);
			while((line= br.readLine())!=null){
				String s[] = line.split(delimiter);
				FoodPackageSystem item = new FoodPackageSystem(s[0],s[1],s[2],Integer.parseInt(s[3]));			
				list.add(item);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showitem() {
		int i = foodlist.getSelectedIndex();
		if (i == -1) {
			i = 0;
		}
		FoodPackageSystem item = filterlist.get(i);
		textCountry.setText(item.getCountry());
		textDesc.setText(item.getDescription());
		textPrice.setText("$" +(item.getPrice()+ " "));
	}
	
	public void updateComoBox () {
		setCountry = new HashSet<String>();
		setCountry.add("All");
		for(FoodPackageSystem i : list) {
			setCountry.add(i.getCountry());
		}
		for (String s : setCountry) {
			this.cmbType.addItem(s);
		}
		this.cmbType.invalidate();
		this.foodlist.setListData(list.toArray());
	}
	protected void filterbyCountry() {
		// TODO Auto-generated method stub
		filterlist = new ArrayList<FoodPackageSystem>();
		String str = this.cmbType.getSelectedItem().toString();
		for (FoodPackageSystem item : this.list) {
			if(item.getCountry().equals(str) || str.equals("All")){
				this.filterlist.add(item);
			}
		}
		this.foodlist.invalidate();
	    this.foodlist.setListData(this.filterlist.toArray());	    
	}
	
	protected void filterbyPrice() {
		filterPricelist = new ArrayList<FoodPackageSystem>();
		String p = this.textPriceSearch.getText();
		float price = Float.parseFloat(p);
		for (FoodPackageSystem item : this.filterlist) {
			if (item.getPrice() <= price) {
				this.filterPricelist.add(item);
			}
		}	
		this.foodlist.setListData(this.filterPricelist.toArray());
		this.foodlist.invalidate();
		filterlist = filterPricelist;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 524, 336);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		cmbType = new JComboBox();
		cmbType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filterbyCountry();
			}
		});
		cmbType.setBounds(10, 11, 127, 20);
		frame.getContentPane().add(cmbType);
		
		textPriceSearch = new JTextField();
		textPriceSearch.setBounds(180, 11, 188, 20);
		frame.getContentPane().add(textPriceSearch);
		textPriceSearch.setColumns(10);
		
		PriceSearch = new JToggleButton("Price Search");
		PriceSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String p = textPriceSearch.getText();
				boolean numeric = true;
				try {
					float price = Float.parseFloat(p);
				} catch(NumberFormatException e1) {
					numeric = false;
				}
				
				if(numeric) {
					filterbyPrice();
				}
				
			}
		});
		PriceSearch.setBounds(375, 11, 123, 20);
		frame.getContentPane().add(PriceSearch);
		
		foodlist = new JList();
		foodlist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				showitem();
			}
		});
		foodlist.setBounds(10, 56, 488, 156);
		frame.getContentPane().add(foodlist);
		
		Description = new JLabel("Description:");
		Description.setHorizontalAlignment(SwingConstants.CENTER);
		Description.setBounds(10, 233, 74, 20);
		frame.getContentPane().add(Description);
		
		textDesc = new JTextField();
		textDesc.setBounds(104, 233, 394, 20);
		frame.getContentPane().add(textDesc);
		textDesc.setColumns(10);
		
		Price = new JLabel("Price:");
		Price.setHorizontalAlignment(SwingConstants.CENTER);
		Price.setBounds(20, 272, 64, 14);
		frame.getContentPane().add(Price);
		
		textPrice = new JTextField();
		textPrice.setHorizontalAlignment(SwingConstants.LEFT);
		textPrice.setBounds(104, 269, 116, 17);
		frame.getContentPane().add(textPrice);
		textPrice.setColumns(10);
		
		Country = new JLabel("Country:");
		Country.setHorizontalAlignment(SwingConstants.CENTER);
		Country.setBounds(273, 272, 56, 14);
		frame.getContentPane().add(Country);
		
		textCountry = new JTextField();
		textCountry.setHorizontalAlignment(SwingConstants.LEFT);
		textCountry.setBounds(350, 269, 148, 17);
		frame.getContentPane().add(textCountry);
		textCountry.setColumns(10);
	}


}
