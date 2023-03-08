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
				if (!checkIfNameUnique(newPage)){
					throw new NameNotUniqueException();
				}
				else {
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
		PageNode curr_dir = null; 
		
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
