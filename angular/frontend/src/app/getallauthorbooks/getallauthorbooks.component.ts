import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-getallauthorbooks',
  templateUrl: './getallauthorbooks.component.html',
  styleUrls: ['./getallauthorbooks.component.scss']
})
export class GetallauthorbooksComponent implements OnInit {
  books: any = [];
  message: any = "";
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Content', 'Active'];
  column: any ="";

  constructor(public bookService: BookService) { 
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
    this.getAllAuthorBooks();
  }

  getAllAuthorBooks(){
    console.log("clicked");
    const observable = this.bookService.getAllAuthorBooks();
    observable.subscribe((books)=>{
      console.log(books);
      this.books = books;
      this.message = "";
    },
    (error)=>{
      this.message = "No books found for author";
      this.books = [];
    })
  }

}
