package com.example.firebasecrudapp;


import android.os.Parcel;
import android.os.Parcelable;


class EntryRvModal implements Parcelable {

    private String name ;
    private String address ;
    private String contactNo ;
    private String panNumber;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    private String aadhaarNumber;
    private String dateOfBirth ;
    private String dateOfAnniversary ;

    private String purpose ;



    // creating an empty constructor.
    public EntryRvModal() {

    }

    protected EntryRvModal(Parcel in) {
        name= in.readString();
        address = in.readString();
        contactNo = in.readString();
        panNumber=in.readString();
        aadhaarNumber=in.readString();
        dateOfBirth = in.readString();
        dateOfAnniversary = in.readString();
        purpose = in.readString();
    }

    public static final Creator<EntryRvModal> CREATOR = new Creator<EntryRvModal>() {
        @Override
        public EntryRvModal createFromParcel(Parcel in) {
            return new EntryRvModal(in);
        }

        @Override
        public EntryRvModal[] newArray(int size) {
            return new EntryRvModal[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }



    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfAnniversary() {
        return dateOfAnniversary;
    }

    public void setDateOfAnniversary(String dateOfAnniversary) {
        this.dateOfAnniversary = dateOfAnniversary;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public EntryRvModal(String name,String address,String contactNo,String aadhaarNumber,String panNumber, String dateOfBirth,String dateOfAnniversary,String purpose) {
        this.name=name;
        this.address=address;
        this.contactNo=contactNo;
        this.aadhaarNumber=aadhaarNumber;
        this.panNumber=panNumber;
        this.dateOfBirth=dateOfBirth;
        this.dateOfAnniversary=dateOfAnniversary;
        this.purpose=purpose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(contactNo);
        dest.writeString(panNumber);
        dest.writeString(aadhaarNumber);

        dest.writeString(dateOfBirth);
        dest.writeString(dateOfAnniversary);
        dest.writeString(purpose);
    }
}
