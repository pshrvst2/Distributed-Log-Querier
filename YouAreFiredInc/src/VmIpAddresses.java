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

	public final String vm1 = "172.22.151.17";
	public final String vm2 = "172.22.151.18";
	public final String vm3 = "172.22.151.19";
	public final String vm4 = "172.22.151.20";
	public final String vm5 = "172.22.151.21";
	public final String vm6 = "172.22.151.22";
	public final String vm7 = "172.22.151.23";

	public List<String> getAddresses() {
		List<String> vmAddressList = new ArrayList<String>();
		vmAddressList.add(vm1);
		vmAddressList.add(vm2);
		vmAddressList.add(vm3);
		vmAddressList.add(vm4);
		vmAddressList.add(vm5);
		vmAddressList.add(vm6);
		vmAddressList.add(vm7);
		
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
