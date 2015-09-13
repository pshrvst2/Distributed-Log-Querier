/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pshrvst2
 *
 */
public class VmIpAddresses {

	public final String machine1 = "192.17.11.11";
	public final String machine2 = "192.17.11.12";
	public final String machine3 = "";
	public final String machine4 = "";
	public final String machine5 = "";
	public final String machine6 = "";
	public final String machine7 = "";

	public List<String> getAddresses() {
		List<String> vmAddressList = new ArrayList<String>();
		vmAddressList.add(machine1);
		vmAddressList.add(machine2);

		return vmAddressList;
	}

	public HashMap<String, String> getMappedAddress() {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put(machine1, "machine1");
		map.put(machine2, "machine2");
		map.put(machine3, "machine3");
		map.put(machine4, "machine4");
		map.put(machine5, "machine5");
		map.put(machine6, "machine6");
		map.put(machine7, "machine7");

		return map;
	}

}
