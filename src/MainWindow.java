import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {
	
	private int remainingGuesses;
	private String wrongGuesses;
	private String word;
	private String visible;
	private Scanner sc;
	private ArrayList<String> lettersGuessed;

	public static void main(String[] args) 
	{
		new MainWindow();
	}
	
	public String chooseRandomWord(int d)
	{
		int difficulty = d;
		int line = (int) (Math.random()*109583);
		String randomWord = "";
		String word = "";
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("wordsEn.txt"));
			boolean wordChosen = false;
			
			for(int i = 0; i < line; i++)
			{
				br.readLine();
			}
			word = br.readLine();
			
			while(!wordChosen)
			{
				int characters = getUniqueCharacters(word);
				if(difficulty == 1 && characters <= 5)
				{
					randomWord = word;
					wordChosen = true;
				}
				else if(difficulty == 2 && 5 < characters && characters <= 10)
				{
					randomWord = word;
					wordChosen = true;
				}
				else if(difficulty == 3 && 10 < characters)
				{
					randomWord = word;
					wordChosen = true;
				}
				else
				{
					try{
						word = br.readLine();
					}
					catch(Exception e)
					{
						System.out.println("Reached end of File");
						br.reset();
						
						line = (int) (Math.random()*109583);
						for(int i = 0; i < line; i++)
						{
							br.readLine();
						}
						word = br.readLine();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return randomWord;
	}
	
	public int getUniqueCharacters(String s)
	{
		ArrayList<String> characters = new ArrayList<String>();
		
		for(int i = 0; i < s.length(); i++)
		{
			if(!characters.contains(s.substring(i, i+1)))
			{
				characters.add(s.substring(i, i+1));
			}
		}
		
		return characters.size();
	}
	
	public MainWindow() {
		lettersGuessed = new ArrayList<String>();
		remainingGuesses = 10;
		wrongGuesses = "";
		word = chooseRandomWord(3);

		visible = "";

		for(int i = 0; i < word.length(); ++i) {
			visible += "_ ";
		}

		JPanel corePanel = new JPanel();
		corePanel.setLayout(new BorderLayout());
		
		final JLabel status = new JLabel("You have "+remainingGuesses+" remaining", SwingConstants.CENTER);
		final JLabel wrong = new JLabel("Wrong guesses so far: "+wrongGuesses);
		final JLabel visibleLabel = new JLabel(visible, SwingConstants.CENTER);
		final JTextField input = new JTextField(); 
		
		JPanel southPanel = new JPanel(new GridLayout(4, 1));
		southPanel.add(status);
		southPanel.add(visibleLabel);
		southPanel.add(input);
		southPanel.add(wrong);
		
		corePanel.add(southPanel, BorderLayout.SOUTH);
		
		final HangmanFigure hf = new HangmanFigure();
		corePanel.add(hf, BorderLayout.CENTER);

		this.add(corePanel, BorderLayout.CENTER);
		
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String text = input.getText();
				
				if(text.length()  == 1 && text.matches("[a-z]") && !lettersGuessed.contains(text)) {
					
					boolean guessFound = false;
					lettersGuessed.add(text);
					
					for(int i = 0; i < word.length(); i++) {
						if(text.charAt(0) == word.charAt(i)) {
							guessFound = true;
							
							String newVisible = "";
							for(int j = 0; j < visible.length(); j++) {
								if(j == (i*2)) {
									newVisible += word.charAt(i);
								}
								else {
									newVisible += visible.charAt(j);
								}
							}
							visible = newVisible;
							visibleLabel.setText(visible);
						}
					}
					
					if(!guessFound) {
						if(--remainingGuesses >= 1) {
							status.setText("You have "+remainingGuesses+" guesses remaining");
							wrongGuesses += text+" ";
							wrong.setText("Wrong guesses so far: "+wrongGuesses);
							hf.set();
						}
						else {
							status.setText("You lost: the word was "+word);
							input.setEnabled(false);
						}
					}
					else {
						String actualVisible = "";
						for(int i = 0; i < visible.length(); i+=2) {
							actualVisible += visible.charAt(i);
						}
						
						if(actualVisible.equals(word)) {
							status.setText("Congratulations, you have won!");
							input.setEnabled(false);
						}
					}
					
				}
				//Ian did this(Can't guess letter you've already guessed)
				else if(lettersGuessed.contains(text))
				{
					status.setText("You already guessed that!");
				}
				//Ian did this(Allowing whole word to be guessed)
				else if(text.equals(word))
				{
					String actualVisible = "";
					for(int i = 0; i < visible.length(); i+=2) {
						actualVisible += visible.charAt(i);
					}
					visibleLabel.setText(text);
					status.setText("Congratulations, you have won!");
					input.setEnabled(false);
				}
				else if(text.equals("CHEAT"))
				{
					String actualVisible = "";
					for(int i = 0; i < visible.length(); i+=2) {
						actualVisible += visible.charAt(i);
					}
					visibleLabel.setText(word);
					status.setText("Congratulations, you have won!");
					input.setEnabled(false);
				}
				else if(text.length() == word.length())
				{
					for(int i = 0; i < remainingGuesses; i++)
					{
						hf.set();
					}
					remainingGuesses = 0;
					status.setText("Wrong guess - You lose. The word was " + word);
					input.setEnabled(false);
				}
				else
					status.setText("Invalid Input - Try again!");
				
				input.setText("");
			}
			
		});
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}