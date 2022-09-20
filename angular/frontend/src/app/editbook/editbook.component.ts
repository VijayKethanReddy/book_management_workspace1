import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import Book, { BookCategory } from '../entity/Book';
import { BookService } from '../services/bookservice/book.service';

@Component({
  selector: 'app-editbook',
  templateUrl: './editbook.component.html',
  styleUrls: ['./editbook.component.scss']
})
export class EditbookComponent implements OnInit {
  book1 : any;
  form: any;
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  successMessage: any = "";
  errorMessage: any = "";
  isSuccessful = false;
  
  constructor(public bookService: BookService) { 
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
    this.categoryList.push(this.bookCategory.COMEDY);
    this.categoryList.push(this.bookCategory.EPIC);
    this.categoryList.push(this.bookCategory.FANTASY);
    this.categoryList.push(this.bookCategory.FICTION);
    AppComponent.isInitialHome=false;
    this.book1 = this.bookService.book1;
    this.form = this.book1;
  }

  ngOnInit(): void {
  }

  edit(): void{
    console.log("clicked");
    this.book1 = this.form;
    const observable = this.bookService.editBook(this.book1);
    observable.subscribe((response)=>{
      console.log(response);
      if(Number.isFinite(Number(response))){
        if(Number(response) == 0){
          this.errorMessage = "Error occurred while updating the book. Please verify the details and update the book";
          this.successMessage = "";
        }
        else{
          this.successMessage = "Book "+this.book1.title+" is updated successfully";
          this.errorMessage = "";
        }
      }
      else{
        this.errorMessage = JSON.stringify(response);
        this.successMessage = "";
      }
    },
    (error)=>{
      console.log("error :",error);
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.errorMessage = "Error occurred while updating the book. Please verify the details and update the book";
      this.successMessage = "";
    })
  }

}
