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

	public final String vm1 = "192.17.11.11";
	public final String vm2 = "192.17.11.12";
	public final String vm3 = "192.17.11.10";
	public final String vm4 = "";
	public final String vm5 = "";
	public final String vm6 = "";
	public final String vm7 = "";

	public List<String> getAddresses() {
		List<String> vmAddressList = new ArrayList<String>();
		vmAddressList.add(vm1);
		vmAddressList.add(vm2);
		vmAddressList.add(vm3);
		return vmAddressList;
	}

	public HashMap<String, String> getMappedAddress() {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put(vm1, "vm1");
		map.put(vm2, "vm2");
		map.put(vm3, "vm3");
		map.put(vm4, "vm4");
		map.put(vm5, "vm5");
		map.put(vm6, "vm6");
		map.put(vm7, "vm7");

		return map;
	}

}
