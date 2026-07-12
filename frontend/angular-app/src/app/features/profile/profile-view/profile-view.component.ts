import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileService } from 'src/app/core/services/profile.service';

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.css']
})
export class ProfileViewComponent implements OnInit {
  profileForm!: FormGroup;
  user: any = null;
  msg: string | null = null;
  isSubmitting = false;
  isLoading = true;

  constructor(private fb: FormBuilder, private profileService: ProfileService) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]]
    });

    const userStr = localStorage.getItem('user');
    let email = '';
    if (userStr) {
      email = JSON.parse(userStr).email;
    }

    this.profileService.getProfile(email).subscribe({
      next: (data) => {
        this.user = data;
        this.profileForm.patchValue(this.user);
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching profile', err);
        this.isLoading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.profileForm.invalid) {
      this.profileForm.markAllAsTouched();
      return;
    }
    this.isSubmitting = true;
    
    const userStr = localStorage.getItem('user');
    let email = '';
    if (userStr) {
      email = JSON.parse(userStr).email;
    }
    
    this.profileService.updateProfile(email, this.profileForm.value).subscribe({
      next: (updated) => {
        this.user = { ...this.user, ...updated };
        this.msg = 'Profile updated successfully';
        this.isSubmitting = false;
        setTimeout(() => this.msg = null, 2500);
      },
      error: (err) => {
        console.error('Error updating profile', err);
        this.isSubmitting = false;
      }
    });
  }
}
