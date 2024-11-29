import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../model/user.model';
import { NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,
    NgIf,
    RouterLink 
    

  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  public user = new User()
  confirmPassword?: string
  myForm!: FormGroup
  err!: any
  loading: boolean = false

  constructor(private formBuilder: FormBuilder, private authService: AuthService ,
    private router:Router
  ) { }
  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]], 
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    });
  }
  onRegister() {
    console.log(this.user);
this.loading=true;
this.authService.registerUser(this.user).subscribe({
  next:(res)=>{
    this.authService.setRegistredUser(this.user);
   
   this.loading=false;
   alert("veillez confirmer votre email");
    this.router.navigate(["/verifEmail"]);
      },
      error: (err: any) => {
        if (err.status = 400) {
          this.err = err.error.message;
        }
      }
    }
    )
  }


}