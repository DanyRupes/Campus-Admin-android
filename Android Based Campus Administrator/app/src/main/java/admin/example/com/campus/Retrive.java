package admin.example.com.campus;

import android.widget.Toast;

public class Retrive {
	private String avatar;
	private String CGPA;
	private String Contact;
	private String Dept;
	private String Year;
	private String ProPic;

	// Default constructor required for calls to
	// DataSnapshot.getValue(User.class)
	public Retrive() {
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setCGPA(String CGPA) {
		this.CGPA = CGPA;
	}
	public void setContact(String Contact) {
		this.Contact = Contact;
	}
	public void setDept(String Dept) {
		this.Dept = Dept;
	}
	public void setYear(String Year) {
		this.Year = Year;
	}
	public void RetriveProPic(String Url) {this.ProPic=Url;}

	public String getAvatar() {return avatar;}
	public String getCGPA(){return CGPA;}
	public String getContact(){
		return Contact;
	}
	public String getDept(){
		return Dept;
	}
	public String getYear(){
		return Year;
	}
	public String getProPic() { return ProPic; }

}
