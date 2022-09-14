import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthGuardService } from './services/auth-guard/auth-guard.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  get loggedIn () {
    return this.authGuardService.isLoggedIn;
  };

  constructor(private authGuardService: AuthGuardService, private router: Router) { }

  toggleLoggedIn() {
    if (this.authGuardService.isLoggedIn==true) 
    {
        alert("you are signing out");
    } 
    else
    {
      alert("you are signing in");
    }
    this.authGuardService.isLoggedIn = !this.authGuardService.isLoggedIn;
    this.router.navigate(['/bookform']);
  }
}
