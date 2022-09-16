import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  form: any = {
    name: null,
    userName: null,
    emailId: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { 
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { name, userName, emailId, password } = this.form;

    this.authService.register(name, userName, emailId, password).subscribe(
      response => {
        console.log(response);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error => {
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
