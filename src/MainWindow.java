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

public class MainWindow extends JFrame 
{
	
	private int remainingGuesses;
	private String wrongGuesses;
	private String word;
	private String visible;
	private Scanner sc;
	private ArrayList<String> lettersGuessed;
	
	private int score1=0;
	private int score2=0;
	private int playerMode; //1: single player (default), 2: 2-player
	private String playerStatus = "Player 1";
	
	public static void main(String[] args) 
	{
		new OptionWindow();
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
					try
					{
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
	
	/*@param s input string
	 * 
	 * Returns the number of unique characters in a word
	 */
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
	
	//updates score after each guess, and switches player turns
	private void adjustScore(boolean correctGuess)
	{
		System.out.println("adjusting score");
		if(correctGuess && playerStatus.equals("Player 1"))
		{
			score1++;
			playerStatus = "Player 2";
			System.out.println("Player 1 scored, yay");
			
		} 
		else if(correctGuess && playerStatus.equals("Player 2"))
		{
			score2++;
			playerStatus = "Player 1";
			System.out.println("Player 2 scored, yay");
		}	
	}
	
	public MainWindow(OptionWindow ow) 
	{
		playerMode = ow.getPlayers();
		lettersGuessed = new ArrayList<String>();
		remainingGuesses = 10;
		wrongGuesses = "";
		word = chooseRandomWord(ow.getDifficulty());

		visible = "";

		for(int i = 0; i < word.length(); ++i) 
		{
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
		
		final JLabel p1Indicator = new JLabel("    Player 1:", SwingConstants.CENTER );
  	    final JLabel p1Score = new JLabel("     "+score1);
		final JLabel p2Indicator = new JLabel("    Player 2:" , SwingConstants.CENTER );
		final JLabel p2Score = new JLabel("     "+score2);		    
		final JLabel nextPlayer = new JLabel(playerStatus + " choose a letter", SwingConstants.CENTER);		
		JPanel p1Panel = new JPanel(new GridLayout(2,2));		
		
		//Craig adding labels here
		if(playerMode == 2)
		{
		    p1Panel.add(p1Indicator);
		    p1Panel.add(p1Score);
		    
		    p1Panel.add(p2Indicator);
		    p1Panel.add(p2Score);
		    
		    corePanel.add(nextPlayer, BorderLayout.NORTH);
		    corePanel.add(p1Panel, BorderLayout.WEST);
		}
		
		final HangmanFigure hf = new HangmanFigure();
		corePanel.add(hf, BorderLayout.CENTER);

		this.add(corePanel, BorderLayout.CENTER);
		
		input.addActionListener(new ActionListener() 
		{
		
			public void actionPerformed(ActionEvent e) 
			{
				String text = input.getText();
				
				if(text.length()  == 1 && text.matches("[a-z]") && !lettersGuessed.contains(text)) 
				{
					boolean guessFound = false;
					//adjustScore(guessFound);
					lettersGuessed.add(text);
					
					for(int i = 0; i < word.length(); i++) 
					{
						if(text.charAt(0) == word.charAt(i)) 
						{
							guessFound = true;
							//adjustScore(guessFound);
							
							//if()
							
							String newVisible = "";
							for(int j = 0; j < visible.length(); j++) 
							{
								if(j == (i*2)) 
								{
									newVisible += word.charAt(i);
								}
								else 
								{
									newVisible += visible.charAt(j);
								}
							}
							visible = newVisible;
							visibleLabel.setText(visible);
						}
					}
					
					adjustScore(guessFound);
					if(playerMode == 1)
					{
						p1Score.setText("     " + score1);
		  		        p2Score.setText("     " + score2);
                        nextPlayer.setText(playerStatus + " choose a letter");
					}
		  		    
					
					if(!guessFound) 
					{
						if(--remainingGuesses >= 1) 
						{
							status.setText("You have "+remainingGuesses+" guesses remaining");
							wrongGuesses += text+" ";
							wrong.setText("Wrong guesses so far: "+wrongGuesses);
							hf.set();														
						}
						else if(playerMode == 2)
						{
							status.setText("Both of you suck: the word was "+word);
							input.setEnabled(false);
						}
						else
						{
							status.setText("Wrong guess - You lose. The word was " + word);
							input.setEnabled(false);
						}
							
					}
					else 
					{
						String actualVisible = "";
						for(int i = 0; i < visible.length(); i+=2) 
						{
							actualVisible += visible.charAt(i);
						}
						
						if(actualVisible.equals(word)) 
						{
							if(playerMode == 2)
							{
								if(score1>score2)
								{
									status.setText("Player 1 wins!");
								}
								else
								{
									status.setText("Player 2 wins!");
								}	
							}
							else
							{
							    status.setText("Congratulations, you have won!");
							}
							input.setEnabled(false);
						}
					}
					
					if(playerMode ==1)
					{
					    p1Score.setText("     " + score1);
		  		        p2Score.setText("     " + score2);
                        nextPlayer.setText(playerStatus + " choose a letter");
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