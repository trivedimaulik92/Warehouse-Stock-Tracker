/* 
 * Class : User
 * Author : Maulik Trivedi, Jemini Malaviya
 * Creation Date : 4/21/2017
 * Last Modified Date : 4/21/2017
 */

package warehouseStockTracker.Business;



public class Vendor{
	
	// declaring private variables
	private int id;
	private String name;
	private String contactName;
	private String contactAddress;
	private String contactPhone;
	private String contactEmail;
	
	// constructor
	public Vendor(){
		id = 0;
		name = "";
		contactName = "";
		contactAddress = "";
		contactPhone = "";
		contactEmail = "";
	}
	
	public Vendor(int id, String name, String contactName, String contactAddress, 
							String contactPhone, String contactEmail){
		this.id = id;
		this.name = name;
		this.contactName = contactName;
		this.contactAddress = contactAddress;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
	}

	// getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	
	
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	// overriding toString() 
	public String toString(){
		return id + "\t" + name + "\t" + contactName + "\t" + contactAddress + "\t" + 
				contactPhone + "\t" + contactEmail;
	}
}

