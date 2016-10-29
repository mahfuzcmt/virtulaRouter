package com.bitsoft.router.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class VirtualRouter extends JFrame {

	private Logger _logger = Logger.getLogger(this.getClass());
	private JTextField txtName;
	private JPasswordField passwordField;
	private JLabel lblStatus = new JLabel("");
	JLabel lblpassword;
	JButton btnSignIn;
	JLabel lblfooter;
	private JMenuBar menuBar;
	
	public VirtualRouter() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\mahfuz.ahmed\\Desktop\\download.jpg"));
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}

	private void initialize() {
		
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(0, 165, 260, 70);
		lblStatus.setBackground(SystemColor.WHITE);
		
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Virtual Router");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		lblpassword = new JLabel();
		lblpassword.setText("Password");
		lblpassword.setBounds(22, 74, 70, 20);
		getContentPane().add(lblpassword);
		
		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtName.setBackground(SystemColor.WHITE);
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    }
			}
		});
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String username = txtName.getText();
				if( username.equals("")){
					txtName.setBackground(new Color(255, 180, 193));
				}
			}
		});
		txtName.setBounds(98, 43, 122, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		lblfooter = new JLabel();
		lblfooter.setToolTipText("mahfuzcmt@gmail.com");
		lblfooter.setText("Developed by Mahfuz Ahmed");
		lblfooter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblfooter.setHorizontalAlignment(SwingConstants.CENTER);
		lblfooter.setOpaque(true);
		lblfooter.setBounds(0, 246, 260, 15);
		lblfooter.setForeground(Color.WHITE);
		lblfooter.setBackground(Color.GRAY);
		
		btnSignIn = new JButton();
		btnSignIn.setForeground(Color.BLACK);
		btnSignIn.setText("Start");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Strings.isNullOrEmpty(txtName.getText())){
					setStatusMsg("Please put a name", false);
					return;
				}
				if(Strings.isNullOrEmpty(passwordField.getPassword().toString()) || passwordField.getPassword().length<8){
					setStatusMsg("Please put a 8 digits/character password", false);
					return;
				}
				final String cmd = "netsh wlan set hostednetwork mode=allow ssid="+txtName.getText()+" key="+passwordField.getPassword()+" ";
				
				if(runCommand(cmd)){
					setStatusMsg("Starting......", true);
				}
				final String start = "netsh wlan start hostednetwork";
				if(runCommand(start)){
					txtName.setEnabled(false);
					passwordField.setEnabled(false);
					 setStatusMsg("Vertual Router "+txtName.getText()+" is running........", true);
					/*final String permission = "add filter permission=allow ssid="+comboBox.getSelectedItem()+" networktype=infrastructure ";
					if(runCommand(permission)){
						lblWar.setText("Vertual Router "+name+" Started. Password is : "+password);
					}*/
				}
			}
		});
		
		btnSignIn.setBounds(98, 131, 56, 23);
		getContentPane().add(btnSignIn);
		
		getContentPane().add(lblfooter);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				passwordField.setBackground(SystemColor.WHITE);
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    }
			}
		});
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String password = passwordField.getPassword().toString();
				if( password.equals("")){
					passwordField.setBackground(new Color(255, 180, 193));
				}
			}
		});
		passwordField.setBounds(98, 74, 122, 20);
		getContentPane().add(passwordField);
		
		getContentPane().add(lblStatus);
		
		JLabel lblName = new JLabel();
		lblName.setText("Name");
		lblName.setBounds(22, 43, 56, 20);
		getContentPane().add(lblName);
		
		JButton btnStop = new JButton();
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final String stop = "netsh wlan stop hostednetwork";
				if(runCommand(stop)){
					lblStatus.setText("Stopped");
					lblStatus.setForeground(Color.red);
					txtName.setEnabled(true);
					passwordField.setEnabled(true);
				};				
			}
		});
		btnStop.setText("Stop");
		btnStop.setForeground(Color.BLACK);
		btnStop.setBounds(164, 131, 56, 23);
		getContentPane().add(btnStop);
		
		final ButtonGroup showHide = new ButtonGroup();
		
		
		JRadioButton rdbtnHide = new JRadioButton("Hide");
		rdbtnHide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwordField.setEchoChar('*');
			}
		});
		
		rdbtnHide.setSelected(true);
		rdbtnHide.setBackground(Color.WHITE);
		rdbtnHide.setBounds(98, 101, 47, 23);
		getContentPane().add(rdbtnHide);
		
		JRadioButton rdbtnShow = new JRadioButton("Show");
		rdbtnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwordField.setEchoChar((char)0);
			}
		});
		rdbtnShow.setBackground(Color.WHITE);
		rdbtnShow.setBounds(164, 101, 56, 23);
		getContentPane().add(rdbtnShow);
		setBounds(100, 100, 266, 280);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final ButtonGroup language = new ButtonGroup();
		language.add(rdbtnHide);
		language.add(rdbtnShow);
		
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 260, 21);
		getContentPane().add(menuBar);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmHomeNetwork = new JMenuItem("Home Network");
		mnSettings.add(mntmHomeNetwork);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmSetHomeNetwork = new JMenuItem("Set Home Network");
		mnHelp.add(mntmSetHomeNetwork);
		
		JMenuItem mntmContactWithDeveloper = new JMenuItem("Contact with developer");
		mnHelp.add(mntmContactWithDeveloper);
		setBounds(100, 100, 266, 290);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		}
	
		protected ImageIcon createImageIcon(String path) {
			java.net.URL imgURL = VirtualRouter.class.getClassLoader().getResource(path);
			if (imgURL != null) {
			return new ImageIcon(imgURL);
			} else {
			return null;
			}
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
	            setStatusMsg(response, true);
			} catch (IOException e1) {
				 setStatusMsg(response, false);
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		
		public void setStatusMsg(String msg, boolean IsSuccess){
			lblStatus.setText(msg);
			lblStatus.setForeground(SystemColor.RED);
			if(IsSuccess){
				lblStatus.setForeground(SystemColor.GREEN);
			}
		}
	}
