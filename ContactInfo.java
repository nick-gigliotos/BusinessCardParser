public class ContactInfo
{
    String _name;
    String _phoneNum;
    String _email;

    public ContactInfo()
    {
        String _name = null;
        String _phoneNum = null;
        String _email = null;
    }

    public ContactInfo(String name, String phoneNum, String email)
    {
        _name = name;
        _phoneNum = phoneNum;
        _email = email;
    }

    //Returns the full name of the individual
    String getName() 
    {
        return _name;
    }
    
    //Returns the phone number of the individual
    String getPhoneNumber()
    {
        return _phoneNum;
    }

    //returns the email address of the individual
    String getEmailAddress()
    {
        return _email;
    }

}