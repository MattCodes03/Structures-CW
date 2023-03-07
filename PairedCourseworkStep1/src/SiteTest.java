
public class SiteTest {
	public static void main(String[] args) 
	{
		Site website = new Site();
		boolean running = true;
		int option;
		
		do
		{
			try{
				option = Input.getInteger("What would you like to do?\n 1. Add new Page\n 2. Display Home Page\n 0. Quit\nChoice: ");
			}
			catch(NumberFormatException e){
				option = 10;
			}
			switch(option)
			{
			case 0:
				running = false;
				System.out.println("\nProgram quit successfully!");
				break;
			case 1:
				String pageName = Input.getString("\nName of Page: ");
				try {
					website.addPage(pageName);
					System.out.println(pageName+" added to site.\n");
				}				
				catch(Site.NameNotUniqueException e) {
					System.out.println("\nError; Page "+pageName+" already exists in site\n");
				}
				break;
			case 2:
				System.out.println("\n"+website+"\n");
				break;
			default:
				System.out.println("\nError; Not a valid option! Please try again!");
				break;
			}
		}while(running);
		
		
		
		
	}
}
