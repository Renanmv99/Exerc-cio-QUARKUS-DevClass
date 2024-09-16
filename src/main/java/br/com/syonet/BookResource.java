package br.com.syonet;

import java.util.List;

import io.quarkus.panache.common.Page;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/book")
public class BookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <Book> list(@QueryParam("size")Integer size, @QueryParam("page") Integer page) {
        if(page == null) page = 0;
        if(size == null) size = 2;
        
        return Book.findAll().page(new Page(page, size)).list();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Book create(Book book){
        book.persist();
        return book;

        //curl --location localhost:8080/book \
        //-d '{ "title": "4 vidas", "author": "desconhecido", "year": 2012 }' \
        //-H 'Content-Type: application/json'
    }

    @POST
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Book update(@PathParam ("id") Long id, Book updateBook){
        Book book = Book.findById(id);

        book.title = updateBook.title;
        book.author = updateBook.author;
        book.year = updateBook.year;

        book.persist();
        return book;

        //curl --location 'http://localhost:8080/book/1' \
        //-d '{ "title": "TESTE", "author": "JRR", "year": 1950 }' -H 'Content-Type: application/json'}
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id){
        Book book = Book.findById(id);
        book.delete();

        //curl --location --request DELETE 'http://localhost:8080/book/1'
    }

    @GET
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Book findById(@PathParam("id") Long id){
        Book book = Book.findById(id);
        return book;

        //curl --location "http://localhost:8080/book/1"
    }

    @GET
    @Path("/year/{year}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> findByYear(@PathParam("year") int year){
        return Book.find("year", year).list();

        //curl --location "http://localhost:8080/book/year/1940"
    }
}
