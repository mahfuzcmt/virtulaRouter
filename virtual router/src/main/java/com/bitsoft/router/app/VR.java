package com.bitsoft.router.app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class VR extends JFrame{

	private JFrame frame;
	private JTextArea textArea = new JTextArea();
	/*private JComboBox comboBox = new JComboBox();*/
	private JTextField txtPassword = new JTextField();
	private JTextField txtName = new JTextField();
	private JPasswordField passwordField;

	
	
	public VR() {
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\mahfuz.ahmed\\Desktop\\vr.png"));
		frame.setBounds(100, 100, 284, 239);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setTitle("Virtual Router");
		
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*if(comboBox.getSelectedIndex()>0){
					String ssid =comboBox.getSelectedItem().toString();
				}*/
				System.out.println(passwordField.getPassword());
				final String cmd = "netsh wlan set hostednetwork mode=allow ssid="+txtName.getText()+" key="+passwordField.getPassword()+" ";
				if(runCommand(cmd)){
					textArea.setText("Starting......");
					textArea.setText("Vertual Router "+txtName.getText()+" is running........");
					textArea.setForeground(Color.green);
				}
						
				final String start = "netsh wlan start hostednetwork";
				if(runCommand(start)){
					/*final String permission = "add filter permission=allow ssid="+comboBox.getSelectedItem()+" networktype=infrastructure ";
					if(runCommand(permission)){
						textArea.setText("Vertual Router "+name+" Started. Password is : "+password);
					}*/
				}
			}
		});
		btnNewButton.setBounds(46, 107, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String stop = "netsh wlan stop hostednetwork";
				if(runCommand(stop)){
					textArea.setText("Stopped");
					textArea.setForeground(Color.red);
				};				
			}
		});
		btnStop.setBounds(145, 107, 89, 23);
		frame.getContentPane().add(btnStop);
		
		textArea.setBounds(0, 141, 278, 42);
		frame.getContentPane().add(textArea);
		
		/*comboBox.setBounds(88, 107, 145, 20);
		frame.getContentPane().add(comboBox);*/

		passwordField = new JPasswordField();
		passwordField.setBounds(77, 76, 126, 20);
		passwordField.setColumns(10);
		frame.getContentPane().add(passwordField);
		
		
		
		txtName.setColumns(10);
		txtName.setBounds(77, 46, 126, 20);
		frame.getContentPane().add(txtName);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 46, 68, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 79, 68, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblDevelopedByMahfuz = new JLabel("Developed by Mahfuz Ahmed\r\n");
		lblDevelopedByMahfuz.setFont(new Font("Arial", Font.BOLD, 11));
		lblDevelopedByMahfuz.setForeground(Color.BLACK);
		lblDevelopedByMahfuz.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevelopedByMahfuz.setToolTipText("mahfuzcmt@gmail.com");
		lblDevelopedByMahfuz.setBounds(0, 182, 247, 20);
		frame.getContentPane().add(lblDevelopedByMahfuz);
		
		final JButton btnSH = new JButton("Show");
		btnSH.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(passwordField.getEchoChar() == (char)0){
					passwordField.setEchoChar('*');
					btnSH.setText("Show");
				}
				else{
					passwordField.setEchoChar((char)0);
					btnSH.setText("Hide");
				}
			}
		});
		btnSH.setBounds(209, 75, 59, 23);
		frame.getContentPane().add(btnSH);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 278, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnSettigns = new JMenu("Settings");
		menuBar.add(mnSettigns);
		
		JMenuItem mntmHomeNetwork = new JMenuItem("Home Network ");
		mnSettigns.add(mntmHomeNetwork);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmContact = new JMenuItem("contact");
		mnHelp.add(mntmContact);
		
		JMenuItem mntmShare = new JMenuItem("share");
		mnHelp.add(mntmShare);
		
	/*	String res;
		try {
			Process process = Runtime.getRuntime().exec("netsh wlan show interfaces");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((res = stdInput.readLine()) != null) {
            	if(res.contains("SSID") && !res.contains("BSSID")){
            		String[] array = res.split("\\:", -1);
            		comboBox.addItem(array[1]);
            	}
            	System.out.println(res);
            }
           
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
	}
	
	public boolean runCommand(String cmd){
		 String response = "";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
           
            while ((response = stdInput.readLine()) != null) {
            	return true;
            }
           
            // Read command errors
            while ((response = stdError.readLine()) != null) {
            	return false;
            }
            textArea.setText(response);
		} catch (IOException e1) {
			textArea.setText(response);
			e1.printStackTrace();
			return false;
		}
		return true;
	}
}
