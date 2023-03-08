public class Site {
	
	class PageNode
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
				if (!checkIfNameUnique(newPage)){
					throw new NameNotUniqueException();
				}
				else {
					this.root.down = newPage;
					this.current = newPage;
					newPage.up = this.root;
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
		
		// Name Not Unique check if current.name == newPage.name
		
		while(!insertionFound) 
		{
			if(current.across == null)
			{
				if(!checkIfNameUnique(newPage)){
					throw new NameNotUniqueException();
				}
				else {
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
	
	private boolean checkIfNameUnique(PageNode newPage)
	{
		
		PageNode current = this.root;
		PageNode curr_dir = null; /* Current Directory will store the PageNode we have traversed down, 
									this is done to avoid infinite looping when we traverse back up the site tree */
		
		while(current != null) 
		{
			if(newPage.name.compareToIgnoreCase(current.name)==0) {
				return false;
			}
			else {
				if(current.down != null && current.down != curr_dir) {
					curr_dir = current;
					current = current.down;
				}
				else if(current.across!=null){
					current = current.across;
				}
				else {
					current = current.up;
					curr_dir = current;
				}
			}
		}
		return true;
	}
	
	public void moveUp() 
	{
		if(this.current.up == null) 
		{
			System.out.println("Cannot go up from Home page!");
		}else 
		{
			this.current = this.current.up;	
		}
		
	}
	
	public void moveDown(String pageName) throws PageNotFoundException
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
	}
	
	public String displayCurrentPage() throws PageNoLinksException
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
