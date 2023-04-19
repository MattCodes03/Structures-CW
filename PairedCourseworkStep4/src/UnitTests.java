import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class UnitTests {

	Site website = new Site();
	
	/* STEP 1 TESTS */
	
	@Test
	public void createHomePage() 
	{
		assertEquals("\nHome\n\tPage has no links", website.toString());
	}
	
	@Test
	public void addNewPage() throws Site.NameNotUniqueException 
	{
		website.addPage("Shop");
		website.addPage("Blogs");
		website.addPage("Explore");
		assertEquals("\nHome\n .. Shop\n .. Blogs\n .. Explore", website.toString());
	}
	
	@Test(expected = Site.NameNotUniqueException.class)
	public void nameUnique() throws Site.NameNotUniqueException 
	{
		website.addPage("Home");
	}
	
	/* STEP 1 TESTS */
	
	/* STEP 2 TESTS */
	
	@Test
	public void displayCurrentPage() throws Site.PageNoLinksException 
	{
		assertEquals("\nHome\n\tPage has no links", website.getCurrentPage());
	}
	
	
	@Test(expected = Site.NoParentPageException.class)
	public void moveUpFromHome() throws Site.NoParentPageException
	{
		website.moveUp();
	}
	
	@Test(expected = Site.PageNoLinksException.class)
	public void moveDownWithNoLinks() throws Site.PageNotFoundException, Site.PageNoLinksException, Site.CannotMoveToCurrentPageException
	{
		website.moveDown();
	}
	
	/* STEP 2 TESTS */
}
