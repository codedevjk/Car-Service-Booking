import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { ProfileService } from 'src/app/core/services/profile.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  isSubmitting = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private profileService: ProfileService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    this.error = null;
    
    this.authService.register(this.registerForm.value).subscribe({
      next: () => {
        // Sync profile to user-service
        const userData = this.registerForm.value;
        this.profileService.updateProfile(userData.email, userData).subscribe({
          next: () => {
            this.isSubmitting = false;
            this.router.navigate(['/auth/login']);
          },
          error: (err) => {
            console.error('Profile sync failed', err);
            this.isSubmitting = false;
            this.router.navigate(['/auth/login']);
          }
        });
      },
      error: (err) => {
        this.error = 'Failed to register. Please try again.';
        this.isSubmitting = false;
      }
    });
  }
}
