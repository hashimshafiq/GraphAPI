import java.awt.BorderLayout;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.SystemColor;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;



public class AdminReports extends JFrame {

	private JPanel contentPane;
	public JTable table;
	private JScrollPane scrollPane;
	private JButton btnLoadRequests;
	private JLabel lblEngineerRequest;
	private DateFormat dataFormat;	
	public static final String DATE_FORMAT_NOW = "dd-MM-yyyy";
	public static final String TIME_FORMAT_NOW = "HH:mm:ss";
	private JButton btnNewButton;
	private JButton buttonFilterDate;
	private JComboBox comboBoxLocation;
	private JButton btnExportToExcel;
	private JPanel panel_1;
	private JComboBox comboBoxHardware;
	private JComboBox comboBox_1;
        private XYSeriesCollection data =null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminReports frame = new AdminReports();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection = null;
	private JTextField textFieldStartDateR1;
	private JTextField textFieldStartDateR2;
	private JComboBox comboBoxBookInEn;
	private JTextField textFieldStartDateR3;
	private JTextField textFieldStartDateR4;
	private JPanel panel;
	private JLabel lblBookIn;
	private JComboBox comboBoxEngineer;
	private JTextField textFieldStartDateR5;
	private JTextField textFieldStartDateR6;
	private JLabel lblndDateRange;
	private JLabel lblEngineer;
	private JLabel lblLocation;
	private JLabel lblHardware;
	private JTextField textField;
	private JTextField textField_1;
        XYSeriesCollection my_data_series= new XYSeriesCollection();
	
	public static String nowDate() {
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		 return sdf.format(cal.getTime());
	}
	
	
	public static String nowTime() {
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);
		 return sdf.format(cal.getTime());
	}
	
	
	
	public void fillHardwareComboBox(){
		try{		
			String query = "select * from hardwareStock";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				comboBoxHardware.addItem(rs.getString("hardwareName"));
				comboBox_1.addItem(rs.getString("hardwareName"));
				comboBoxBookInEn.addItem(rs.getString("hardwareName"));

				

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void fillEngineerComboBox(){
		try{		
			String query = "select * from userID";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				comboBoxEngineer.addItem(rs.getString("fullname"));
				comboBoxBookInEn.addItem(rs.getString("fullname"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void refreshTable(){
		
		
		try{		
			String query = "select TicketNumber,Hardware,Quantity,Comments,Asset,Date,Time,Engineer,Location from request_1v2 order by date desc";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		}catch(Exception e2){
			e2.printStackTrace();
		}
		
	}
	
	/**
	 * Create the frame.
	 */
	public AdminReports() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Temp\\workspace\\EngineerRequest\\img\\logo.jpg"));
		setBackground(new Color(51, 153, 102));
		setTitle("Engineer Request");
		setResizable(false);
		setForeground(new Color(0, 153, 255));
		connection = dbConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1405, 738);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(new Color(153, 255, 204));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 559, 697, 139);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnLoadRequests = new JButton("Load All Requests");
		btnLoadRequests.setBackground(new Color(0, 204, 255));
		btnLoadRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{		
					String query = "select * from request_1v2";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
			}catch(Exception e2){
				e2.printStackTrace();
				}
			}
		});
		btnLoadRequests.setBounds(729, 559, 161, 64);
		contentPane.add(btnLoadRequests);
		
		lblEngineerRequest = new JLabel("Reporting");
		lblEngineerRequest.setFont(new Font("Gadugi", Font.BOLD, 36));
		lblEngineerRequest.setBounds(10, -16, 217, 94);
		contentPane.add(lblEngineerRequest);
		
		
		JButton btnQuery = new JButton("View");
		btnQuery.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent arg0) {
							
				String txt1 =textFieldStartDateR1.getText().toString();
				String txt2 =textFieldStartDateR2.getText().toString();
				String txt5 =textFieldStartDateR5.getText().toString();
				String txt6 =textFieldStartDateR6.getText().toString();
				String txt3 =comboBoxEngineer.getSelectedItem().toString();
				String txt4 =comboBoxLocation.getSelectedItem().toString();
				String txt7 =comboBoxHardware.getSelectedItem().toString();
			
                                System.out.println("Me hashim");
				
				if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
						&& comboBoxEngineer.getSelectedItem().equals("All") 
						&& comboBoxLocation.getSelectedItem().equals("All") 
						&& comboBoxHardware.getSelectedItem().equals("All")){
					
					JOptionPane.showMessageDialog(null, "All, All, All");
					
				
					try{
                                                // Here is my code that I have added
                                                // removed your query because you have used two different queries and
                                                // then make two different dataset to plot graph but every time it overrides
                                                // the previous line because we are dealing with two different dataset and thats
                                                // always overirde it. Also we cannot use XYSeriesCollection because
                                                // it change 70% of your code becuase you are not coded in style of XYSeriesCollection.
                                                // So after a lot of hard work I finally figure out a most handsome method I till found
                                                // on the internet. Why not if we just change our database query so that we merge your previous
                                                // two quries to make a single one and in this way we get data into only one dataset.
                                                // But this one dataset contains your two different data values from table
                                                // When we plot a chart using this dataset it displays two lines
                                                // The below query is most handsome of all queries.
//										
						
//                                              //Query to get two different resultsets. 
                                                String query = "SELECT hardware,SUM(CASE WHEN date between '"+txt1+"' AND '"+txt2+"' THEN Quantity ELSE 0 END) As Series1,SUM(CASE WHEN date between '"+txt5+"' AND '"+txt6+"' THEN Quantity ELSE 0 END) As Series2  FROM request_1v2 group by hardware";
                                                
                                                //Creating a dataset using dbconnection 
                                                JDBCCategoryDataset dataset1 = new JDBCCategoryDataset(dbConnection.dbConnector());
						//executing query and creating a dataset
                                                dataset1.executeQuery(query);
                                                //creating a chart with that dataset
						JFreeChart chart = ChartFactory.createLineChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset1, PlotOrientation.VERTICAL, false, true, true);
                                                // creating a chart panel with chart object
                                                ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						// adding that chartpanel object to our GUI panel
                                                panel.add(chartPanel);
						panel.validate();
						
						
						String sql = "select * from request_1v2 where Date between '"+txt1+"' and '"+txt2+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
											
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
					
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
					else if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
							&& comboBoxEngineer.getSelectedItem().equals("All") 
							&& comboBoxLocation.getSelectedItem().equals(txt4)
							&& comboBoxHardware.getSelectedItem().equals("All")){
					
					JOptionPane.showMessageDialog(null, "All, Location, All");
				
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and "
								+ "date between '"+txt1+"' and '"+txt2+"' group by hardware";
						
						
						String querys = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and "
								+ "date between '"+txt5+"' and '"+txt6+"' group by hardware";
						
						
						
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset.executeQuery(query);
						
						JDBCCategoryDataset dataset2 = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset2.executeQuery(querys);
						
						
						JFreeChart chart = ChartFactory.createLineChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
						JFreeChart chart2 = ChartFactory.createLineChart("Hardware booked out between "+txt5+" and "+txt6+"", "Hardware", "Number", dataset2, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel2 = new ChartPanel(chart2);
						panel_1.removeAll();
						panel_1.add(chartPanel2, BorderLayout.CENTER);
						panel_1.validate();
						
						
						String sql = "select * from request_1v2 where Date between '"+txt1+"' and '"+txt2+"' and Location = '"+txt4+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst=connection.prepareStatement(sql);					
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}

					
				
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
					else if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
							&& comboBoxEngineer.getSelectedItem().equals("All") 
							&& comboBoxLocation.getSelectedItem().equals("All")
							&& comboBoxHardware.getSelectedItem().equals(txt7)){
					
					JOptionPane.showMessageDialog(null, "All, All, Hardware");
				
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM request_1v2 where hardware = '"+txt7+"' and "
								+ "date between '"+txt1+"' and '"+txt2+"' group by hardware";
						
						String query2 = "SELECT hardware, SUM(Quantity) FROM request_1v2 where hardware = '"+txt7+"' and "
								+ "date between '"+txt5+"' and '"+txt6+"' group by hardware";
						
						
						
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset.executeQuery(query);
						
						JDBCCategoryDataset dataset2 = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset2.executeQuery(query2);
						
						
						JFreeChart chart = ChartFactory.createBarChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
						
						JFreeChart chart1 = ChartFactory.createBarChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset2, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel1 = new ChartPanel(chart1);
						panel_1.removeAll();
						panel_1.add(chartPanel1, BorderLayout.CENTER);
						panel_1.validate();
						
						
						
						
						
						
						String sql = "select * from request_1v2 where Date between '"+txt1+"' and '"+txt2+"' and hardware = '"+txt7+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst=connection.prepareStatement(sql);					
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
				
				
				
				
				
					}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				else if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
						&& comboBoxEngineer.getSelectedItem().equals(txt3) 
						&& comboBoxLocation.getSelectedItem().equals("All")
						&& comboBoxHardware.getSelectedItem().equals("All")){
					
					JOptionPane.showMessageDialog(null, "Engineer, All, All");
				
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM request_1v2 where engineer = '"+txt3+"' and date between '"+txt1+"' and '"+txt2+"' group by hardware";
						String query2 = "SELECT hardware, SUM(Quantity) FROM request_1v2 where engineer = '"+txt3+"' and date between '"+txt5+"' and '"+txt6+"' group by hardware";

						
						
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset.executeQuery(query);
						
						JDBCCategoryDataset dataset2 = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset2.executeQuery(query2);
						
						
						JFreeChart chart = ChartFactory.createLineChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
						
						JFreeChart chart1 = ChartFactory.createLineChart("Hardware booked out between "+txt5+" and "+txt6+"", "Hardware", "Number", dataset2, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel1 = new ChartPanel(chart1);
						panel_1.removeAll();
						panel_1.add(chartPanel1, BorderLayout.CENTER);
						panel_1.validate();
						
						
						
						
						String sql = "select * from request_1v2 where Date between '"+txt1+"' and '"+txt2+"' and Engineer = '"+txt3+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst=connection.prepareStatement(sql);					
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
				
				}	
				
				
				
				
				
				
				
				
				
				
				
				
				
				

				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				else if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
						&& comboBoxEngineer.getSelectedItem().equals(txt3) 
						&& comboBoxLocation.getSelectedItem().equals(txt4)
						&& comboBoxHardware.getSelectedItem().equals("All")){
					
					JOptionPane.showMessageDialog(null, "Engineer, Location, All");
				
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and engineer = '"+txt3+"' and date between '"+txt1+"' and '"+txt2+"' group by hardware";
						
						String query2 = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and engineer = '"+txt3+"' and date between '"+txt5+"' and '"+txt6+"' group by hardware";

						
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset.executeQuery(query);
						
						
						JDBCCategoryDataset dataset2 = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset2.executeQuery(query2);
						
						
						
						
						JFreeChart chart = ChartFactory.createLineChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);				
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
						
						JFreeChart chart1 = ChartFactory.createLineChart("Hardware booked out between "+txt5+" and "+txt6+"", "Hardware", "Number", dataset2, PlotOrientation.VERTICAL, false, true, true);				
						ChartPanel chartPanel1 = new ChartPanel(chart1);
						panel_1.removeAll();
						panel_1.add(chartPanel1, BorderLayout.CENTER);
						panel_1.validate();
						
						
						
						
						String sql = "select * from request_1v2 where Engineer = '"+txt3+"' and Location = '"+txt4+"' and Date between '"+txt1+"' and '"+txt2+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst=connection.prepareStatement(sql);					
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
				
				}
				
				
				
				else if(textFieldStartDateR1.getText().equals(txt1) && textFieldStartDateR2.getText().equals(txt2) 
						&& comboBoxEngineer.getSelectedItem().equals(txt3) 
						&& comboBoxLocation.getSelectedItem().equals(txt4)
						&& comboBoxHardware.getSelectedItem().equals(txt7)){
					
					JOptionPane.showMessageDialog(null, "Engineer, Location, Hardware");
				
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and engineer = '"+txt3+"' and hardware = '"+txt7+"' and date between '"+txt1+"' and '"+txt2+"' group by hardware";

						String query2 = "SELECT hardware, SUM(Quantity) FROM request_1v2 where location = '"+txt4+"' and engineer = '"+txt3+"' and hardware = '"+txt7+"' and date between '"+txt5+"' and '"+txt6+"' group by hardware";

						
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset.executeQuery(query);
						
						
						JDBCCategoryDataset dataset2 = new JDBCCategoryDataset(dbConnection.dbConnector());
						dataset2.executeQuery(query2);
						
						
						
						JFreeChart chart = ChartFactory.createBarChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
						
						JFreeChart chart1 = ChartFactory.createBarChart("Hardware booked out between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset2, PlotOrientation.VERTICAL, false, true, true);
						ChartPanel chartPanel1 = new ChartPanel(chart1);
						panel_1.removeAll();
						panel_1.add(chartPanel1, BorderLayout.CENTER);
						panel_1.validate();
						
						
						
						String sql = "select * from request_1v2 where Engineer = '"+txt3+"' and Location = '"+txt4+"' and Date between '"+txt1+"' and '"+txt2+"' order by date desc";
						PreparedStatement pst = connection.prepareStatement(sql);
						pst=connection.prepareStatement(sql);					
						ResultSet rs = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
				
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}	
			
		});
		btnQuery.setBounds(272, 107, 561, 23);
		contentPane.add(btnQuery);
		
		textFieldStartDateR1 = new JTextField();
		textFieldStartDateR1.setBounds(272, 44, 82, 20);
		contentPane.add(textFieldStartDateR1);
		textFieldStartDateR1.setColumns(10);
		
		textFieldStartDateR2 = new JTextField();
		textFieldStartDateR2.setBounds(364, 44, 82, 20);
		contentPane.add(textFieldStartDateR2);
		textFieldStartDateR2.setColumns(10);
		
		textFieldStartDateR3 = new JTextField();
		textFieldStartDateR3.setBounds(872, 44, 82, 21);
		contentPane.add(textFieldStartDateR3);
		textFieldStartDateR3.setColumns(10);
		
		textFieldStartDateR4 = new JTextField();
		textFieldStartDateR4.setBounds(964, 44, 82, 20);
		contentPane.add(textFieldStartDateR4);
		textFieldStartDateR4.setColumns(10);
		
		panel = new JPanel();
		panel.setBounds(10, 141, 697, 407);
		contentPane.add(panel);
		
		JLabel lblRequest = new JLabel("Request");
		lblRequest.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRequest.setBounds(300, 19, 71, 14);
		contentPane.add(lblRequest);
		
		lblBookIn = new JLabel("Book In");
		lblBookIn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBookIn.setBounds(872, 19, 82, 14);
		contentPane.add(lblBookIn);
		
		comboBoxBookInEn = new JComboBox();
		comboBoxBookInEn.setModel(new DefaultComboBoxModel(new String[] {"", "All"}));
		comboBoxBookInEn.setBounds(1056, 40, 98, 56);
		contentPane.add(comboBoxBookInEn);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String txt1 =textFieldStartDateR3.getText().toString();
				String txt2 =textFieldStartDateR4.getText().toString();
				String txt3 =comboBoxBookInEn.getSelectedItem().toString();
				
				if(textFieldStartDateR3.getText().equals(txt1) && textFieldStartDateR4.getText().equals(txt2) && comboBoxBookInEn.getSelectedItem().equals("All")){
					try{
						String query = "SELECT hardware, SUM(Quantity) FROM bookin_v2 where date between '"+txt1+"' and '"+txt2+"'group by hardware";
						//String querys = "select hardware,count(*) from request_1 where date between '"+val1+"' and '"+val1+"' and hardware ='"+val1+"'";
						JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector(),query);
						JFreeChart chart = ChartFactory.createLineChart("All Hardware booked in between "+txt1+" and "+txt2+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);				
						BarRenderer rendererer = null;
						CategoryPlot plot = null;
						rendererer = new BarRenderer();
						ChartPanel chartPanel = new ChartPanel(chart);
						panel.removeAll();
						panel.add(chartPanel, BorderLayout.CENTER);
						panel.validate();
						
						
					}catch (Exception e){
					JOptionPane.showConfirmDialog(null, e);
				}
					
				}
				
				else if(textFieldStartDateR3.getText().equals(txt1) && textFieldStartDateR4.getText().equals(txt2) && comboBoxBookInEn.getSelectedItem().equals(txt3)){
					
					
					//SELECT hardware, SUM(Quantity) FROM bookin_v2 where location = "Jersey" and date between '"+txt1+"' and '"+txt2+"'group by hardware 
					
					//JOptionPane.showMessageDialog(null, txt3);
		
					
					try{
							String query = "SELECT hardware, SUM(Quantity) FROM bookin_v2 where location = '"+txt3+"' and date between '"+txt1+"' and '"+txt2+"'group by hardware";
							//String querys = "select hardware,count(*) from request_1 where date between '"+val1+"' and '"+val1+"' and hardware ='"+val1+"'";
							JDBCCategoryDataset dataset = new JDBCCategoryDataset(dbConnection.dbConnector(),query);
							JFreeChart chart = ChartFactory.createLineChart("All Hardware booked in between "+txt1+" and "+txt2+" from "+txt3+"", "Hardware", "Number", dataset, PlotOrientation.VERTICAL, false, true, true);				
							BarRenderer rendererer = null;
							CategoryPlot plot = null;
							rendererer = new BarRenderer();
							ChartPanel chartPanel = new ChartPanel(chart);
							panel.removeAll();
							panel.add(chartPanel, BorderLayout.CENTER);
							panel.validate();
                                                        
							
							
						}catch (Exception e){
						JOptionPane.showConfirmDialog(null, e);
						}
						
				}
				
			}
		});
		btnView.setBounds(872, 107, 490, 23);
		contentPane.add(btnView);
		
		comboBoxEngineer = new JComboBox();
		comboBoxEngineer.setModel(new DefaultComboBoxModel(new String[] {"", "All"}));
		comboBoxEngineer.setBounds(456, 44, 124, 52);
		contentPane.add(comboBoxEngineer);
		
		comboBoxLocation = new JComboBox();
		comboBoxLocation.setModel(new DefaultComboBoxModel(new String[] {"", "All", "London", "Amsterdam", "Athens", "Brussels", "Isle of Man", "Dublin", "Dubai", "Cape Town", "Edinburgh", "Frankfurt", "Geneva", "Madrid", "Milan", "Paris", "Peterborough", "Luxembourg", "Jersey", "Stockholm", "Vienna", "Warsaw", "Zurich"}));
		comboBoxLocation.setBounds(583, 44, 124, 52);
		contentPane.add(comboBoxLocation);
		
		btnExportToExcel = new JButton("Export To Excel");
		btnExportToExcel.setBackground(new Color(0, 204, 51));
		btnExportToExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				//ExcelExporter exp = new ExcelExporter();
//				try {
//					exp.exportTable(table , new File ("C:/Users/Caiser/Desktop/Excel.xls"));
//					JOptionPane.showMessageDialog(null, "Data exported sucessfully to C:/Users/Jay/Desktop/Excel.xls");
//					
//				} catch (IOException e) {
//					e.getMessage();
//				}
				
				
				
				
				
				
			}
			
		});
		btnExportToExcel.setBounds(729, 631, 161, 67);
		contentPane.add(btnExportToExcel);
		
		textFieldStartDateR5 = new JTextField();
		textFieldStartDateR5.setBounds(272, 76, 82, 20);
		contentPane.add(textFieldStartDateR5);
		textFieldStartDateR5.setColumns(10);
		
		textFieldStartDateR6 = new JTextField();
		textFieldStartDateR6.setBounds(364, 76, 82, 20);
		contentPane.add(textFieldStartDateR6);
		textFieldStartDateR6.setColumns(10);
		
		comboBoxHardware = new JComboBox();
		comboBoxHardware.setModel(new DefaultComboBoxModel(new String[] {"", "All"}));
		comboBoxHardware.setBounds(709, 44, 124, 52);
		contentPane.add(comboBoxHardware);
		
		JLabel lblDate = new JLabel("Date Range");
		lblDate.setBounds(200, 47, 82, 14);
		contentPane.add(lblDate);
		
		lblndDateRange = new JLabel("2nd Date Range  (Optional)");
		lblndDateRange.setBounds(104, 79, 161, 14);
		contentPane.add(lblndDateRange);
		
		panel_1 = new JPanel();
		panel_1.setBounds(717, 141, 645, 407);
		contentPane.add(panel_1);
		
		lblEngineer = new JLabel("Engineer");
		lblEngineer.setBounds(494, 19, 71, 14);
		contentPane.add(lblEngineer);
		
		lblLocation = new JLabel("Location");
		lblLocation.setBounds(622, 19, 59, 14);
		contentPane.add(lblLocation);
		
		lblHardware = new JLabel("Hardware");
		lblHardware.setBounds(748, 19, 71, 14);
		contentPane.add(lblHardware);
		
		textField = new JTextField();
		textField.setBounds(872, 76, 82, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(964, 76, 82, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "All", "London", "Amsterdam", "Athens", "Brussels", "Isle of Man", "Dublin", "Dubai", "Cape Town", "Edinburgh", "Frankfurt", "Geneva", "Madrid", "Milan", "Paris", "Peterborough", "Luxembourg", "Jersey", "Stockholm", "Vienna", "Warsaw", "Zurich"}));
		comboBox.setBounds(1164, 40, 98, 56);
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"", "All"}));
		comboBox_1.setBounds(1272, 40, 90, 56);
		contentPane.add(comboBox_1);
		
		refreshTable();
		fillEngineerComboBox();
		fillHardwareComboBox();
	}
        
}