public class Site {
	
	class PageNode
	{
		String name;
		PageNode up;
		PageNode down;
		PageNode across;
		
		PageNode(String name)
		{
			this.name = name;
			this.up = null;
			this.down = null;
			this.across = null;
		}
	}
	
	public static class NameNotUniqueException extends Exception{}
	
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
				this.root.down = newPage;
				this.current = newPage;
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
				current.across = newPage;
				this.current = newPage;
				insertionFound = true;
				
			}else 
			{
				this.addPage(current.across, newPage);
			}
		}
	}
	
	private void checkIfNameUnique(PageNode newPage)
	{
		
		PageNode current = this.root;
		
		while(current != null) 
		{
			
		}
		
	}
	
	@Override
	public String toString() 
	{
		String result = "";
		
		PageNode current = this.root.down;
		
		result += this.root.name;
		
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
