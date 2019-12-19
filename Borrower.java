/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;

/**
 *
 * @author Minahil Imtiaz
 */
public class Borrower extends Users {

    private String address;
    private String telephone;
    private boolean fine_defaulter;
    private double fine;
    private ArrayList<Loan> BookLoans;

    Borrower() {
        super();
        fine_defaulter = false;
        BookLoans = null;
        fine = 0.0;
        address = " ";
        telephone = "  ";
        BookLoans = new ArrayList<>();

    }

    Borrower(int user_id, String user_name, char gender, String telephone, String address, boolean fine_defaulter, double fine) {
        super(user_id, user_name, gender);
        this.fine_defaulter = fine_defaulter;
        this.fine = fine;
        this.address = address;
        this.telephone = telephone;
        BookLoans = new ArrayList<>();

    }

    @Override
    public boolean GetFineStatus() {
//        return this.fine_defaulter;
        dbConnectivity db = new dbConnectivity();
        return (db.GetFineStatus(this.GetId()));
    }

    public double GetFineAmout() {
//        return this.fine;
        dbConnectivity db = new dbConnectivity();
        return (db.GetFineAmount(this.GetId()));

    }

    public String GetAddress() {
        return this.address;

    }

    public String GetTelephone() {

        return this.telephone;
    }

    @Override
    public boolean SetFineStatus(boolean fine_defaulter) {
        this.fine_defaulter = fine_defaulter;
        dbConnectivity db = new dbConnectivity();
        boolean result = db.SetFineStatus(this.GetId(), fine_defaulter);
        return result;
    }

    public boolean SetTelephone(String Telephone) {
        this.telephone = Telephone;
        dbConnectivity db = new dbConnectivity();
        boolean result = db.SetTelephone(this.GetId(), this.telephone);
        return result;

    }

   
    public boolean SetAddress(String Address) {
        this.address = Address;
       dbConnectivity db = new dbConnectivity();
        boolean result = db.SetAddress(this.GetId(), this.address);
        return result;

    }
  
    @Override
    public void SetFineAmount(double user_fine) {
        this.fine = user_fine;
        dbConnectivity db = new dbConnectivity();
        boolean result = db.SetFineAmount(this.GetId(), user_fine);
        

    }

    public void SetName(String name)
    {
        super.SetName(name);
        dbConnectivity db = new dbConnectivity();
        boolean result = db.SetName(this.GetId(), name);

    }
    
    
    public void SetGender(char g)
    {
        super.SetGender(g);
        dbConnectivity db = new dbConnectivity();
        boolean result = db.SetGender(this.GetId(), g);

    }
    
    @Override
    public boolean AddLoanInfo(Loan LoanInfo) {

        BookLoans.add(LoanInfo);
        return true;
//        dbConnectivity db = new dbConnectivity ();
//        boolean result=db.AddLoanInfo(this.GetId(), LoanInfo);
//        return result;

    }

    public void AllLoansofUser(ArrayList<Loan> LoansofUser) {

        // this.BookLoans=LoansofUser;
        dbConnectivity db = new dbConnectivity();
        this.BookLoans = db.LoadLoanListofSpecificUser(this.GetId());
    }

    //helping function to update loan info 
     @Override
    public void UpdateLoanInfo(Loan Update, int book_id) {

//        Iterator<Loan> itr = BookLoans.Iterator(BookLoans.size());
//        while (itr.hasPrevious()) {
//
//            Loan L = itr.previous();
      for (int counter=BookLoans.size()-1 ; counter >=0; counter--){
                 Loan L = BookLoans.get(counter);

            if ((L.GetaBookId() == book_id)) {
                L.SetLoan(Update);
                break;
            }
        }

    }

    //needed to be checked 
    @Override
    public String ViewInformation(ArrayList<Loan> LoanList, int user_id) {

        
        String Resultant="";
        Resultant=Resultant+super.PrintInformation();
        Resultant+="Adress :" + address+"  ";
        Resultant+="Telephone:" + telephone+"  ";
        Resultant+="\n Loan Info \n";

        if (BookLoans.isEmpty() == false) {
            for (int i=0;i< BookLoans.size();i++) {
                Loan L=BookLoans.get(i);
                Resultant+=L.PrintLoanInfo();
                Resultant+="\n";
            }
        } else {

            Resultant +="NO LOANS TILL YET!";
        }
        
        return Resultant;

    }

}
