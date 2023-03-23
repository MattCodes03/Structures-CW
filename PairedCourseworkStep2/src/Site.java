import java.util.ArrayList;

public class Site {
	
	private class PageNode
	{
		private String name;
		private PageNode up;
		private PageNode down;
		private PageNode across;
		
		PageNode(String name)
		{
			this.name = name;
			this.up = null;
			this.down = null;
			this.across = null;
		}
	}
	
	public static class NameNotUniqueException extends Exception {}
	
	public static class PageNotFoundException extends Exception {}
	
	public static class PageNoLinksException extends Exception {}
	
	public static class NoParentPageException extends Exception {}
	
	protected PageNode root;
	protected PageNode current;
	
	Site() 
	{
		PageNode homePage = new PageNode("Home");
		this.root = homePage;
		this.current = homePage;
	}
	
	public void addPage(String name) throws NameNotUniqueException
	{
		
		PageNode newPage = new PageNode(name);

		if (this.root != null) 
		{
			this.addPage(this.root, newPage);
		}
	}
	
	private void addPage(PageNode current, PageNode newPage) throws NameNotUniqueException
	{
			if(this.root.down == null) 
			{
				if(this.checkIfNameUnique(newPage)) 
				{
					
					this.root.down = newPage;
					
					newPage.up = this.root;
					this.current = newPage;
					
				}else 
				{
					throw new NameNotUniqueException();
				}
		
			}
			else if(current.down == null) {
				
				if(this.checkIfNameUnique(newPage)) 
				{
					this.current.down = newPage;
					newPage.up = this.current;
						
					this.current = newPage;
				}else 
				{
					throw new NameNotUniqueException();
				}
				
						
			}
			else if(current.across == null)
			{
				
				if(this.checkIfNameUnique(newPage)) 
				{
					print("Line 91");
					this.current.across = newPage;	
					this.current = newPage;
					this.current.up = this.root;
				
				}else 
				{
					throw new NameNotUniqueException();
				}
				
			}
			else 
			{
				if(current.down == null)
				{
					this.addPage(current.down, newPage);
				}
				else {
					this.addPage(current.across, newPage);
				}
			}
		}
	
	
	public void moveUp() throws NoParentPageException
	{
		if(this.current.up == null) 
		{
			throw new NoParentPageException();
		}else 
		{
			this.current = this.current.up;	
			System.out.println("Current Page is now: "+ this.current.name +"\n");
		}
		
	}
	
	public void moveDown() throws PageNotFoundException, PageNoLinksException

	{
		/* 
		 * 1. Check if current.down == null
		 * 2. If this.current.down is null RETURN "Page has no links"
		 * 3. If this.current.down is not null set current = this.current.down
		 * 4. while(found = False) check if current.across.name == PageName
		 * 5. If pageName == current.across.name then set this.current = current.across and set found = true
		 * 6. If pageName != current.across.name then set current = current.across
		 * 7. If current.across == null then page does not exist so set found = false and RETURN "Page does not exit"
		 * */
	
		PageNode current = this.current;
		boolean found = false;
		String pageName;
		
		if(this.current.down == null) 
		{
			throw new PageNoLinksException();
		}else 
		{
			pageName = Input.getString("\nWhich page would you like to go to: ");
			current = current.down;
			while(!found) 
			{
				if(pageName.compareToIgnoreCase(current.name) == 0)
				{
					this.current = current;
					System.out.println("Current Page is now: "+ this.current.name +"\n");
					found = true;
				}else 
				{
					if(current.across == null) 
					{
						throw new PageNotFoundException();
					}
					current = current.across;
				}
			}
		}
	}
	
	public String getCurrentPage() throws PageNoLinksException
	{
		String result = "";
		PageNode current = this.current;
		
		result += this.traversal(current);
		
		if(current.down == null) 
		{
			result += "\n\tPage has no links";
		}
		
		return result;
	}
	
	public boolean hasParentNode(PageNode page) 
	{
		if(page.up != null && page.up != this.root) 
		{
			return true;
		}
		
		return false;
	}
	
	// TODO: Remove once debugging and testing is complete, built for convenience
	public void print(String args) 
	{
		System.out.println(args);
	}
	
	public boolean checkIfNameUnique(PageNode newPage) 
	{
		
		String results = this.getPageNames(this.root);
		String[] names = results.split(",");
		
		
		for(String name : names) 
		{
			if(name.compareToIgnoreCase(newPage.name) == 0) 
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	public String getPageNames(PageNode current) 
	{
		String results = "";

		if(current != null) 
		{
			results += ","+current.name;
		
			if(current.down != null) 
			{
				results += this.getPageNames(current.down);
			}
			
				
			if(current.across != null) 
			{
				results += this.getPageNames(current.across);
			}
		}
		
		return results;
	}
	
	
	public String traversal(PageNode current) 
	{
		String results = "";
		
		if(current != null) 
		{
			if(current == this.root) 
			{
				results += current.name;
			}else if(hasParentNode(current)) 
			{
				results += "\n\t\t"+current.name; // Two Tabs
			}else 
			{
				results += "\n\t"+current.name; // One Tab
			}
			
			
			if(current.down != null) 
			{
				results += this.traversal(current.down);
			}
			
			if(current.across != null) 
			{
				results += this.traversal(current.across);
			}	
		}
		
        return results;
    }
	
	@Override
	public String toString()
	{
		String result = "";
		PageNode current = this.root;
		
		result += this.traversal(current);
		
		if(current.down == null) 
		{
			result += "\n\tPage has no links";
		}
		
		
		
		return result;
	}
}
