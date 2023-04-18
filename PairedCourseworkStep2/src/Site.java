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
	
	public static class CannotMoveToCurrentPageException extends Exception {}
	
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
	
	private void addPageDown(PageNode current, PageNode newPage) throws Site.NameNotUniqueException 
	{
		if(this.checkIfNameUnique(newPage)) 
		{
			
			this.current.down = newPage;
			this.current.down.up = this.root;
			
			// New Page Node
			//this.current = newPage;
			
		}else 
		{
			throw new NameNotUniqueException();
		}
	}
	
	private void addPageAcross(PageNode current, PageNode newPage) throws Site.NameNotUniqueException
	{
		if(current.across == null) 
		{
			if(this.checkIfNameUnique(newPage)) 
			{
				
				current.across = newPage;
				current.across.up = this.root;
				
				// New Page Node
				//this.current = newPage;
				
			}else 
			{
				throw new NameNotUniqueException();
			}
		}else 
		{
			this.addPageAcross(current.across, newPage);
		}
	}
	
	private void addPage(PageNode current, PageNode newPage) throws NameNotUniqueException
	{
			if(current.down == null) {
				
				this.addPageDown(current, newPage);
			}		
			else if(current.down != null)
			{
				
				this.addPageAcross(current.down, newPage);
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
	
	public void moveDown() throws PageNotFoundException, PageNoLinksException, CannotMoveToCurrentPageException

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
			
			if(pageName.compareToIgnoreCase(this.current.name) == 0) 
			{
				throw new CannotMoveToCurrentPageException();
			}
			
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
		
		result += this.traverseCurrentPage(current);
		
		if(current.down == null) 
		{
			result += "\n\tPage has no links";
		}
		
		return result;
	}
	
	public String traverseCurrentPage(PageNode current) 
	{
		String results = "";
		
		if(current != null) 
		{
			if(current == this.current) 
			{
				results += current.name;
			}else if(hasParentNode(current)) 
			{
				results += "\n\t\t"+current.name;
			}else 
			{
				results += "\n\t"+current.name;
			}
			
			
			if(current.down != null) 
			{
				results += this.traverseCurrentPage(current.down);
			}
			
			if(current.across != null && current != this.current) 
			{
				results += this.traverseCurrentPage(current.across);
			}	
		}
		
        return results;
	}
    
	
	
	// TODO: Remove once debugging and testing is complete, built for convenience
	public void print(String args) 
	{
		System.out.println(args);
	}
	
	/*
	 * This function will create an array of all page names in the site based on the string provided by the getPageNames function, 
	 * we then loop through this array to make sure the new page name is not a duplicate
	 */
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
	
	/*
	 * This function acts similar to the traversal function but it will append all page names from the root of the site rather than a specified current page
	 * This function does not use any formatting and is only used for checking if new pages are unique inside the checkIfUnique function
	 */
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
	
	public boolean hasParentNode(PageNode page) 
	{
		if(page.up != null && page.up != this.root) 
		{
			return true;
		}
		
		return false;
	}
	
	
	/*
	 * Traversal function will traverse through the entire site map adding the name of each web page to a results string 
	 * that will them be displayed either in the toString method or getCurrentPage method
	 * We have made use of a hasParentNode function to determine whether or not we need to tab the page name, 
	 * we also add a check to see if this.current is the current page we are traversing through this is to avoid adding tabs to the top level page when we display the current page
	 */
	public String traverseSite(PageNode current) 
	{
		String results = "";
		
		if(current != null) 
		{
			if(current == this.root) 
			{
				results += current.name;
			}else if(hasParentNode(current)) 
			{
				results += "\n\t\t"+current.name;
			}else 
			{
				results += "\n\t"+current.name;
			}
			
			
			if(current.down != null) 
			{
				results += this.traverseSite(current.down);
			}
			
			if(current.across != null) 
			{
				results += this.traverseSite(current.across);
			}	
		}
		
        return results;
    }
	
	@Override
	public String toString()
	{
		String result = "";
		PageNode current = this.root;
		
		result += this.traverseSite(current);
		
		if(current.down == null) 
		{
			result += "\n\tPage has no links";
		}
		
		
		
		return result;
	}
}
