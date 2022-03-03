package review.pojo;

public class Spartan2 {


    private int id;
    private String name;
    private String gender;
    private long phone;


      /*
    so for easy config , we will just create another Spartan POJO
		with id , name , gender , phone fields to start with
		getters and setters
		no arg constructors , 4 arg constructors
		add toString method so we can print it out

     */

    //this pojo is for retrieving data, for get request, not for posting new data

    public Spartan2(){

        //does not do anything but required  for jackson to work
        //default constructor with no arguments
    }

    public Spartan2(int id, String name, String gender, long phone){

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;


    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public long getPhone() {

        return phone;
    }

    public void setPhone(long phone) {

        this.phone = phone;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @Override
    public String toString() {

        return "Spartan2{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                ", id=" + id +
                '}';
    }

}
