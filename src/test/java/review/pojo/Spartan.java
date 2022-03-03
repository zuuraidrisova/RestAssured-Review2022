package review.pojo;

public class Spartan {

    //this pojo is for creating a spartan, for post request

    public Spartan(){

    }

    public Spartan(String name, String gender, long phone){

        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    private String name;
    private String gender;
    private long phone;

    public String getName(){

        return  name;
    }

    public String getGender(){

        return gender;
    }

    public long getPhone(){

        return phone;
    }

    public void setName(String name){

        this.name = name;

    }

    public void setGender(String gender){

        this.gender = gender;

    }

    public void setPhone(long phone){

        this.phone = phone;

    }


    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }


}
