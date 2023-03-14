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
			if(this.root.down == null) 
			{
				if (!checkIfUnique(this.root, newPage)){
					throw new NameNotUniqueException();
				}
				else {
					newPage.up = this.root;
					this.root.down = newPage;
					this.current = newPage;
				}
			}else 
			{
				this.addPage(this.current, newPage);
			}
		}
	}
	
	private void addPage(PageNode current, PageNode newPage) throws NameNotUniqueException
	{
		boolean insertionFound = false;

		while(!insertionFound) 
		{
			if(current.across == null)
			{
				if(!checkIfUnique(this.root, newPage))
				{
					throw new NameNotUniqueException();
				}
				else 
				{
					
					newPage.up = current.up;
					current.across = newPage;
					this.current = newPage;
					insertionFound = true;	
				}
			}else 
			{
				this.addPage(current.across, newPage);
			}
		}
	}
	
	
	private boolean checkIfUnique(PageNode current, PageNode newPage) 
	{
		if(current == null) 
		{
			return false;
		}
		
		System.out.println("Current: "+ current.name +" New Page: "+ newPage.name);
		
		if(newPage.name.compareToIgnoreCase(current.name)==0) {
			System.out.println("Name is same");
			return false;
		}
		else 
		{
			if(current == this.root) 
			{
				this.checkIfUnique(current.down, newPage);
			}else 
			{
				this.checkIfUnique(current.across, newPage);
			}
			
		}
		
		return true;
			
	}
	
	public void moveUp() 
	{
		if(this.current.up == null) 
		{
			System.out.println("Cannot go up from Home page!\n");
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
		
		if(current.down == null) 
		{
			throw new PageNoLinksException();
		}else 
		{
			pageName = Input.getString("\nWhich page would you like to go to: ");
			current = this.current.down;
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
		
		result += this.current.name;
		
		if(current.down == null && current.across == null) 
		{
			System.out.println(result);
			throw new PageNoLinksException();
		}
		
		while(current != null) 
		{
			if(current.down == null) 
			{
				result += "\n\t"+ current.name;
				current = current.across;
				
			}else
			{
				result += "\n\t"+ current.name;
				current = current.down;
			}
			
		}
		
		return result;
	}
	
	@Override
	public String toString()
	{
		String result = "";
		
		// We know we are starting at the root which will not have any across so we can just start from the root.down page
		PageNode current = this.root.down;
		
		result += this.root.name;
		
		if(this.root.down == null) 
		{
			result += "\n\tPage has no links";
			current = null;
		}
		
		while(current != null) 
		{
			result += "\n\t"+ current.name;
			if(current.down == null) 
			{
				current = current.across;
			}else 
			{
				current = current.down;
			}
			
		}
		
		return result;
	}
}
