/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Minahil Imtiaz
 */
public class Loan {

    private int loanId;
    private Date issue_date;
    private Date due_date;
    private Date return_date;
    private Users borrower;
    private Books borrowed_book;
    private String fine_status;
    private boolean returned_status;

    Loan() {
        loanId = -1;
        Calendar calendar = Calendar.getInstance();
        issue_date = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 7);

        due_date = calendar.getTime();
        this.return_date = due_date;
        returned_status = false;
        fine_status = "no fine";

    }
    
    Loan(int id ) {
        loanId = id;
        Calendar calendar = Calendar.getInstance();
        issue_date = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 7);

        due_date = calendar.getTime();
        this.return_date = due_date;
        returned_status = false;
        fine_status = "no fine";

    }

    Loan(int id, int user_id, int borrowedbook_id, boolean returned_status, String fine_status, Date issue_date, Date due_date, Date return_date) {
        dbConnectivity db = new dbConnectivity();
        this.loanId = id;
        this.issue_date = issue_date;
        this.return_date = return_date;
        this.due_date = due_date;
        this.fine_status = fine_status;
        this.borrower = db.GetaBorrowerObjectByUserId(user_id);
        this.borrowed_book = db.GetaBookbyId(borrowedbook_id);
        this.returned_status = returned_status;
    }

    Loan(int id , Users borrower, Books borrowed_book, String finestatus, boolean returned_status, Date issue_date, Date due_date, Date return_date) {

        this.loanId =id; 
        this.issue_date = issue_date;
        this.return_date = return_date;
        this.due_date = due_date;
        this.fine_status = finestatus;
        this.borrower = borrower;
        this.borrowed_book = borrowed_book;
        this.returned_status = returned_status;
    }

    public int GetaBookId() {
         return this.borrowed_book.GetBookId();
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetLoanedBookId(this.loanId));

    }

    public String GetFineStatus() {

        return fine_status;
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetLoanFineStatus(this.loanId));
    }

    public int GetLoanId() {
        return this.loanId;

    }

    public Date getReturnDate() {
        return this.return_date;
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetReturnDate(this.loanId));
    }

    public Date getDueDate() {
         return this.due_date;
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetDueDate(this.loanId));
    }

    public Date getIssueDate() {
         return this.issue_date;
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetIssueDate(this.loanId));
    }

    public Books GetaBook() {

        //  return this.borrowed_book;
        dbConnectivity db = new dbConnectivity();
        return (db.GetLoanedBook(this.loanId));
    }

    public int GetaBorrowerId() {

            return this.borrower.GetId();
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetLoaneeId(this.loanId));

    }

    public Users Getborrower()
    {
        return this.borrower;
    }
    public boolean GetStatus() {
          return this.returned_status;
//        dbConnectivity db = new dbConnectivity();
//        return (db.GetLoanReturnedStatus(this.loanId));

    }

    public void SetReturnedDate(Date Ret_date) {

        this.return_date = Ret_date;
        dbConnectivity db = new dbConnectivity();
        db.SetLoanReturnedDate(this.loanId, Ret_date);

    }

    public void SetaBook(Books NewBook) {

        this.borrowed_book = NewBook;
        dbConnectivity db = new dbConnectivity();
        db.SetLoanedBook(this.loanId, this.borrowed_book.GetBookId());

    }

    public void SetaBorrower(Users Loanee) {

        this.borrower = Loanee;
        dbConnectivity db = new dbConnectivity();
        db.SetLoaneeObject(this.loanId, this.borrower.GetId());

    }

    public void SetReturnStatus(boolean status) {

        this.returned_status = status;
        dbConnectivity db = new dbConnectivity();
        db.SetReturnStatus(this.loanId, status);
    }

    public void SetLoan(Loan Update) {
        this.borrowed_book = Update.borrowed_book;
        this.borrower = Update.borrower;
        this.due_date = Update.due_date;
        this.issue_date = Update.issue_date;
        this.fine_status = Update.fine_status;
        this.return_date = Update.return_date;
        this.returned_status = Update.returned_status;

        dbConnectivity db = new dbConnectivity();
        db.SetLoan(this.loanId, Update);
    }

    public void SetFineStatus(String status) {

        this.fine_status = status;
        dbConnectivity db = new dbConnectivity();
        db.SetLoanFineStatus(loanId, status);
    }

    public boolean GetLoan(ArrayList<Books> BooksList, Borrower Loanee, ArrayList<Users> Borrowers, Staff AdminBody, ArrayList<Loan> LoanList) {
        System.out.print("Search the book here \n Press 1. to search with title \n Press 2. to search by author name \n 3. to search by subject\n ");
        Scanner input = new Scanner(System.in);
        boolean status = false;
        int command = input.nextInt();
        if (command == 1) {
            System.out.print("Enter the title ");
            String Title = input.next();
            Loanee.SearchBookbyTitle(Title);
        } else if (command == 2) {
            System.out.print("Enter the author name ");
            String A_name = input.next();
            Loanee.SearchBookbyAuthor(A_name);
        } else if (command == 3) {

            System.out.print("Enter the subject ");
            String subject = input.next();
            Loanee.SearchBookbySubject(subject);
        } else {
            System.out.print("You pressed invalid key ! Now displaying all the books ");
            for (Books b : BooksList) {

                b.PrintInformation();
            }
        }
        System.out.print("Now provide the id of book you want ");
        int id = input.nextInt();
        boolean is_available = false;
        for (Books b : BooksList) {
            if (b.ChekcAvailability(id) == true) {
                is_available = true;
                // return status;
            }
        }

        if (is_available == true) {
            status = AdminBody.CheckOutItem(id, Loanee, BooksList, this, LoanList);
            
        }

        return status;

    }

    public double CalculateFine() {

        if (return_date.after(due_date)) {

            long difference = (return_date.getTime() - due_date.getTime()) / 86400000;
            difference = Math.abs(difference);
            return 30.0 * difference;

        } else {
            return 0.0;

        }

    }

    String PrintLoanInfo() {
        int id = this.borrower.GetId();
        int bookid = borrowed_book.GetBookId();
         String resultant=" ";
         resultant=resultant+"loanId:" + loanId + "\t" + "issue date:" + issue_date + "\t" + "due date" + due_date + "\t"
                + "return_date:" + return_date + "\t" + "borrower id " + id + "\t" + " borrowed_book :"
                + bookid + "\t" + "fine status  :" + fine_status + "\t" + "returned status" + returned_status + "\n";
         return resultant;

    }

}
