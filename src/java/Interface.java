import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Window.Type;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JList;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Interface extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interface() {
		setBackground(Color.GRAY);
		setForeground(Color.BLACK);
		setTitle("Juguinhu ingarssadu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 3, 10, 30));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblManager = new JLabel("Manager 1");
		lblManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblManager.setBounds(12, 13, 228, 16);
		panel_4.add(lblManager);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.setBounds(123, 42, 112, 196);
		panel_4.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblEmpresas = new JLabel("Empresas");
		lblEmpresas.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmpresas.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmpresas.setBounds(12, 6, 93, 16);
		panel_6.add(lblEmpresas);
		
		JLabel lblEmpresa = new JLabel("Empresa 1");
		lblEmpresa.setBounds(12, 35, 93, 16);
		panel_6.add(lblEmpresa);
		
		JLabel lblEmpresa_3 = new JLabel("Empresa 2");
		lblEmpresa_3.setBounds(12, 64, 93, 16);
		panel_6.add(lblEmpresa_3);
		
		JLabel lblEmpresa_2 = new JLabel("Empresa 3");
		lblEmpresa_2.setBounds(12, 93, 93, 16);
		panel_6.add(lblEmpresa_2);
		
		JLabel lblEmpresa_1 = new JLabel("Empresa 4");
		lblEmpresa_1.setBounds(12, 122, 93, 16);
		panel_6.add(lblEmpresa_1);
		
		JLabel lblMoney = new JLabel("Money");
		lblMoney.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoney.setBounds(12, 42, 99, 16);
		panel_4.add(lblMoney);
		
		JLabel lblXxxxx = new JLabel("XXXXX \u20AC");
		lblXxxxx.setHorizontalAlignment(SwingConstants.CENTER);
		lblXxxxx.setBounds(12, 71, 99, 16);
		panel_4.add(lblXxxxx);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblManager_2 = new JLabel("Manager 2");
		lblManager_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblManager_2.setBounds(12, 13, 228, 16);
		panel_1.add(lblManager_2);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setBounds(123, 47, 112, 191);
		panel_1.add(panel_7);
		
		JLabel label_1 = new JLabel("Empresas");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(12, 6, 93, 16);
		panel_7.add(label_1);
		
		JLabel label_2 = new JLabel("Empresa 1");
		label_2.setBounds(12, 35, 93, 16);
		panel_7.add(label_2);
		
		JLabel label_3 = new JLabel("Empresa 2");
		label_3.setBounds(12, 64, 93, 16);
		panel_7.add(label_3);
		
		JLabel label_4 = new JLabel("Empresa 3");
		label_4.setBounds(12, 93, 93, 16);
		panel_7.add(label_4);
		
		JLabel label_5 = new JLabel("Empresa 4");
		label_5.setBounds(12, 122, 93, 16);
		panel_7.add(label_5);
		
		JLabel label_6 = new JLabel("Money");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_6.setBounds(12, 51, 99, 16);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("XXXXX \u20AC");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(12, 86, 99, 16);
		panel_1.add(label_7);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_8.setBackground(Color.LIGHT_GRAY);
		panel_8.setBounds(123, 42, 112, 196);
		panel_2.add(panel_8);
		
		JLabel label_9 = new JLabel("Empresas");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_9.setBounds(12, 6, 93, 16);
		panel_8.add(label_9);
		
		JLabel label_10 = new JLabel("Empresa 1");
		label_10.setBounds(12, 35, 93, 16);
		panel_8.add(label_10);
		
		JLabel label_11 = new JLabel("Empresa 2");
		label_11.setBounds(12, 64, 93, 16);
		panel_8.add(label_11);
		
		JLabel label_12 = new JLabel("Empresa 3");
		label_12.setBounds(12, 93, 93, 16);
		panel_8.add(label_12);
		
		JLabel label_13 = new JLabel("Empresa 4");
		label_13.setBounds(12, 122, 93, 16);
		panel_8.add(label_13);
		
		JLabel label_14 = new JLabel("Money");
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_14.setBounds(12, 42, 99, 16);
		panel_2.add(label_14);
		
		JLabel label_15 = new JLabel("XXXXX \u20AC");
		label_15.setHorizontalAlignment(SwingConstants.CENTER);
		label_15.setBounds(12, 71, 99, 16);
		panel_2.add(label_15);
		
		JLabel lblManager_1 = new JLabel("Manager 3");
		lblManager_1.setBounds(12, 13, 228, 16);
		panel_2.add(lblManager_1);
		lblManager_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblInvestor = new JLabel("Investor");
		lblInvestor.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvestor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInvestor.setBounds(12, 13, 228, 16);
		panel.add(lblInvestor);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_9.setBackground(Color.LIGHT_GRAY);
		panel_9.setBounds(123, 42, 112, 196);
		panel.add(panel_9);
		
		JLabel label_17 = new JLabel("Empresas");
		label_17.setHorizontalAlignment(SwingConstants.CENTER);
		label_17.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_17.setBounds(12, 6, 93, 16);
		panel_9.add(label_17);
		
		JLabel label_18 = new JLabel("Empresa 1");
		label_18.setBounds(12, 35, 93, 16);
		panel_9.add(label_18);
		
		JLabel label_19 = new JLabel("Empresa 2");
		label_19.setBounds(12, 64, 93, 16);
		panel_9.add(label_19);
		
		JLabel label_20 = new JLabel("Empresa 3");
		label_20.setBounds(12, 93, 93, 16);
		panel_9.add(label_20);
		
		JLabel label_21 = new JLabel("Empresa 4");
		label_21.setBounds(12, 122, 93, 16);
		panel_9.add(label_21);
		
		JLabel label_22 = new JLabel("Money");
		label_22.setHorizontalAlignment(SwingConstants.CENTER);
		label_22.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_22.setBounds(12, 42, 99, 16);
		panel.add(label_22);
		
		JLabel label_23 = new JLabel("XXXXX \u20AC");
		label_23.setHorizontalAlignment(SwingConstants.CENTER);
		label_23.setBounds(12, 73, 99, 16);
		panel.add(label_23);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblInvestor_1 = new JLabel("Investor 2");
		lblInvestor_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvestor_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInvestor_1.setBounds(12, 13, 228, 16);
		panel_3.add(lblInvestor_1);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_10.setBackground(Color.LIGHT_GRAY);
		panel_10.setBounds(123, 42, 112, 196);
		panel_3.add(panel_10);
		
		JLabel label_25 = new JLabel("Empresas");
		label_25.setHorizontalAlignment(SwingConstants.CENTER);
		label_25.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_25.setBounds(12, 6, 93, 16);
		panel_10.add(label_25);
		
		JLabel label_26 = new JLabel("Empresa 1");
		label_26.setBounds(12, 35, 93, 16);
		panel_10.add(label_26);
		
		JLabel label_27 = new JLabel("Empresa 2");
		label_27.setBounds(12, 64, 93, 16);
		panel_10.add(label_27);
		
		JLabel label_28 = new JLabel("Empresa 3");
		label_28.setBounds(12, 93, 93, 16);
		panel_10.add(label_28);
		
		JLabel label_29 = new JLabel("Empresa 4");
		label_29.setBounds(12, 122, 93, 16);
		panel_10.add(label_29);
		
		JLabel label_30 = new JLabel("Money");
		label_30.setHorizontalAlignment(SwingConstants.CENTER);
		label_30.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_30.setBounds(12, 42, 99, 16);
		panel_3.add(label_30);
		
		JLabel label_31 = new JLabel("XXXXX \u20AC");
		label_31.setHorizontalAlignment(SwingConstants.CENTER);
		label_31.setBounds(12, 71, 99, 16);
		panel_3.add(label_31);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblInvestor_2 = new JLabel("Investor 3");
		lblInvestor_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvestor_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInvestor_2.setBounds(12, 13, 228, 16);
		panel_5.add(lblInvestor_2);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11.setBackground(Color.LIGHT_GRAY);
		panel_11.setBounds(123, 42, 112, 196);
		panel_5.add(panel_11);
		
		JLabel label_33 = new JLabel("Empresas");
		label_33.setHorizontalAlignment(SwingConstants.CENTER);
		label_33.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_33.setBounds(12, 6, 93, 16);
		panel_11.add(label_33);
		
		JLabel label_34 = new JLabel("Empresa 1");
		label_34.setBounds(12, 35, 93, 16);
		panel_11.add(label_34);
		
		JLabel label_35 = new JLabel("Empresa 2");
		label_35.setBounds(12, 64, 93, 16);
		panel_11.add(label_35);
		
		JLabel label_36 = new JLabel("Empresa 3");
		label_36.setBounds(12, 93, 93, 16);
		panel_11.add(label_36);
		
		JLabel label_37 = new JLabel("Empresa 4");
		label_37.setBounds(12, 122, 93, 16);
		panel_11.add(label_37);
		
		JLabel label_38 = new JLabel("Money");
		label_38.setHorizontalAlignment(SwingConstants.CENTER);
		label_38.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_38.setBounds(12, 42, 99, 16);
		panel_5.add(label_38);
		
		JLabel label_39 = new JLabel("XXXXX \u20AC");
		label_39.setHorizontalAlignment(SwingConstants.CENTER);
		label_39.setBounds(12, 71, 99, 16);
		panel_5.add(label_39);
	}
}
