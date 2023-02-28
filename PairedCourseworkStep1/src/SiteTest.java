
public class SiteTest {
	public static void main(String[] args) 
	{
		Site website = new Site();
		boolean running = true;
		int option;
		
		do
		{
			option = Input.getInteger("What would you like to do?\n 1. Add new Page\n 2. Display Site Map\n 0. Quit\nChoice: ");	
			switch(option)
			{
			case 0:
				running = false;
				System.out.println("Program Quitted Successfully!");
				break;
			case 1:
				String pageName = Input.getString("Name of Page: ");
				// website.addPage(pageName); Need to insert Try-Catch for this line to work again
				break;
			case 2:
				System.out.println(website);
				break;
			default:
				System.out.println("Not a valid choice! Please Try-Again!");
				break;
			}
		}while(running);
		
		
		
		
	}
}
