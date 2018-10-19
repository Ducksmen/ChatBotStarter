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
public class ChatBotDavis
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	int sn = 0;
	int an = 0;
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
			System.out.println(getResponse(statement));


		}

	}
	/**
	 * Get a default greeting
	 * @return a greeting
	 */
	public String getGreeting()
	{
		return "Yo, what's good?";
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

		if (trackLyricsBE(statement))
		{
			response = transformbutterflyEffect(statement);
		}

		else if (trackLyricsTTS(statement))
		{
			response = transformtouchTheSky(statement);
		}
		else if (trackLyricsIAHTT(statement))
		{
			response = transformitAintHardToTell(statement);
		}

		else if (statement.length() == 0)
		{
			response = "My son you gotta talk";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "No? You don't gotta be like that c'mon";
			emotion--;
		}

		else if (findKeyword(statement, "play Check") >= 0)
		{
			if (musicRunning == false) {
				songName = "Check.mp3";
				pathToMp3 = System.getProperty("user.dir") + "/" + songName;
				player = new BasicPlayer();
				try {
					player.open(new URL("file:///" + pathToMp3));
					player.play();
				} catch (BasicPlayerException | MalformedURLException e) {
					e.printStackTrace();
				}
				musicRunning = true;
				response = "Yo Check is valid lemme hop on it";
			}
			else
			{
				response = "You gotta type 'stop music' and try again homie, you can only play one song at at time";
			}
		}
        else if (findKeyword(statement, "play Good Night") >= 0)
        {
            if (musicRunning == false) {
                songName = "Good Night.mp3";
                pathToMp3 = System.getProperty("user.dir") + "/" + songName;
                player = new BasicPlayer();
                try {
                    player.open(new URL("file:///" + pathToMp3));
                    player.play();
                } catch (BasicPlayerException | MalformedURLException e) {
                    e.printStackTrace();
                }
                musicRunning = true;
                response = "Yo Good Night is valid lemme hop on it";
            }
            else
            {
                response = "You gotta type 'stop music' and try again homie, you can only play one song at at time";
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
			response = "Aight I'll stop it";
		}
		else if (findKeyword(statement, "hip hop") >= 0)
		{
			response = "Yo my bad I just get hype whenever I hear hip hop";
			emotion++;
		}
		else if (findKeyword(statement, "I",0) >= 0 && findKeyword (statement, "you") > findKeyword(statement, "I",0))
		{
			response = transformIYouStatement(statement);
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
		else if (findKeyword(statement, "I like",0) >= 0)
		{
			response = transformIlikeStatement(statement);
		}
		else
		{
			an = (int)Math.random()*31;
			sn = (int)Math.random()*3;
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
		return "Aight bet cuz I wanna " + restOfStatement + " too, let's go do that";
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
		return "You got " + restOfStatement + " money?";
	}

	private String transformIlikeStatement(String statement)
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
		return "Yo you a real one, I like " + restOfStatement + " too!";
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
		return "Shoot, I " + restOfStatement + " you too, so we cool or nah?";
	}

	private String transformbutterflyEffect(String statement)
	{
		for (int i = 0; i < butterflyEffect.length; i++)
		{
			if (butterflyEffect[i].equals(statement))
			{
				if (i == butterflyEffect.length - 1)
				{
					return "Aight aight that's all I remember I'm sorry";
				}
				return butterflyEffect[i + 1];
			}
		}
		return null ;
	}
    private String transformtouchTheSky(String statement)
    {
        for (int i = 0; i < touchTheSky.length; i++)
        {
            if (touchTheSky[i].equals(statement))
            {
                if (i == touchTheSky.length - 1)
                {
                    return "Aight aight that's all I remember I'm sorry";
                }
                return touchTheSky[i + 1];
            }
        }
        return null ;
    }
	private String transformitAintHardToTell(String statement)
	{
		for (int i = 0; i < itAintHardToTell.length; i++)
		{
			if (itAintHardToTell[i].equals(statement))
			{
				if (i == itAintHardToTell.length - 1)
				{
					return "Aight aight that's all I remember I'm sorry";
				}
				return itAintHardToTell[i + 1];
			}
		}
		return null ;
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

	private boolean trackLyricsBE(String statement)
	{
		for (int i = 0; i < butterflyEffect.length; i++)
		{
			if (butterflyEffect[i].equals(statement))
			{
				return true;
			}
		}
		return false;
	}
	private boolean trackLyricsTTS(String statement)
	{
		for (int i = 0; i < touchTheSky.length; i++)
		{
			if (touchTheSky[i].equals(statement))
			{
				return true;
			}
		}
		return false;
	}
	private boolean trackLyricsIAHTT(String statement)
	{
		for (int i = 0; i < itAintHardToTell.length; i++)
		{
			if (itAintHardToTell[i].equals(statement))
			{
				return true;
			}
		}
		return false;
	}
	private String [] songNames ={"Butterfly Effect by Travis Scott",
			"Touch The Sky by Kanye West",
			"It Ain't Hard To Tell by Nas"
	};
	private String [] artistNames ={"21 Savage",
			"A$AP Ferg",
			"A$AP Rocky",
			"A$AP Twelvyy",
			"Big K.R.I.T",
			"Big Pun",
			"Biggie Smalls",
			"Big Sean",
			"A Boogie",
			"Childish Gambino",
			"CyHi da Prynce",
			"Denzel Curry",
			"DMX",
			"Drake",
			"Eric Bellinger",
			"G-Eazy",
			"Jaden Smith",
			"Jay Rock",
			"Jay Critch",
			"Kanye West",
			"Kendrick Lamar",
			"KYLE",
			"Nas",
			"Lupe Fiasco",
			"Rae Sremmurd",
			"Snoop Dogg",
			"Travis Scott",
			"Ty Dolla $ign",
			"Tyler, The Creator",
			"Young Thug"
	};

	private String [] randomNeutralResponses = {"Yo that sounds valid, tell me more",
			"Das tuff",
			"Are you for real?",
			"Yo you remember the lyrics to " + songNames[sn] + "?",
			"Don't trip, iss all good",
			"Yo you tryna bump some " + artistNames[an] + "?",
			"Yo lets get back to hip hop aight?"
	};
	private String [] randomAngryResponses = {"Yo what you tweakin' for?",
            "Do you actually wanna fight? I'm down",
            "Yo I think we needa start over, I ain't feelin it",
            "Yo you remember the lyrics to " + songNames[sn] + "?",
            "Yo you tryna bump some " + artistNames[an] + "?"
    };
	private String [] randomHappyResponses = {"Everyone I talk to is a real G, you one too",
            "Yo you tryna bump some " + artistNames[an] + "?",
            "Yo you remember the lyrics to " + songNames[sn] + "?",
            "Yo today's been dumb valid so far",
            "I feel like I just hit it big in Vegas homie!"
	};
	private String [] butterflyEffect = {"For this life I cannot change",
			"Hidden hills, deep off in the main",
			"M&M's, sweet like candy canes",
			"Drop the top, pop it let it bang",
			"Drop the top play hide and seek",
			"Jump inside, jump straight to the league",
			"Take a sip, feel just how I be",
			"On freeway, but no ain't nothin' free",
			"Bend laws bend lanes",
			"Been bustin' bills, but still ain't nothin' change",
			"You in the mob, soon as you rock the chain",
			"She caught the waves just thumbin' through my braids",
			"Heatin' up, baby I'm just heatin' up",
			"Need ya love not a need it is a must",
			"Feelin' stuck you know how to keep me up",
			"Icy love icy like a hockey puck"
	};
	private String [] touchTheSky = {"I gotta testify",
            "Come up in the spot lookin' extra fly",
            "Before the day I die, I'ma touch the sky",
            "Gotta testify",
            "Come up in the spot lookin' extra fly",
            "Before the day I die, I'ma touch the sky",
            "Back when they thought pink Polos would hurt the Roc",
            "Before Cam got the stuff to pop",
            "The doors was closed",
            "I felt like Bad Boy's street team, I couldn't work the locks",
            "Now let's go, take 'em back to the plan",
            "Me and my momma hopped in that U-Haul van",
            "Any pessimists I ain't talk to them",
            "Plus I ain't have no phone in my apart-a-ment",
            "Let's take 'em back to the club",
            "Least about an hour I stand on line",
            "I just wanted to dance, I went to Jacob an hour",
            "After I got my advance, I just wanted to shine",
            "Jay favorite line, Dawg, in due time!",
            "Now he look at me, like, Dang, dawg! You where I am!",
            "A hip-hop legend",
            "I think I died in that accident, ‘cause this must be Heaven"};

	private String [] itAintHardToTell = {"It ain't hard to tell, I excel, then prevail",
    "The mic is contacted, I attract clientele",
    "My mic check is life or death, breathin' a sniper's breath",
    "I exhale the yellow smoke of buddha through righteous steps",
    "Deep like The Shining, sparkle like a diamond",
    "Sneak a Uzi on the island in my army jacket linin'",
    "Hit the Earth like a comet—invasion!",
    "Nas is like the Afrocentric Asian: half-man, half-amazin'",
    "‘Cause in my physical I can express through song",
    "Delete stress like Motrin, then extend strong",
    "I drink Moët with Medusa, give her shotguns in Hell",
    "From the spliff that I lift and inhale; it ain't hard to tell",};
}

