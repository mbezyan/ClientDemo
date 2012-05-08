import java.net.*;
public class Demo {

	public static void main(String[] args) {
		String[] diggers = new String[3];
		diggers[0] = "jan";
		diggers[1] = "jul";
		diggers[2] = "feb";
		
		try
		{	
			URL u = new URL("http://localhost:8066/golddigger/digger/jul/move/east");
			URLConnection jan = u.openConnection();
			jan.connect();
			jan.getContent();
		}
		catch (Exception e)
		{
				
		}
		try
		{
			URL u = new URL("http://localhost:8066/golddigger/digger/feb/move/east");
			URLConnection jan = u.openConnection();
			jan.connect();
			jan.getContent();
		}
		catch (Exception e)
		{
				
		}
		try
		{
			
			URL u = new URL("http://localhost:8066/golddigger/digger/feb/move/east");
			URLConnection jan = u.openConnection();
			jan.connect();
			jan.getContent();
		}
		catch (Exception e)
		{
				
		}
		
		
		
		
		for (int k = 0; k < 3; k ++)
		{
			for (int i = 0; i < 6; i++)
			{
				try
				{	
					URL u = new URL("http://localhost:8066/golddigger/digger/"+diggers[k]+"/move/south");
					URLConnection jan = u.openConnection();
					jan.connect();
					jan.getContent();
				}
				catch (Exception e)
				{
						
				}
			}	
		}
		
	}

}
