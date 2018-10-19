import java.util.Scanner;

/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		ChatBotHoang chatbot1 = new ChatBotHoang();
		ChatBotDavis chatbot2 = new ChatBotDavis();
		ChatBotLee chatbot3 = new ChatBotLee();
		

		Scanner in = new Scanner (System.in);
		System.out.println("Welcome to the music chatbot, Would like to talk about vocaloid, rap or pop.");
		String statement = in.nextLine();


		if (statement.equals("vocaloid")) {
			while (!statement.equals("Bye")) {
				//Use Logic to control which chatbot is handling the conversation
				//This example has only chatbot1
				chatbot1.chatLoop(statement);
				statement = in.nextLine();
			}
		}
		if (statement.equals("rap")) {
			while (!statement.equals("Bye")) {
				//Use Logic to control which chatbot is handling the conversation
				chatbot2.chatLoop(statement);
				statement = in.nextLine();
			}
		}
		if (statement.equals("pop")) {
			while (!statement.equals("Bye")) {
				//Use Logic to control which chatbot is handling the conversation
				chatbot3.chatLoop(statement);
				statement = in.nextLine();
			}
		}
	}

}
