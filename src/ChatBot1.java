import java.util.Random;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URL;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

//Jack Hoang
/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */

public class ChatBot1
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	//responseStage changes from regular conversation into talks about vocaloid. Changes how the bot responds to the same statement.
	//musicRunning this boolean makes it so only one instance of a song can be run.
	//player allows music to be played.
	//songName keeps track of the song name.
	//pathToMp3 is what allows the bot to go directly to the mp3.
	int emotion = 0;
	int responseStage = 0;
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
			if (responseStage == 1) {
				System.out.println(getResponse2(statement));
			}
			if (responseStage == 0) {
				System.out.println(getResponse(statement));
			}
		}
	}
	/**
	 * Get a default greeting
	 * @return a greeting
	 */;
	public String getGreeting()
	{
		return "So your interested in vocaloid right?";
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
			response = "Oh ok then. What do you like to listen to?";
			emotion--;
		}

		else if (findKeyword(statement, "sorry") >= 0)
		{
			response = "I forgive you. Lets continue on.";
			emotion++;
		}

        else if (findKeyword(statement, "because") >= 0)
        {
            response = "Oh that's interesting. Wanna talk about something else?";
        }

        else if (findKeyword(statement, "thanks") >= 0)
        {
            response = "Your welcome?";
        }

        else if (findKeyword(statement, "yes") >= 0)
        {
            response = "Vocaloid is music sung by software, they usually tell a story which has led to many people creating their own songs. Some artists include Jin and Orangestar. " +
					"If you want to know more about them and their songs just type their name.";
            responseStage++;
        }

        else if (findKeyword(statement, "ya") >= 0)
        {
            response = "Vocaloid is music sung by software, they usually tell a story which has led to many people creating their own songs. Some artists include Jin and Orangestar. " +
					"If you want to know more about them and their songs just type their name.";
            responseStage++;
        }

        else if (findKeyword(statement, "sure") >= 0)
        {
            response = "Vocaloid is music sung by software, they usually tell a story which has led to many people creating their own songs. Some artists include Jin and Orangestar. " +
					"If you want to know more about them and their songs just type their name.";
            responseStage++;
        }

		else if (findKeyword(statement, "I listen to", 0) >= 0)
		{
			response = transformILikeToListenToStatement(statement);
		}
		else if (findKeyword(statement, "I like",0) >= 0)
		{
			response = transformILikeStatement(statement);
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

	public String getResponse2(String statement)
	{
		String response = "";

		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Oh, don't be like that.";
			emotion--;
		}

		else if (findKeyword(statement, "sorry") >= 0)
		{
			response = "I forgive you. Let's continue on.";
			emotion++;
		}


		else if (findKeyword(statement, "thanks") >= 0)
		{
			response = "Your welcome?";
		}

		else if (findKeyword(statement, "Jin") >= 0)
		{
			response = "Known mostly for his song series Kagerou Project, several of which include " +
					"Artificial Enemy, Kagerou Days, Outer Science and recently Additional Memory. " +
					"If you want to know more about each song just type it exactly to me.";
		}

		else if (findKeyword(statement, "Heat Haze Daze") >= 0)
		{
			response = "Heat Haze Daze was Jin's third work and most notable having millions of views. " +
					"If you want to listen to it type 'play hDaze'.";
		}
		else if (findKeyword(statement, "play hDaze") >= 0)
		{
			if (musicRunning == false) {
				songName = "Heat Haze Daze.mp3";
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
		else if (findKeyword(statement, "Additional Memory") >= 0)
		{
			response = "Additional Memory is Jin's most recent song and part of his new album soon to be released November 7th." +
					"If you want to listen to it type 'play aMemory'.";
		}
		else if (findKeyword(statement, "play aMemory") >= 0)
		{
			if (musicRunning == false) {
				songName = "Additional Memory.mp3";
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

		else if (findKeyword(statement, "Outer Science") >= 0)
		{
			response = "Outer Science is Jin's 14th song and known for it's unsettling nature.If you want to listen to it type 'play oScience'.";
		}
		else if (findKeyword(statement, "play oScience") >= 0)
		{
			if (musicRunning == false) {
				songName = "Outer Science.mp3";
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
		else if (findKeyword(statement, "Orangestar") >= 0)
		{
			response = "Orangestar produces mainly memorable and sorrowful, poetic melodies. " +
					"His most popular songs are Night Sky Patrol of Tomorrow, Alice in Freezer and Daybreak Frontline. " +
					"If you want to know more about each song just type it exactly to me.";
		}
		else if (findKeyword(statement, "Night Sky Patrol of Tomorrow") >= 0)
		{
			response = "By far Orangestar's most popular song it conveys a sense of hope. " +
					"If you want to listen to it type 'play nSky'.";
		}
		else if (findKeyword(statement, "play nSky") >= 0)
		{
			if (musicRunning == false) {
				songName = "Night Sky Patrol of Tomorrow.mp3";
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
		else if (findKeyword(statement, "Alice in Freezer") >= 0)
		{
			response = "Alice in Freezer is an cheerful melody with soft piano sounds." +
					"If you want to listen to it type 'play aFreezer'.";
		}
		else if (findKeyword(statement, "play aFreezer") >= 0)
		{
			if (musicRunning == false) {
				songName = "Alice in Freezer.mp3";
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
		else if (findKeyword(statement, "Daybreak Frontline") >= 0)
		{
			response = "Daybreak Frontline is an upbeat song that can help motivate you." +
					"If you want to listen to it type 'play dFrontline'.";
		}
		else if (findKeyword(statement, "dFrontline") >= 0)
		{
			if (musicRunning == false) {
				songName = "Daybreak Frontline.mp3";
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
		else if (findKeyword(statement, "vocaloid",0) >= 0)
		{
			response = transformVocaloidStatement(statement);
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

	private String transformILikeToListenToStatement(String statement)
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
		int psn = findKeyword (statement, "I like to listen to", 0);
		String restOfStatement = statement.substring(psn + 19).trim();
		return "Why do you like to listen to " + restOfStatement + "?";
	}

	private String transformILikeStatement(String statement)
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
		int psn = findKeyword (statement, "I like", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Why do you like " + restOfStatement + "?";
	}

	private  String transformVocaloidStatement (String statement)
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
        int psn = findKeyword (statement, "vocaloid", 0);
        String restOfStatement = statement.substring(1, psn).trim();
        return "Why do you " + restOfStatement + " vocaloid?";
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
			"Goodness.",
			"Oh dear.",
			"Could you say that again?"
	};
	private String [] randomAngryResponses = {"Now I'm angry.", "My patience is running thin.", "I'm not impressed."};
	private String [] randomHappyResponses = {"Your an interesting person.", "Today is a good day.", "I hope your having a great day."};

}

