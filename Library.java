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

public class Library {
    
    public static ArrayList<Users> UsersList;
    public static ArrayList<Books> BooksList;
    public static ArrayList<Librarian> LibrarianList;
    public static ArrayList<Clerk> ClerkList;
    public static ArrayList<Loan> LoanList;
    Library()
    {
    
        dbConnectivity db= new dbConnectivity();
        UsersList=db.LoadAllBorrowers();
      
        BooksList=db.LoadAllBooks();
        LibrarianList=db.LoadAllLibrarians();
        ClerkList=db.LoadAllClerk();
        LoanList=db.LoadLoanList();
        
          for (int i=0; i<UsersList.size() ; i++)
        {
                 Users B= UsersList.get(i);
                 B.ViewInformation(LoanList, B.GetId());
                 System.out.println("\n");
        
        }
          
          
            for (int i=0; i<BooksList.size() ; i++)
        {
                  Books B= BooksList.get(i);
                  B.PrintInformation();
                 System.out.println("\n");
        
        }

            
            
         for (int i=0; i<LibrarianList.size(); i++)
        {
                 Librarian L= LibrarianList.get(i);
                 L.PrintInformation();
                 System.out.println("\n");
        
        }
         
         
        for (int i=0; i<ClerkList.size() ; i++)
        {
                 Clerk L= ClerkList.get(i);
                 L.PrintInformation();
                 System.out.println("\n");
        
        }
        
        for (int i=0; i<LoanList.size() ; i++)
        {
                 Loan L= LoanList.get(i);
                 L.PrintLoanInfo();
                 System.out.println("\n");
        
        }

 
        
    }
    
    
    
    boolean IsBorrowerPresent(int id)
        {
        
            
            for(int i=0; i < UsersList.size() ; i++)
            {
               Users U= UsersList.get(i);
                
                if(U.GetId()== id)
                {
                    return true;
                }
            
            }
            return false;
        
        } 
    
    
    
     boolean IsClerkPresent(int id)
     {
        
            
            for(int i=0; i < ClerkList.size() ; i++)
            {
               Clerk C= ClerkList.get(i);
                
                if(C.GetId()== id)
                {
                    return true;
                }
            
            }
            return false;
        
        } 
    
    
       
  
    
     boolean IsLibrarianPresent(int id)
     {
        
            
            for(int i=0; i < LibrarianList.size() ; i++)
            {
               Librarian L= LibrarianList.get(i);
                
                if(L.GetId()== id)
                {
                    return true;
                }
            
            }
            return false;
        
        } 
    
     
     
     
     String CheckLoanofUser (int user_id, int clerk_id)
     {
        String Str="";
       for(int i=0; i< ClerkList.size() ; i++)
       {
           Clerk C=ClerkList.get(i);
           if(clerk_id == C.GetId())
           {
              
              Str=Str+ C.ViewInformation(LoanList, user_id);
              break;
           
           }
       
       
       }
       
      
       return Str;
     
     
     }
     
     
     
     ArrayList <Books>ClerkSearchBookbyTitle(String title, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < ClerkList.size() ; i++)
         {
         
             Clerk C=ClerkList.get(i);
             if(C.GetId()==clerk_id)
             {
                 SelectedBooks= C.SearchBookbyTitle(title);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     
     
     ArrayList <Books>ClerkSearchBookbyAuthor(String author, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < ClerkList.size() ; i++)
         {
         
             Clerk C=ClerkList.get(i);
             if(C.GetId()==clerk_id)
             {
                 SelectedBooks= C.SearchBookbyAuthor(author);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     ArrayList <Books>ClerkSearchBookbySubject(String subject, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < ClerkList.size() ; i++)
         {
         
             Clerk C=ClerkList.get(i);
             if(C.GetId()==clerk_id)
             {
                 SelectedBooks= C.SearchBookbySubject(subject);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     
    boolean  AddNewBorrower(String borrower_name ,char gender  ,String tel_num ,String address, int clerk_id)
    {
        boolean result=false;
       for(int i=0; i < ClerkList.size() ; i++)
       {
           Clerk C=ClerkList.get(i);
           if(C.GetId()== clerk_id)
           {
              result= C.AddaBorrower(UsersList, borrower_name, gender, tel_num, address);
              result= true;
              break;
           
           }
          
       
       }
       return result;
        
    }
    
    
    
    Boolean ClerkUpdatingInfo( String Info , int choice, int clerk_id, int user_id)
    {
    
        
         boolean result=false;
       for(int i=0; i < ClerkList.size() ; i++)
       {
           Clerk C=ClerkList.get(i);
           if(C.GetId()== clerk_id)
           {
              result= C.UpdatePerosnalInformation(UsersList , Info , choice, user_id);

              break;
           
           }
          
       
       }
       return result;
        
    
    }
     
    
    
    void ClerkRecordFine(int user_id ,int clerk_id)
    {
    
          for(int i=0; i < ClerkList.size() ; i++)
       {
           Clerk C=ClerkList.get(i);
           if(C.GetId()== clerk_id)
           {
              C.PayFine(user_id, UsersList, LoanList);
           }
          
       
       }
      
        
      
    }
    
    
    
     void ClerkCheckInItem(String ret_date, int book_id, int borrower_id, int clerk_id) {

        for (int i = 0; i < ClerkList.size(); i++) {

            Clerk C = ClerkList.get(i);
            if (C.GetId() == clerk_id) {
                for (int j = 0; j < UsersList.size(); j++) {
                    Users U = UsersList.get(j);
                    if (borrower_id == U.GetId()) {
                        C.CheckInItem(ret_date, book_id, U, BooksList, LoanList);

                    }

                }

            }

        }
    }
     
     
     
      void ClerkCheckOutItem( int book_id, int borrower_id, int clerk_id) {

        for (int i = 0; i < ClerkList.size(); i++) {

            Clerk C = ClerkList.get(i);
            if (C.GetId() == clerk_id) {
                for (int j = 0; j < UsersList.size(); j++) {
                    Users U = UsersList.get(j);
                    if (borrower_id == U.GetId()) {
                        int size=LoanList.size();
                        Loan L=LoanList.get(size-1);
                        int index=L.GetLoanId();
                        index=index+1;
                        Loan LoanObj= new Loan(index);
                        C.CheckOutItem(book_id , U,BooksList ,LoanObj,  LoanList );

                    }

                }

            }

        }
    }
     
      
      
    void ClerkRenewItem(int book_id ,int option ,int clerk_id)  
    {
       
        for (int i = 0; i < ClerkList.size(); i++) {

            Clerk C = ClerkList.get(i);
            if (C.GetId() == clerk_id) {
                C.RenewItem(book_id, BooksList, option);
            }

        }
    
    }
     
     
     //////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    String LibrarianCheckLoanofUser (int user_id, int clerk_id)
     {
        String Str="";
       for(int i=0; i< LibrarianList.size() ; i++)
       {
           Librarian L=LibrarianList.get(i);
           if(clerk_id == L.GetId())
           {
              
              Str=Str+ L.ViewInformation(LoanList, user_id);
              break;
           
           }
       
       
       }
       
      
       return Str;
     
     
     }
     
    
     
     ArrayList <Books>LibrarianSearchBookbyTitle(String title, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < LibrarianList.size() ; i++)
         {
         
             Librarian L=LibrarianList.get(i);
             if(L.GetId()==clerk_id)
             {
                 SelectedBooks= L.SearchBookbyTitle(title);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     
     
     ArrayList <Books>LibrarianSearchBookbyAuthor(String author, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < LibrarianList.size() ; i++)
         {
         
             Librarian L=LibrarianList.get(i);
             if(L.GetId()==clerk_id)
             {
                 SelectedBooks= L.SearchBookbyAuthor(author);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     ArrayList <Books>LibrarianSearchBookbySubject(String subject, int clerk_id)
     {
         ArrayList <Books> SelectedBooks = new ArrayList <>();
     
         for (int i=0; i < LibrarianList.size() ; i++)
         {
         
             Librarian L=LibrarianList.get(i);
             if(L.GetId()==clerk_id)
             {
                 SelectedBooks= L.SearchBookbySubject(subject);
             }
             
         
         }
     
         return SelectedBooks ;
     
     }
     
     
    boolean  AddNewBorrowerLibrarian(String borrower_name ,char gender  ,String tel_num ,String address, int clerk_id)
    {
        boolean result=false;
       for(int i=0; i < LibrarianList.size() ; i++)
       {
           Librarian L=LibrarianList.get(i);
           if(L.GetId()== clerk_id)
           {
              result= L.AddaBorrower(UsersList, borrower_name, gender, tel_num, address);
              result= true;
              break;
           
           }
          
       
       }
       return result;
        
    }
    
    
    
    Boolean LibrarianUpdatingInfo( String Info , int choice, int clerk_id, int user_id)
    {
    
        
         boolean result=false;
       for(int i=0; i < LibrarianList.size() ; i++)
       {
           Librarian L=LibrarianList.get(i);
           if(L.GetId()== clerk_id)
           {
              result= L.UpdatePerosnalInformation(UsersList , Info , choice, user_id);

              break;
           
           }
          
       
       }
       return result;
        
    
    }
     
    
    
    void LibrarianRecordFine(int user_id ,int clerk_id)
    {
    
          for(int i=0; i < LibrarianList.size() ; i++)
       {
           Librarian L=LibrarianList.get(i);
           if(L.GetId()== clerk_id)
           {
              L.PayFine(user_id, UsersList, LoanList);
           }
          
       
       }
      
        
      
    }
     
    
    
    
       void LibrarianCheckInItem(String ret_date, int book_id, int borrower_id, int lib_id) {

        for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L= LibrarianList.get(i);
            if (L.GetId() == lib_id) {
                for (int j = 0; j < UsersList.size(); j++) {
                    Users U = UsersList.get(j);
                    if (borrower_id == U.GetId()) {
                        L.CheckInItem(ret_date, book_id, U, BooksList, LoanList);

                    }

                }

            }

        }
    }
     
     
     
      void LibrarianCheckOutItem( int book_id, int borrower_id, int lib_id) {

        for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L1 = LibrarianList.get(i);
            if (L1.GetId() == lib_id) {
                for (int j = 0; j < UsersList.size(); j++) {
                    Users U = UsersList.get(j);
                    if (borrower_id == U.GetId()) {
                        int size=LoanList.size();
                        Loan L=LoanList.get(size-1);
                        int index=L.GetLoanId();
                        index=index+1;
                        Loan LoanObj= new Loan(index);
                        L1.CheckOutItem(book_id , U,BooksList ,LoanObj,  LoanList );

                    }

                }

            }

        }
    }
     
      
      
    void LibrarianRenewItem(int book_id ,int option ,int lib_id)  
    {
       
        for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L = LibrarianList.get(i);
            if (L.GetId() == lib_id) {
                L.RenewItem(book_id, BooksList, option);
            }

        }
    
    }
     
    void LibrarianAddNewBook( String NewAuthor ,String NewTitle ,String NewSubject , int quantity , int lib_id)
    {
          for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L = LibrarianList.get(i);
            if (L.GetId() == lib_id) {
                L.AddBook(BooksList ,NewAuthor , NewTitle , NewSubject ,  quantity);
            }

        }
    
    
    }
    
    
    boolean LibrarianDeleteBook(int book_id , int lib_id)
    {
        boolean result=false;
    
         for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L = LibrarianList.get(i);
            if (L.GetId() == lib_id) {
               result= L.DeleteBook(BooksList ,book_id);
            }

        }
    
         return result;
    }
  
    
    
    void LibrarianUpdateBookInfo(int book_id , int lib_id , String NewInfo, int newquantity, int command)
    {
    
    
        for (int i = 0; i < LibrarianList.size(); i++) {

            Librarian L = LibrarianList.get(i);
            if (L.GetId() == lib_id) {
                L.ChangeInfo(BooksList, book_id,command, NewInfo, newquantity);
            }

        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        dbConnectivity db = new dbConnectivity();
        Loan L = new Loan(6);
 


    }

}
