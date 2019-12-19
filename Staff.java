/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;
import java.util.Scanner;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
//import java.util.ArrayList;

/**
 *
 * @author Minahil Imtiaz
 */
public class Staff extends Users {

    Staff(int user_id, String user_name, char gender) {
        super(user_id, user_name, gender);

    }

    public boolean CheckOutItem(int book_id, Users current_borrower, ArrayList<Books> BooksList, Loan Current_Loan, ArrayList<Loan> LoanList) {

        if (current_borrower.GetFineStatus() == true) {
            System.out.println("You are fine defaulter Can't get any more loans");
            return false;
        } else {
            Books loaned_book;
            for (Books b : BooksList) {
                if (b.GetBookId() == book_id) {
                    RenewItem(book_id, BooksList, 2);
                    Current_Loan.SetaBook(b);
                    Current_Loan.SetaBorrower(current_borrower);
                    current_borrower.AddLoanInfo(Current_Loan);
                    LoanList.add(Current_Loan);
                    dbConnectivity db = new dbConnectivity ();
                   return( db.AddNewLoan(Current_Loan) );
                            
                    
                    

                }
            }

            return false;

        }
    }

    public boolean CheckInItem(String ret_date ,int book_id, Users current_borrower, ArrayList<Books> BooksList, List<Loan> LoanList) {

//        Iterator<Loan> itr = LoanList.listIterator(LoanList.size());
//        while (itr.hasPrevious()) {
          for (int counter=LoanList.size()-1 ; counter >=0; counter--){
                 Loan L = LoanList.get(counter);
          //  Loan L = itr.previous();

            if ((L.GetaBookId() == book_id) && (L.GetaBorrowerId() == current_borrower.GetId()) && L.GetStatus()==false) {

                L.SetReturnStatus(true);
                String expectedPattern = "yyyy/MM/dd";
                SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
               
                String return_date = ret_date;
                Date date = new Date();
                try {
                    date = formatter.parse(return_date);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                
                L.SetReturnedDate(date);
                double user_fine = L.CalculateFine();
                if (user_fine > 0.0) {
                    L.SetFineStatus("Fined");
                    current_borrower.SetFineStatus(true);
                    current_borrower.SetFineAmount(user_fine);

                    RenewItem(book_id, BooksList, 1);
                    current_borrower.UpdateLoanInfo(L, book_id);
                    return true;

                }

            }

        }
        return false;
    }

    @Override
    public String ViewInformation(ArrayList<Loan> LoanList, int user_id) {
        String Result="  ";
        for ( int i=0; i <LoanList.size(); i++) {
            Loan l=LoanList.get(i);
            int currentborrower=l.GetaBorrowerId();
            if (/*(l.GetStatus() == false) &&*/ currentborrower == user_id) {
                Users U=l.Getborrower();
                Result=Result+U.PrintInformation();
                Books LoanedBook = l.GetaBook();
                Result=Result+LoanedBook.PrintInformation();
            } 
        }
        
        return Result;
    }

    public boolean AddaBorrower(ArrayList<Users> BorrowerList, String borrower_name , char borrower_gender  ,String  telephone_number , String address ) {
        Users LastBorrower = BorrowerList.get(BorrowerList.size()-1);
        int LastBorrowerId = LastBorrower.GetId();
        int new_id =LastBorrowerId + 1;
        Borrower NewBorrower = new  Borrower(new_id, borrower_name , borrower_gender ,telephone_number , address , false , 0.0);

        
        
      //1  NewBorrower.SetName(borrower_name);
//        System.out.println("Enter the gender of user");
//        char borrower_gender = input.next().charAt(0);
     //1   NewBorrower.SetGender(borrower_gender);
//        System.out.println("Enter the telephone number of user");
//        String telephone_number = input.next();
    //1    NewBorrower.SetTelephone(telephone_number);
//        System.out.println("Enter the address of user");
//        String address = input.next();
     //1   NewBorrower.SetAddress(address);
        BorrowerList.add(NewBorrower);

        dbConnectivity db = new dbConnectivity();
        boolean result = db.AddBorrower(LastBorrowerId + 1, borrower_name, borrower_gender,  address ,telephone_number);
        return result;

    }

    public boolean UpdatePerosnalInformation(ArrayList<Users> BorrowerList, String Info , int command, int user_id) {
//        System.out.println("Enter the id of user whose information you want to update");
//        Scanner input = new Scanner(System.in);
//        int user_id = input.nextInt();
        boolean result=false;
        for (int i=0 ; i< BorrowerList.size() ; i++) {
            
            Users B=BorrowerList.get(i);
            if (B.GetId() == user_id) {

//                System.out.println("Press 1 to change name \n  Press 2 to change gender");
//                {

//                    int command = input.nextInt();
                    if (command == 1) {

//                        System.out.println("Enter the new name of user");
//                        String borrowers_name = input.next();
                       B.SetName(Info);
                       result=true;
                    } else if (command == 2) {
//                        System.out.println("Enter the gender of user");
//                        char borrowers_gender = input.next().charAt(0);
                        B.SetGender(Info.charAt(0));
                        result=true;
                    }
//                    } else if (command == 3) {
////                        System.out.println("Enter the telephone");
////                        String TelNum = input.next();
//                        
//                        B.SetTelephone(Info);
//                    } else if (command == 4) {
////                        System.out.println("Enter the address");
////                        String Address = input.next();
//                        B.SetAddress(Info);
//                    } else {
                    else{
                        System.out.println("You have pressed an invalid command");
                        result=false;
                         
                    }
//                }
            }
        }
                return result;
    }

    public void RenewItem(int book_id, ArrayList<Books> BooksList, int type) {
        for (Books b : BooksList) {
            if (b.GetBookId() == book_id) {
                if (type == 1) {
                    b.IncreaseQuantity();
                } else {
                    b.DecreaseQuantity();
                }
                break;

            }

        }
    }

    void PayFine(int user_id, ArrayList< Users> BorrowerLists, ArrayList<Loan> LoanList) {

     //   Iterator<Loan> itr = LoanList.Iterator(LoanList.size());
     //   while (itr.hasPrevious()) {
          for (int counter=LoanList.size()-1 ; counter >=0; counter--){
                 Loan L = LoanList.get(counter);
                 String F=L.GetFineStatus();
            if (L.GetaBorrowerId() == user_id && F.equals("Fined")==true ){
                L.SetFineStatus("Paid");
                for (Users b : BorrowerLists) {
                    int book_id = L.GetaBookId();
                    if (b.GetId() == user_id) {
                        b.SetFineAmount(0);
                        b.SetFineStatus(false);
                        b.UpdateLoanInfo(L, book_id);
                        break;
                    } 
                }
            }
            
        }

    }
}
