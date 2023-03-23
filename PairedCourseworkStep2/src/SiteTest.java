
public class SiteTest {
	public static void main(String[] args) 
	{
		Site website = new Site();
		boolean running = true;
		int option;
		String pageName = "";
		
		do
		{
			try{
				option = Input.getInteger("What would you like to do?\n 1. Add new Page\n 2. Display Home Page\n 3. Display Current Page\n 4. Move Up\n 5. Move Down\n 0. Quit\nChoice: ");
			}
			catch(NumberFormatException e){
				option = 100; // Sets Option to large number so default Not Valid Option message is thrown if user inputs something that is not an integer
			}
			
			switch(option)
			{
			case 0:
				running = false;
				System.out.println("\nProgram quit successfully!");
				break;
			case 1:
				pageName = Input.getString("\nName of Page: ");
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
			case 3:
				try 
				{
					System.out.println(website.getCurrentPage()+"\n");
				}catch(Site.PageNoLinksException e) 
				{
					System.out.println("\tPage has no links\n");
				}
				break;
			case 4:
				try 
				{
					website.moveUp();
				}catch(Site.NoParentPageException e)
				{
					System.out.println("Cannot move up from Home page!\n");
				}
				break;
			case 5:
				try 
				{
				website.moveDown();
				}catch(Site.PageNotFoundException e) 
				{
					System.out.println("Page does not exist in the site!\n");
				}catch(Site.PageNoLinksException e) 
				{
					System.out.println("Current page has no links!\n");
				}
				break;
			default:
				System.out.println("\nError; Not a valid option! Please try again!");
				break;
			}
		}while(running);
		
		
		
		
	}
}
