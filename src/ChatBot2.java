import java.util.Random;
import java.util.Scanner;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot2
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;


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

		if (trackLyricsTTS(statement))
		{
			response = transformtouchTheSky(statement);
		}

		else if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
			emotion--;
		}

		else if (findKeyword(statement, "levin") >= 0)
		{
			response = "More like LevinTheDream amiright?";
			emotion++;
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

	private String [] randomNeutralResponses = {"Yo that sounds valid, tell me more",
			"Das tuff",
			"Are you for real?",
			"Yo you remember the lyrics to by ?",
			"Don't trip man, iss all good",
			"Yo you tryna bump some ?",
			"Yo lets get back to hip hop aight?"
	};
	private String [] randomAngryResponses = {"Yo what you tweakin' for?", "Do you actually wanna fight? I'm down", ""};
	private String [] randomHappyResponses = {"H A P P Y, what's that spell?", "Yo today's been dumb valid so far", "You make me feel like a brand new pair of shoes."};
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

