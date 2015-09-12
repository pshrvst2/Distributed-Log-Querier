/**
 * 
 */

import java.util.ArrayList;
import java.util.List;



/**
 * @author pshrvst2
 *
 */
public class VmIpAddresses {
	
	public final String machine1 = "130.126.28.13";
	public final String machine2 = "192.17.11.145";
	/*public final String machine3 = "";
	public final String machine4 = "";
	public final String machine5 = "";
	public final String machine6 = "";
	public final String machine7 = "";*/
	
	public List<String> getAddresses()
	{
		List<String> vmAddressList = new ArrayList<String>();
		vmAddressList.add(machine1);
		//vmAddressList.add(machine2);
		
		return vmAddressList;
	}
	
	

}
