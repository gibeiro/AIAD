import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Window.Type;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.JSeparator;
import javax.swing.JList;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

import game.Engine;
/*import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;*/


import java.util.logging.*;



public class Interface extends JFrame {

	private JPanel contentPane;
	private final int PANEL_WIDTH = 228;
	private final int PANEL_HEIGHT = 400;
	private final int HOR_MARGIN = 10;
	private final int VER_MARGIN = 20;

	/**
	 * Create the frame.
	 */
	public Interface(Engine game) {
		setBackground(Color.GRAY);
		setForeground(Color.BLACK);
		setTitle("Juguinhu ingarssadu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 900);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*JPanel panels = new JPanel();
		panels.setBounds(10, 10, PANEL_WIDTH, PANEL_HEIGHT);
		contentPane.add(panels);
		panels.setLayout(null);
		
		JLabel lblManager = new JLabel("Manager 1");
		lblManager.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblManager.setBounds(12, 13, 204, 22);
		panels.add(lblManager);
		
		JLabel lblNewLabel = new JLabel("Money :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 61, 103, 16);
		panels.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(12, 90, 204, 500);
		//panels.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Companies");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 13, 180, 16);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(12, 42, 56, 16);
		panel_1.add(lblNewLabel_2);
		
		JLabel label = new JLabel("New label");
		label.setBackground(Color.RED);
		label.setBounds(12, 71, 56, 16);
		label.setOpaque(true);
		panel_1.add(label);
		
		JLabel lblXxx = new JLabel("xxx \u20AC");
		lblXxx.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXxx.setBounds(136, 42, 56, 16);
		panel_1.add(lblXxx);
		
		JLabel lblXxxxxx = new JLabel("XXXXXX \u20AC");
		lblXxxxxx.setHorizontalAlignment(SwingConstants.CENTER);
		lblXxxxxx.setBounds(113, 61, 103, 16);
		panels.add(lblXxxxxx);*/
		
		for(int i = 0 ; i<game.managers.size() ; i++) {
			JPanel panel = new JPanel();
			panel.setBounds(10+PANEL_WIDTH*i+HOR_MARGIN*i, 10, PANEL_WIDTH, PANEL_HEIGHT);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblManager1 = new JLabel(game.managers.get(i).getName());
			lblManager1.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblManager1.setHorizontalAlignment(SwingConstants.CENTER);
			lblManager1.setBounds(12, 13, 204, 22);
			panel.add(lblManager1);
			
			JLabel lblMoney = new JLabel("Money :");
			lblMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
			lblMoney.setBounds(12, 61, 103, 16);
			panel.add(lblMoney);
			
			JLabel money = new JLabel(Integer.toString(game.managers.get(i).getCash()) + " \u20AC");
			money.setHorizontalAlignment(SwingConstants.CENTER);
			money.setBounds(113, 61, 103, 16);
			panel.add(money);
			
			JPanel panel_companies = new JPanel();
			panel_companies.setBackground(Color.LIGHT_GRAY);
			panel_companies.setPreferredSize(new Dimension(204,297));
			//panel.add(panel_companies);
			panel_companies.setLayout(null);
			
			JScrollPane scroll = new JScrollPane(panel_companies);
			scroll.setBounds(10, 90, 208, 297);
			panel.add(scroll);
			
			JLabel lblCompanies = new JLabel("Companies");
			lblCompanies.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblCompanies.setHorizontalAlignment(SwingConstants.CENTER);
			lblCompanies.setBounds(12, 13, 180, 16);
			panel_companies.add(lblCompanies);
			
			//ciclo for
			for(int j=0;j<game.managers.get(i).getCompanies().size();j++) {
				JLabel lblCompanie_1 = new JLabel(game.managers.get(i).getCompanies().get(j).getName());
				lblCompanie_1.setBounds(12, 42+29*j, 56, 16);
				lblCompanie_1.setOpaque(true);
				switch(game.managers.get(i).getCompanies().get(j).getColor().toString()) {
				case "red":
					lblCompanie_1.setBackground(Color.RED);
					break;
				case "yellow":
					lblCompanie_1.setBackground(Color.YELLOW);
					break;
				case "green":
					lblCompanie_1.setBackground(Color.GREEN);
					break;
				case "blue":
					lblCompanie_1.setBackground(Color.BLUE);
					break;
				default:
					lblCompanie_1.setOpaque(false);
					break;
				}
				panel_companies.add(lblCompanie_1);
			}
		}
		
		for(int i = 0 ; i<game.investors.size() ; i++) {
			JPanel panel = new JPanel();
			panel.setBounds(10+PANEL_WIDTH*i+HOR_MARGIN*i, 10+PANEL_HEIGHT+VER_MARGIN, PANEL_WIDTH, PANEL_HEIGHT);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblManager1 = new JLabel(game.investors.get(i).getName());
			lblManager1.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblManager1.setHorizontalAlignment(SwingConstants.CENTER);
			lblManager1.setBounds(12, 13, 204, 22);
			panel.add(lblManager1);
			
			JLabel lblMoney = new JLabel("Money :");
			lblMoney.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
			lblMoney.setBounds(12, 61, 103, 16);
			panel.add(lblMoney);
			
			JLabel money = new JLabel(Integer.toString(game.managers.get(i).getCash()) + " \u20AC");
			money.setHorizontalAlignment(SwingConstants.CENTER);
			money.setBounds(113, 61, 103, 16);
			panel.add(money);
			
			JPanel panel_companies = new JPanel();
			panel_companies.setBackground(Color.LIGHT_GRAY);
			panel_companies.setPreferredSize(new Dimension(204,297));
			//panel.add(panel_companies);
			panel_companies.setLayout(null);
			
			JScrollPane scroll = new JScrollPane(panel_companies);
			scroll.setBounds(10, 90, 208, 297);
			panel.add(scroll);
			
			JLabel lblCompanies = new JLabel("Companies");
			lblCompanies.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblCompanies.setHorizontalAlignment(SwingConstants.CENTER);
			lblCompanies.setBounds(12, 13, 180, 16);
			panel_companies.add(lblCompanies);
			
			//ciclo for
			
			JLabel lblCompanie_1 = new JLabel("New label");
			lblCompanie_1.setBounds(12, 42, 56, 16);
			panel_companies.add(lblCompanie_1);
			
			JLabel lblCompanie_2 = new JLabel("New label");
			lblCompanie_2.setBounds(12, 71, 56, 16);
			panel_companies.add(lblCompanie_2);
		}
	}
	
	/**
	 * update values
	 */
	public void update(Engine game) {
		for(int i=0;i<game.managers.size();i++) {
			JPanel panel = (JPanel)this.getContentPane().getComponent(i);
			if(game.managers.get(i).isBankrupt()){
				((JLabel)panel.getComponent(2)).setText("BANKRUPT");
			}else{
				((JLabel)panel.getComponent(2)).setText(Integer.toString(game.managers.get(i).getCash()) + " €");
			}
			
			JPanel panel_companies = (JPanel)((JViewport)((JScrollPane)panel.getComponent(3)).getComponent(0)).getView();
			panel_companies.setPreferredSize(new Dimension(204,42+29*game.managers.get(i).getCompanies().size()));
			panel_companies.removeAll();
			
			JLabel lblCompanies = new JLabel("Companies");
			lblCompanies.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblCompanies.setHorizontalAlignment(SwingConstants.CENTER);
			lblCompanies.setBounds(12, 13, 180, 16);
			panel_companies.add(lblCompanies);
			
			for(int j=0;j<game.managers.get(i).getCompanies().size();j++) {
				JLabel lblCompanie_1 = new JLabel(game.managers.get(i).getCompanies().get(j).getName());
				lblCompanie_1.setBounds(12, 42+29*j, 56, 16);
				lblCompanie_1.setOpaque(true);
				switch(game.managers.get(i).getCompanies().get(j).getColor().toString()) {
				case "red":
					lblCompanie_1.setBackground(Color.RED);
					break;
				case "yellow":
					lblCompanie_1.setBackground(Color.YELLOW);
					break;
				case "green":
					lblCompanie_1.setBackground(Color.GREEN);
					break;
				case "blue":
					lblCompanie_1.setBackground(Color.BLUE);
					break;
				default:
					lblCompanie_1.setOpaque(false);
					break;
				}
				panel_companies.add(lblCompanie_1);
			}
			
			panel_companies.revalidate();
			panel_companies.repaint();
		}
		for(int i=0;i<game.investors.size();i++) {
			JPanel panel = (JPanel)this.getContentPane().getComponent(game.managers.size()+i);
			if(game.investors.get(i).isBankrupt()) {
				((JLabel)panel.getComponent(2)).setText("BANKRUPT");
			}else {
				((JLabel)panel.getComponent(2)).setText(Integer.toString(game.investors.get(i).getCash()) + " €");
			}
			
			JPanel panel_companies = (JPanel)((JViewport)((JScrollPane)panel.getComponent(3)).getComponent(0)).getView();
			panel_companies.setPreferredSize(new Dimension(204,42+29*game.managers.get(i).getCompanies().size()));
			panel_companies.removeAll();
			
			JLabel lblCompanies = new JLabel("Companies");
			lblCompanies.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblCompanies.setHorizontalAlignment(SwingConstants.CENTER);
			lblCompanies.setBounds(12, 13, 180, 16);
			panel_companies.add(lblCompanies);
			
			for(int j=0;j<game.investors.get(i).getCompanies().size();j++) {
				JLabel lblCompanie_1 = new JLabel(game.investors.get(i).getCompanies().get(j).getName());
				lblCompanie_1.setBounds(12, 42+29*j, 56, 16);
				lblCompanie_1.setOpaque(true);
				switch(game.investors.get(i).getCompanies().get(j).getColor().toString()) {
				case "red":
					lblCompanie_1.setBackground(Color.RED);
					break;
				case "yellow":
					lblCompanie_1.setBackground(Color.YELLOW);
					break;
				case "green":
					lblCompanie_1.setBackground(Color.GREEN);
					break;
				case "blue":
					lblCompanie_1.setBackground(Color.BLUE);
					break;
				default:
					lblCompanie_1.setOpaque(false);
					break;
				}
				panel_companies.add(lblCompanie_1);
				
				JLabel lblCost = new JLabel(Integer.toString(game.investors.get(i).getCompanies().get(j).getPrice()) + " \u20AC");
				lblCost.setHorizontalAlignment(SwingConstants.RIGHT);
				lblCost.setBounds(136, 42+29*j, 56, 16);
				panel_companies.add(lblCost);
			}
			
			panel_companies.revalidate();
			panel_companies.repaint();
		}
	}
}
