import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot3
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	boolean musicRunning = false;
	BasicPlayer player = new BasicPlayer();
	String songName = "";
	String pathToMp3 = "";


	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye"))
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));


		}

	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Excuse me, Do you enjoy listening to pop music?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "How rude.";
                	emotion--;
		}
		
		else if (findKeyword(statement, "yes") >= 0)
		{
			response = "What are some of the artists that you listen to?";
			emotion++;
		}

        else if (findKeyword(statement, "yea") >= 0)
        {
            response = "What are some of the artists that you listen to?";
            emotion++;
        }

        else if (findKeyword(statement, "sure") >= 0)
        {
            response = "What are some of the artists that you listen to?";
            emotion++;
        }

        else if (findKeyword(statement, "maybe") >= 0)
        {
            response = "What do you mean maybe?";
            emotion = 0;
        }
		else if (findKeyword(statement, "songs") >= 0)
		{
			response = "Here are some songs you can listen to, just type 'play <Insert Song Name Here>'. Marry You, Sad Song and Happy";
			emotion = 0;
		}
		else if (findKeyword(statement, "play Marry You") >= 0)
		{
			if (musicRunning == false) {
				songName = "Marry you.mp3";
				pathToMp3 = System.getProperty("user.dir") + "/" + songName;
				player = new BasicPlayer();
				try {
					player.open(new URL("file:///" + pathToMp3));
					player.play();
				} catch (BasicPlayerException | MalformedURLException e) {
					e.printStackTrace();
				}
				musicRunning = true;
				response = "Here we go!";
			}
			else
			{
				response = "You can only play song at a time. Type 'stop music' and try again.";
			}
		}
		else if (findKeyword(statement, "play Happy") >= 0)
		{
			if (musicRunning == false) {
				songName = "Happy.mp3";
				pathToMp3 = System.getProperty("user.dir") + "/" + songName;
				player = new BasicPlayer();
				try {
					player.open(new URL("file:///" + pathToMp3));
					player.play();
				} catch (BasicPlayerException | MalformedURLException e) {
					e.printStackTrace();
				}
				musicRunning = true;
				response = "Here we go!";
			}
			else
			{
				response = "You can only play song at a time. Type 'stop music' and try again.";
			}
		}
		else if (findKeyword(statement, "play Sad Song") >= 0)
		{
			if (musicRunning == false) {
				songName = "Sad Song.mp3";
				pathToMp3 = System.getProperty("user.dir") + "/" + songName;
				player = new BasicPlayer();
				try {
					player.open(new URL("file:///" + pathToMp3));
					player.play();
				} catch (BasicPlayerException | MalformedURLException e) {
					e.printStackTrace();
				}
				musicRunning = true;
				response = "Here we go!";
			}
			else
			{
				response = "You can only play song at a time. Type 'stop music' and try again.";
			}
		}
		else if (findKeyword(statement, "stop music",0) >= 0)
		{
			try {
				player.open(new URL("file:///" + pathToMp3));
				player.stop();
			} catch (BasicPlayerException | MalformedURLException e)
			{
				e.printStackTrace();
			}
			musicRunning = false;
			response = "Stopping the music";
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}

		else
		{
			response = getRandomResponse();
		}
		
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	
	/**
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}

	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
	}
	

	
	
	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Hmmm.",
			"Do you really think so?",
			"You don't say.",
			"It's all music to me.",
	};
	private String [] randomAngryResponses = {"Unfortunate", "You're missing out", "That's a feels bad"};
	private String [] randomHappyResponses = {"Huh, because I'm happy" +
            "Clap along if you feel like a room without a roof" +
            "Because I'm happy" +
            "Clap along if you feel like happiness is the truth" +
            "Because I'm happy" +
            "Clap along if you know what happiness is to you" +
            "Because I'm happy" +
            "Clap along if you feel like that's what you wanna do",
            "I had a dream so big and loud" +
            "I jumped so high I touched the clouds" +
            "Wo-o-o-o-o-oh, wo-o-o-o-o-oh" +
            "I stretched my hands out to the sky" +
            "We danced with monsters through the night" +
            "Wo-o-o-o-o-oh, wo-o-o-o-o-oh",
            "Oh" +
            "It's a beautiful night, we're looking for something dumb to do" +
            "Hey baby, I think I wanna marry you" +
            "Is it the look in your eyes, or is it this dancing juice" +
            "Who cares baby, I think I wanna marry you"};
	
}
