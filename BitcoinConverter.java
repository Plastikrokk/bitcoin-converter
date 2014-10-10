import java.awt.event.*;
import java.io.IOException;
import org.jsoup.nodes.*;
import javax.swing.*;
import org.jsoup.Jsoup;

public class BitcoinConverter extends JFrame {
	//Begin creating components
	JTextField jtfCoins, jtfPrice, jtfConversionRate;
	JComboBox jcbExchange;
	
	public BitcoinConverter() {
		//Write components
		//Buttons
		JButton jbtConvert = new JButton("Covnert");
		JButton jbtQuit = new JButton("Quit");
		
		//Text Fields
		jtfCoins = new JTextField("Bitcoins", 12);
		jtfPrice = new JTextField("Total price", 12);
		//Set jtfPrice to be uneditable 
		jtfPrice.setEditable(false);  
		jtfConversionRate = new JTextField("Conversion Rate", 35);
		//Set jtfConversionRate to be uneditable
		jtfConversionRate.setEditable(false);  
		//This will put the text in the jtfConversionRate box in the center
		jtfConversionRate.setHorizontalAlignment(JTextField.CENTER);
		
		//ComboBox for the different Bitcoin exchanges
		String exchanges[] = {"Bitstamp", "MTGox", "BTC-E"};
		jcbExchange = new JComboBox(exchanges);
		
		//Add all the components to a new panel
		//Create a new panel object
		JPanel panel = new JPanel();
		//Add components to the panel
		panel.add(jtfCoins);
		panel.add(jtfPrice);
		panel.add(jcbExchange);
		panel.add(jtfConversionRate);
		panel.add(jbtConvert);
		panel.add(jbtQuit);
		//Put the panel together
		add(panel);
		
		//Add listeners to components
		//Convert listener class that connects to the internet and grabs/calculates the bitcoin price
		ConvertListenerClass listener1 = new ConvertListenerClass();
		jbtConvert.addActionListener(listener1);
		jtfCoins.addActionListener(listener1);
		//Quit listener class that terminates the program
		QuitListenerClass listener2 = new QuitListenerClass();
		jbtQuit.addActionListener(listener2);
	}
	
	//Driver class setting up the frame!
	public static void main(String[] args) {
		JFrame frame = new BitcoinConverter();
		frame.setTitle("Bitcoin to USD Converter");
		frame.setSize(450, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// Create the converter listener method!  
	class ConvertListenerClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			double price;
			String totalPrice;
			
			//In case the user tries to input something other than a numerical value
			try {
				double x = Double.parseDouble(jtfCoins.getText());
				//Prevent the user from inputting a negative number
				if (x < 0) { 
					JOptionPane.showMessageDialog(null, "You must have a positive amount of Bitcoins!");
				} else {
					//Grab the selected JComboBox item
					String s = (String)jcbExchange.getSelectedItem();
					switch(s) {
						//If the user has Bitstamp selected
						case "Bitstamp":
							try {
								//Create a 'document' that contains the HTML code of the needed website
								Document doc = Jsoup.connect("http://www.bitcoinity.org/markets/bitstamp/USD").get();
								//Create an Element using the select search method, then getting the 5th instance of the search results
								Element jPrice = doc.select("span[id]").get(4);
								//Grab the text between HTML tags <span id='last_buy'> and </span>
								String text = jPrice.text();
								//Convert the string to a double value and multiply by the number of bitcoins input by user
								price = Double.parseDouble(text) * x;
								//Use the toString method to convert the total calculated price back to a string value
								totalPrice = Double.toString(price);
								//Set the jtfPrice text field as the calculated total in USD using the setText method
								jtfPrice.setText("$" + totalPrice);
								//Set the jtfConversionRate text field with a custom string stating the price of 1 bitcoin at the current time
								jtfConversionRate.setText("The conversion rate is 1 Bitcoin to $" + text);
							} catch (IOException e1) {
								//In case the program is unable to connect, present an error message.
								JOptionPane.showMessageDialog(null, e1.toString());
							}
							break;
						//If the user has MTGox selected
						case "MTGox":
							try {
								//Create a 'document' that contains the HTML code from the needed website
								Document doc = Jsoup.connect("http://www.bitcoinity.org/markets/mtgox/USD").get();
								//Create an Element using the select search method, then getting the 5th instance of the search results
								Element jPrice = doc.select("span[id]").get(4);
								//Grab the text between the HTML tags <span id='last_buy'> and </span>
								String text = jPrice.text();
								//Convert the string to a double value and multiply by the number of bitcoins input by user
								price = Double.parseDouble(text) * x;
								//Use the toString method to convert the total calculated price back to a string value
								totalPrice = Double.toString(price);
								//Set the jtfPrice text field as the calculated total in USD using the setText method
								jtfPrice.setText("$" + totalPrice);
								//Set the jtfConversionRate text field with a custom string stating the price of 1 bitcoin at the current time
								jtfConversionRate.setText("The conversion rate is 1 Bitcoin to $" + text);
							} catch (IOException e1) {
								//In case the program is unable to connect, present an error message
								JOptionPane.showMessageDialog(null,  "Error! " + e1.toString());
							} 
							break;
						//If the user has BTC-E selected
						case "BTC-E":
							try {
								//Create a 'document' that contains the HTML code from the needed website
								Document doc = Jsoup.connect("http://www.bitcoinity.org/markets/btce/USD").get();
								//Create an Element using the select search method, then getting the 5th instance of the search results
								Element jPrice = doc.select("span[id]").get(4);
								//Grab the text between the HTML tags <span id='last_buy'> and </span>
								String text = jPrice.text();
								//Convert the string to a double value and multiply by the number of bitcoins input by the user
								price = Double.parseDouble(text) * x;
								//Use the toString method to convert the total calculated price back to a string value
								totalPrice = Double.toString(price);
								//Set the jtfPrice text field as the calculated total in USD using the setText method
								jtfPrice.setText("$" + totalPrice);
								//Set the jtfConversionRate text field with a custom string stating the price of 1 bitcoin at the current time
								jtfConversionRate.setText("The conversion rate is 1 Bitcoin to $" + text);
							} catch (IOException e1) {
								//In case the program is unable to connect, present an error message
								JOptionPane.showMessageDialog(null, "Error! " + e1.toString());
							}
							break;
					}
				}
			}
			catch(NumberFormatException nfe) {
				//If the user tries to input something other than a numerical value, present an error message
				JOptionPane.showMessageDialog(null, "Must be a positive number!");
			}
		}
	}
	
	//Create the quit listener method!
	class QuitListenerClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Terminate GUI and program immediately
			System.exit(0);
		}
	}
}