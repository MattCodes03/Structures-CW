import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class UnitTests {

	Site website = new Site();
	
	/* STEP 1 TESTS */
	
	@Test
	public void createHomePage() 
	{
		assertEquals("Home\n\tPage has no links", website.toString());
	}
	
	@Test
	public void addNewPage() throws Site.NameNotUniqueException 
	{
		website.addPage("Shop");
		website.addPage("Blogs");
		website.addPage("Explore");
		assertEquals("Home\n\tShop\n\tBlogs\n\tExplore", website.toString());
	}
	
	@Test(expected = Site.NameNotUniqueException.class)
	public void nameUnique() throws Site.NameNotUniqueException 
	{
		website.addPage("Shop");
		
	}
	
	/* STEP 1 TESTS */

}
