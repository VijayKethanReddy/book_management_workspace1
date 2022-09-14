import { Component, OnInit } from '@angular/core';
import Book, { BookCategory } from 'src/app/entity/Book';
import { BookService } from 'src/app/services/bookservice/book.service';

@Component({
  selector: 'app-savebook',
  templateUrl: './savebook.component.html',
  styleUrls: ['./savebook.component.scss']
})
export class SavebookComponent implements OnInit {
  book:Book= new Book('book1_url', 'Book1', BookCategory.ADVENTURE, 1, '', '', 'ABC Publisher', new Date(), 'This is book1 content', true);
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  message: any = "";

  constructor(public bookService: BookService) { 
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
  }

  ngOnInit(): void {
  }

  save(){
    console.log("clicked");
    const observable = this.bookService.saveBook(this.book);
    observable.subscribe((response)=>{
      console.log(response);
      if(Number.isFinite(Number(response))){
        this.message = "Book "+this.book.title+" is saved successfully";
      }
      else{
        this.message = JSON.stringify(response);
      }
    },
    (error)=>{
      //alert('Something went wrong'+ error);
      console.log("error :",error);
      this.message = error;
      this.message = "Error occurred while saving the book. Please verify the details and save the book";
    })
  }
  
}
