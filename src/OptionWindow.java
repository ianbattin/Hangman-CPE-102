import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class OptionWindow extends JFrame implements ActionListener
{
	private int difficulty;
	final JLabel difficultyButton;
	final JLabel players;
	private int playerAmount;
	
	public OptionWindow()
	{
		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		
		difficultyButton = new JLabel("  Choose your Difficulty:   ", SwingConstants.CENTER);
		final JButton easy = new JButton("Easy");
		final JButton medium = new JButton("Medium");
		final JButton hard = new JButton("Hard");
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		players = new JLabel("Choose player amount", SwingConstants.CENTER);
		final JButton one =new JButton("One");
		final JButton two = new JButton("Two");
		final JButton start = new JButton("Start");
		one.addActionListener(this);
		two.addActionListener(this);
		start.addActionListener(this);
		
		JPanel westPanel = new JPanel(new GridLayout(4, 2));
		JPanel eastPanel = new JPanel(new GridLayout(4, 1));
		
		westPanel.add(difficultyButton);
		westPanel.add(easy);
		westPanel.add(medium);
		westPanel.add(hard);
		
		eastPanel.add(players);
		eastPanel.add(one);
		eastPanel.add(two);
		eastPanel.add(start);
		
		corePanel.add(westPanel, BorderLayout.WEST);	
		corePanel.add(eastPanel, BorderLayout.EAST);	

		this.add(corePanel, BorderLayout.CENTER);
		
		Dimension d = new Dimension(300, 300);
		this.setPreferredSize(d);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		String selected = e.getActionCommand();
		if(selected.equals("Easy"))
		{
			difficulty = 1;
			difficultyButton.setText("Difficulty selected: Easy");
		}
		else if(selected.equals("Medium"))
		{
			difficulty = 2;
			difficultyButton.setText("Difficulty selected: Medium");
		}
		else if(selected.equals("Hard"))
		{
			difficulty = 3;
			difficultyButton.setText("Difficulty selected: Hard");
		}
		else if(selected.equals("One"))
		{
			playerAmount = 1;
			players.setText("  Player amount: One   ");
		}
		else if(selected.equals("Two"))
		{
			playerAmount = 2;
			players.setText("Player amount: Two");
		}
		else if(selected.equals("Start") && difficulty != 0 && playerAmount != 0)
		{
			new MainWindow(this);
			this.dispose();
		}
		else
		{
			difficultyButton.setText("Please select a difficulty");
			players.setText("Please select a player amount");
		}
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public int getPlayers()
	{
		return playerAmount;
	}
}
