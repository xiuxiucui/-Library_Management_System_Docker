package com.xiuxiucui.librarymanagementsystem.resoueces;

import com.xiuxiucui.librarymanagementsystem.exception.ErrorException;
import com.xiuxiucui.librarymanagementsystem.jpa.BookRepository;
import com.xiuxiucui.librarymanagementsystem.jpa.PersonRepository;
import com.xiuxiucui.librarymanagementsystem.jpa.RecordsRepository;
import com.xiuxiucui.librarymanagementsystem.library.Book;
import com.xiuxiucui.librarymanagementsystem.library.Person;
import com.xiuxiucui.librarymanagementsystem.library.Records;
import com.xiuxiucui.librarymanagementsystem.library.Response;
import helper.sha224Generator;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class LibraryJpaController {


    private BookRepository bookRepository;

    private PersonRepository personRepository;

    private RecordsRepository recordsRepository;

    public  LibraryJpaController(BookRepository bookRepository,PersonRepository personRepository,RecordsRepository recordsRepository){

        this.bookRepository=bookRepository;
        this.personRepository=personRepository;
        this.recordsRepository=recordsRepository;
    }



    @GetMapping("/search/ISBN13")
    public Book searchByISBN(@RequestParam("ISBN13") String ISBN){
        Optional <Book> books = bookRepository.findById(ISBN);

        if(books.isEmpty()){

            throw new ErrorException("no book found");
        }

        return  books.get();

    }


    @GetMapping("/search/keyword")
    public List<Book> searchByKeyword(@RequestParam("keyword") String keyword){
        List <Book> books = bookRepository.findAll();
        List<Book> filteredbooks= books.stream().filter(book->book.getTitle().contains(keyword)).collect(Collectors.toList());

        if(filteredbooks.isEmpty()){

            throw new ErrorException("no book found");
        }

        return filteredbooks;


    }
    @PostMapping("/borrow")
    public Response borrow_book(@RequestBody Map<String,Object> payload){


        try {
            String ISBN13 = payload.get("ISBN13").toString();
            String username = payload.get("username").toString();
            String password = payload.get("password").toString();
            System.out.println(ISBN13 + " "+ username + " "+password);

            Optional<Person> option_person = personRepository.findById(username);

            if(option_person.isEmpty()){

                return new Response("User "+username + " does not exists");
            }

            Person person = option_person.get();

            String hashedPassword = sha224Generator.encryptThisString(password);

            if ( !person.getSha224_password().equals(hashedPassword)){
                return new Response("Wrong password");
            }

            if(person.getUser_group().equals("librarian")){
                return new Response("User "+username + " is a librarian, only customer account can borrow book");
            }

            Optional<Book> option_book = bookRepository.findById(ISBN13);

            if(option_book.isEmpty()){
                return  new Response(ISBN13+ " is not in our database");
            }
            Book book=option_book.get();
            if (book.getAvailability()==0){
                return  new Response(ISBN13+ " stock is 0");
            }

            Records new_record= new Records("borrow",book,person,1,LocalDateTime.now(),"N");
            recordsRepository.save(new_record);

            int bookAvailability= book.getAvailability()-1;

            book.setAvailability(bookAvailability);
            bookRepository.save(book);

            return new Response("Record updated");


        }catch (Exception e){
            throw new ErrorException("input error");
        }


    }

    @PostMapping("/return")
    public Response return_book(@RequestBody Map<String,Object> payload){

        try {
            String ISBN13 = payload.get("ISBN13").toString();
            String username = payload.get("username").toString();
            String password = payload.get("password").toString();
            System.out.println(ISBN13 + " "+ username + " "+password);

            Optional<Person> option_person = personRepository.findById(username);

            if(option_person.isEmpty()){

                return new Response("User "+username + " does not exists");
            }

            Person person = option_person.get();
            System.out.println(person.getFirst_name()+" "+ person.getLast_name() + "1");

            String hashedPassword = sha224Generator.encryptThisString(password);

            if ( !person.getSha224_password().equals(hashedPassword)){
                return new Response("Wrong password");
            }
            System.out.println(person.getFirst_name()+" "+ person.getLast_name()+ "2");

            if(person.getUser_group().equals("librarian")){
                return new Response("User "+username + " is a librarian, only customer account can return book");
            }
            System.out.println(person.getFirst_name()+" "+ person.getLast_name()+ "3");


            Optional<Book> option_book = bookRepository.findById(ISBN13);

            if(option_book.isEmpty()){
                return  new Response(ISBN13+ " is not in our database");
            }
            System.out.println(person.getFirst_name()+" "+ person.getLast_name()+ "4");
            Book book=option_book.get();
            int bookAvailability1=book.getAvailability();
            if (book.getTotal()==bookAvailability1){
                return  new Response(ISBN13+ " stock at max, please return to the right library");
            }
            System.out.println(person.getFirst_name()+" "+ person.getLast_name()+ "5");

            List<Records> rental_records= recordsRepository.findAll();
            System.out.println(rental_records+"record 1");

            Optional<Records> final_records=rental_records.stream()
                    .filter(records->records.getPerson().getUsername()==username)
                    .filter(records->records.getBook().getISBN13()==ISBN13)
                    .filter(records->records.getReturn_status()=="N").findFirst();


            if (final_records.isEmpty()){
                return new Response("no rental record for book " + ISBN13 + " under user "+username);
            }

            Records record_to_update= final_records.get();
            record_to_update.setReturn_status("Y");
            recordsRepository.save(record_to_update);

            book.setAvailability(bookAvailability1+1);
            bookRepository.save(book);

            return new Response("Record updated");

        }catch (Exception e){
            throw new ErrorException("input error");
        }
    }

    @PostMapping("/add")
    public Response add_book(@RequestBody Map<String,Object> payload) {

        try {
            String ISBN13 = payload.get("ISBN13").toString();
            String username = payload.get("username").toString();
            String password = payload.get("password").toString();
            String title = payload.get("title").toString();
            String quantity = payload.get("quantity").toString();
            System.out.println(ISBN13+username+password+title+quantity);


            Optional<Person> option_person = personRepository.findById(username);

            if(option_person.isEmpty()){

                return new Response("User "+username + " does not exists");
            }

            Person person = option_person.get();


            String hashedPassword = sha224Generator.encryptThisString(password);

            if ( !person.getSha224_password().equals(hashedPassword)){
                return new Response("Wrong password");
            }


            if(person.getUser_group().equals("customer")){
                return new Response("User "+username + " is a customer, only customer account can add book");
            }


            Optional<Book> option_book = bookRepository.findById(ISBN13);
            if(option_book.isEmpty()){
                Book new_book= new Book(ISBN13,title,Integer.parseInt(quantity),Integer.parseInt(quantity));
                bookRepository.save(new_book);

                Records new_records= new Records("add",new_book,person,Integer.parseInt(quantity),LocalDateTime.now(),"NA");
                recordsRepository.save(new_records);
                return new Response("Newbook added. "+quantity+" copy of "+ ISBN13 +" added");
            }else{
                Book new_book=option_book.get();
                int new_total=new_book.getTotal()+Integer.parseInt(quantity);
                int new_availbility=new_book.getAvailability()+Integer.parseInt(quantity);

                new_book.setTotal(new_total);
                new_book.setAvailability(new_availbility);
                bookRepository.save(new_book);

                Records new_records= new Records("add",new_book,person,Integer.parseInt(quantity),LocalDateTime.now(),"NA");
                recordsRepository.save(new_records);
                return new Response("Stock topped up. "+quantity+" copy of "+ ISBN13 +" added");

            }







        }catch (Exception e){
            throw new ErrorException("input error");
        }

    }


}
